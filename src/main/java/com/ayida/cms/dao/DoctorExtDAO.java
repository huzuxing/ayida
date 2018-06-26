package com.ayida.cms.dao;

import com.ayida.cms.entity.doctor.DoctorExt;
public interface DoctorExtDAO
{
	public DoctorExt save(DoctorExt bean);

	public DoctorExt findById(Integer id);

	public int updateById(Integer id);

	public int deleteById(Integer id);

}
