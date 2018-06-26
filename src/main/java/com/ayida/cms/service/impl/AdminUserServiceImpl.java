package com.ayida.cms.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.AdminUserDAO;
import com.ayida.cms.entity.user.AdminUser;
import com.ayida.cms.service.AdminUserService;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.MD5EncoderUtils;

@Service(value = "adminUserService")
@Transactional
public class AdminUserServiceImpl implements AdminUserService
{
	@Autowired
	private AdminUserDAO adminUserDao;

	@Override
	public AdminUser save(String username, String password, boolean isDisabled)
	{
		AdminUser bean = new AdminUser();
		bean.setUsername(username);
		bean.setPassword(MD5EncoderUtils.encodePassword(password));
		bean.setAdmin(true);
		bean.setDisabled(isDisabled);
		bean.init();
		return adminUserDao.save(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return adminUserDao.deleteById(id);
	}

	@Override
	public int update(AdminUser bean)
	{
		return adminUserDao.update(bean);
	}

	@Override
	public AdminUser findById(Integer id)
	{
		return adminUserDao.findById(id);
	}

	@Override
	public AdminUser findByUsername(String username)
	{
		return adminUserDao.findByUsername(username);
	}

	@Override
	public List<AdminUser> getByPager(Pager<AdminUser> page)
	{
		return adminUserDao.getByPager(page);
	}

	@Override
	public void saveRole(Integer userId, Integer roleId)
	{
		adminUserDao.saveRole(userId, roleId);
	}

	@Override
	public void updateLoginInfo(Integer id, String ip)
	{
		Date now = new Timestamp(System.currentTimeMillis());
		AdminUser user = findById(id);
		if (null != user)
		{
			user.setLastLoginIp(ip);
			user.setLastLoginTime(now);
			user.setLoginCount(user.getLoginCount() + 1);
		}
		adminUserDao.updateLoginInfo(user);
	}

}
