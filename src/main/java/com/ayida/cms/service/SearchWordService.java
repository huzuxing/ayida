package com.ayida.cms.service;

import java.util.List;

import net.sf.ehcache.Ehcache;

import com.ayida.cms.entity.hotword.SearchWord;

public interface SearchWordService
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public SearchWord save(SearchWord bean);

	public int updateSearchWord(SearchWord bean);

	public int refreshToDB(Ehcache cache);

	public SearchWord findByName(String name);
	
	public List<SearchWord> findListByName(String name);
}
