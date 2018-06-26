package com.ayida.cms.entity.user;

import java.util.Date;
import java.util.Set;

import com.google.gson.annotations.Expose;

public class BaseUser implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	private Integer id;
	
	@Expose
	private String username;
	
	@Expose
	private String password;
	
	@Expose
	private String lastLoginIp;
	
	@Expose
	private Date lastLoginTime;
	
	@Expose
	private Integer loginCount;
	
	@Expose
	private boolean isDisabled;
	
	@Expose
	private Date updateTime;
	
	@Expose
	private boolean isAdmin;
	
	@Expose
	private Set<com.ayida.cms.entity.role.Role> roles; 

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp()
	{
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp)
	{
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime()
	{
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the loginCount
	 */
	public Integer getLoginCount()
	{
		return loginCount;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Integer loginCount)
	{
		this.loginCount = loginCount;
	}

	public boolean isDisabled()
	{
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled)
	{
		this.isDisabled = isDisabled;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Set<com.ayida.cms.entity.role.Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<com.ayida.cms.entity.role.Role> roles)
	{
		this.roles = roles;
	}
}
