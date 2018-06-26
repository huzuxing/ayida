package com.ayida.common.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * session 提供者
 * @author John Hu
 *
 */
public interface SessionProvider
{
	public Serializable getAttribute(HttpServletRequest request, String name);
	
	public void setAttribute(HttpServletRequest request, 
			HttpServletResponse response, String name, Serializable value);
	
	public String getSessionId(HttpServletRequest request, HttpServletResponse respsonse);
	
	public void logOut(HttpServletRequest request, HttpServletResponse respsonse);
}
