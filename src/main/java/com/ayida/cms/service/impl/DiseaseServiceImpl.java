package com.ayida.cms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.DiseaseDAO;
import com.ayida.cms.entity.disease.Disease;
import com.ayida.cms.service.DiseaseService;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.PageUtils;

@Service(value = "diseaseService")
@Transactional
public class DiseaseServiceImpl implements DiseaseService
{
	@Autowired
	private DiseaseDAO diseaseDao;

	@Override
	public Disease save(Disease bean)
	{
		return diseaseDao.save(bean);
	}

	@Override
	public Disease findById(Integer id)
	{
		return diseaseDao.findById(id);
	}

	@Override
	public int updateBean(Disease bean)
	{
		return diseaseDao.updateBean(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return diseaseDao.deleteById(id);
	}

	@Override
	public List<Disease> getAllDisease()
	{
		return diseaseDao.getAllDisease();
	}

	@Override
	public List<Disease> getDiseasePagerList(Integer pageNo, Integer pageSize, Map<String, Object> params)
	{
		Pager<Disease> page = new Pager<Disease>();
		page.setPageNo(PageUtils.cpn(pageNo));
		page.setPageSize(PageUtils.cps(pageSize));
		page.setParams(params);
		return diseaseDao.getDiseasePagerList(page);
	}

	@Override
	public Disease findByName(String name)
	{
		return diseaseDao.findByName(name);
	}

}
