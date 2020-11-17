package com.example.demoThymeleaf_1117.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberVO {
	
	private String id;
	private String pw;
	private String name;
	private Date joindate;
	
}
