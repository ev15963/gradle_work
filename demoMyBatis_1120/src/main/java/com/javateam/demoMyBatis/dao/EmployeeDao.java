package com.javateam.demoMyBatis.dao;

import java.util.List;

import com.javateam.demoMyBatis.vo.EmployeesVO;

public interface EmployeeDao {

	 public List<EmployeesVO> getAll();
	 
	 public EmployeesVO getOne(int id);
	 
}