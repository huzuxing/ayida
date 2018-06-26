package com.ayida.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.common.mybatis.BaseDAO;
@Repository(value = "authorDao")
public class AuthorDAOImpl extends BaseDAO<Author> implements AuthorDAO
{

	@Override
	public Author findById(Integer id)
	{
		Map<String, String> map = new HashMap<>();
		map.put("id", "huzuxing");
		return sqlSession.selectOne("com.ayida.test.AuthorDAO.findById", map);
	}

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
