package com.ayida.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.DoctorDAO;
import com.ayida.cms.dao.DoctorExtDAO;
import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.service.DoctorService;
import com.ayida.common.mybatis.Pager;

@Service(value = "doctorService")
@Transactional
public class DoctorServiceImpl implements DoctorService
{
	@Autowired
	private DoctorDAO docDao;

	@Autowired
	private DoctorExtDAO docExtDao;

	@Override
	public Doctor save(Doctor bean)
	{
		docDao.save(bean);
		return bean;
	}

	@Override
	public Doctor findById(Integer id)
	{
		return docDao.findById(id);
	}

	@Override
	public Pager<Doctor> getCollectedDoctorsByUserId(Integer userId,
			Integer pageNo, Integer pageSize)
	{
		Pager<Doctor> page = new Pager<Doctor>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		page.setParams(params);
		return docDao.getCollectedDoctorsByUserId(page);
	}

	@Override
	public int updateBean(Doctor bean)
	{
		return docDao.updateBean(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return docDao.deleteById(id);
	}

	@Override
	public List<Doctor> getAllDoctors()
	{
		return docDao.getAllDoctors();
	}

	@Override
	public List<Doctor> getDoctorPagerList(Pager<Doctor> pager)
	{
		return docDao.getDoctorPagerList(pager);
	}

	@Override
	public List<Doctor> findDoctorBySearching(Map<String, Object> map)
	{
		return docDao.findDoctorBySearching(map);
	}

	@Override
	public void saveDoctorSubprofessional(Integer doctorId, Integer subId)
	{
		docDao.saveDoctorSubprofessional(doctorId, subId);
	}

	@Override
	public void saveDoctorHospital(Integer doctorId, Integer hospitalId)
	{
		docDao.saveDoctorHospital(doctorId, hospitalId);
	}

	@Override
	public Doctor isCollected(Integer userId, Integer doctorId)
	{
		return docDao.isCollected(userId, doctorId);
	}

}
