package cn.jdbcutil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 依赖：
 * 	   + c3p0-config.xml --配置文件！记得创建！
 * 	   + c3p0-0.9.2-pre1.jar
 * 	   + mchange-commons-0.2.jar
 * 版本1.3 
 * 更新日期：2015/2/23
 * @author Administrator
 *
 */
public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static Connection con = null;
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();//以线程为集合。保存相应的连接对象
	
	/**
	 * 获取连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		con = tl.get();
		if(con != null){return con;}
		return dataSource.getConnection();
	}
	/**
	 * 设置ComboPooledDataSource的构造函数的参数值
	 * 方便使用c3p0-config.xml的命名参数 
	 */
	public static Connection getConnection(String dataSourceParameter) throws SQLException{
		con = tl.get();
		if(con != null){return con;}
		dataSource = new ComboPooledDataSource(dataSourceParameter);
		return dataSource.getConnection();
	}
	/**
	 * 获取连接池对象
	 * @return
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void beginTransaction() throws SQLException{
		con = tl.get();
		if(con != null){throw new RuntimeException("事务已经开启！不能重复开启！");}
		con = getConnection();
		con.setAutoCommit(false);
		tl.set(con);
	}
	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public static void commitTransaction() throws SQLException{
		con = tl.get();
		if(con == null){throw new RuntimeException("事务还没开启！不能提交！");}
		con.commit();
		con.close();//归还连接对象到连接池
		tl.remove();//移除连接对象con。那么tl.get() == null
	}
	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public static void rollbackTransaction() throws SQLException{
		con = tl.get();
		if(con == null){throw new RuntimeException("事务还没开启！不能回滚！");}
		con.rollback();
		con.close();
		tl.remove();
	}
	/**
	 * 关闭 非事务专用的连接
	 * @param connection
	 * @throws SQLException
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		con = tl.get();//获取事务专用连接
		if(con == null){connection.close();}
		if(con != connection){connection.close();}
	} 
}
