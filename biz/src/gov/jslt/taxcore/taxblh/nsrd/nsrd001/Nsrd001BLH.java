package gov.jslt.taxcore.taxblh.nsrd.nsrd001;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;
import sun.jdbc.rowset.CachedRowSet;

import com.ctp.core.blh.BaseBizLogicHandler;
import com.ctp.core.bpo.QueryBPO;
import com.ctp.core.commutils.StringUtils;
import com.ctp.core.event.RequestEvent;
import com.ctp.core.event.ResponseEvent;
import com.ctp.core.exception.TaxBaseBizException;

public class Nsrd001BLH extends BaseBizLogicHandler {

	@Override
	protected ResponseEvent performTask(RequestEvent reqEvent, Connection conn)
			throws SQLException, TaxBaseBizException {
		String swglm = (String) reqEvent.reqMapParam.get("swglm");
		ResponseEvent resEvent = new ResponseEvent();
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, String> tmpMap = null;
		// sql参数
		ArrayList<String> sqlParams = new ArrayList<String>();
		sqlParams.add(swglm);
		// 用户基本信息
		Map<String, String> jbXxMap = null;
		String sql = "SELECT T.SWGLM,T.NSRSBM,T.NSR_MC,A.MC_J DJZCLX_MC,B.MC_J GBHY_MC,T.ZCDZ,(SELECT NVL(XYPDDJ, '')"
				+ " FROM T_XYDJ_NSXYPDSHMD C WHERE C.SWGLM = T.SWGLM AND PJND = TO_CHAR(SYSDATE, 'YYYY')) XYDJ"
				+ " FROM T_DJ_JGNSR T, T_DM_GY_DJZCLX A, T_DM_GY_HYZXL B WHERE T.DJZCLX_DM = A.DJZCLX_DM AND T.GBHY_DM = B.HYZXL_DM AND T.SWGLM = ?";
		CachedRowSet rs = QueryBPO.findAll(conn, sql, sqlParams);
		if (rs.next()) {
			jbXxMap = new HashMap<String, String>();
			jbXxMap.put("swglm", rs.getString("SWGLM"));
			jbXxMap.put("nsrSbm", rs.getString("NSRSBM"));
			jbXxMap.put("nsrMc", rs.getString("NSR_MC"));
			jbXxMap.put("djzclxMc", rs.getString("DJZCLX_MC"));
			jbXxMap.put("gbhyMc", rs.getString("GBHY_MC"));
			jbXxMap.put("zcDz", rs.getString("ZCDZ"));
			jbXxMap.put("xyDj",
					StringUtils.stringNullRep(rs.getString("XYDJ"), ""));
		}
		resMap.put("jbXx", jbXxMap);
		// 税费记录
		List<Map<String, String>> sFXxList = new ArrayList<Map<String, String>>();
		sql = "SELECT B.SWGLM,B.NSRSBM,B.NSR_MC,TO_CHAR(T.SFSSQ_QSRQ, 'YYYY') SSND,T.ZSXM_DM SZ,SUM(T.SJ_JE) RKSE FROM T_ZS_JKMX T, T_DJ_JGNSR B"
				+ " WHERE T.SWGLM = B.SWGLM AND T.RK_RQ IS NOT NULL AND T.SFSSQ_QSRQ BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'YYYY'), -24) AND SYSDATE"
				+ " AND T.SWGLM = ? GROUP BY TO_CHAR(T.SFSSQ_QSRQ, 'YYYY'),B.SWGLM,B.NSRSBM,B.NSR_MC,T.ZSXM_DM ORDER BY TO_CHAR(T.SFSSQ_QSRQ, 'YYYY'), T.ZSXM_DM";
		rs = QueryBPO.findAll(conn, sql, sqlParams);
		while (rs.next()) {
			tmpMap = new HashMap<String, String>();
			tmpMap.put("swglm", rs.getString("SWGLM"));
			tmpMap.put("nsrSbm", rs.getString("NSRSBM"));
			tmpMap.put("nsrMc", rs.getString("NSR_MC"));
			tmpMap.put("ssNd", rs.getString("SSND"));
			tmpMap.put("sz", rs.getString("SZ"));
			tmpMap.put("rkSe", rs.getString("RKSE"));
			sFXxList.add(tmpMap);
		}
		resMap.put("sfXx", sFXxList);
		// 社保费
		List<Map<String, String>> sbfXxList = new ArrayList<Map<String, String>>();
		sql = "SELECT B.SWGLM,B.NSRSBM,B.NSR_MC,TO_CHAR(T.SFSSQ_QSRQ, 'YYYY') SSND,T.SBZSPM_DM XZ,SUM(T.SJ_JE) SJJE FROM T_ZS_JKMXSBF T, T_DJ_JGNSR B"
				+ " WHERE T.SWGLM = B.SWGLM AND T.RK_RQ IS NOT NULL AND T.SFSSQ_QSRQ BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'YYYY'), -24) AND SYSDATE "
				+ " AND T.SWGLM = ? GROUP BY TO_CHAR(T.SFSSQ_QSRQ, 'YYYY'),B.SWGLM,B.NSRSBM,B.NSR_MC,T.SBZSPM_DM ORDER BY TO_CHAR(T.SFSSQ_QSRQ, 'YYYY'), T.SBZSPM_DM";
		rs = QueryBPO.findAll(conn, sql, sqlParams);
		while (rs.next()) {
			tmpMap = new HashMap<String, String>();
			tmpMap.put("swglm", rs.getString("SWGLM"));
			tmpMap.put("nsrSbm", rs.getString("NSRSBM"));
			tmpMap.put("nsrMc", rs.getString("NSR_MC"));
			tmpMap.put("ssNd", rs.getString("SSND"));
			tmpMap.put("xz", rs.getString("XZ"));
			tmpMap.put("sjJe", rs.getString("SJJE"));
			sbfXxList.add(tmpMap);
		}
		resMap.put("sbfXx", sbfXxList);
		// 财务报表信息
		List<Map<String, String>> cwXxList = new ArrayList<Map<String, String>>();
		sql = "SELECT B.SWGLM,\n"
				+ "       B.NSRSBM,\n"
				+ "       B.NSR_MC,\n"
				+ "       TO_CHAR(TO_DATE(T.YF, 'YYYY-MM'), 'YYYY') SSND,\n"
				+ "       SUM(NVL(T.ZCHJQMS, 0)) ZCZE,\n"
				+ "       SUM(NVL(T.FZHJQMS, 0)) FZZE,\n"
				+ "       SUM(NVL(T.SSZBQMS, 0)) SSZB,\n"
				+ "       SUM(NVL(T.ZBGJQMS, 0)) ZBGJ,\n"
				+ "       SUM(NVL(A.ZYYWSRLJS, 0)) ZYYWSR,\n"
				+ "       SUM(NVL(A.ZYYWCBLJS, 0)) ZYYWCB,\n"
				+ "       SUM(NVL(A.JLRLJS, 0)) KJLR\n"
				+ "  FROM F_CW_ZCFZ T, F_CW_LRB A, T_DJ_JGNSR B\n"
				+ " WHERE T.SWGLM = A.SWGLM\n"
				+ "   AND T.YF = A.YF\n"
				+ "   AND T.SWGLM = B.SWGLM\n"
				+ "   AND TO_DATE(T.YF, 'YYYY-MM') BETWEEN\n"
				+ "       ADD_MONTHS(TRUNC(SYSDATE, 'YYYY'), -24) AND SYSDATE\n"
				+ "   AND T.SJZT = '0'\n" + "   AND A.SJZT = '0'\n"
				+ "   AND T.SWGLM = ?\n" + " GROUP BY B.SWGLM,\n"
				+ "          B.NSRSBM,\n" + "          B.NSR_MC,\n"
				+ "          TO_CHAR(TO_DATE(T.YF, 'YYYY-MM'), 'YYYY')\n"
				+ " ORDER BY SSND";
		rs = QueryBPO.findAll(conn, sql, sqlParams);
		while (rs.next()) {
			tmpMap = new HashMap<String, String>();
			tmpMap.put("swglm", rs.getString("SWGLM"));
			tmpMap.put("nsrSbm", rs.getString("NSRSBM"));
			tmpMap.put("nsrMc", rs.getString("NSR_MC"));
			tmpMap.put("ssNd", rs.getString("SSND"));
			tmpMap.put("zcZe", rs.getString("ZCZE"));
			tmpMap.put("fzZe", rs.getString("FZZE"));
			tmpMap.put("ssZb", rs.getString("SSZB"));
			tmpMap.put("zbGj", rs.getString("ZBGJ"));
			tmpMap.put("zyywSr", rs.getString("ZYYWSR"));
			tmpMap.put("zyywCb", rs.getString("ZYYWCB"));
			tmpMap.put("kjLr", rs.getString("KJLR"));
			cwXxList.add(tmpMap);
		}
		resMap.put("cwXx", cwXxList);
		// 处罚信息
		sqlParams.add(swglm);
		List<Map<String, String>> cfXxList = new ArrayList<Map<String, String>>();
		sql = "SELECT *\n"
				+ "  FROM (SELECT B.SWGLM,\n"
				+ "               B.NSRSBM,\n"
				+ "               B.NSR_MC,\n"
				+ "               WSZGH CFJDWH,\n"
				+ "               WFSS CFSY,\n"
				+ "               TO_CHAR(T.ZZ_RQ, 'YYYY-MM-DD') CFRQ,\n"
				+ "               TO_CHAR(A.RK_RQ, 'YYYY-MM-DD') ZXWCRQ,\n"
				+ "               A.FK_JE CFJE\n"
				+ "          FROM T_FG_CF_SWXZCFJDS T, T_FG_CF_CFMX A, T_DJ_JGNSR B\n"
				+ "         WHERE T.AJBH = A.AJBH\n"
				+ "           AND T.SWGLM = B.SWGLM\n"
				+ "           AND A.RK_RQ BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'YYYY'), -24) AND\n"
				+ "               SYSDATE\n"
				+ "           AND T.ZF_BJ = '0'\n"
				+ "           AND SPTYBJ = '1'\n"
				+ "           AND T.SWGLM = ?\n"
				+ "        UNION ALL\n"
				+ "        SELECT T.SWGLM,\n"
				+ "               T.NSRSBM,\n"
				+ "               T.NSR_MC,\n"
				+ "               DECODE(T3.WSZG,\n"
				+ "                      NULL,\n"
				+ "                      '',\n"
				+ "                      T3.WSJG || T3.WSZG || '〔' || T3.WSNH || '〕' || T3.WSWH || '号') CFJDWH,\n"
				+ "               T1.WFSS CFSY,\n"
				+ "               TO_CHAR(T1.XG_SJ, 'YYYY-MM-DD') CFRQ,\n"
				+ "               TO_CHAR(T.ZXJZ_RQ, 'YYYY-MM-DD') ZXWCRQ,\n"
				+ "               T.CBFK CFJE\n"
				+ "          FROM T_JC_AJK       T,\n"
				+ "               T_JC_SWXZCFJDS T1,\n"
				+ "               T_DJ_JGNSRFB   T2,\n"
				+ "               T_WS_SSWSJBXX  T3\n"
				+ "         WHERE T.AJ_BH = T1.AJ_BH\n"
				+ "           AND T.SWGLM = T2.SWGLM\n"
				+ "           AND T1.WSH = T3.WSH\n"
				+ "           AND T.CABZ = '0'\n"
				+ "           AND T1.ZF_BJ = '0'\n"
				+ "           AND T.SSJCJG_DM LIKE '23212%'\n"
				+ "           AND T.ZXJZ_RQ BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'YYYY'), -24) AND\n"
				+ "               SYSDATE\n" + "           AND T.SWGLM = ?)\n"
				+ " ORDER BY CFRQ";
		rs = QueryBPO.findAll(conn, sql, sqlParams);
		while (rs.next()) {
			tmpMap = new HashMap<String, String>();
			tmpMap.put("swglm", rs.getString("SWGLM"));
			tmpMap.put("nsrSbm", rs.getString("NSRSBM"));
			tmpMap.put("nsrMc", rs.getString("NSR_MC"));
			tmpMap.put("cfjdWh", rs.getString("CFJDWH"));
			tmpMap.put("cfSy", rs.getString("CFSY"));
			tmpMap.put("cfRq", rs.getString("CFRQ"));
			tmpMap.put("zswcRq", rs.getString("ZXWCRQ"));
			tmpMap.put("cfJe", rs.getString("CFJE"));
			cfXxList.add(tmpMap);
		}
		resMap.put("cfXx", cfXxList);

		resEvent.respMapParam.put("content", JSONSerializer.toJSON(resMap)
				.toString());
		return resEvent;
	}

	@Override
	protected ResponseEvent validateData(RequestEvent arg0, Connection arg1)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
