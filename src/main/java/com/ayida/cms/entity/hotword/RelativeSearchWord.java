package com.ayida.cms.entity.hotword;

import com.google.gson.annotations.Expose;

public class RelativeSearchWord implements java.io.Serializable
{
	/**
	 * 查询联词实体类
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer relativeId;

	@Expose
	private Integer id;

	@Expose
	private String doctorName;

	@Expose
	private String diseaseName;

	@Expose
	private String doctorNameInitial;

	@Expose
	private String diseaseNameInitial;

	@Expose
	private String cityName;

	@Expose
	private Integer hit;

	@Expose
	private String hospitalBelongings;

	// 以下两个属性不需要入库，仅作Lucene创建索引时用
	private Integer professionalTitles;
	
	private Integer degree;

	public Integer getDegree()
	{
		return degree;
	}

	public void setDegree(Integer degree)
	{
		this.degree = degree;
	}

	public String getHospitalBelongings()
	{
		return hospitalBelongings;
	}

	public void setHospitalBelongings(String hospitalBelongings)
	{
		this.hospitalBelongings = hospitalBelongings;
	}

	public Integer getProfessionalTitles()
	{
		return professionalTitles;
	}

	public void setProfessionalTitles(Integer professionalTitles)
	{
		this.professionalTitles = professionalTitles;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public Integer getRelativeId()
	{
		return relativeId;
	}

	public void setRelativeId(Integer relativeId)
	{
		this.relativeId = relativeId;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getDoctorName()
	{
		return doctorName;
	}

	public void setDoctorName(String doctorName)
	{
		this.doctorName = doctorName;
	}

	public String getDiseaseName()
	{
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName)
	{
		this.diseaseName = diseaseName;
	}

	public String getDoctorNameInitial()
	{
		return doctorNameInitial;
	}

	public void setDoctorNameInitial(String doctorNameInitial)
	{
		this.doctorNameInitial = doctorNameInitial;
	}

	public String getDiseaseNameInitial()
	{
		return diseaseNameInitial;
	}

	public void setDiseaseNameInitial(String diseaseNameInitial)
	{
		this.diseaseNameInitial = diseaseNameInitial;
	}

	public Integer getHit()
	{
		return hit;
	}

	public void setHit(Integer hit)
	{
		this.hit = hit;
	}

}
