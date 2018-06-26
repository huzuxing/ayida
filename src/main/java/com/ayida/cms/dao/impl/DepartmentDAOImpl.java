package com.ayida.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ayida.cms.dao.DepartmentDAO;
import com.ayida.cms.entity.department.Department;
import com.ayida.common.mybatis.BaseDAO;
import com.ayida.common.mybatis.Pager;

@Repository(value = "departmentDao")
public class DepartmentDAOImpl extends BaseDAO<Department>
		implements
			DepartmentDAO
{
	private static final String DEPARTMENT_DAO = "com.ayida.cms.dao.DepartmentDAO.";

	@Override
	public Department save(Department bean)
	{
		return super.save(bean);
	}

	@Override
	public Department findById(Integer id)
	{
		return get(getIdMap(id));
	}

	@Override
	public int updateBean(Department bean)
	{
		Map<String, Department> map = new HashMap<String, Department>();
		map.put("bean", bean);
		return update(map);
	}

	@Override
	public int deleteById(Integer id)
	{
		return delete(getIdMap(id));
	}

	@Override
	public Department findByName(String name)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return sqlSession.selectOne(getMethodPath("findByName"), map);
	}

	@Override
	public List<Department> getAllDepartment()
	{
		return getAll();
	}

	@Override
	public List<Department> getDepartmentPagerList(Pager<Department> pager)
	{
		return getPagerList(pager);
	}

	@Override
	protected String getMethodPath(String methodName)
	{
		return DEPARTMENT_DAO + methodName;
	}

	@Override
	protected Map<String, Integer> getIdMap(Integer id)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		return map;
	}

}
