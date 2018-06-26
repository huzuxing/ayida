package com.ayida.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.UserService;
import com.ayida.core.web.util.ComUtils;

/**
 * 上下文信息 包括登录信息、权限信息
 * 
 * @author John Hu
 *
 */
public class ContextInterceptor extends HandlerInterceptorAdapter
{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered())
		{
			String username = (String) subject.getPrincipal();
			User user = userService.findByUsername(username);
			ComUtils.setUser(request, user);
			ComThreadVairable.setUser(user);
		}
		return true;
	}

	@Autowired
	private UserService userService;
}
