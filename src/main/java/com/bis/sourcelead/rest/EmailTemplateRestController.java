package com.bis.sourcelead.rest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.EmailScenarioDTO;
import com.bis.sourcelead.entity.EmailScenario;
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
		public @ResponseBody List<EmailScenario> getEmailTemplatesList(HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 List<EmailScenario> emailScenarioList=null;
		 if(user.getUserCode().equalsIgnoreCase("sladmin")){
			 emailScenarioList= emailScenarioService.getEmailScenarioList();
		 }else{
			 emailScenarioList=emailScenarioService.getEmailScenarioList("sourceLead");
		 }
		 return emailScenarioList;
		 
		}
				
	 @RequestMapping(value = "/storeEmailTemplates" , method = RequestMethod.POST)
		public @ResponseBody String storeEmailTemplates(EmailScenarioDTO emailScenarioDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 EmailScenario emailScenario=null;
		 if(emailScenarioDTO.getId() != null){
			  emailScenario=emailScenarioService.getEmailTemplateById(Long.parseLong(emailScenarioDTO.getId()));
		 }
		 if(user.getUserCode().equalsIgnoreCase("sladmin") && emailScenario != null&& emailScenario.getDefaultTemplate().equalsIgnoreCase("Y")){
			 String path=staticPath.substring(0, staticPath.lastIndexOf("/"));
			 String bodyUrl=emailScenario.getBodyUrl();
			 String fileName=emailScenario.getUniqueScenarioName();
			 String entityType=emailScenario.getEntityType();
			 try{
					File file = new File(path+bodyUrl);
					file.delete();
				}catch(Exception e){
					e.printStackTrace();
					
				}
			 String link=null;
			 
			 File folder = new File(staticPath+"/emailDocuments/");
				if(!folder.exists())
					folder.mkdirs();
				File createFile = new File(folder,fileName+entityType+".txt");
				link = "/slead-static/"+"emailDocuments/"+createFile.getName();
				try{
					BufferedWriter bw=new BufferedWriter(new FileWriter(createFile));
					bw.write(emailScenarioDTO.getBody());
					bw.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			 	/*
				File folder = new File(staticPath+"/"+orgLocation.getId()+"/emailTemplates/");
				if (!folder.exists())
					folder.mkdirs();
				File file=new File(folder,title+".txt");
				link = "/slead-static/"+orgLocation.getId()+"/emailTemplates/"+file.getName();
				try{
					BufferedWriter bw=new BufferedWriter(new FileWriter(file));
					bw.write(emailScenarioDTO.getBody());
					bw.close();
				}catch(Exception e){
					e.printStackTrace();
				}*/
				emailScenario.setBodyUrl(link);
				emailScenario.setSubject(emailScenarioDTO.getSubject());
				emailScenario.setSignature(emailScenarioDTO.getSignature());
				emailScenarioService.save(emailScenario);
		 }
		 /*if(emailScenario != null && emailScenario.getDefaultTemplate().equalsIgnoreCase("N")){
			 String path=staticPath.substring(0, staticPath.lastIndexOf("/"));
			 String bodyUrl=emailScenario.getBodyUrl();
			 try{
					File file = new File(path+bodyUrl);
					file.delete();
				}catch(Exception e){
					e.printStackTrace();
					
				}
			 String link=null;
			 String title=null;
			 	if(emailScenarioDTO.getUniqueScenarioName() != null){
			 		 title=emailScenarioDTO.getUniqueScenarioName()+SourceLeadRandomCodeHelper.generateRandomOrgCode();
			 	}
				File folder = new File(staticPath+"/"+orgLocation.getId()+"/emailTemplates/");
				if (!folder.exists())
					folder.mkdirs();
				File file=new File(folder,title+".txt");
				link = "/slead-static/"+orgLocation.getId()+"/emailTemplates/"+file.getName();
				try{
					BufferedWriter bw=new BufferedWriter(new FileWriter(file));
					bw.write(emailScenarioDTO.getBody());
					bw.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				emailScenario.setBodyUrl(link);
				emailScenarioService.save(emailScenario);
		 }else{
			 EmailScenario defEmailScenario=null;
			 if(emailScenario != null ){
				  defEmailScenario= emailScenarioService.getEmailDefTemplateNo(emailScenario.getUniqueScenarioName(), "N");
				  if(defEmailScenario!=null){
					  String path=staticPath.substring(0, staticPath.lastIndexOf("/"));
						 String bodyUrl=defEmailScenario.getBodyUrl();
						 try{
								File file = new File(path+bodyUrl);
								file.delete();
							}catch(Exception e){
								e.printStackTrace();
								
							}
						 String link=null;
						 String title=null;
						 	if(emailScenarioDTO.getUniqueScenarioName() != null){
						 		 title=emailScenarioDTO.getUniqueScenarioName()+SourceLeadRandomCodeHelper.generateRandomOrgCode();
						 	}
							File folder = new File(staticPath+"/"+orgLocation.getId()+"/emailTemplates/");
							if (!folder.exists())
								folder.mkdirs();
							File file=new File(folder,title+".txt");
							link = "/slead-static/"+orgLocation.getId()+"/emailTemplates/"+file.getName();
							try{
								BufferedWriter bw=new BufferedWriter(new FileWriter(file));
								bw.write(emailScenarioDTO.getBody());
								bw.close();
							}catch(Exception e){
								e.printStackTrace();
							}
							defEmailScenario.setBodyUrl(link);
							emailScenarioService.save(defEmailScenario);
				  }else{
						 String link=null;
						 String title=null;
						 	if(emailScenarioDTO.getUniqueScenarioName() != null){
						 		 title=emailScenarioDTO.getUniqueScenarioName()+SourceLeadRandomCodeHelper.generateRandomOrgCode();
						 	}
							File folder = new File(staticPath+"/"+orgLocation.getId()+"/emailTemplates/");
							if (!folder.exists())
								folder.mkdirs();
							File file=new File(folder,title+".txt");
							link = "/slead-static/"+orgLocation.getId()+"/emailTemplates/"+file.getName();
							try{
								BufferedWriter bw=new BufferedWriter(new FileWriter(file));
								bw.write(emailScenarioDTO.getBody());
								bw.close();
							}catch(Exception e){
								e.printStackTrace();
							}
						
						 EmailScenario newEmailScenario=new EmailScenario();
						 newEmailScenario.setBodyUrl(link);
						 newEmailScenario.setScenarioName(emailScenario.getScenarioName());
						 newEmailScenario.setEntityType(emailScenario.getEntityType());
						 newEmailScenario.setUniqueScenarioName(emailScenario.getUniqueScenarioName());
						 newEmailScenario.setDescription(emailScenario.getDescription());
						 newEmailScenario.setSubject(emailScenario.getSubject());
						 newEmailScenario.setTemplateType(emailScenario.getTemplateType());
						 newEmailScenario.setSignature(emailScenario.getSignature());
						 newEmailScenario.setBodyUrl(link);
						 newEmailScenario.setDefaultTemplate("N");
						 newEmailScenario.setOrgLocId(orgLocation.getId());
						 newEmailScenario.setEmailUUID(SourceLeadRandomCodeHelper.generateRandomOrgCode());
						 newEmailScenario.setIsTemplateModified("Y");
						 newEmailScenario.setOrgCode(orgLocation.getOrgCode());
						 newEmailScenario.setLocationCode(orgLocation.getLocationCode());
						 newEmailScenario.setTabId(emailScenario.getTabId());
						 emailScenarioService.save(newEmailScenario);
					 }
			 }
			
		 }*/
		 
		  return "success";
			
		}
	 
	 @RequestMapping(value = "/viewEmailTemplate" , method = RequestMethod.GET)
		public @ResponseBody EmailScenarioDTO viewEmailTemplate(EmailScenarioDTO emailScenarioDTO,HttpServletRequest request)
				throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 EmailScenarioDTO emailScenarioDto=new EmailScenarioDTO();
		  EmailScenario emailScenario= emailScenarioService.getEmailTemplateById(Long.parseLong(emailScenarioDTO.getId()));
		  if(emailScenario != null){
			  emailScenarioDto.setSubject(emailScenario.getSubject());
			  emailScenarioDto.setSignature(emailScenario.getSignature());
			  emailScenarioDto.setBody(emailScenario.getBody());
		  }
		  return emailScenarioDto;
			
		}
	 
}