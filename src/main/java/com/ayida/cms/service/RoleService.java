package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.role.Role;

public interface RoleService
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public Integer save(Role bean);
	
	public Role getRoleByRoleName(String name);
	
	public Role getRoleById(Integer id);
	
	public List<Role> getAll();
}
