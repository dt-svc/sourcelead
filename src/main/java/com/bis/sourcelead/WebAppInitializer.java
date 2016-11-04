
package com.bis.sourcelead;
import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.bis.sourcelead.util.Annie;

import gate.Gate;
import gate.util.GateException;
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
    	
    	//gate initialization
		File gateHome = new File(servletContext.getRealPath("/WEB-INF/classes"));
    	Gate.setGateHome(gateHome);
    	File siteConfig = new File(servletContext.getRealPath("/WEB-INF/classes/gate.xml"));
    	Gate.setSiteConfigFile(siteConfig);
    	try {
    		Gate.setPluginsHome(gateHome);
			Gate.init();
			
			Annie annie = new Annie();
			annie.initAnnie();
			servletContext.setAttribute("Annie", annie);
		} catch (GateException e) {
		} catch (IOException e) {
		}
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
	  return new Filter[] { new HiddenHttpMethodFilter() };
	}
	
}
