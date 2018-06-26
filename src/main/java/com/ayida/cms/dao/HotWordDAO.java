package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.common.mybatis.Pager;

public interface HotWordDAO
{
	
	public HotWord save(HotWord bean);
	
	public int delete(Integer id);
	
	public HotWord update(HotWord bean);
	
	public HotWord findById(Integer id);
	
	public HotWord findByName(String name);
	
	public List<HotWord> getAll();
	
	public List<HotWord> getListByPager(Pager<HotWord> page);
	
	public List<HotWord> getChildList(Integer parentId);
	
	public void deleteChild(Integer parentId);
}
