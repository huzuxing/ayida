package com.ayida.cms.entity.role;

import com.google.gson.annotations.Expose;

public class Permission implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer roleId;

	@Expose
	private String uri;

	public Integer getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri(String uri)
	{
		this.uri = uri;
	}

}
