package gov.jslt.taxcore.taxblh.comm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import sun.jdbc.rowset.CachedRowSet;

import com.ctp.core.bpo.QueryCssBPO;

public class Helper {
	public static String getGUID(Connection con) throws SQLException {
		String guid = "";
		String getGuidSql = "select  F_XT_GET_GUID() as uuid from dual";
		CachedRowSet rs = QueryCssBPO.findAll(con, getGuidSql, new ArrayList<Object>());
		if (rs.next()) {
			guid = rs.getString("uuid");
		}
		return guid;
	}
}
