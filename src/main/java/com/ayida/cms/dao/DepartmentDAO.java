package com.ayida.cms.dao;

import java.util.List;

import com.ayida.cms.entity.department.Department;
import com.ayida.common.mybatis.Pager;

public interface DepartmentDAO
{
	public Department save(Department bean);

	public Department findById(Integer id);

	public int updateBean(Department bean);

	public int deleteById(Integer id);

	public Department findByName(String name);

	public List<Department> getAllDepartment();

	public List<Department> getDepartmentPagerList(Pager<Department> pager);
}
