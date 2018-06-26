package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.AuthenticationDAO;
import com.ayida.cms.entity.authc.Authentication;
import com.ayida.common.mybatis.BaseDAO;
@Repository(value = "authcDao")
public class AuthenticationDAOImpl extends BaseDAO<Authentication> implements AuthenticationDAO
{
	private static final String AUTHENTICATION_DAO = "com.ayida.cms.dao.AuthenticationDAO.";

	@Override
	public Authentication login(Authentication bean)
	{
		return save(bean);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return AUTHENTICATION_DAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public Authentication findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public List<Authentication> findAll()
	{
		return getAll();
	}

	@Override
	public int deleteOneAuthentication(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public Authentication findByUsername(String username)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		return sqlSession.selectOne(getMethodPath("findByUsername"), map);
	}

	@Override
	public void saveAuthentication(Authentication authc)
	{
		save(authc);
	}

}
