package com.ayida.common.mybatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(type = StatementHandler.class, args = {Connection.class}, method = "prepare")})
public class PageInterceptor implements Interceptor
{
	private static final Logger log = LoggerFactory
			.getLogger(PageInterceptor.class);

	/** 数据库类型--不同的数据库分页机制不同 **/
	private String dialect;

	public Object intercept(Invocation invocation) throws Throwable
	{
		if (!(invocation.getTarget() instanceof RoutingStatementHandler))
		{
			return invocation.proceed();
		}
		/** 获取RoutingStatementHandler 对象 **/
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		/** 通过反射获取RoutingStatementHandler 的delegate属性 **/
		StatementHandler delegate = (StatementHandler) ReflectUtil
				.getFieldValue(statementHandler, "delegate");
		/** 获取到绑定的sql：可以通过statementHandler获取，也可以通过delegate获取，结果是一样的 **/
		BoundSql boundSql = delegate.getBoundSql();
		/** 获取绑定sql中绑定的参数对象，即Mapper文件中的parameterType指定的参数 **/
		Object obj = boundSql.getParameterObject();
		/** 判断，当传入的参数是Pager类型，则判定要进行分页处理 **/
		if (obj instanceof Pager<?>)
		{
			Pager<?> page = (Pager<?>) obj;
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil
					.getFieldValue(delegate, "mappedStatement");
			/** 拦截到的prepare方法第一个参数是Connection **/
			Connection connection = (Connection) invocation.getArgs()[0];
			/** 获取当前要执行的sql语句 ，即配置文件中写的sql语句 **/
			String sql = boundSql.getSql();
			this.setTotalCount(page, mappedStatement, connection);
			String pageSql = this.getPageSql(page, sql);
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}
	@Override
	public Object plugin(Object target)
	{
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties)
	{
		this.dialect = properties.getProperty("dialect");
	}

	/**
	 * 获取分页SQL
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	private String getPageSql(Pager<?> page, String sql)
	{
		StringBuilder builder = new StringBuilder(sql);
		if ("mysql".equalsIgnoreCase(dialect))
		{
			return getMysqlPageSql(page, builder);
		}
		else if ("oracle".equalsIgnoreCase(dialect))
			;
		{
			return getOraclePageSql(page, builder);
		}
	}

	/**
	 * 获取Mysql分页sql Mysql 分页记录的位置是从0 开始的
	 * 
	 * @param page
	 * @param builder
	 * @return
	 */
	private String getMysqlPageSql(Pager<?> page, StringBuilder builder)
	{
		/** 计算 第一条记录的位置 **/
		int offSet = (page.getPageNo() - 1) * page.getPageSize();
		builder.append(" limit ").append(offSet).append(",")
				.append(page.getPageSize());
		return builder.toString();
	}

	/**
	 * 获取Oracle分页SQl oracle 分页关键字是rownum，默认从1 开始
	 * 
	 * @param page
	 * @param builder
	 * @return
	 */
	private String getOraclePageSql(Pager<?> page, StringBuilder builder)
	{
		int offSet = (page.getPageNo() - 1) * page.getPageSize() + 1;
		builder.insert(0, "select u.*, rownum r from (")
				.append(") u where rownum < ")
				.append(offSet + page.getPageSize());
		builder.insert(0, "select * from (").append(") where r >= ")
				.append(offSet);
		return builder.toString();
	}

	/**
	 * 总条数
	 * 
	 * @param page
	 * @param mappedStatement
	 * @param con
	 */
	private void setTotalCount(Pager<?> page, MappedStatement mappedStatement,
			Connection con)
	{
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(
				mappedStatement.getConfiguration(), countSql,
				parameterMappings, page);
		ParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, page, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = con.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				int totalCount = rs.getInt(1);
				page.setTotalCount(totalCount);
			}
		}
		catch (SQLException e)
		{
			log.error("");
		}
		finally
		{
			try
			{
				if (null != rs)
				{
					rs.close();
				}
				if (null != pstmt)
				{
					pstmt.close();
				}
			}
			catch (SQLException e)
			{
				log.error("");
			}
		}
	}

	/**
	 * 根据原sql语句，获得查询总记录数的sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql)
	{
		return "select count(*) from (" + sql + ") as c";
	}

	/**
	 * 利用反射进行操作的工具类
	 * 
	 * @author John Hu
	 *
	 */
	private static class ReflectUtil
	{
		/**
		 * 利用反射获取制定对象的指定属性
		 * 
		 * @param obj
		 * @param fieldName
		 * @return
		 */
		public static Object getFieldValue(Object obj, String fieldName)
		{
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (null != field)
			{
				field.setAccessible(true);
				try
				{
					result = field.get(obj);
				}
				catch (IllegalArgumentException e)
				{
					log.error("IllegalArgumentException in {PageInterceptor}:", e);
				}
				catch (IllegalAccessException e)
				{
					log.error("IllegalAccessException in {PageInterceptor}:", e);
				}
			}
			return result;
		}

		/**
		 * 获取指定对象的指定属性
		 * 
		 * @param obj
		 * @param fieldName
		 * @return
		 */
		private static Field getField(Object obj, String fieldName)
		{
			Field field = null;
			
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz
					.getSuperclass())
			{

				try
				{
					field = clazz.getDeclaredField(fieldName);
				}
				catch (Exception e)
				{
					log.error("");
				}
			}
			return field;
		}

		public static void setFieldValue(Object obj, String fieldName,
				String fieldValue)
		{
			Field field = getField(obj, fieldName);
			if (null != field)
			{
				field.setAccessible(true);
				try
				{
					field.set(obj, fieldValue);
				}
				catch (IllegalArgumentException e)
				{
					log.error("IllegalArgumentException in {PageInterceptor}:", e);
				}
				catch (IllegalAccessException e)
				{
					log.error("IllegalAccessException in {PageInterceptor}:", e);
				}
			}
		}
	}
}
