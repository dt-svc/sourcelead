package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class SignatureRequestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SignatureRequestUtil.class);
	
	
	@Autowired
	private Html2PdfUtil html2PdfUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	public Contracts sendDocumentForSignatureRequest(User vendorUser, ContractsTemplate contractsTemplate, OrgLocations orgLocation, User loginUser, Contracts contracts, Org org, RequirementSubmission requirementSubmission) throws Exception{
		
		// Enter your DocuSign credentials:
		String UserName = "sourcelead@rediffmail.com";
		String Password = "Pwd!123";    
		String IntegratorKey = "1bf5d901-629e-4ec3-a915-8991fd08206a";

		// for production environment update to "www.docusign.net/restapi"
		String BaseUrl = "https://demo.docusign.net/restapi";

		// initialize the api client for the desired environment
		ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(BaseUrl);

		// create JSON formatted auth header
		String creds = "{\"Username\":\"" +  UserName + "\",\"Password\":\"" +  Password + "\",\"IntegratorKey\":\"" +  IntegratorKey + "\"}";
		apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);

		// assign api client to the Configuration object
		Configuration.setDefaultApiClient(apiClient);

		// create an empty list that we will populate with account(s)
		List<LoginAccount> loginAccounts = null;
		
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

//			logger.info("LoginInformation: " + loginAccounts);
		}
		catch (com.docusign.esign.client.ApiException ex)
		{
			logger.info("Exception: " + ex);
		}
		
		// create a byte array that will hold our document bytes
		byte[] fileBytes = null;

		try
		{
//			String currentDir = System.getProperty("user.dir");
//			// read file from a local directory
//			Path path = Paths.get(currentDir + SignTest1File);
//			fileBytes = html2PdfUtil.getPdfBytes(contractsTemplate);
			fileBytes = html2PdfUtil.getPdfBytes(contractsTemplate,contracts,orgLocation,org);		}
		catch (IOException ioExcp)
		{
			// handle error
			logger.info("Exception: " + ioExcp);
		}
		// create an envelope that will store the document(s), tab(s), and recipient(s)
		EnvelopeDefinition envDef = new EnvelopeDefinition();
		envDef.setEmailSubject("Business Intelli Solutions : Please sign this doc");
		// add a document to the envelope
		Document doc = new Document();  
		String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
		doc.setDocumentBase64(base64Doc);
		
		doc.setName(contractsTemplate.getTemplateName()+".pdf");    // can be different from actual file name
		/*doc.setDocumentId("1");*/
		doc.setDocumentId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
		// create a list of docs and add it to the envelope
		List<Document> docs = new ArrayList<Document>();
		docs.add(doc);
		envDef.setDocuments(docs);
		
	        PdfReader pdfReader = new PdfReader(fileBytes);
	        Integer numberOfPages = pdfReader.getNumberOfPages();

		// add a recipient to sign the document, identified by name, email, and recipientId
		Signer signer = new Signer();
		if("Full time offer".equalsIgnoreCase(contracts.getContractType())){
			signer.setEmail(requirementSubmission.getCandidate().getEmail());
			signer.setName(requirementSubmission.getCandidate().getFirstName());
		}
		else{
			signer.setEmail(vendorUser.getEmail());
			signer.setName(vendorUser.getFirstName());
		}
		signer.setRecipientId("1");

		// create a signHere tab somewhere on the document for the signer to sign
		// default unit of measurement is pixels, can be mms, cms, inches also
		SignHere signHere = new SignHere();
		signHere.setDocumentId(doc.getDocumentId());
		signHere.setPageNumber(numberOfPages.toString());
		signHere.setRecipientId("1");
		signHere.setXPosition("250");
		signHere.setYPosition("750");

		// can have multiple tabs, so need to add to envelope as a single element list
		List<SignHere> signHereTabs = new ArrayList<SignHere>();
		signHereTabs.add(signHere);
		Tabs tabs = new Tabs();
		tabs.setSignHereTabs(signHereTabs);
		signer.setTabs(tabs);

		// add recipients (in this case a single signer) to the envelope
		envDef.setRecipients(new Recipients());
		envDef.getRecipients().setSigners(new ArrayList<Signer>());
		envDef.getRecipients().getSigners().add(signer);

		// send the envelope by setting |status| to "sent". To save as a draft set to "created"
		envDef.setStatus("sent");

		
			// use the |accountId| we retrieved through the Login API to create the Envelope
			String accountId = loginAccounts.get(0).getAccountId();
			
			// instantiate a new EnvelopesApi object
			EnvelopesApi envelopesApi = new EnvelopesApi();
			
			// call the createEnvelope() API to send the signature request!
			EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
			
			Contracts con  = new Contracts();
			con.setTempdocId(doc.getDocumentId());
			con.setTempEnvId(envelopeSummary.getEnvelopeId());
			con.setContractStatus(envDef.getStatus());
		
		
		return con;
	}

	public Contracts sendDocumentForSignatureRequestForCustom(User vendorUser, Contracts contracts,
			OrgLocations orgLocation, User user, Contracts savedContracts, Org org, RequirementSubmission requirementSubmission) throws Exception {
		// Enter your DocuSign credentials:
				String UserName = "sourcelead@rediffmail.com";
				String Password = "Pwd!123";    
				String IntegratorKey = "1bf5d901-629e-4ec3-a915-8991fd08206a";

				// for production environment update to "www.docusign.net/restapi"
				String BaseUrl = "https://demo.docusign.net/restapi";

				// initialize the api client for the desired environment
				ApiClient apiClient = new ApiClient();
				apiClient.setBasePath(BaseUrl);

				// create JSON formatted auth header
				String creds = "{\"Username\":\"" +  UserName + "\",\"Password\":\"" +  Password + "\",\"IntegratorKey\":\"" +  IntegratorKey + "\"}";
				apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);

				// assign api client to the Configuration object
				Configuration.setDefaultApiClient(apiClient);

				// create an empty list that we will populate with account(s)
				List<LoginAccount> loginAccounts = null;
				
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

