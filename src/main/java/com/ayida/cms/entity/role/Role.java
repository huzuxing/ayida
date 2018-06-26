package com.ayida.cms.entity.role;

import java.util.Set;

import com.google.gson.annotations.Expose;

public class Role implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer id;

	@Expose
	private String name;

	@Expose
	private Integer isSuper;

	@Expose
	private Set<com.ayida.cms.entity.role.Permission> perms;

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	public Integer getIsSuper()
	{
		return isSuper;
	}

	public void setIsSuper(Integer isSuper)
	{
		this.isSuper = isSuper;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the perms
	 */
	public Set<com.ayida.cms.entity.role.Permission> getPerms()
	{
		return perms;
	}

	/**
	 * @param perms
	 *            the perms to set
	 */
	public void setPerms(Set<com.ayida.cms.entity.role.Permission> perms)
	{
		this.perms = perms;
	}
}
