package cn.itcast.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * JDBC工具类:
 * 	* 加载驱动
 *  * 获得连接
 *  * 释放资源
 */
public class JDBCUtils {
	static final String JDBC_DRIVER ="com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/chatroom?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
	static final String USER = "root";
	static final String PASS = "951106";
	public static Connection getConnection(){
		
		Connection conn=null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(SQLException ex)
		{
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
