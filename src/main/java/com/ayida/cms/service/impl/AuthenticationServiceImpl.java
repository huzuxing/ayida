package com.ayida.cms.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.AuthenticationDAO;
import com.ayida.cms.entity.authc.Authentication;
import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.AuthenticationService;
import com.ayida.cms.service.UserService;
import com.ayida.common.util.RequestUtils;
import com.ayida.common.web.SessionProvider;

@Service(value = "authcService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService
{
	public static final String AUTH_KEY = "auth_key";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationDAO authcDao;
	
	@Override
	public Authentication login(String username, String password, String phone,
			HttpServletResponse response, HttpServletRequest request, 
			SessionProvider session)
	{
		String ip = RequestUtils.getIpAddr(request);
		User user = userService.login(username, password, 
				ip);
		Authentication authc = new Authentication();
		authc.setLoginIp(ip);
		authc.setLoginTime(user.getLastLoginTime());
		authc.setUserId(user.getId());
		authc.setUsername(username);
		save(authc);
		session.setAttribute(request, response, AUTH_KEY, authc.getId());
		return authc;
	}

	@Override
	public Authentication findById(Integer id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authentication> findAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteOneAuthentication(Integer id)
	{
		return authcDao.deleteOneAuthentication(id);
	}

	@Override
	public Authentication findByUsername(String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Authentication authc)
	{
		authcDao.saveAuthentication(authc);
	}

}
