package com.bis.sourcelead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
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
        	 .antMatcher("/pms/rest/**").addFilterBefore(customTokenAuthenticationFilter, BasicAuthenticationFilter.class).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint) 
                 .and()
                 .authorizeRequests().antMatchers("/pms/rest/**").fullyAuthenticated()
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
    	SecurityContextRepository securityContextRepository;
    	
    	@Value("${sourceleadLoginUrl}")
    	private String sourceleadLoginUrl;
    	
    	@Value("${sourceleadLogoutUrl}")
    	private String sourceleadLogoutUrl;
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http

    		.authorizeRequests()
    			.antMatchers("/pms/resources/**").permitAll()
    			.antMatchers("/resources/**").permitAll()
    			.antMatchers("/pms/ws/**").permitAll()
    			.antMatchers("/ws/**").permitAll() 
    			.antMatchers("/submissionRate.jsp").permitAll() 
    			.antMatchers("/jobs/**").permitAll()
    			.antMatchers("/job/**").permitAll()
    			.antMatchers("/blog/**").permitAll()
    			.antMatchers("/openJobs").permitAll() 
    			.antMatchers("/saveProposeTime").permitAll() 
    			.antMatchers("/getInterviewrsViewFeedbackList").permitAll() 
    			.antMatchers("/ws/displayOrgImageInMail/**").permitAll() 
    			.antMatchers("/pms/ws/displayOrgImageInMail/**").permitAll()
    			.antMatchers("/pms/ws/unsubscribe/**").permitAll()
    			.antMatchers("/pms/userVerification/**").permitAll()
    			.antMatchers("/pms/storeUnsubscriptionPublic/**").permitAll()
    			.antMatchers("/pms/isActiveUserByUserEmail/**").permitAll()
    			.antMatchers("/openJobsPublicPage").permitAll()
    			.antMatchers("/guestApplyToJob/**").permitAll()
    			.antMatchers("/validateRequirementGuestCandidateEmail/**").permitAll()
    			.antMatchers("/getGuestUserRequirmentOrgSubmissionTemplate/**").permitAll()
    			.antMatchers("/saveGuestAddApplicant/**").permitAll()
    			.antMatchers("/addBlogSubscription/**").permitAll()
    			.antMatchers("/downloadReleaseNote/**").permitAll()
    			.antMatchers("/validateBlogSubscriptionEmail/**").permitAll()
    			.antMatchers("/pms/getRequirementSubmissionsCount/**").permitAll()
    			.antMatchers("/parseResume/**").permitAll()
    			.antMatchers("/pms/getDemoTimezonesList/**").permitAll()
    			.antMatchers("/pms/getDemoModulesList/**").permitAll()
    			.antMatchers("/pms/getActionHistoryList/**").permitAll()
    			.antMatchers("/pms/jobCampaignSummary/**").permitAll()
    			.antMatchers("/pms/getRequirementProfessionalProfileDetails/**").permitAll()
    			.anyRequest().authenticated()                                                   
    			.and()
    			.formLogin().loginPage(sourceleadLoginUrl)
    			.and().logout().logoutUrl(sourceleadLogoutUrl)
    			.and().sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    			.and().securityContext().securityContextRepository(securityContextRepository)
    			.and().csrf().disable();
    			
    			
        }
     }
    
    @Bean
    public SecurityContextRepository securityContextRepository(@Value("${auth.token}") String tokenName, 
                                                              @Value("${auth.secret}") String tokenSecret) {
        return new JWTSecurityContextRepository(tokenName, tokenSecret);
    }
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

}