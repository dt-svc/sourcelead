
package com.bis.sourcelead;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.sf.ehcache.constructs.web.filter.GzipFilter;
/**
 * This class is used to configure the web application without web.xml
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 *
 */

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		//Session Initialization
    	servletContext.addListener(new SourceleadSessionListener());
    	
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class,  WebAppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
	  return new Filter[] { new HiddenHttpMethodFilter()/*, new GzipFilter()*/ };
	}
	
	
	
}
