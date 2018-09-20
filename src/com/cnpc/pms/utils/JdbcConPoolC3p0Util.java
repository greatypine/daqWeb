package com.cnpc.pms.utils;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * impala-jdbc 连接池
 * @author gbl
 *
 */
public class JdbcConPoolC3p0Util {
	private static ComboPooledDataSource ds = null;
	 
	//在静态代码块中创建数据库连接池
	 
	static{
	 
		try{
		 
			//通过代码创建C3P0数据库连接池
			 
			ds= new ComboPooledDataSource();
			 
			ds.setDriverClass("com.cloudera.impala.jdbc41.Driver");
			 
			String impala_host = PropertiesUtil.getValue("impala_guoan_host");
			String impala_port = PropertiesUtil.getValue("impala_guoan_port");
			String impala_user =  PropertiesUtil.getValue("impala_guoan_user");
			String impala_password = PropertiesUtil.getValue("impala_guoan_password");
	        String connectionUrl = "jdbc:impala://" + impala_host + ':' + impala_port + "/daqWeb;auth=noSasl";
	            
			ds.setJdbcUrl(connectionUrl);
			/*ds.setUser("");
			 
			ds.setPassword("");*/
			 
			ds.setInitialPoolSize(10);
			 
			ds.setMinPoolSize(5);
			 
			ds.setMaxPoolSize(20);
		 
		}catch (Exception e) {
		 
			e.printStackTrace();
		 
		}
	 
	}
	 
	 
	 
	//从数据源中获取数据库连接
	public static Connection getConnection() throws SQLException {
		Logger logger = LoggerFactory.getLogger(JdbcConPoolC3p0Util.class);
		try {
			logger.info("Connection jdbcUrl is [{}]", ds.getJdbcUrl());
			logger.info("Connection use is [{}]", ds.getNumBusyConnections());
			logger.info("Connection all  is [{}]", ds.getNumConnections());
			logger.info("Connection idle is [{}]", ds.getNumIdleConnections());
		}catch (Exception e) {
			logger.info("Exception is {}",e.getMessage());
			e.printStackTrace();

		}
		return ds.getConnection();
	 
	}
	 
	 
	 
	//关闭数据库连接
	 
	public static void close(Connection conn, Statement st, ResultSet rs) {
	 
		if(rs != null) {
		 
			try{
			 
				//关闭存储查询结果的ResultSet对象
				rs.close();
			 
			}catch (Exception e) {
			 
				e.printStackTrace();
			 
			}
			 
			rs= null;
		 
		}
	 
		if(st != null) {
		 
			try{
			 
				//关闭负责执行SQL命令的Statement对象
				st.close();
			 
			}catch (Exception e) {
			 
				e.printStackTrace();
			 
			}
		 
		}
	 
	 
	 
		if(conn != null) {
		 
			try{
			 
				//将Connection连接对象还给数据库连接池
				conn.close();
			 
			}catch (Exception e) {
			 
				e.printStackTrace();
			 
			}
		 
		}
	}
}
