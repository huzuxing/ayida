package com.ayida.cms.service;

import com.ayida.cms.entity.doctor.DoctorExt;

public interface DoctorExtService
{
	public DoctorExt save(DoctorExt bean);

	public DoctorExt findById(Integer id);

	public int updateById(Integer id);

	public int deleteById(Integer id);
}
