package com.ayida.core.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ayida.cms.entity.user.AdminUser;
import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.AdminUserService;
import com.ayida.cms.service.UserService;
import com.ayida.common.util.RequestUtils;

public class ComAuthenticationFilter extends FormAuthenticationFilter
{
	@Autowired
	private UserService userService;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 返回URL
	 */
	private static final String RETURN_URL = "returnUrl";

	private static final Logger log = LoggerFactory
			.getLogger(ComAuthenticationFilter.class);

	/**
	 * 登录成功
	 * 
	 * @param token
	 * @param adminLogin
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private boolean onLoginSuccess(AuthenticationToken token,
			boolean adminLogin, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) subject.getPrincipal();
		String ip = RequestUtils.getIpAddr(req);
		if (adminLogin)
		{
			AdminUser user = adminUserService.findByUsername(username);
			adminUserService.updateLoginInfo(user.getId(), ip);
			// 日志记录--待做

		}
		else
		{
			User user = userService.findByUsername(username);
			userService.updateLoginInfo(user.getId(), ip);
		}
		return super.onLoginSuccess(token, subject, request, response);
	}

	private boolean onLoginFailure(AuthenticationToken token,
			boolean adminLogin, AuthenticationException e,
			ServletRequest request, ServletResponse response)
	{
		// String username = (String) token.getPrincipal();
		return super.onLoginFailure(token, e, request, response);
	}

	@Override
	protected boolean isLoginRequest(ServletRequest request,
			ServletResponse response)
	{
		return pathsMatch(getLoginUrl(), request)
				|| pathsMatch(getAdminLogin(), request);
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String successUrl = req.getParameter(RETURN_URL);
		if (StringUtils.isBlank(successUrl))
		{
			if (req.getRequestURI().startsWith(
					req.getContextPath() + getAdminLogin()))
			{
				// 后台直接返回首页
				successUrl = getAdminIndex();
				// 清楚SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				WebUtils.issueRedirect(request, response, successUrl, null,
						true);
				return;
			}
			else
			{
				successUrl = getSuccessUrl();
			}
		}
		WebUtils.redirectToSavedRequest(req, res, successUrl);
	}

	@Override
	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception
	{
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		// 登录跳转
		if (isAllowed && isLoginRequest(request, response))
		{
			try
			{
				issueSuccessRedirect(request, response);
			}
			catch (Exception e)
			{
				log.error("", e);
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response);
	}

	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception
	{
		AuthenticationToken token = createToken(request, response);
		if (null == token)
		{
			throw new IllegalStateException("create AuthenticationToken error");
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) token.getPrincipal();
		boolean adminLogin = false;
		if (req.getRequestURI().startsWith(
				req.getContextPath() + getAdminLogin()))
		{
			adminLogin = true;
		}
		if (isDisabledUser(username))
		{
			return onLoginFailure(token, adminLogin, new DisabledException(),
					req, res);
		}
		try
		{
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, adminLogin, subject, request, response);
		}
		catch (AuthenticationException e)
		{
			return onLoginFailure(token, e, request, response);
		}
	}

	private boolean isDisabledUser(String username)
	{
		User user = userService.findByUsername(username);
		if (null == user)
		{
			return false;
		}
		if (user.isDisabled())
			return true;
		else
			return false;
	}

	private String adminPrefix;

	private String adminIndex;

	private String adminLogin;

	public String getAdminPrefix()
	{
		return adminPrefix;
	}

	public void setAdminPrefix(String adminPrefix)
	{
		this.adminPrefix = adminPrefix;
	}

	public String getAdminIndex()
	{
		return adminIndex;
	}

	public void setAdminIndex(String adminIndex)
	{
		this.adminIndex = adminIndex;
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
