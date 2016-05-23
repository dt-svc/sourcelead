package com.bis.sourcelead;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;

/**
 * This class is used to read the properties from properties file and configures
 * security context in spring.
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Configuration
@EnableWebSecurity
@ComponentScan(excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Controller.class),
		@Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebAppConfig.class) })
@PropertySource(value = { "classpath:config.properties" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll()
				.and().httpBasic();
	}
}
