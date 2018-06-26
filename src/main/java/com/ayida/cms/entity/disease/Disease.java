package com.ayida.cms.entity.disease;

import com.google.gson.annotations.Expose;

public class Disease implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer id;

	@Expose
	private String typeId;

	@Expose
	private String name;

	public String getTypeId()
	{
		return typeId;
	}

	public void setTypeId(String typeId)
	{
		this.typeId = typeId;
	}

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

}
