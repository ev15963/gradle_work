/**
 * 
 */
package com.javateam.SpringMediaDemo.config;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * Excel ViewResolver
 * @author javateam
 *
 */
public class ExcelViewResolver implements ViewResolver {

	/**
	 * @see org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang.String, java.util.Locale)
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {

		ExcelView excelView = new ExcelView();
		return excelView;
	} //

}
