package com.ayida.core.security;

import static com.ayida.cms.service.impl.AuthenticationServiceImpl.AUTH_KEY;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.ayida.cms.service.AuthenticationService;
import com.ayida.common.web.SessionProvider;

public class ComLogoutFilter extends LogoutFilter
{

	/**
	 * 返回URL
	 */
	private static final String RETURN_URL = "returnUrl";

	@Override
	protected String getRedirectUrl(ServletRequest request,
			ServletResponse response, Subject subject)
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response;
		String redirectUrl = req.getParameter(RETURN_URL);
		if (StringUtils.isBlank(redirectUrl))
		{
			if (req.getRequestURI().startsWith(
					req.getContextPath() + getAdminPrefix()))
			{
				redirectUrl = getAdminLogin();
			}
			else
			{
				redirectUrl = getRedirectUrl();
			}
		}
		Integer authId = (Integer) session.getAttribute(req, AUTH_KEY);
		authcService.deleteOneAuthentication(authId);
		session.logOut(req, res);
		return redirectUrl;
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
	
	@Autowired
	private SessionProvider session;
	
	@Autowired
	private AuthenticationService authcService;
}
