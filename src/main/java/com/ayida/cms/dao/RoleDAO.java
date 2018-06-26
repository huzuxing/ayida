package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.role.Role;

public interface RoleDAO
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public Role save(Role bean);
	
	public Role getRoleByRoleName(String name);
	
	public Role getRoleById(Integer id);
	
	public List<Role> getAll();
}
