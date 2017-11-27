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
    			.antMatchers("/slead/login").permitAll() 
    			.antMatchers("/slead/Inlogin").permitAll() 
    			.antMatchers("/slead/register").permitAll() 
    			.antMatchers("/slead/restAuthenticate").permitAll() 
    			.antMatchers("/slead/registerAsProfessional").permitAll() 
    			.antMatchers("/slead/registerAsIndividualRecruiter").permitAll() 
    			.antMatchers("/slead/registerAsProfessionalFromSocialInviter").permitAll() 
    			.antMatchers("/slead/registerAsIndividualRecruiterFromSocialInviter").permitAll() 
    			.antMatchers("/slead/resetloginCredentials").permitAll() 
    			.antMatchers("/slead/validateCrowdSourcingEmail").permitAll() 
    			.antMatchers("/slead/validateCrowdSourcingUserName").permitAll() 
    			.antMatchers("/slead/validateCrowdSourcingResetUserName").permitAll() 
    			.antMatchers("/slead/resetCredentials").permitAll() 
    			.antMatchers("/slead/forgotPassword").permitAll() 
    			.antMatchers("/slead/resetPasswordWithUserName").permitAll() 
    			.antMatchers("/slead/resetPassword").permitAll() 
    			.antMatchers("/slead/referRequirementToProfessional").permitAll() 
    			.antMatchers("/slead/getOptionalValueList").permitAll() 
    			.antMatchers("/slead/openRequirements/**").permitAll() 
    			.antMatchers("/slead/getOpenJobsELK").permitAll()
    			.antMatchers("/slead/getOrgsListforOpenJobs").permitAll()
    			.antMatchers("/slead/openRequirementsRedirect").permitAll()
    			.antMatchers("/slead/openRequirementsSummary").permitAll()
    			.antMatchers("/slead/openJobsPublicPage").permitAll()
    			.antMatchers("/slead/openJobsRequiremetsAdvancedSearch").permitAll()
    			.antMatchers("/slead/getStatesByCountry").permitAll()
    			.antMatchers("/slead/getCitiesByStateAndCountry").permitAll()
    			.antMatchers("/slead/getAPIService").permitAll() 
    			.antMatchers("/slead/storeCsv").permitAll() 
    			.antMatchers("/slead/getRequirementProfessionalProfileDetails").permitAll() 
    			.antMatchers("/slead/ws/**").permitAll() 
    			.antMatchers("/slead/verifyResetPassword").permitAll() 
    			.antMatchers("/slead/acceptClientOrVendorInvitation").permitAll() 
    			.antMatchers("/slead/acceptOrRejectInterview").permitAll() 
    			.antMatchers("/slead/acceptOrRejectOffer").permitAll()
    			.antMatchers("/slead/acceptOrRejectRequirementOffer").permitAll()
    			.antMatchers("/slead/downloadOfferAttachment").permitAll()
    			.antMatchers("/slead/downloadInvoiceAttachment").permitAll()
    			.antMatchers("/slead/acceptOrRejectReopenTimeSheet").permitAll() 
    			.antMatchers("/slead/acceptOrRejectReopenExpenseBySuperUser").permitAll()
    			.antMatchers("/slead/acceptOrRejectReopenExpense").permitAll()
    			.antMatchers("/slead/publicInterviewFeedBackTemplate").permitAll()
    			.antMatchers("/slead/downloadRequirementAttachment").permitAll()
    			.antMatchers("/slead/resources/**/**").permitAll() 
    			.antMatchers("/slead/acceptOrRejectOrRateNegotiateApplicantRates").permitAll() 
    			.antMatchers("/slead/acceptOrRejectRequirement").permitAll() 
    			.antMatchers("/slead/storeApplicantRejectReason").permitAll() 
    			.antMatchers("/slead/acceptOrRejectOrRateNegotiateUserRates").permitAll()
    			.antMatchers("/slead/storeReject").permitAll() 
    			.antMatchers("/slead/getRateHistoryBySubmissionId").permitAll() 
    			.antMatchers("/slead/storeNegotiation").permitAll() 
    			.antMatchers("/slead/submissionRate.jsp").permitAll() 
    			.antMatchers("/slead/requestForCandidateResume").permitAll() 
    			.antMatchers("/slead/getRequestedCandidates").permitAll() 
    			.antMatchers("/slead/validateCampaignToEmail").permitAll() 
    			.antMatchers("/slead/sendRequestForResume").permitAll() 
    			.antMatchers("/slead/displayOrgImageInMail/**").permitAll()
    			.antMatchers("/slead/countryAutoComplete").permitAll() 
    			.antMatchers("/slead/getStatesByCountry").permitAll()
    			.antMatchers("/slead/getCitiesByStateAndCountry").permitAll()
    			.antMatchers("/slead/saveProposeTime").permitAll()
    			.antMatchers("/slead/jobAlertRequirementSummary").permitAll()
    			.antMatchers("/slead/proposeNewTimePublicpage").permitAll()
    			.antMatchers("/slead/getJobDetailsForJobAlert").permitAll()
    			.antMatchers("/slead/storeJobAlert").permitAll()
    			.antMatchers("/slead/validateAlertUserEmail").permitAll()
    			.antMatchers("/slead/getInterviewrsViewFeedbackList").permitAll()
    			.antMatchers("/slead/savePublicInterviewFeddbackTemlateReason").permitAll()
    			.antMatchers("/slead/savePublicInterviewFeddbackTemlate").permitAll()
    			.antMatchers("/slead/getPublicInterviewFeedbackTemplate").permitAll()
    			.antMatchers("/slead/validateCrowdSourcingRegistrationEmail").permitAll()
    			.antMatchers("/slead/requestedDocuments").permitAll()
    			.antMatchers("/slead/uploadRequestedDocuments").permitAll()
    			.antMatchers("/slead/viewAllJobAlerts").permitAll()
    			.antMatchers("/slead/getAllJobsAlertList").permitAll()
    			.antMatchers("/slead/upload/*").permitAll()
    			.antMatchers("/slead/static-web/*").permitAll()
    			.antMatchers("/slead/viewJobSummaryDocumentDocView").permitAll()
    			.antMatchers("/slead/check-session").permitAll()
    			.antMatchers("/").permitAll()
    			.antMatchers("/**").permitAll()
    			
    			/*.antMatchers("/**").access("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_APPROVER') OR hasRole('ROLE_SOURCELEAD_ADMIN') OR hasRole('ROLE_HR_MANAGER') OR hasRole('ROLE_ACCOUNT_MANAGER') OR hasRole('ROLE_SALES_MANAGER') OR hasRole('ROLE_RECRUITER') ")*/ 
    			.antMatchers("/slead/**").fullyAuthenticated()
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