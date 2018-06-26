package com.ayida.cms.service;

import java.util.Map;

import com.ayida.cms.entity.hotword.RelativeSearchWord;

public class RelativeWordListenerAbstract implements RelativeWordLuceneListener
{

	@Override
	public void preSave(RelativeSearchWord bean)
	{
		
	}

	@Override
	public void afterSave(RelativeSearchWord bean)
	{
		
	}

	@Override
	public Map<String, Object> preChange(RelativeSearchWord bean)
	{
		return null;
	}

	@Override
	public void afterChange(RelativeSearchWord bean, Map<String, Object> map)
	{
		
	}

	@Override
	public void preDelete(RelativeSearchWord bean)
	{
		
	}

	@Override
	public void afterDelete(RelativeSearchWord bean)
	{
		
	}

}
