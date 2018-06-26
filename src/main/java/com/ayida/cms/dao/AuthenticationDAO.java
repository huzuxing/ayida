package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.authc.Authentication;

public interface AuthenticationDAO
{
	public void saveAuthentication(Authentication authc);
	
	public Authentication login(Authentication bean);
	
	public Authentication findById(Integer id);
	
	public List<Authentication> findAll();
	
	public int deleteOneAuthentication(Integer id);
	
	public Authentication findByUsername(String username);
}
