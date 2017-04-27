package com.bis.sourcelead.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.util.CommonUtil;
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.api.AuthenticationApi.LoginOptions;
import com.docusign.esign.api.EnvelopesApi.ListStatusChangesOptions;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;

/**
 * @author Ajith Matta
 * @date 31/08/2016
 * @author shivayogi
 * @date 02/12/2016
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementAssignToServiceTest {
	
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
		@Autowired
		RequirementAssignToService requirementAssignToService ;
		private static final Logger logger = LoggerFactory.getLogger(RequirementAssignToServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		//@Test
		public void storeRequirement() throws Exception{
			Long requirementId=1l;
			List<RequirementAssignTo> AssignTo =requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
			for(RequirementAssignTo requirementAssignTo:AssignTo){
				requirementAssignTo.getId();
				logger.info("success");
			}
		}
			//@Test
			public void getRequirementAssignToByRequirementIdandAssignToId() throws Exception{
				Long requirementId=1l;
				Long assignToId=1l;
				requirementAssignToService.getRequirementAssignToByRequirementIdandAssignToId(requirementId, assignToId);
					logger.info("success");
				}
			
			
			//@Test
			public void getAssignedTeamMembersCountByTeamMemberUserId() throws Exception{
				Long assignUserId=1l;
				String jobType="OPEN";
				requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(assignUserId, jobType);
					logger.info("success");
			}
			
			//@Test
			public void getRequirementAssignToByRequirementId() throws Exception{
				Long requirementId=1l;
				requirementAssignToService.getRequirementAssignToByRequirementId(requirementId);
					logger.info("success");
			}
			
			//@Test
			public void checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId() throws Exception{
				Long requirementId=1l;
				Long userId=1l;
				requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, userId);
					logger.info("success");
			}
			
			//@Test
			public void deleteRequirementAssignToByRequirementId() throws Exception{
				Long requirementId=1l;
				requirementAssignToService.deleteRequirementAssignToByRequirementId(requirementId);
					logger.info("success");
			}
			
			@Test
			public void testing() throws Exception{
			
			List<Contracts> conList =  contractsTemplateService.getListOfContractsEnvId();
			
			String UserName = "prasannavadla9@gmail.com";
			String Password = "prasanna";    
			String IntegratorKey = "d5a84b14-1b12-4735-a455-604a8f7c9754";

			// for production environment update to "www.docusign.net/restapi"
			String BaseUrl = "https://demo.docusign.net/restapi";

	        // list of user account(s)
	        List<LoginAccount> loginAccounts = null;
	        
	        // initialize the api client
	        ApiClient apiClient = new ApiClient();
	        apiClient.setBasePath(BaseUrl);
	        
	        // create JSON formatted auth header
	        String creds = "{\"Username\":\"" +  UserName + "\",\"Password\":\"" +  Password + "\",\"IntegratorKey\":\"" +  IntegratorKey + "\"}";
	        apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);
	        
	        // assign api client to the Configuration object
	        Configuration.setDefaultApiClient(apiClient);
	        
	        //===============================================================================
	        // Step 1:  Login() API
	        //===============================================================================
	        try
	        {
	        	// login call available off the AuthenticationApi
	        	AuthenticationApi authApi = new AuthenticationApi();
	        	
	        	// login has some optional parameters we can set
	            AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
	            loginOps.setApiPassword("true");
	            loginOps.setIncludeAccountIdGuid("true");
	            LoginInformation loginInfo = authApi.login(loginOps);
	         
	            // note that a given user may be a member of multiple accounts
	            loginAccounts = loginInfo.getLoginAccounts();
	            
	            System.out.println("LoginInformation: " + loginAccounts);
	        
	        
	        //===============================================================================
	        // Step 2:  List Envelopes API 
	        //===============================================================================
	            String accountId = loginAccounts.get(0).getAccountId();
	            // instantiate a new EnvelopesApi object
	            EnvelopesApi envelopesApi = new EnvelopesApi();  
	            
	            // the list status changes call requires at least a from_date
	            EnvelopesApi.ListStatusChangesOptions options = envelopesApi.new ListStatusChangesOptions();
	            
	           /* // set from date to filter envelopes (ex: Dec 1, 2015)
	            options.setFromDate("2017/04/01");*/
	            
	            // call the listStatusChanges() API
	           /* EnvelopesInformation envelopes = envelopesApi.listStatusChanges(accountId, options);
	            List<Envelope> env = envelopes.getEnvelopes();*/
	            
	            
	            for (Contracts contracts : conList) {
	            	if(contracts.getEnvelopId()!=null){
	            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
	            		Contracts pCt = contractsTemplateService.getContractById(contracts.getId());
	            		childContarct.setId(childContarct.getId());
	            		pCt.setId(contracts.getId());
	            	Envelope evp =  envelopesApi.getEnvelope(accountId, contracts.getEnvelopId());
	            	if(evp.getStatus().equalsIgnoreCase("completed")){
	        			if(null != childContarct.getParentContractId()){
	        				childContarct.setId(childContarct.getId());
	        				childContarct.setTemplateSubmissionStatus("Send For Verification");
	        				contracts.setId(contracts.getId());
	        				pCt.setTemplateSubmissionStatus("Waiting To Be Verify");
	        			}
	            	}
	            	if(evp.getStatus().equalsIgnoreCase("declined")){
	        			if(null != childContarct.getParentContractId()){
	        				childContarct.setId(childContarct.getId());
	        				childContarct.setTemplateSubmissionStatus("Rejected");
	        				pCt.setTemplateSubmissionStatus("Rejected");
	        			}
	            	}
	            	if(evp.getStatus().equalsIgnoreCase("deleted")){
	            		
	        			if(null != childContarct.getParentContractId()){
	        				childContarct.setTemplateSubmissionStatus("deleted");
	        				pCt.setTemplateSubmissionStatus("deleted");
	        			}
	            	}
	            	childContarct.setContractStatus(evp.getStatus());
//	            	childContarct.setContractsTemplate(pCt.getContractsTemplate());
	            	pCt.setContractStatus(evp.getStatus());
	            	contractsTemplateService.saveNewContract(childContarct);
	            	contractsTemplateService.saveNewContract(pCt);
				}
	           
	            }
	        }
	        catch (com.docusign.esign.client.ApiException ex)
	        {
	            System.out.println("Exception: " + ex);
	        }
			}
			
	}
		

