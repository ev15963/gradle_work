package com.javateam.demoMyBatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.demoMyBatis.mapper.EmployeesMapper;
import com.javateam.demoMyBatis.vo.EmployeesVO;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
    @Autowired
    private SqlSession sqlSession;

	@Override
	public List<EmployeesVO> getAll() {
         return sqlSession.getMapper(EmployeesMapper.class).getAll();
	}

	@Override
    public EmployeesVO getOne(int id) {
        return sqlSession.getMapper(EmployeesMapper.class).getOne(id);
    }

}
