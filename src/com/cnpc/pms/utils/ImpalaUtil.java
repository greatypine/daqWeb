package com.cnpc.pms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cnpc.pms.base.util.PropertiesUtil;

/**
 * impala 连接工具类
 * @author gbl
 *
 */
public class ImpalaUtil {
	
	
	
	public static List<Map<String,Object>> execute(String sql){
		
		Connection con = null;  
		ResultSet rs = null;
		Statement stat=null;
        List<Map<String,Object>> list = null;
        PreparedStatement ps = null;
        try {  
        	
            
            con = JdbcConPoolC3po.getConnection();
            System.out.println("\n== Begin Query Results ======================");  
            //ps = con.prepareStatement(sql);
            stat = con.createStatement();
            //rs = ps.executeQuery();
            rs = stat.executeQuery(sql);
            list = convertList(rs);
           
            System.out.println("== End Query Results =======================\n\n");  
   
        } catch (SQLException e) {  
            e.printStackTrace();  
            return list;
        } catch (Exception e) {  
            e.printStackTrace(); 
            return list;
        } finally {  
            try {  
            	JdbcConPoolC3po.close(con, stat, rs);
            } catch (Exception e) {  
                e.printStackTrace();
                return list;
            }  
        }
        return list;
    }
	
	
	private static List<Map<String,Object>> convertList(ResultSet rs) throws SQLException{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while (rs.next()) {
		Map rowData = new HashMap();//声明Map
		for (int i = 1; i <= columnCount; i++) {
		rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
		}
		list.add(rowData);
		}
		return list;
	}




    // set the impalad host
    private static final String IMPALAD_HOST = "10.16.31.192";

    // port 21050 is the default impalad JDBC port
    private static final String IMPALAD_JDBC_PORT = "21050";

    private static final String CONNECTION_URL = "jdbc:impala://" + IMPALAD_HOST + ':' + IMPALAD_JDBC_PORT + "/gemini;auth=noSasl";

    private static final String JDBC_DRIVER_NAME = "com.cloudera.impala.jdbc41.Driver";

    public static List<Map<String,Object>> test(String sql) {

        System.out.println("\n=============================================");
        System.out.println("Cloudera Impala JDBC Example");
        System.out.println("Using Connection URL: " + CONNECTION_URL);

        List<Map<String,Object>> list = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            Class.forName(JDBC_DRIVER_NAME);

            con = DriverManager.getConnection(CONNECTION_URL);

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            list = convertList(rs);
            System.out.println("\n== Begin Query Results ======================");

            
            System.out.println("== End Query Results =======================\n\n");

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        } finally {
            try {
                con.close();
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
	
}
