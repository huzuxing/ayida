package com.ayida.cms.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.UserDAO;
import com.ayida.cms.entity.user.User;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;
@Repository(value = "userDao")
public class UserDAOImpl extends BaseDAO<User> implements UserDAO
{
	private static final String USERDAO = "com.ayida.cms.dao.UserDAO.";

	@Override
	public User save(User user)
	{
		return super.save(user);
	}

	@Override
	public User findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public List<User> findAll()
	{
		return findAll();
	}

	@Override
	public User findByUsername(String username)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		return sqlSession.selectOne(getMethodPath("findByUsername"), map);
	}

	@Override
	public int updateUserPassword(Integer userId, String newPassword)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		map.put("password", newPassword);
		return sqlSession.update(getMethodPath("updatePassword"), map);
	}

	@Override
	public int collectionDoctor(Integer userId, Integer doctorId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("doctorId", doctorId);
		return sqlSession.insert(getMethodPath("collectionDoctor"), map);
	}

	@Override
	public User findByPhone(BigInteger phone)
	{
		Map<String, BigInteger> map = new HashMap<String, BigInteger>();
		map.put("phone", phone);
		return sqlSession.selectOne(getMethodPath("findByPhone"), map);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return USERDAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

	@Override
	public void saveUserRole(Integer userId, Integer roleId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("roleId", roleId);
		sqlSession.insert(getMethodPath("saveUserRole"), map);
	}

	@Override
	public int updateUser(User user)
	{
		Map<String, User> map = new HashMap<String, User>();
		map.put("user", user);
		return update(map);
	}

	@Override
	public int cancelCollection(Integer userId, Integer doctorId)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("doctorId", doctorId);
		return sqlSession.insert(getMethodPath("cancelConnection"), map);
	}

	@Override
	public void updateLoginInfo(User user)
	{
		Map<String, User> map = new HashMap<String, User>();
		map.put("bean", user);
		sqlSession.update(getMethodPath("updateLoginInfo"), map);
	}

	@Override
	public List<User> getPagerByList(Pager<User> page)
	{
		return super.getPagerList(page);
	}
}
