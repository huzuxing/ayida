package com.ayida.cms.entity.api;

import java.util.List;

import com.ayida.cms.entity.doctor.Doctor;
import com.google.gson.annotations.Expose;

public class ApiDoctorEntity
{
	@Expose
	private Integer code;
	
	@Expose
	private String text;
	
	@Expose
	private Doctor doctor;
	
	@Expose
	private List<Doctor> doctors;

	public Integer getCode()
	{
		return code;
	}

	public void setCode(Integer code)
	{
		this.code = code;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Doctor getDoctor()
	{
		return doctor;
	}

	public void setDoctor(Doctor doctor)
	{
		this.doctor = doctor;
	}

	public List<Doctor> getDoctors()
	{
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors)
	{
		this.doctors = doctors;
	}
}
