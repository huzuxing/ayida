package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.RoleDAO;
import com.ayida.cms.entity.role.Role;
import com.ayida.cms.service.RoleService;

@Service(value = "roleService")
@Transactional
public class RoleServiceImpl implements RoleService
{	
	@Autowired
	private RoleDAO roleDao;
	
	@Override
	public Integer save(Role bean)
	{
		roleDao.save(bean);
		return bean.getId();
	}

	@Override
	public Role getRoleByRoleName(String name)
	{
		return roleDao.getRoleByRoleName(name);
	}

	@Override
	public Role getRoleById(Integer id)
	{
		return roleDao.getRoleById(id);
	}

	@Override
	public List<Role> getAll()
	{
		return roleDao.getAll();
	}

}
