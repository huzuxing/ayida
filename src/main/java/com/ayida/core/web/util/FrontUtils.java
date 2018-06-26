package com.ayida.core.web.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.ayida.cms.entity.user.User;
import com.ayida.common.util.RequestUtils;

public class FrontUtils
{
	/**
	 * 部署路径
	 */
	private static final String BASE = "base";

	public static void frontData(HttpServletRequest request, Model model)
	{
		String baseName = request.getServletContext().getContextPath();
		baseName = baseName == null ? "" : baseName;
		User user = ComUtils.getUser(request);
		String location = RequestUtils.getLocation(request);
		model.addAttribute("location", location);
		model.addAttribute(BASE, baseName);
		model.addAttribute("user", user);
	}
}
