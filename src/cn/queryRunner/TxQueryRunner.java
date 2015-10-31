package cn.queryRunner;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.jdbcutil.JdbcUtils;
/**
 * 依赖：
 *    + commons-dbutils.jar-->QueryRunner所依赖的
 *    + JdbcUtils.java(依赖以下) 
 * 	  	  + c3p0-config.xml(配置文件！)
 * 	 	  + c3p0-0.9.2-pre1.jar
 * 	 	  + mchange-commons-0.2.jar
 * 本类主要修改：把所有方法中没有传入Connection参数的方法，进行修改。
 * 		给这这些没有传入Connection参数的方法内部添加了Connection con = JdbcUtils.getConnection()
 * 		跟jdbcutils.releaseConnection(con);
 * 如何使用：以后进行数据操作，不需要自己创建连接，直接给出SQL模版、参数数组、ResultSetHandler<>就可以直接使用以下相应的方法。
 * 
 * 
 * 更新日期：2015/2/23
 * @author Administrator
 *
 */
public class TxQueryRunner extends QueryRunner{
	
	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int[] result = super.batch(con, sql, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params)
			throws SQLException {
		Connection con = JdbcUtils.getConnection();
		T result = super.query(con, sql, rsh,params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		T result = super.query(con, sql, rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int result = super.update(con, sql, params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object param) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int result = super.update(con, sql, param);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql) throws SQLException {
		Connection con = JdbcUtils.getConnection();
		int result = super.update(con, sql);
		JdbcUtils.releaseConnection(con);
		return result;
	}
	
}
