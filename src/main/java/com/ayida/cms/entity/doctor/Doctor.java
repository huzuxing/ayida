package com.ayida.cms.entity.doctor;

import java.math.BigInteger;
import java.util.Set;

import com.ayida.cms.entity.department.Department;
import com.ayida.cms.entity.hospital.Hospital;
import com.google.gson.annotations.Expose;

public class Doctor extends BaseDoctor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private BigInteger phone;

	@Expose
	private String email;

	@Expose
	private String nationality;

	@Expose
	private Integer cardType;

	@Expose
	private String cardId;

	@Expose
	private Integer gender;

	@Expose
	private String degree;

	@Expose
	private DoctorExt doctorExt;

	@Expose
	private Department department;

	@Expose
	private Set<Hospital> hospitals;

	@Expose
	private Set<SubProfessional> subProfessionals;

	public Set<SubProfessional> getSubProfessionals()
	{
		return subProfessionals;
	}

	public void setSubProfessionals(Set<SubProfessional> subProfessionals)
	{
		this.subProfessionals = subProfessionals;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public Set<Hospital> getHospitals()
	{
		return hospitals;
	}

	public void setHospitals(Set<Hospital> hospitals)
	{
		this.hospitals = hospitals;
	}

	/**
	 * @return the phone
	 */
	public BigInteger getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(BigInteger phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality()
	{
		return nationality;
	}

	/**
	 * @param nationality
	 *            the nationality to set
	 */
	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	/**
	 * @return the cardType
	 */
	public Integer getCardType()
	{
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(Integer cardType)
	{
		this.cardType = cardType;
	}

	/**
	 * @return the gender
	 */
	public Integer getGender()
	{
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(Integer gender)
	{
		this.gender = gender;
	}

	/**
	 * @return the cardId
	 */
	public String getCardId()
	{
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}
	
	public String getDegree()
	{
		return degree;
	}

	public void setDegree(String degree)
	{
		this.degree = degree;
	}

	public DoctorExt getDoctorExt()
	{
		return doctorExt;
	}

	public void setDoctorExt(DoctorExt doctorExt)
	{
		this.doctorExt = doctorExt;
	}
}
