package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.hotword.SearchWord;

public interface SearchWordDAO
{
	/**
	 * save User
	 * 
	 * @param user
	 * @return
	 */
	public SearchWord save(SearchWord bean);

	public int updateSearchWord(SearchWord bean);

	public SearchWord findByName(String name);
	
	public List<SearchWord> findListByName(String name);
}
