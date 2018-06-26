package com.ayida.cms.entity.department;

import com.google.gson.annotations.Expose;

public class Department implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/****/
	@Expose
	private Integer id;

	/** 科室名 **/
	@Expose
	private String name;

	/** 科室介绍 **/
	@Expose
	private String description;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
