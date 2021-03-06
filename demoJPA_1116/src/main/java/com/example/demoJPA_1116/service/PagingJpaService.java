package com.example.demoJPA_1116.service;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.demoJPA_1116.domain.DemoVO;

@Transactional(readOnly = true)

public interface PagingJpaService extends PagingAndSortingRepository<DemoVO, Integer> {

	/**
	 * @param sort 정렬 정보
	 * @return 회원정보 리스트
	 */
	
	Iterable<DemoVO> findAll(Sort sort);

	Page<DemoVO> findAll(Pageable pageable);

	DemoVO findById(BigDecimal id);

}