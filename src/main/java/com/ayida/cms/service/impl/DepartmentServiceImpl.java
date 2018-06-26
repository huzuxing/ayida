package com.ayida.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayida.cms.dao.DepartmentDAO;
import com.ayida.cms.entity.department.Department;
import com.ayida.cms.service.DepartmentService;
import com.ayida.common.mybatis.Pager;

@Service(value = "departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService
{
	@Autowired
	private DepartmentDAO departmentDao;
	
	@Override
	public Department save(Department bean)
	{
		return departmentDao.save(bean);
	}

	@Override
	public Department findById(Integer id)
	{
		return departmentDao.findById(id);
	}

	@Override
	public int updateBean(Department bean)
	{
		return departmentDao.updateBean(bean);
	}

	@Override
	public int deleteById(Integer id)
	{
		return departmentDao.deleteById(id);
	}

	@Override
	public Department findByName(String name)
	{
		return departmentDao.findByName(name);
	}

	@Override
	public List<Department> getAllDepartment()
	{
		return departmentDao.getAllDepartment();
	}

	@Override
	public List<Department> getDepartmentPagerList(Pager<Department> pager)
	{
		return departmentDao.getDepartmentPagerList(pager);
	}

}
