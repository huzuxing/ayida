package com.ayida.cms.entity.doctor;

import com.google.gson.annotations.Expose;

public class DoctorExt extends BaseDoctor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 职务 **/
	@Expose
	private String job;

	/** 科室 **/
	@Expose
	private Integer departmentId;

	/** 职称 **/
	@Expose
	private String professionalTitles;

	/** 擅长领域 **/
	@Expose
	private String skilledField;

	/** 职业证书 **/
	@Expose
	private String certification;

	/** 头像 **/
	@Expose
	private String img;

	/** 备注 **/
	@Expose
	private String comment;

	/** 坐诊时间 **/
	@Expose
	private String seeTime;

	/** 医生简介 **/
	@Expose
	private String description;
	
	public String getProfessionalTitles()
	{
		return professionalTitles;
	}

	public void setProfessionalTitles(String professionalTitles)
	{
		this.professionalTitles = professionalTitles;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSeeTime()
	{
		return seeTime;
	}

	public void setSeeTime(String seeTime)
	{
		this.seeTime = seeTime;
	}

	/**
	 * @return the ranks
	 */
	public String getJob()
	{
		return job;
	}

	/**
	 * @param ranks
	 *            the ranks to set
	 */
	public void setJob(String job)
	{
		this.job = job;
	}

	/**
	 * @return the skilledField
	 */
	public String getSkilledField()
	{
		return skilledField;
	}

	/**
	 * @param skilledField
	 *            the skilledField to set
	 */
	public void setSkilledField(String skilledField)
	{
		this.skilledField = skilledField;
	}

	/**
	 * @return the certification
	 */
	public String getCertification()
	{
		return certification;
	}

	/**
	 * @param certification
	 *            the certification to set
	 */
	public void setCertification(String certification)
	{
		this.certification = certification;
	}

	/**
	 * @return the comment
	 */
	public String getComment()
	{
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	/**
	 * @return the img
	 */
	public String getImg()
	{
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img)
	{
		this.img = img;
	}

	public Integer getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId)
	{
		this.departmentId = departmentId;
	}

}
