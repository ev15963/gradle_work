/**
 * 
 */
package com.javateam.SpringMediaDemo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Annotation configuration
 * 
 * @author javateam
 *
 */
@EnableWebMvc
@ComponentScan("com.javateam.SpringMediaDemo")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/*
     * Configure ContentNegotiationManager
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    	
        configurer.ignoreAcceptHeader(true)
        		  .defaultContentType(MediaType.TEXT_HTML);
    }
	
	/* @Bean
	public InternalResourceViewResolver setViewResolver() {
		
		InternalResourceViewResolver viewResolver 
			= new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	} //
	*/	
	
	@Bean
	public InternalResourceViewResolver jspResolver() {
		
		InternalResourceViewResolver viewResolver 
			= new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2); // priority(우선순위)
		
		return viewResolver;
	} //
	
   /*
    * Configure PDF ViewResolver
    */
    @Bean
    public ViewResolver pdfViewResolver() {
        return new PdfViewResolver();
    }
 
   /*
    * Configure Excel ViewResolver
    */
    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }
    
    @Bean
    public ResourceBundleViewResolver setResourceBundleViewResolver() {
    	
    	ResourceBundleViewResolver rbvr = new ResourceBundleViewResolver();
    	rbvr.setBasename("views");
    	rbvr.setOrder(1);
    	
    	return rbvr;
    }
	
	@Bean
	public ContentNegotiatingViewResolver setContentViewResolver() {
		
		ContentNegotiatingViewResolver contentViewResolver
			 = new ContentNegotiatingViewResolver();
				
		contentViewResolver.setOrder(1); 
		// InternalResourceViewResolver보다 priority(우선순위)를 높여준다. (중요) 
		
		// various ViewResolvers setting
		List<ViewResolver> listViewResolver = new ArrayList<>();
		listViewResolver.add(new BeanNameViewResolver());
		listViewResolver.add(this.jspResolver());
		listViewResolver.add(this.excelViewResolver());
		listViewResolver.add(this.pdfViewResolver());
		listViewResolver.add(this.setResourceBundleViewResolver());
		
		contentViewResolver.setViewResolvers(listViewResolver);
		
		List<View> defaultViews = new ArrayList<>();
		
		MappingJackson2JsonView mappingJackson2JsonView 
			= new MappingJackson2JsonView();
		mappingJackson2JsonView.setPrettyPrint(true);
		
		defaultViews.add(mappingJackson2JsonView);
		
		contentViewResolver.setDefaultViews(defaultViews);
		
		return contentViewResolver;
	} //

}