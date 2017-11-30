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
    	
    	@Autowired
    	SourceleadLogoutSuccessHandler sourceleadLogoutSuccessHandler;
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http
    		.authorizeRequests()                                                                
    			.antMatchers("/path/login").permitAll() 
    			.antMatchers("/path/Inlogin").permitAll() 
//    			.antMatchers("/path/register").permitAll() 
//    			.antMatchers("/path/restAuthenticate").permitAll() 
//    			.antMatchers("/path/registerAsProfessional").permitAll() 
//    			.antMatchers("/path/registerAsIndividualRecruiter").permitAll() 
//    			.antMatchers("/path/registerAsProfessionalFromSocialInviter").permitAll() 
//    			.antMatchers("/path/registerAsIndividualRecruiterFromSocialInviter").permitAll() 
//    			.antMatchers("/path/resetloginCredentials").permitAll() 
//    			.antMatchers("/path/validateCrowdSourcingEmail").permitAll() 
//    			.antMatchers("/path/validateCrowdSourcingUserName").permitAll() 
//    			.antMatchers("/path/validateCrowdSourcingResetUserName").permitAll() 
//    			.antMatchers("/path/resetCredentials").permitAll() 
    			.antMatchers("/path/forgotPassword").permitAll() 
//    			.antMatchers("/path/resetPasswordWithUserName").permitAll() 
    			.antMatchers("/path/resetPassword").permitAll() 
//    			.antMatchers("/path/referRequirementToProfessional").permitAll() 
//    			.antMatchers("/path/getOptionalValueList").permitAll() 
//    			.antMatchers("/path/openRequirements/**").permitAll() 
//    			.antMatchers("/path/getOpenJobsELK").permitAll()
//    			.antMatchers("/path/getOrgsListforOpenJobs").permitAll()
//    			.antMatchers("/path/openRequirementsRedirect").permitAll()
//    			.antMatchers("/path/openRequirementsSummary").permitAll()
//    			.antMatchers("/path/openJobsPublicPage").permitAll()
//    			.antMatchers("/path/openJobsRequiremetsAdvancedSearch").permitAll()
//    			.antMatchers("/path/getStatesByCountry").permitAll()
//    			.antMatchers("/path/getCitiesByStateAndCountry").permitAll()
//    			.antMatchers("/path/getAPIService").permitAll() 
//    			.antMatchers("/path/storeCsv").permitAll() 
//    			.antMatchers("/path/getRequirementProfessionalProfileDetails").permitAll() 
    			.antMatchers("/path/ws/**").permitAll() 
    			.antMatchers("/path/ws/jobs/**/**/**").permitAll() 
//    			.antMatchers("/path/verifyResetPassword").permitAll() 
//    			.antMatchers("/path/acceptClientOrVendorInvitation").permitAll() 
//    			.antMatchers("/path/acceptOrRejectInterview").permitAll() 
//    			.antMatchers("/path/acceptOrRejectOffer").permitAll()
//    			.antMatchers("/path/acceptOrRejectRequirementOffer").permitAll()
//    			.antMatchers("/path/downloadOfferAttachment").permitAll()
//    			.antMatchers("/path/downloadInvoiceAttachment").permitAll()
//    			.antMatchers("/path/acceptOrRejectReopenTimeSheet").permitAll() 
//    			.antMatchers("/path/acceptOrRejectReopenExpenseBySuperUser").permitAll()
//    			.antMatchers("/path/acceptOrRejectReopenExpense").permitAll()
//    			.antMatchers("/path/publicInterviewFeedBackTemplate").permitAll()
//    			.antMatchers("/path/downloadRequirementAttachment").permitAll()
    			.antMatchers("/path/resources/**/**").permitAll() 
//    			.antMatchers("/path/acceptOrRejectOrRateNegotiateApplicantRates").permitAll() 
//    			.antMatchers("/path/acceptOrRejectRequirement").permitAll() 
//    			.antMatchers("/path/storeApplicantRejectReason").permitAll() 
//    			.antMatchers("/path/acceptOrRejectOrRateNegotiateUserRates").permitAll()
//    			.antMatchers("/path/storeReject").permitAll() 
//    			.antMatchers("/path/getRateHistoryBySubmissionId").permitAll() 
//    			.antMatchers("/path/storeNegotiation").permitAll() 
    			.antMatchers("/path/submissionRate.jsp").permitAll() 
//    			.antMatchers("/path/requestForCandidateResume").permitAll() 
//    			.antMatchers("/path/getRequestedCandidates").permitAll() 
//    			.antMatchers("/path/validateCampaignToEmail").permitAll() 
//    			.antMatchers("/path/sendRequestForResume").permitAll() 
//    			.antMatchers("/path/displayOrgImageInMail/**").permitAll()
//    			.antMatchers("/path/countryAutoComplete").permitAll() 
//    			.antMatchers("/path/getStatesByCountry").permitAll()
//    			.antMatchers("/path/getCitiesByStateAndCountry").permitAll()
//    			.antMatchers("/path/saveProposeTime").permitAll()
//    			.antMatchers("/path/jobAlertRequirementSummary").permitAll()
//    			.antMatchers("/path/proposeNewTimePublicpage").permitAll()
//    			.antMatchers("/path/getJobDetailsForJobAlert").permitAll()
//    			.antMatchers("/path/storeJobAlert").permitAll()
//    			.antMatchers("/path/validateAlertUserEmail").permitAll()
//    			.antMatchers("/path/getInterviewrsViewFeedbackList").permitAll()
//    			.antMatchers("/path/savePublicInterviewFeddbackTemlateReason").permitAll()
//    			.antMatchers("/path/savePublicInterviewFeddbackTemlate").permitAll()
//    			.antMatchers("/path/getPublicInterviewFeedbackTemplate").permitAll()
//    			.antMatchers("/path/validateCrowdSourcingRegistrationEmail").permitAll()
//    			.antMatchers("/path/requestedDocuments").permitAll()
//    			.antMatchers("/path/uploadRequestedDocuments").permitAll()
//    			.antMatchers("/path/viewAllJobAlerts").permitAll()
//    			.antMatchers("/path/getAllJobsAlertList").permitAll()
    			.antMatchers("/path/upload/*").permitAll()
    			.antMatchers("/path/static-web/*").permitAll()
//    			.antMatchers("/path/viewJobSummaryDocumentDocView").permitAll()
//    			.antMatchers("/path/check-session").permitAll()
    			.antMatchers("/").permitAll()
    			.antMatchers("/**").permitAll()
    			
    			/*.antMatchers("/**").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_APPROVER') OR hasRole('ROLE_SOURCELEAD_ADMIN') OR hasRole('ROLE_HR_MANAGER') OR hasRole('ROLE_ACCOUNT_MANAGER') OR hasRole('ROLE_SALES_MANAGER') OR hasRole('ROLE_RECRUITER') ")*/ 
    			.antMatchers("/path/**").fullyAuthenticated()
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
    			.and().csrf().disable();
    			
    			
        }
     }
	@Bean
	public FormattingConversionService conversionService() {
		FormattingConversionService conversionService = new DefaultFormattingConversionService();
		return conversionService;
	}

}