//					logger.info("LoginInformation: " + loginAccounts);
				}
				catch (com.docusign.esign.client.ApiException ex)
				{
					logger.info("Exception: " + ex);
				}
				
				// create a byte array that will hold our document bytes
				byte[] fileBytes = readBytesFromFile(staticPath+contracts.getFileURL());
				
				 PdfReader pdfReader = new PdfReader(fileBytes);
			        Integer numberOfPages = pdfReader.getNumberOfPages();
				// create an envelope that will store the document(s), tab(s), and recipient(s)
				EnvelopeDefinition envDef = new EnvelopeDefinition();
				envDef.setEmailSubject("Business Intelli Solutions : Please sign this doc");
				// add a document to the envelope
				Document doc = new Document();  
				String base64Doc = Base64.getEncoder().encodeToString(fileBytes);
				doc.setDocumentBase64(base64Doc);
				doc.setName(contracts.getFileName()+".pdf");    // can be different from actual file name
				doc.setDocumentId(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode());
				// create a list of docs and add it to the envelope
				List<Document> docs = new ArrayList<Document>();
				docs.add(doc);
				envDef.setDocuments(docs);

				// add a recipient to sign the document, identified by name, email, and recipientId
				Signer signer = new Signer();
				if("Full time offer".equalsIgnoreCase(contracts.getContractType())){
					signer.setEmail(requirementSubmission.getCandidate().getEmail());
					signer.setName(requirementSubmission.getCandidate().getFirstName());
				}
				else{
					signer.setEmail(vendorUser.getEmail());
					signer.setName(vendorUser.getFirstName());
				}
				signer.setRecipientId("1");

				// create a signHere tab somewhere on the document for the signer to sign
				// default unit of measurement is pixels, can be mms, cms, inches also
				SignHere signHere = new SignHere();
				signHere.setDocumentId(doc.getDocumentId());
				signHere.setPageNumber(numberOfPages.toString());
				signHere.setRecipientId("1");
				signHere.setXPosition("250");
				signHere.setYPosition("250");

				// can have multiple tabs, so need to add to envelope as a single element list
				List<SignHere> signHereTabs = new ArrayList<SignHere>();
				signHereTabs.add(signHere);
				Tabs tabs = new Tabs();
				tabs.setSignHereTabs(signHereTabs);
				signer.setTabs(tabs);

				// add recipients (in this case a single signer) to the envelope
				envDef.setRecipients(new Recipients());
				envDef.getRecipients().setSigners(new ArrayList<Signer>());
				envDef.getRecipients().getSigners().add(signer);

				// send the envelope by setting |status| to "sent". To save as a draft set to "created"
				envDef.setStatus("sent");

					// use the |accountId| we retrieved through the Login API to create the Envelope
					String accountId = loginAccounts.get(0).getAccountId();
					// instantiate a new EnvelopesApi object
					EnvelopesApi envelopesApi = new EnvelopesApi();
					// call the createEnvelope() API to send the signature request!
					EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
					
					Contracts con  = new Contracts();
					con.setTempdocId(doc.getDocumentId());
					con.setTempEnvId(envelopeSummary.getEnvelopeId());
					con.setContractStatus(envDef.getStatus());
					//logger.info("EnvelopeSummary: " + envelopeSummary);
				return con;
	}
	
	
	private static byte[] readBytesFromFile(String filePath)
	{
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {
			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];
			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytesArray;
	}
	
}
