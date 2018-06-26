package com.ayida.cms.entity.doctor;

import java.util.Set;

import com.ayida.cms.entity.disease.Disease;
import com.google.gson.annotations.Expose;

public class SubProfessional implements java.io.Serializable
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
	private Set<Disease> diseases;

	public Set<Disease> getDiseases()
	{
		return diseases;
	}

	public void setDiseases(Set<Disease> diseases)
	{
		this.diseases = diseases;
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
