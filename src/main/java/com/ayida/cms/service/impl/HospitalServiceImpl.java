package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.HospitalDAO;
import com.ayida.cms.entity.hospital.Hospital;
import com.ayida.cms.service.HospitalService;
import com.ayida.common.mybatis.Pager;

@Service(value = "hospitalService")
@Transactional
public class HospitalServiceImpl implements HospitalService
{

	@Autowired
	private HospitalDAO hospitalDao;
	
	@Override
	public Hospital save(Hospital bean)
	{
		return hospitalDao.save(bean);
	}

	@Override
	public int delete(Integer id)
	{
		return hospitalDao.delete(id);
	}

	@Override
	public int update(Hospital bean)
	{
		return hospitalDao.update(bean);
	}

	@Override
	public Hospital findById(Integer id)
	{
		return hospitalDao.findById(id);
	}

	@Override
	public Hospital findByName(String name)
	{
		return hospitalDao.findByName(name);
	}

	@Override
	public List<Hospital> getAll()
	{
		return hospitalDao.getAll();
	}

	@Override
	public List<Hospital> getDoctorListByPager(Pager page)
	{
		return hospitalDao.getDoctorListByPager(page);
	}

}
