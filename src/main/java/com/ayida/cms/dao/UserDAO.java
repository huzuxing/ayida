package com.ayida.cms.dao;

import java.math.BigInteger;
import java.util.List;

import com.ayida.cms.entity.user.User;
import com.ayida.common.mybatis.Pager;

public interface UserDAO
{

	public User save(User user);

	public User findById(Integer id);

	public List<User> findAll();
	
	public List<User> getPagerByList(Pager<User> page);
	
	public User findByUsername(String username);

	public int updateUserPassword(Integer userId, String newPassword);

	public int collectionDoctor(Integer userId, Integer doctorId);
	
	public int cancelCollection(Integer userId, Integer doctorId);

	public User findByPhone(BigInteger phone);
	
	public void saveUserRole(Integer userId, Integer roleId);
	
	public int updateUser(User user);
	
	public void updateLoginInfo(User user);
}
