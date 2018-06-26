package com.ayida.cms.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.role.Role;
import com.ayida.cms.entity.user.AdminUser;
import com.ayida.cms.service.AdminUserService;
import com.ayida.cms.service.RoleService;
import com.ayida.common.util.ResponseUtils;
import com.ayida.core.web.util.FrontUtils;

@Controller
@RequestMapping(value = "/ayida/admin/user/")
public class AdminUserAct
{
	private static final Logger log = LoggerFactory
			.getLogger(AdminUserAct.class);

	private static final String STATUS = "status";

	private static final Integer EXIST = 1;

	private static final Integer NOT_EXTIST = 0;

	private static final String ADD = "admin/user/add";

	private static final String LIST = "admin/user/list";

	private static final String UPDATE = "admin/user/update";

	@Autowired
	private AdminUserService adminUserService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model)
	{
		List<Role> roles = roleService.getAll();
		model.addAttribute("roles", roles);
		FrontUtils.frontData(request, model);
		return ADD;
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String username,
			String password, Integer[] roleIds, boolean isDisabled)
	{
		AdminUser user = adminUserService.save(username, password,isDisabled);
		if (null != roleIds && roleIds.length > 0)
		{
			for (Integer roleId : roleIds)
				adminUserService.saveRole(user.getId(), roleId);
		}
		return LIST;
	}

	/**
	 * 校验用户名
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param username
	 */
	@RequestMapping(value = "validateUsername.do", method = RequestMethod.POST)
	public void validate(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String username)
	{
		AdminUser user = adminUserService.findByUsername(username);
		JSONObject obj = new JSONObject();
		if (null == user)
		{
			obj.put(STATUS, EXIST);
		}
		else
		{
			obj.put(STATUS, NOT_EXTIST);
		}
		ResponseUtils.renderJson(response, obj.toString());
	}
}
