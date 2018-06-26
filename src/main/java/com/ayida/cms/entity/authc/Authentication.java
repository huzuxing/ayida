package com.ayida.cms.entity.authc;

import java.util.Date;

public class Authentication implements java.io.Serializable
{

	/**
	 * 认证基本信息，用于保存用户每次登录的基本信息
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer userId;

	private String username;

	private String loginIp;

	private Date loginTime;

	public Authentication()
	{

	}

	public Authentication(Integer id, Integer userId, String username,
			String loginIp, Date loginTime)
	{
		super();
		this.id = id;
		this.userId = userId;
		this.username = username;
		this.loginIp = loginIp;
		this.loginTime = loginTime;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getLoginIp()
	{
		return loginIp;
	}

	public void setLoginIp(String loginIp)
	{
		this.loginIp = loginIp;
	}

	public Date getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(Date loginTime)
	{
		this.loginTime = loginTime;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
