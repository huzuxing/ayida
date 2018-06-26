package com.ayida.cms.service;

import java.util.List;

import com.ayida.cms.entity.hotword.HotWord;
import com.ayida.common.mybatis.Pager;

public interface HotWordService
{
	public HotWord save(String name, Integer parentId);
	
	public int delete(Integer id);
	
	public HotWord update(HotWord bean);
	
	public HotWord findById(Integer id);
	
	public HotWord findByName(String name);
	
	public List<HotWord> getPagerList(Pager<HotWord> page);
	
	public List<HotWord> getChildList(Integer parentId);
	
	public void deleteChild(Integer parentId);
}
