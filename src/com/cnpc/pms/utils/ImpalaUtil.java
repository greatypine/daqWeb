package com.cnpc.pms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
        PreparedStatement ps = null;
        List<Map<String,Object>> list = null;
        try {  
        	
            
            con = JdbcConPoolC3po.getConnection();
   
            ps = con.prepareStatement(sql);
            
            System.out.println("\n== Begin Query Results ======================");  
            
            rs = ps.executeQuery();  
            
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
            	JdbcConPoolC3po.close(con, ps, rs);
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
	
}
