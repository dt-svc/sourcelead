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
        	 .antMatcher("/console/rest/**").addFilterBefore(customTokenAuthenticationFilter, BasicAuthenticationFilter.class).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint) 
                 .and()
                 .authorizeRequests().antMatchers("/console/rest/**").fullyAuthenticated()
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
    	
    	@Autowired
    	SourceleadLogoutSuccessHandler sourceleadLogoutSuccessHandler;
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http

    		.authorizeRequests()
       		     .antMatchers("/").permitAll() 
       		     .antMatchers("/getScheduleStatus").permitAll() 
    		    .antMatchers("/login").permitAll()
    		    .antMatchers("/demoRequest").permitAll() 
    		    .antMatchers("/*.html").permitAll() 
    		    .antMatchers("/resetPassword").permitAll() 
    		    .antMatchers("/forgotPassword").permitAll() 
    		    .antMatchers("/register").permitAll()
    		    .antMatchers("/register/**").permitAll()
    			.antMatchers("/console/resources/**").permitAll()
    			.antMatchers("/resources/**").permitAll()
    			.antMatchers("/console/ws/**").permitAll()
    			.antMatchers("/ws/**").permitAll() 
    			.antMatchers("/submissionRate.jsp").permitAll() 
    			.antMatchers("/jobs/**").permitAll()
    			.antMatchers("/job/**").permitAll()
    			.antMatchers("/blog/**").permitAll()
    			.antMatchers("/openJobs").permitAll() 
    			.antMatchers("/saveProposeTime").permitAll() 
    			.antMatchers("/getInterviewrsViewFeedbackList").permitAll() 
    			.antMatchers("/ws/displayOrgImageInMail/**").permitAll() 
    			.antMatchers("/console/ws/displayOrgImageInMail/**").permitAll()
    			.antMatchers("/console/ws/unsubscribe/**").permitAll()
    			.antMatchers("/console/userVerification/**").permitAll()
    			.antMatchers("/console/storeUnsubscriptionPublic/**").permitAll()
    			.antMatchers("/console/isActiveUserByUserEmail/**").permitAll()
    			.antMatchers("/openJobsPublicPage").permitAll()
    			.antMatchers("/guestApplyToJob/**").permitAll()
    			.antMatchers("/validateRequirementGuestCandidateEmail/**").permitAll()
    			.antMatchers("/getGuestUserRequirmentOrgSubmissionTemplate/**").permitAll()
    			.antMatchers("/saveGuestAddApplicant/**").permitAll()
    			.antMatchers("/addBlogSubscription/**").permitAll()
    			.antMatchers("/downloadReleaseNote/**").permitAll()
    			.antMatchers("/validateBlogSubscriptionEmail/**").permitAll()
    			.antMatchers("/console/getRequirementSubmissionsCount/**").permitAll()
    			.antMatchers("/parseResume/**").permitAll()
    			.antMatchers("/console/getDemoTimezonesList/**").permitAll()
    			.antMatchers("/console/getDemoModulesList/**").permitAll()
    			.antMatchers("/console/getActionHistoryList/**").permitAll()
    			.antMatchers("/console/jobCampaignSummary/**").permitAll()
    			.antMatchers("/console/getRequirementProfessionalProfileDetails/**").permitAll()
    			.anyRequest().authenticated()                                                   
    			.and()
    		    .formLogin().loginPage("/login")
      			.usernameParameter("username")
    			.passwordParameter("password")
    			.failureHandler(sourceleadAuthenticationFailureHandler)
    			.successHandler(sourceleadAuthenticationSuccessHandler)
    			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(sourceleadLogoutSuccessHandler).logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
    			.invalidateHttpSession(true)
    			.and().exceptionHandling().accessDeniedPage("/403")
    			//.and().requiresChannel().anyRequest().requiresSecure()
    			.and().csrf().disable();
    			
    			
        }
     }
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

}