package com.example.demoJPA_1116.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demoJPA_1116.domain.DemoVO;
import com.example.demoJPA_1116.service.PagingJpaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class PagingController {

	@Autowired
	private PagingJpaService svc;

	@RequestMapping("/")
	public String home() {
		return "redirect:/paging?page=1";
	}

	@RequestMapping("/sort")

	public String sort(Model model) {

		log.info("sort");
		Iterable<DemoVO> list = svc.findAll(Sort.by(new Order(Direction.DESC, "name")));
		model.addAttribute("list", list);
		return "sorted";

	} //

	@RequestMapping("/paging")

	public String paging(@RequestParam("page") int page, Model model) {

		log.info("paging");

		Pageable pageable = PageRequest.of(page - 1, 5);
		Page<DemoVO> pageList = svc.findAll(pageable);

		List<DemoVO> list = pageList.getContent();
		model.addAttribute("total_page", pageList.getTotalPages());

		model.addAttribute("curr_page", page);

		model.addAttribute("list", list);

		return "paging";

	} //

	@RequestMapping("/member/{id}")

	@ResponseBody

	public DemoVO getOne(@PathVariable BigDecimal id) {

		log.info("getOne");

		return svc.findById(id);

	} //

}