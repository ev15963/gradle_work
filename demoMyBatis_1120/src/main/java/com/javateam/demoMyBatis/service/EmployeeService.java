package com.javateam.demoMyBatis.service;

import java.util.List;

import com.javateam.demoMyBatis.vo.EmployeesVO;

public interface EmployeeService {

	 public List<EmployeesVO> getAll();
	 
	 public EmployeesVO getOne(int id);
}
