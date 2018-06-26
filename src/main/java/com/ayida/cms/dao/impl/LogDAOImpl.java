package com.ayida.cms.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.entity.log.Log;
import com.ayida.common.mybatis.BaseDAO;
@Repository(value = "logDao")
public class LogDAOImpl extends BaseDAO<Log>
{

	@Override
	protected String getMethodPath(String methodName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
