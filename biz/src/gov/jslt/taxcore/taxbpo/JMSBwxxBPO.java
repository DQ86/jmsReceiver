package gov.jslt.taxcore.taxbpo;
import gov.jslt.taxevent.comm.JMSBwxxVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ctp.core.bpo.CssBaseBPOWithLOB;
import com.ctp.core.exception.TaxBaseBizException;

public class JMSBwxxBPO extends CssBaseBPOWithLOB {

public JMSBwxxBPO() {
super();
}

private static final String TABLENAME = "T_JMS_BWXX";

private static  JMSBwxxVO loadConcreteBPO(ResultSet rs) throws SQLException  ,TaxBaseBizException{
 		JMSBwxxVO    vo = new JMSBwxxVO()	 ;
     	vo.setBizkey(rs.getString("BIZKEY"));
     	vo.setBwlxdm(rs.getString("BWLX_DM"));
     	vo.setCwrz(readCLOB(rs,"CWRZ"));
     	vo.setDjxh(rs.getString("DJXH"));
     	vo.setFhbw(readCLOB(rs,"FHBW"));
     	vo.setFsbw(readCLOB(rs,"FSBW"));
     	vo.setSjly(rs.getString("SJLY"));
     	vo.setSwglm(String.valueOf(rs.getLong("SWGLM")));
     	vo.setUuid(rs.getString("UUID"));
     	vo.setZtdm(rs.getString("ZT_DM"));
		vo.setStatus(new HashMap());
		return vo;

}

/**
*  返回一条记录的自定义查询;
 * @param 
* @param con:Connection 与数据库建立的连接
  @param String tablename 表
  @param String sqlOrder 排序字段
  @param String sqlWhere 条件 sql
  @param ArrayList sqlParams 条件数据
  @param boolean isCount false
* @throws SQLException
* 修改历史： 
       版本0.5 2008-4-24周红江 :  1.将原来的findAll废弃  因为其存在内存浪费，2.不再使用 CachedRowSet  3.在子类中申明ResultSet,PreparedStatement 在子类中释放;
*/

private static JMSBwxxVO queryByWhere(Connection con,String tablename,String sqlOrder,String sqlWhere,ArrayList sqlParams,boolean isCount) throws SQLException ,TaxBaseBizException {

	ResultSet result=null;
	PreparedStatement ps=null;
	result = findAllByWhere(con,tablename,sqlOrder,  sqlWhere, sqlParams,isCount,result,ps);

	JMSBwxxVO vo = null;
	if (result != null) {
		    if(result.next())
    		vo = loadConcreteBPO(result);
     	} 
	closeDbsession(result,ps);;
	return vo;
}


/**
*  返回多条记录的自定义查询;
 * @param 
* @param con:Connection 与数据库建立的连接
  @param String tablename 表
  @param String sqlOrder 排序字段
  @param String sqlWhere 条件 sql
  @param ArrayList sqlParams 条件数据
  @param boolean isCount false
* @throws SQLException
* 修改历史：
		 版本0.5 2008-4-24周红江 :  1.将原来的findAll废弃 因为其存在内存浪费 ，2.不再使用 CachedRowSet  3.在子类中申明ResultSet,PreparedStatement 在子类中释放;
*/

private static ArrayList queryByZdyWhere(Connection con,String tablename,String sqlOrder,String sqlWhere,ArrayList sqlParams,boolean isCount) throws SQLException  ,TaxBaseBizException{

	ResultSet result=null;
	PreparedStatement ps=null;
	result = findAllByWhere(con,tablename,sqlOrder,  sqlWhere, sqlParams,isCount,result,ps);

	ArrayList listLodadata=null;
	if (result != null) {
 			listLodadata = new ArrayList();
 			while(result.next())
				listLodadata.add(loadConcreteBPO(result));
	} 
	closeDbsession(result,ps);
	return listLodadata;
}

////////////////////////////////////////以下为业 务方法 //////////////////////////////////////////////////////////////

/**
*  增加一条新记录;
 * @param 
* @param con:Connection 与数据库建立的连接
* @throws SQLException
*/

public static boolean insert(Connection con , JMSBwxxVO vo)throws SQLException  ,TaxBaseBizException{
	 //1.保存非大对象信息
	 boolean result=false ;
	 result=  insert(  con ,TABLENAME  , vo) ;

	 String strLobSqlWhere ="UUID= '"+vo.getUuid()+"' ";
    //2.保存大对象信息
	 saveLOBS(vo,TABLENAME ,strLobSqlWhere,con) ;
	 return result;
}

/**
*  根据主键条件删除一条记录
* @param 
* @param con:Connection 与数据库建立的连接
* @throws SQLException
*/
public static boolean deleteByPK(Connection con,String UUID) throws SQLException {
	String strSqlWhere="UUID=? ";
	ArrayList sqlParams=new ArrayList();
 	sqlParams.add(UUID);

	return delete(con,TABLENAME ,strSqlWhere,sqlParams)  ;
}

/**
*   根据主键条件查询一条记录
* @param 
* @param con:Connection 与数据库建立的连接
* @throws SQLException
*/
public static JMSBwxxVO queryByPK(Connection con,String UUID) throws SQLException ,TaxBaseBizException {
	String strSqlWhere="UUID=? ";
	ArrayList sqlParams=new ArrayList();
 	sqlParams.add(UUID);

	return  queryByWhere(con,TABLENAME ,null,strSqlWhere,sqlParams,false) ;
}

/**
*    根据主键条件更新一条记录
* @param 
* @param con:Connection 与数据库建立的连接
* @throws SQLException
*/
 public static boolean updateByPK(Connection con,String UUID, JMSBwxxVO vo) throws SQLException ,TaxBaseBizException {
	String strSqlWhere="UUID=? ";
	ArrayList sqlParams=new ArrayList();
 	sqlParams.add(UUID);

	//保存大对象信息
	boolean result=false;
	 result= update(con,TABLENAME ,strSqlWhere,sqlParams,vo) ;
	String strPkSqlWhereValue="UUID='"+UUID+"'";
	 saveLOBS(vo,TABLENAME ,strPkSqlWhereValue,con) ;
	 return result;
}
////////////////////////////////////////以下为【自定义部分】/////////////////////////////////////////////////////////
}

