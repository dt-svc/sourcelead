package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.EmailScenarioDTO;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class EmailTemplateRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateRestController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	
		 
	 @RequestMapping(value = "/getEmailTemplatesList" , method = RequestMethod.GET)
		public @ResponseBody List<EmailScenario> getEmailTemplatesList(HttpServletRequest request,@RequestParam(value="tabIds", required=false) String tabIds,@RequestParam(value="specification", required=false) String specification)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 List<EmailScenario> emailScenarioList=new ArrayList<>();
		 List<EmailScenario> emailScenarioDefList=null;
		 List<EmailScenario> emailScenarioOrgList=null;
		 
		 List<Long> tabList = new ArrayList<>();
		 
		 if(tabIds != null && !tabIds.isEmpty()) {
				String[] entyties = tabIds.split(",");
				for(int i=0; i < entyties.length; i++) {
					tabList.add(Long.parseLong(entyties[i]));
				}
			}
		 
		 List<String> specList = new ArrayList<>();
		 
		 if(specification != null && !specification.isEmpty()) {
				String[] specifi = specification.split(",");
				for(int i=0; i < specifi.length; i++) {
					specList.add(specifi[i]);
				}
			}
		 
		 if(org.getOrgCode().equalsIgnoreCase("slead")){
			 emailScenarioList= emailScenarioService.getEmailScenarioList("sourcelead",tabList,specList);
		 }else{
			 emailScenarioDefList=emailScenarioService.getEmailScenarioList("org",tabList,specList);
			 Map<String,EmailScenario> emailScenarioDef=new HashMap<>();
			 Map<String,EmailScenario> emailScenarioOrg=new HashMap<>();
			 for(EmailScenario emailScenario:emailScenarioDefList){
				 emailScenarioDef.put(emailScenario.getUniqueScenarioName(),emailScenario);
			 }
			 emailScenarioOrgList=emailScenarioService.getEmailScenarioListByOrgLocId(orgLocation.getId());
			 for(EmailScenario emailScenariotwo:emailScenarioOrgList){
				 emailScenarioOrg.put(emailScenariotwo.getUniqueScenarioName(),emailScenariotwo);
			 }
			 
			 for (Map.Entry<String, EmailScenario> defentry : emailScenarioDef.entrySet()) {
				    for(Map.Entry<String, EmailScenario> orgentry : emailScenarioOrg.entrySet()){
				    	if(defentry.getKey().equalsIgnoreCase(orgentry.getKey())){
				    		emailScenarioDef.put(orgentry.getKey(), orgentry.getValue());
				    	}
				    }
				}
			 for (Map.Entry<String, EmailScenario> finalEmailsce : emailScenarioDef.entrySet()) {
				 emailScenarioList.add((EmailScenario)finalEmailsce.getValue());
			 }
		 }
		 return emailScenarioList;
		 
		}
	 
				
	 @RequestMapping(value = "/storeEmailTemplates" , method = RequestMethod.POST)
		public @ResponseBody String storeEmailTemplates(EmailScenarioDTO emailScenarioDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 EmailScenario emailScenario=null;
		 if(emailScenarioDTO.getId() != null){
			  emailScenario=emailScenarioService.getEmailTemplateById(Long.parseLong(emailScenarioDTO.getId()));
		 }
		 if(org.getOrgCode().equalsIgnoreCase("slead") && emailScenario != null&& emailScenario.getDefaultTemplate().equalsIgnoreCase("Y")){
			 
				emailScenario.setScenarioName(emailScenarioDTO.getScenarioName());
				emailScenario.setBody(emailScenarioDTO.getBody());
				emailScenario.setSubject(emailScenarioDTO.getSubject());
				emailScenario.setSignature(emailScenarioDTO.getSignature());
				emailScenarioService.save(emailScenario);
		 }else if(emailScenario != null && emailScenario.getDefaultTemplate().equalsIgnoreCase("N")){
			 
				emailScenario.setSubject(emailScenarioDTO.getSubject());
				emailScenario.setSignature(emailScenarioDTO.getSignature());
				emailScenario.setScenarioName(emailScenarioDTO.getScenarioName());
				emailScenario.setBody(emailScenarioDTO.getBody());
				emailScenarioService.save(emailScenario);
		 }else{
			
			 EmailScenario newEmailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(emailScenario.getUniqueScenarioName(), emailScenario.getEntityType(), orgLocation.getId());
			 if(newEmailScenario == null) {
				 newEmailScenario = new EmailScenario();
			 }
			 newEmailScenario.setBody(emailScenarioDTO.getBody());
			 newEmailScenario.setScenarioName(emailScenarioDTO.getScenarioName());
			 newEmailScenario.setEntityType(emailScenario.getEntityType());
			 newEmailScenario.setUniqueScenarioName(emailScenario.getUniqueScenarioName());
			 newEmailScenario.setDescription(emailScenario.getDescription());
			 newEmailScenario.setSubject(emailScenarioDTO.getSubject());
			 newEmailScenario.setTemplateType(emailScenario.getTemplateType());
			 newEmailScenario.setSignature(emailScenarioDTO.getSignature());
			 newEmailScenario.setDefaultTemplate("N");
			 newEmailScenario.setOrgLocationId(orgLocation);
			 newEmailScenario.setEmailUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			 newEmailScenario.setIsTemplateModified("Y");
			 newEmailScenario.setOrgCode(orgLocation.getOrgCode());
			 newEmailScenario.setLocationCode(orgLocation.getLocationCode());
			 newEmailScenario.setTabId(emailScenario.getTabId());
			 newEmailScenario.setParentId(emailScenario);
			 emailScenarioService.save(newEmailScenario);
		 }
		  return "success";
			
		}
	 
	 @RequestMapping(value = "/viewEmailTemplate" , method = RequestMethod.GET)
		public @ResponseBody EmailScenario viewEmailTemplate(EmailScenarioDTO emailScenarioDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 EmailScenarioDTO emailScenarioDto=new EmailScenarioDTO();
		  EmailScenario emailScenario= emailScenarioService.getEmailTemplateById(Long.parseLong(emailScenarioDTO.getId()));
		/*String body = (emailScenario.getBody()).replaceAll("(\r\n|\n)", "<br />");
		emailScenario.setBody(body);*/
		  
		  return emailScenario;
			
		}
	 
}