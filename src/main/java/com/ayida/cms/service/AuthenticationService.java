package com.ayida.cms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ayida.cms.entity.authc.Authentication;
import com.ayida.common.web.SessionProvider;

public interface AuthenticationService
{
	public void save(Authentication authc);
	
	public Authentication login(String username, String password, String phone,
			HttpServletResponse response, HttpServletRequest request, 
			SessionProvider session);

	public Authentication findById(Integer id);

	public List<Authentication> findAll();

	public int deleteOneAuthentication(Integer id);

	public Authentication findByUsername(String username);
}
