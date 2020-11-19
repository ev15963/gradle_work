/**
 * 
 */
package com.javateam.SpringJsonFeed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.javateam.SpringJsonFeed.service.JsonFeedService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author java
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {  
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",  
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"  
      })  
@WebAppConfiguration  
@Slf4j  
public class JSONFeedTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private JsonFeedService jsonSvc;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		// MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(jsonSvc).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testUrl() throws Exception {
		
		mockMvc.perform(get("/jsonFeed3").param("id", "abcd"))
					  .andExpect(status().isOk());
		
	}
	
	@Test
	public void testJson() throws Exception {
		
		mockMvc.perform(get("/jsonFeed3").param("id", "abcd")
					   .contentType(MediaType.APPLICATION_JSON))
					  .andExpect(content().contentType("application/json; charset=UTF-8"))
					  .andExpect(status().isOk())
					  .andExpect(jsonPath("$.id").value("abcd"))
					  //.andExpect(jsonPath("$.name").value("홍길동"))
					  .andExpect(jsonPath("$['name']").value("홍길동"))
					  .andExpect(jsonPath("$['address']").value("서울 구로"));
		
	}

}
