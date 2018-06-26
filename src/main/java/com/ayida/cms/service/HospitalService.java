package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.hospital.Hospital;
import com.ayida.common.mybatis.Pager;

public interface HospitalService
{
	public Hospital save(Hospital bean);

	public int delete(Integer id);

	public int update(Hospital bean);

	public Hospital findById(Integer id);

	public Hospital findByName(String name);

	public List<Hospital> getAll();

	public List<Hospital> getDoctorListByPager(Pager<Hospital> page);
}
