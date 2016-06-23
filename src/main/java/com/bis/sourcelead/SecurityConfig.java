package com.bis.sourcelead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.impl.SourceleadUserDetailsService;

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
	
	
	@Autowired
	SourceleadUserDetailsService sourceleadUserDetailsService;

	@Autowired
	SourceleadAuthenticationSuccessHandler sourceleadAuthenticationSuccessHandler;
	
	@Autowired
	SourceleadAuthenticationFailureHandler sourceleadAuthenticationFailureHandler;

	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(sourceleadUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
		.authorizeRequests()                                                                
			.antMatchers("/login").permitAll()      
			.antMatchers("/resources/**/**").permitAll() 
			.antMatchers("/**").access("hasRole('ROLE_ADMIN')")                                  
			/*.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")   */         
			.anyRequest().authenticated()                                                   
			.and()
		    .formLogin().loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.failureHandler(sourceleadAuthenticationFailureHandler)
			.successHandler(sourceleadAuthenticationSuccessHandler)
			.and().logout().logoutSuccessUrl("/logout")
			.and().csrf()
			.and().exceptionHandling().accessDeniedPage("/403");;
		

		/*http.authorizeRequests()
		         .antMatchers("/**").access("hasRole('ROLE_ADMIN')")
		        .and().formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(sourceleadAuthenticationSuccessHandler)
				 .and().logout().logoutSuccessUrl("/login?logout")
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/403");*/
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new SourceleadPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

}
