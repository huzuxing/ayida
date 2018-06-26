package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.user.AdminUser;
import com.ayida.common.mybatis.Pager;

public interface AdminUserService
{
	public AdminUser save(String username, String password, boolean isDisabled);

	public int deleteById(Integer id);

	public int update(AdminUser bean);

	public AdminUser findById(Integer id);

	public AdminUser findByUsername(String username);

	public List<AdminUser> getByPager(Pager<AdminUser> page);
	
	public void saveRole(Integer userId, Integer roleId);
	
	public void updateLoginInfo(Integer id, String ip);
}
