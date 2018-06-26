package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.AdminUserDAO;
import com.ayida.cms.entity.user.AdminUser;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;

@Repository(value = "adminUserDao")
public class AdminUserDAOImpl extends BaseDAO<AdminUser>
		implements
			AdminUserDAO
{
	private static final String ADMINDAO = "com.ayida.cms.dao.AdminUserDAO.";
	@Override
	public AdminUser save(AdminUser bean)
	{
		return super.save(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public int update(AdminUser bean)
	{
		Map<String, AdminUser> map = new HashMap<String, AdminUser>();
		map.put("bean", bean);
		return update(map);
	}

	@Override
	public AdminUser findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public AdminUser findByUsername(String username)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		return sqlSession.selectOne(getMethodPath("findByUsername"), map);
	}

	@Override
	public List<AdminUser> getByPager(Pager<AdminUser> page)
	{
		return getPagerList(page);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return ADMINDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public void saveRole(Integer userId, Integer roleId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("roleId", roleId);
		sqlSession.insert(getMethodPath("saveRole"), map);
	}

	@Override
	public void updateLoginInfo(AdminUser bean)
	{
		Map<String, AdminUser> map = new HashMap<String, AdminUser>();
		map.put("bean", bean);
		sqlSession.update(getMethodPath("updateLoginInfo"), map);
	}

}
