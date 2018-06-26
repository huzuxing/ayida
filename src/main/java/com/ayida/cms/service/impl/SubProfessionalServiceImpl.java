package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.SubProfessionalDAO;
import com.ayida.cms.entity.doctor.SubProfessional;
import com.ayida.cms.service.SubProfessionalService;
import com.ayida.common.mybatis.Pager;

@Service(value = "subProfessionalService")
@Transactional
public class SubProfessionalServiceImpl implements SubProfessionalService
{

	@Autowired
	private SubProfessionalDAO subProfessionalDao;

	@Transactional
	public SubProfessional save(SubProfessional bean)
	{
		return subProfessionalDao.save(bean);
	}

	@Override
	public SubProfessional findById(Integer id)
	{
		return subProfessionalDao.findById(id);
	}

	@Override
	public int updateBean(SubProfessional bean)
	{
		return subProfessionalDao.updateBean(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return subProfessionalDao.deleteById(id);
	}

	@Override
	public List<SubProfessional> getAllSubProfessional()
	{
		return subProfessionalDao.getAllSubProfessional();
	}

	@Override
	public List<SubProfessional> getSubProfessionalPagerList(
			Pager<SubProfessional> pager)
	{
		return subProfessionalDao.getSubProfessionalPagerList(pager);
	}

	@Override
	public SubProfessional findByName(String name)
	{
		return subProfessionalDao.findByName(name);
	}

	@Override
	public void saveSubDisease(Integer subId, Integer diseaseId)
	{
		subProfessionalDao.saveSubDisease(subId, diseaseId);
	}

}
