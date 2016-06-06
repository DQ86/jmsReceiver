package gov.jslt.taxcore.taxblh.jms;

import gov.jslt.taxcore.taxblh.comm.Helper;
import gov.jslt.taxcore.taxbpo.JMSBwxxBPO;
import gov.jslt.taxevent.comm.JMSBwxxVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ctp.core.blh.BaseBizLogicHandler;
import com.ctp.core.event.RequestEvent;
import com.ctp.core.event.ResponseEvent;
import com.ctp.core.exception.TaxBaseBizException;

public class JMSBLH extends BaseBizLogicHandler {

	protected ResponseEvent performTask(RequestEvent reqEvent, Connection conn)
			throws SQLException, TaxBaseBizException {
		if ("saveBw".equals(reqEvent.getDealMethod())) {
			return saveBw(reqEvent, conn);
		} else if ("updateBw".equals(reqEvent.getDealMethod())) {
			return updateBw(reqEvent, conn);
		}
		return null;
	}

	protected ResponseEvent saveBw(RequestEvent req, Connection conn)
			throws SQLException, TaxBaseBizException {
		ResponseEvent reEvent = new ResponseEvent();
		JMSBwxxVO vo = (JMSBwxxVO) req.reqMapParam.get("JMSBwxxVO");
		vo.setSjly("0");
		vo.setUuid(Helper.getGUID(conn));
		JMSBwxxBPO.insert(conn, vo);
		PreparedStatement pt = conn
				.prepareStatement("INSERT INTO T_JMS_BWXX@WSBS SELECT * FROM T_JMS_BWXX WHERE UUID=?");
		pt.setString(1, vo.getUuid());
		pt.execute();
		pt.close();
		reEvent.respMapParam.put("JMSBwxxVO", vo);
		reEvent.respMapParam.put("sucessMsg", "保存报文信息成功!");
		return reEvent;
	}

	protected ResponseEvent updateBw(RequestEvent req, Connection conn)
			throws SQLException, TaxBaseBizException {
		ResponseEvent reEvent = new ResponseEvent();
		JMSBwxxVO vo = (JMSBwxxVO) req.reqMapParam.get("JMSBwxxVO");
		JMSBwxxBPO.deleteByPK(conn, vo.getUuid());
		vo.setSjly("0");
		JMSBwxxBPO.insert(conn, vo);

		PreparedStatement pt = conn
				.prepareStatement("DELETE FROM T_JMS_BWXX@WSBS  WHERE UUID=?");
		pt.setString(1, vo.getUuid());
		pt.execute();

		pt = conn
				.prepareStatement("INSERT INTO T_JMS_BWXX@WSBS SELECT * FROM T_JMS_BWXX WHERE UUID=?");
		pt.setString(1, vo.getUuid());
		pt.execute();
		pt.close();

		reEvent.respMapParam.put("sucessMsg", "更新报文信息成功!");
		return reEvent;
	}

	protected ResponseEvent validateData(RequestEvent arg0, Connection arg1)
			throws Exception {
		return null;
	}

}
