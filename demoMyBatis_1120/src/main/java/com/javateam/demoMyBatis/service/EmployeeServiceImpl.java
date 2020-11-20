package com.javateam.demoMyBatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.demoMyBatis.dao.EmployeeDao;
import com.javateam.demoMyBatis.vo.EmployeesVO;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDao dao;
	
	@Transactional(readOnly = true)
	@Override
	public List<EmployeesVO> getAll() {
		return dao.getAll();
	}

	@Transactional(readOnly = true)
	@Override
	public EmployeesVO getOne(int id) {
		return dao.getOne(id);
	}
	
}