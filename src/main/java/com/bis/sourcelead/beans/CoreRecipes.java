package com.bis.sourcelead.beans;
/* CoreRecipes.java
 * 
 * Simple Class that demonstrates how to accomplish various REST API use-cases.  
 */

import java.util.ArrayList;

// DocuSign imports
/* CoreRecipes.java
	 * 
	 * Simple Class that demonstrates how to accomplish various REST API use-cases.  
	 */

// DocuSign imports
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.CustomFields;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.LoginInformation;
import com.docusign.esign.model.TemplateCustomFields;
import com.docusign.esign.model.TemplateRole;
import com.docusign.esign.model.TextCustomField;

	public class CoreRecipes {public static void main(String[] args) {
	    String username = "rajasekhar.chelpuri@gmail.com";
	    String password = "rajashekarbis";
	    String integratorKey = "52592fb6-498c-4311-979c-1dfe90de6555";
	    
	    // initialize client for desired environment and add X-DocuSign-Authentication header
	    ApiClient apiClient = new ApiClient("https://demo.docusign.net/restapi");
	    
	    // configure 'X-DocuSign-Authentication' authentication header
	    String authHeader = "{\"Username\":\"" +  username + "\",\"Password\":\"" +  password + "\",\"IntegratorKey\":\"" +  integratorKey + "\"}";
	    // If you have an OAuth access token stored in a variable named 'access_token', let's say, then instead, you can set authHeader as following (notice the extra space after 'Bearer'):
	    // String authHeader = "{\"Bearer \":\"" +  access_token + "\"}";
	    apiClient.addDefaultHeader("X-DocuSign-Authentication", authHeader);
	    Configuration.setDefaultApiClient(apiClient);
	    try
	    {
	      /////////////////////////////////////////////////////////////////////////////////////////////////////////
	      // STEP 1: AUTHENTICATE TO RETRIEVE ACCOUNTID & BASEURL         
	      /////////////////////////////////////////////////////////////////////////////////////////////////////////

	      AuthenticationApi authApi = new AuthenticationApi();
	      LoginInformation loginInfo = authApi.login();
	      
	      // parse first account ID (user might belong to multiple accounts) and baseUrl
	      String accountId = loginInfo.getLoginAccounts().get(0).getAccountId(); 
	      String baseUrl = loginInfo.getLoginAccounts().get(0).getBaseUrl();
	      String[] accountDomain = baseUrl.split("/v2");
	      
	      // below code required for production, no effect in demo (same domain) 
	      apiClient.setBasePath(accountDomain[0]);
	      Configuration.setDefaultApiClient(apiClient);
	      
	      /////////////////////////////////////////////////////////////////////////////////////////////////////////
	      // *** STEP 2: CREATE ENVELOPE FROM TEMPLATE       
	      /////////////////////////////////////////////////////////////////////////////////////////////////////////
	      
	      // create a new envelope to manage the signature request
	      EnvelopeDefinition envDef = new EnvelopeDefinition();
	      envDef.setEmailSubject("BIS USA");
	      
	      // assign template information including ID and role(s)
	      String templateId = "b1fdea9f-d3ff-4796-8496-25d91afb82c3";
	      envDef.setTemplateId(templateId);
	      
	      // create a template role with a valid templateId and roleName and assign signer info
	      TemplateRole tRole = new TemplateRole();
	      tRole.setRoleName("user");
	      tRole.setName("Prasanna");
	      tRole.setEmail("rajashekarchelpuri525@gmail.com");
	      // create a list of template roles and add our newly created role
	      java.util.List<TemplateRole> templateRolesList = new java.util.ArrayList<TemplateRole>();
	      templateRolesList.add(tRole);
	      TemplatesApi templateApi = new TemplatesApi();
	     
	      TemplateCustomFields templateCustomFields = new TemplateCustomFields();
	      TextCustomField textCustomField = new TextCustomField();
	      textCustomField.setFieldId("1");
	      textCustomField.setName("username");
	      textCustomField.setRequired("true");
	      textCustomField.setShow("true");
	      textCustomField.setValue("Rajashekar");
	      
	      TextCustomField textCustomField1 = new TextCustomField();
	      textCustomField1.setFieldId("2");
	      textCustomField1.setName("Lastname");
	      textCustomField1.setRequired("true");
	      textCustomField1.setShow("true");
	      textCustomField1.setValue("Chelpuri");
	      java.util.List<TextCustomField> listCustomField = new ArrayList<>();
	      listCustomField.add(textCustomField);
	      listCustomField.add(textCustomField1);
	      templateCustomFields.setTextCustomFields(listCustomField);
	     CustomFields  custo =  templateApi.createCustomFields(accountId, templateId, templateCustomFields);
//	     CustomFields  custo =  templateApi.listCustomFields(accountId, templateId);
	     
	     System.out.println("******************************* "+custo);
	     envDef.setCustomFields(custo);
	      // assign template role(s) to the envelope 
	      envDef.getCustomFields().setTextCustomFields(listCustomField);
//	      envDef.getCustomFields().setListCustomFields(listCustomFields);
	      envDef.setTemplateRoles(templateRolesList);
	      
	      // ************************* create custom fields
	     
	      
	      // ************************* end custom fields
	      
	      // send the envelope by setting |status| to "sent". To save as a draft set to "created"
	      envDef.setStatus("sent");
	    
	      // instantiate a new EnvelopesApi object
	      EnvelopesApi envelopesApi = new EnvelopesApi();
	    
	      // call the createEnvelope() API
	      EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
	      System.out.println(envelopeSummary);
	    }
	    catch (com.docusign.esign.client.ApiException ex)
	    {
	      System.out.println("Exception: " + ex);
	    }
	  }
//	}  // end class

	
    //public static final String IntegratorKey = "52592fb6-498c-4311-979c-1dfe90de6555";	
	
}

