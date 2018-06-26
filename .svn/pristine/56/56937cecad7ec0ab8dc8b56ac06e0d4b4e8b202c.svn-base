package com.ayida.common.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionProvider implements SessionProvider
{

	@Override
	public Serializable getAttribute(HttpServletRequest request, String name)
	{
		HttpSession session = request.getSession(false);
		if (null != session)
		{
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	@Override
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value)
	{
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	@Override
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse respsonse)
	{
		return request.getSession().getId();
	}

	@Override
	public void logOut(HttpServletRequest request, HttpServletResponse respsonse)
	{
		HttpSession session = request.getSession(false);
		if (null != session)
		{
			session.invalidate();
		}
	}

}
