package cn.jdbcutil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
	//已经被淘汰使用！替换为JdbcUtils.java
	/**
	 * jdbc工具类
	 * 	依赖：
	 * 		一个配置文件：dbconfig.properties
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException{
		/* 
		 * 1. 获取配置文件
		 * 2. 注册驱动类
		 * 3. 获取Connection
		 */
		//能够获取得到在src目录下的文件，并把该文件的字节流返回
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties");
		Properties proper = new Properties();//配置文件对象
		proper.load(in);//把该文件加载给了proper对象
		
		Class.forName(proper.getProperty("driverClassName"));
		return DriverManager.getConnection(proper.getProperty("url"),
				proper.getProperty("username"),
				proper.getProperty("password"));
	}
	
}
