package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.RoleDAO;
import com.ayida.cms.entity.role.Role;
import com.ayida.common.mybatis.BaseDAO;

@Repository(value = "roleDao")
public class RoleDAOImpl extends BaseDAO<Role> implements RoleDAO
{
	private static final String ROLEDAO = "com.ayida.cms.dao.RoleDAO.";
	
	@Override
	protected String getMethodPath(String methodName)
	{
		return ROLEDAO + methodName;
	}

	@Override
	public Role save(Role bean)
	{
		return super.save(bean);
	}
	
	@Override
	public Role getRoleByRoleName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("rolename", name);
		return sqlSession.selectOne(getMethodPath("getRoleByRoleName"), map);
	}

	@Override
	public Role getRoleById(Integer id)
	{
		return get(getIdMap(id));
	}

	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}
	
	@Override
	public List<Role> getAll()
	{
		return super.getAll();
	}
}
