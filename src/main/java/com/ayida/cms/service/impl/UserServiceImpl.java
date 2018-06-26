package com.ayida.cms.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.UserDAO;
import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.RoleService;
import com.ayida.cms.service.UserService;
import com.ayida.common.util.MD5EncoderUtils;
import com.ayida.core.security.exception.BadCredentialsException;
import com.ayida.core.security.exception.UsernameNotFoundException;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService
{
	// private static final String DEFAULT_ROLE = "会员";

	@Autowired
	private UserDAO userDao;

	@Autowired
	private RoleService roleService;

	public int save(String username, String password, String phone, String ip)
	{
		User user = new User();
		user.setRegisterIp(ip);
		user.setRegisterTime(new Date());
		user.setUsername(phone);
		BigInteger phoneBig = BigInteger.valueOf(Long.valueOf(phone));
		user.setPhone(phoneBig);
		user.setPassword(MD5EncoderUtils.encodePassword(password));
		user.setDisabled(false);
		user.init();
		user = userDao.save(user);
		// Role role = roleService.getRoleByRoleName(DEFAULT_ROLE);
		// userDao.saveUserRole(user.getId(), role.getId());
		return user.getId();
	}

	@Override
	public User findById(Integer id)
	{
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll()
	{
		return userDao.findAll();
	}

	@Override
	public User findByUsername(String username)
	{
		return userDao.findByUsername(username);
	}

	@Override
	public int updateUserPassword(Integer userId, String newPassword)
	{
		String password = MD5EncoderUtils.encodePassword(newPassword);
		return userDao.updateUserPassword(userId, password);
	}

	@Override
	public int collection(Integer userId, Integer doctorId)
	{

		return userDao.collectionDoctor(userId, doctorId);
	}

	@Override
	public User findByPhone(String phone)
	{
		BigInteger phoneNum = BigInteger.valueOf(Long.valueOf(phone));
		return userDao.findByPhone(phoneNum);
	}

	@Override
	public User login(String username, String password, String ip)
	{
		User user = findByUsername(username);
		if (null == user)
		{
			throw new UsernameNotFoundException("username not found");
		}
		if (!MD5EncoderUtils.isPasswordValidate(user.getPassword(), password,
				null))
		{
			throw new BadCredentialsException("password invalid");
		}
		return updateLoginSucess(user.getId(), ip);
	}

	private User updateLoginSucess(Integer userId, String ip)
	{
		User user = findById(userId);
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ip);
		Date now = new Timestamp(System.currentTimeMillis());
		user.setLastLoginTime(now);
		updateUser(user);
		return user;
	}

	@Override
	public int updateUser(User user)
	{
		return userDao.updateUser(user);
	}

	@Override
	public int cancelCollection(Integer userId, Integer doctorId)
	{
		return userDao.cancelCollection(userId, doctorId);
	}

	@Override
	public void updateLoginInfo(Integer id, String ip)
	{
		User user = findById(id);
		if (null != user)
		{
			Date now = new Timestamp(System.currentTimeMillis());
			user.setLastLoginIp(ip);
			user.setLastLoginTime(now);
			user.setLoginCount(user.getLoginCount() + 1);
		}
		userDao.updateLoginInfo(user);
	}
}
