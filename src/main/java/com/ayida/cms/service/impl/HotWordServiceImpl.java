package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.HotWordDAO;
import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.cms.service.HotWordService;
import com.ayida.common.mybatis.Pager;

@Service(value = "hotWordService")
@Transactional
public class HotWordServiceImpl implements HotWordService
{

	@Autowired
	private HotWordDAO hotWordDao;
	
	@Override
	public HotWord save(String name, Integer parentId)
	{
		HotWord bean = new HotWord();
		bean.setName(name);
		bean.setParentId(parentId);
		return hotWordDao.save(bean);
	}

	@Override
	public int delete(Integer id)
	{
		return hotWordDao.delete(id);
	}

	@Override
	public HotWord update(HotWord bean)
	{
		hotWordDao.update(bean);
		return bean;
	}

	@Override
	public HotWord findById(Integer id)
	{
		return hotWordDao.findById(id);
	}

	@Override
	public HotWord findByName(String name)
	{
		return hotWordDao.findByName(name);
	}

	@Override
	public List<HotWord> getPagerList(Pager<HotWord> page)
	{
		return hotWordDao.getListByPager(page);
	}

	@Override
	public List<HotWord> getChildList(Integer parentId)
	{
		return hotWordDao.getChildList(parentId);
	}

	@Override
	public void deleteChild(Integer parentId)
	{
		hotWordDao.deleteChild(parentId);
	}

}
