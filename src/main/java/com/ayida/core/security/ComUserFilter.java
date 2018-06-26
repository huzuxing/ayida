package com.ayida.core.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

public class ComUserFilter extends UserFilter
{
	@Override
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String loginUrl;
		//后台登陆，跳转到后台登陆地址，前台则跳转到shiro配置的登录地址
		if (req.getRequestURI().startsWith(req.getContextPath() + getAdminPrefix()))
		{
			loginUrl = getAdminLogin();
		}
		else loginUrl = getLoginUrl();
		WebUtils.issueRedirect(req, res, loginUrl);
	}

	private String adminPrefix;
	
	private String adminLogin;

	public String getAdminPrefix()
	{
		return adminPrefix;
	}

	public void setAdminPrefix(String adminPrefix)
	{
		this.adminPrefix = adminPrefix;
	}

	public String getAdminLogin()
	{
		return adminLogin;
	}

	public void setAdminLogin(String adminLogin)
	{
		this.adminLogin = adminLogin;
	}
}
