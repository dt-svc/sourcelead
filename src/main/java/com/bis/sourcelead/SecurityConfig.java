package com.bis.sourcelead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;

/**
 * This class is used to read the properties from properties file and configures
 * security context in spring.
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Configuration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Controller.class),
@Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebAppConfig.class) })
@PropertySource(value = { "${propertyLocation:app.properties}" })
@EnableWebSecurity
public class SecurityConfig  {
	
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    	
    	@Autowired
    	CustomTokenAuthenticationFilter customTokenAuthenticationFilter;
    	
    	@Autowired
    	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	   
       @Bean
   	   @Override
   	   public AuthenticationManager authenticationManagerBean() throws Exception {
   	       return super.authenticationManagerBean();
   	   }
    	
        protected void configure(HttpSecurity http) throws Exception {
        	 http
        	 .antMatcher("/rest/**").addFilterBefore(customTokenAuthenticationFilter, BasicAuthenticationFilter.class).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint) 
                 .and()
                 .authorizeRequests().antMatchers("/rest/**").fullyAuthenticated()
                 .and().anonymous()
                  .and()
                   .securityContext()
                   .and()
                   .headers().disable()
                   .rememberMe().disable()
                   .requestCache().disable()
                   .x509().disable()
                   .csrf().disable()
                   .httpBasic().disable()
                   .formLogin().disable()
                   .logout().disable()
                   .httpBasic();
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    	@Autowired
    	SourceleadAuthenticationProvider sourceleadAuthenticationProvider;
    	
    	@Autowired
    	SourceleadAuthenticationSuccessHandler sourceleadAuthenticationSuccessHandler;
    	
    	@Autowired
    	SourceleadAuthenticationFailureHandler sourceleadAuthenticationFailureHandler;
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http
    		.authorizeRequests()                                                                
    			.antMatchers("/login").permitAll() 
    			/*.antMatchers("/registration").permitAll() 
    			.antMatchers("/companyRegistration").permitAll() */
    			.antMatchers("/forgotPassword").permitAll() 
    			.antMatchers("/resetPasswordWithUserName").permitAll() 
    			.antMatchers("/resetPassword").permitAll() 
    			.antMatchers("/ws/**").permitAll() 
    			.antMatchers("/verifyResetPassword").permitAll() 
    			.antMatchers("/acceptClientOrVendorInvitation").permitAll() 
    			.antMatchers("/acceptOrRejectInterview").permitAll() 
    			.antMatchers("/resources/**/**").permitAll() 
    			/*.antMatchers("/**").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_APPROVER') OR hasRole('ROLE_SOURCELEAD_ADMIN') OR hasRole('ROLE_HR_MANAGER') OR hasRole('ROLE_ACCOUNT_MANAGER') OR hasRole('ROLE_SALES_MANAGER') OR hasRole('ROLE_RECRUITER') ")*/ 
    			.antMatchers("/**").fullyAuthenticated()
    			.anyRequest().authenticated()                                                   
    			.and()
    		    .formLogin().loginPage("/login")
      			.usernameParameter("username")
    			.passwordParameter("password")
    			.failureHandler(sourceleadAuthenticationFailureHandler)
    			.successHandler(sourceleadAuthenticationSuccessHandler)
    			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
    			.invalidateHttpSession(true)
    			.and().csrf()
    			.and().exceptionHandling().accessDeniedPage("/403");
    			
    			
        }
     }
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

}