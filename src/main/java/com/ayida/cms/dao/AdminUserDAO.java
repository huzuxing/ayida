package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.user.AdminUser;
import com.ayida.common.mybatis.Pager;

public interface AdminUserDAO
{
	public AdminUser save(AdminUser bean);
	
	public int deleteById(Integer id);
	
	public int update(AdminUser bean);
	
	public AdminUser findById(Integer id);
	
	public AdminUser findByUsername(String username);
	
	public List<AdminUser> getByPager(Pager<AdminUser> page);
	
	public void saveRole(Integer userId, Integer roleId);
	
	public void updateLoginInfo(AdminUser bean);
}
