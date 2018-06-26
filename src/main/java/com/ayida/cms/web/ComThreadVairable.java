package com.ayida.cms.web;

import com.ayida.cms.entity.user.User;

/**
 * 线程变量
 * @author John Hu
 *
 */
public class ComThreadVairable
{
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<User> comUserVariable = new ThreadLocal<User>();

	/**
	 * 获得当前用户
	 * @return
	 */
	public static User getUser()
	{
		return comUserVariable.get();
	}
	
	/**
	 * 设置当前用户
	 * @param user
	 */
	public static void setUser(User user) 
	{
		comUserVariable.set(user);
	}
	
	/**
	 * 移除当前用户
	 */
	public static void removeUser()
	{
		comUserVariable.remove();
	}
}
