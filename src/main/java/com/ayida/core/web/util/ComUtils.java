package com.ayida.core.web.util;

import javax.servlet.http.HttpServletRequest;

import com.ayida.cms.entity.user.User;

public class ComUtils
{
	/**
	 * 用户key
	 */
	private static final String USER_KEY = "_user_key";
	
	/**
	 * 获得用户
	 * @param request
	 * @return
	 */
	public static User  getUser(HttpServletRequest request)
	{
		return (User)request.getAttribute(USER_KEY);
	}
	
	/**
	 * 设置用户
	 * @param request
	 * @param user
	 */
	public static void setUser(HttpServletRequest request, User user)
	{
		request.setAttribute(USER_KEY, user);
	}
	
	/**
	 * 获得用户ID
	 * @param request
	 * @return
	 */
	public static Integer getUserId(HttpServletRequest request)
	{
		User user = getUser(request);
		if (null != user)
		{
			return user.getId();
		}
		return null;
	}
}
