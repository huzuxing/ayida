package com.ayida.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.DoctorExtDAO;
import com.ayida.cms.entity.doctor.DoctorExt;
import com.ayida.cms.service.DoctorExtService;

@Service(value = "doctorExtService")
@Transactional
public class DoctorExtServiceImpl implements DoctorExtService
{

	@Autowired
	private DoctorExtDAO docExtDao;
	
	@Override
	public DoctorExt save(DoctorExt bean)
	{
		return docExtDao.save(bean);
	}

	@Override
	public DoctorExt findById(Integer id)
	{
		return docExtDao.findById(id);
	}

	@Override
	public int updateById(Integer id)
	{
		return docExtDao.updateById(id);
	}

	@Override
	public int deleteById(Integer id)
	{
		return docExtDao.deleteById(id);
	}

}
