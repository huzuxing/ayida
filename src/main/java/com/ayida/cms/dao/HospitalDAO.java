package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.hospital.Hospital;
import com.ayida.common.mybatis.Pager;

public interface HospitalDAO
{
	public Hospital save(Hospital bean);
	
	public int delete(Integer id);
	
	public int update(Hospital bean);
	
	public Hospital findById(Integer id);
	
	public Hospital findByName(String name);
	
	public List<Hospital> getAll();
	
	public List<Hospital> getDoctorListByPager(Pager page);
}
