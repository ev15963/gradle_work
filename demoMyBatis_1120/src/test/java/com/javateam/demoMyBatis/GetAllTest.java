package com.javateam.demoMyBatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.javateam.demoMyBatis.controller.DemoController;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
// @RunWith(SpringRunner.class) // JUnit4 사용시 
@AutoConfigureMockMvc
@Slf4j
public class GetAllTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	MockMvc mockMvc;
	
    // 목 객체에 테스트할 컨트롤러(Controller) 삽입
    @InjectMocks
    private DemoController demoController;
	
	// 통합 테스트 사전 준비 작업
    @Before
    public void setup() {
        log.info("before setup");
        
        mockMvc = MockMvcBuilders.standaloneSetup(demoController)
				        		 .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가 
				        		 .alwaysDo(print())
				        		 .build();
        
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//					    		.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가 
//					   		 	.alwaysDo(print())
//					   		 	.build();
    } //
		
	@Test
	public void test(){
		
		// getOne Test
		try {
			 
			log.info("########  getOne 통합 테스트(Integration Test) #############");
			
			String result = mockMvc.perform(get("/getOne/100")) 
						         .andExpect(status().isOk())
						         .andExpect(content().contentType("text/html; charset=UTF-8"))
						         .andDo(print())
						         .andReturn()
						         .getResponse()
						         .getContentAsString();
			
			// 출력되는 html 내용에"2003-06-17" 포함 여부 점검 
			assertTrue(result.contains("2003-06-17"));
			// log.info("result : "+result);
			
		} catch (UnsupportedEncodingException e) {
			log.trace("uee : " +e);
			e.printStackTrace();
		} catch (Exception e) {
			log.trace("e : "+e);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test2(){
		
		// getOneJson Test
		try {
			 
			log.info("########  getOneJson 통합 테스트(Integration Test) #############");
			
			mockMvc.perform(get("/getOneJson").param("employeeId", "100")) 
				         .andExpect(status().isOk())
				         // .andExpect(content().contentType("application/json; charset=UTF-8"))
				         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				         .andExpect(jsonPath("employeeId").exists())
				         .andExpect(jsonPath("$['lastName']").value("King"))
				         .andExpect(jsonPath("$.firstName", is("Steven")));
			
		} catch (UnsupportedEncodingException e) {
			log.trace("uee : " +e);
			e.printStackTrace();
		} catch (Exception e) {
			log.trace("e : "+e);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test3(){
		
		// getAll Test
		long result = 0;
		
		try {
			 
			log.info("########  getAll 통합 테스트(Integration Test) #############");
			
			result = mockMvc.perform(get("/getAll")) 
				         .andExpect(status().isOk())
				         .andExpect(content().contentType("text/html; charset=UTF-8"))
				         .andReturn()
				         .getResponse()
				         .getContentAsString()
				         .lines()
				         .count(); 
			
		     log.info("전체 검색 결과(html 라인수) : "+ result);
		     assertEquals(1524, result);
			
		} catch (UnsupportedEncodingException e) {
			log.trace("uee : " +e);
			e.printStackTrace();
		} catch (Exception e) {
			log.trace("e : "+e);
			e.printStackTrace();
		}
		
	}

}