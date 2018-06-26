package com.ayida.cms.action.admin.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.user.AdminUser;
import com.ayida.cms.service.AdminUserService;
import com.ayida.core.web.util.FrontUtils;

@Controller
@RequestMapping(value = "/ayida/admin/")
public class AdminLoginAct
{
	private static final Logger log = LoggerFactory
			.getLogger(AdminLoginAct.class);

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String adminLogin(String username, String password,
			HttpServletRequest request, Model model)
	{
		FrontUtils.frontData(request, model);
		return "admin/login";
	}

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model,
			String adminUsername, String adminPassword)
	{
		AdminUser user = adminUserService.findByUsername(adminUsername);
		if (user.isDisabled() || !user.isAdmin())
		{
			return "redirect:login.do";
		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(adminUsername,
				adminPassword);
		try
		{
			subject.login(token);
		}
		catch (AuthenticationException e)
		{
			log.error("Login Failed : AuthenticationException", e);
			return "redirect:login.do";
		}
		FrontUtils.frontData(request, model);
		return "redirect:index.do";
	}

	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model,
			String username, String password)
	{
		FrontUtils.frontData(request, model);
		return "admin/index";
	}
}
