package com.ayida.cms.entity.user;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ayida.cms.entity.role.Role;
import com.google.gson.annotations.Expose;

public class User extends BaseUser
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private BigInteger phone;

	@Expose
	private String registerIp;

	@Expose
	private Date registerTime;

	public String getRegisterIp()
	{
		return registerIp;
	}

	public void setRegisterIp(String registerIp)
	{
		this.registerIp = registerIp;
	}

	public Date getRegisterTime()
	{
		return registerTime;
	}

	public void setRegisterTime(Date registerTime)
	{
		this.registerTime = registerTime;
	}

	/**
	 * @return the phone
	 */
	public BigInteger getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(BigInteger phone)
	{
		this.phone = phone;
	}

	/**
	 * 获取权限集
	 * 
	 * @return
	 */
	public Set<String> getPerms()
	{
		if (isDisabled())
		{
			return null;
		}
		Set<Role> roles = getRoles();
		if (null == roles)
		{
			return null;
		}
		Set<String> perms = new HashSet<String>();
		// 利用Java8 forEach,处理权限集问题
		roles.forEach((role) -> role.getPerms().forEach(
				(perm) -> perms.add(perm.getUri())));
		return perms;
	}

	/**
	 * 初始化
	 */
	public void init()
	{
		if (null == getLoginCount())
		{
			setLoginCount(0);
		}
	}
}
