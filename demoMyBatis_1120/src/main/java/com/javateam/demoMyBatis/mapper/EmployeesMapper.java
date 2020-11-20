package com.javateam.demoMyBatis.mapper;
 
import java.util.List;
 
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.javateam.demoMyBatis.vo.EmployeesVO;
 
@Mapper
public interface EmployeesMapper {
   
    @Select("SELECT * FROM employees ORDER BY employee_id")
    List<EmployeesVO> getAll();
   
    @Select("SELECT * FROM employees WHERE employee_id = #{employeeId}")
    EmployeesVO getOne(@Param("employeeId") int id);
 
}