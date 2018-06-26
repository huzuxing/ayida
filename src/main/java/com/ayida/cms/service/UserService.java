package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.user.User;

public interface UserService
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public int save(String username, String password, String phone, String ip);

	public User findById(Integer id);

	public List<User> findAll();

	public User findByUsername(String username);
	
	public int updateUserPassword(Integer userId, String newPassword);
	
	public int collection(Integer userId, Integer doctorId);
	
	public int cancelCollection(Integer userId, Integer doctorId);
	
	public User findByPhone(String phone);
	
	public User login(String username, String password, String ip);
	
	public int updateUser(User user);
	
	public void updateLoginInfo(Integer id, String ip);
}
