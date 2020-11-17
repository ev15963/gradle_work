package com.example.demoJPA_1116.domain;

import java.math.BigDecimal;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;

import javax.persistence.Id;

import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author 
 * 회원아이디
 *
 */
@Entity //pom 점검도구
@Table(name = "demo_tbl")
@Data

public class DemoVO {

	@Id
	@Column(nullable = false, precision = 4, scale = 0) // number(4,0)
	@GeneratedValue // sequence.nextval
	private BigDecimal id;

	@Column(nullable = false, length = 40)
	private String name;

}