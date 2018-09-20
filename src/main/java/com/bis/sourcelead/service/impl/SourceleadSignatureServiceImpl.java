package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadSignatureDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadSignature;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class SourceleadSignatureServiceImpl implements SourceleadSignatureService{
	
	@Autowired
	SourceleadSignatureDao sourceleadSignatureDao;
	
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public List<SourceleadSignature> getSourceLeadSignatureByLocationId(Long orgLocationId) throws Exception {
		return sourceleadSignatureDao.getSourceLeadSignatureByLocationId(orgLocationId);
	}
	
	@Override
	public List<SourceleadSignature> getSourceLeadSignature() throws Exception {
		return sourceleadSignatureDao.getSourceLeadSignature();
	}

	@Override
	public SourceleadSignature saveSourceleadSignature(SourceleadSignature signature) throws Exception {
		signature.setUpdatedDate(commonUtil.currentDate());
		return sourceleadSignatureDao.saveSourceleadSignature(signature);
	}

	
	@Override
	public SourceleadSignature getSourceleadSignatureByLabelNameAndOrgLocationId(String labelName, Long orgLocationId)
			throws Exception {
		return sourceleadSignatureDao.getSourceleadSignatureByLabelNameAndOrgLocationId(labelName, orgLocationId);
	}
	
	
	@Override
	public void saveOrgSignatrue(JSONObject jsonObj, OrgLocations orgLocations, User user) throws Exception {
		
		List<SourceleadSignature> signaturesList = null;
		
		signaturesList = sourceleadSignatureDao.getSourceLeadSignatureByLocationId(orgLocations.getId());
		if(signaturesList.size() == 0){
			signaturesList = sourceleadSignatureDao.getSourceLeadSignature();
		}
		
		for(SourceleadSignature signature : signaturesList){
			
			StringBuilder fontStyle = new StringBuilder();
			
			SourceleadSignature orgSign = sourceleadSignatureDao.getSourceleadSignatureByLabelNameAndOrgLocationId(signature.getLabelName(), orgLocations.getId());
			if(orgSign == null){
				orgSign = new SourceleadSignature();
			}
			
			orgSign.setOrgLocationId(orgLocations);
			orgSign.setLabelName(signature.getLabelName());
			if(signature.getLabelName().equalsIgnoreCase("Sign Off")){
				orgSign.setLabelText(jsonObj.getString("SignOff"));
			}
			
			if(jsonObj.has("fontFamily"+signature.getId())){
				orgSign.setFontFamily(jsonObj.getString("fontFamily"+signature.getId()));
			}
			
			if(jsonObj.has("fontsize"+signature.getId())){
				orgSign.setFontSize(jsonObj.getString("fontsize"+signature.getId()));
			}
			
			if(jsonObj.has("fontsize"+signature.getId())){
				orgSign.setFontColor(jsonObj.getString("fontcolor"+signature.getId()));
			}
			
			
			
			if(jsonObj.has("bold"+signature.getId()) && jsonObj.getString("bold"+signature.getId()).equalsIgnoreCase("Y")){
				fontStyle = fontStyle.append("Bold").append(",");
			}
			
			if(jsonObj.has("underline"+signature.getId()) && jsonObj.getString("underline"+signature.getId()).equalsIgnoreCase("Y")){
				fontStyle = fontStyle.append("Underline").append(",");
			}
			
			if(jsonObj.has("italic"+signature.getId()) &&  jsonObj.getString("italic"+signature.getId()).equalsIgnoreCase("Y")){
				fontStyle = fontStyle.append("Italic").append(",");
			}
			
			fontStyle.toString();
			if(!isNullOrEmpty(fontStyle.toString())){
				orgSign.setFontStyle(fontStyle.substring(0,fontStyle.length()-1));
			}else{
				orgSign.setFontStyle(null);
			}
			
			
			if(jsonObj.has("check"+signature.getId()) &&  jsonObj.getString("check"+signature.getId()).equalsIgnoreCase("Y")){
				orgSign.setStatus(1);
			}else{
				orgSign.setStatus(0);
			}
			
			orgSign.setCreatedBy(user.getUserCode());
			orgSign.setCreatedDate(new Date());
			orgSign.setUpdatedBy(user.getUserCode());
			orgSign.setUpdatedDate(new Date());
			sourceleadSignatureDao.saveSourceleadSignature(orgSign);
		}
		
	}

	
	

	@Override
	public String buildSignature(User user, OrgLocations orgLocations) throws Exception {
		
		 StringBuilder signatureHTML = new StringBuilder();
		 UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocations.getId());
		 List<SourceleadSignature> signatureList = null;
		 
		 signatureList = sourceleadSignatureDao.getSourceLeadSignatureByLocationId(orgLocations.getId());
		 if(signatureList.size() == 0){
			 signatureList = sourceleadSignatureDao.getSourceLeadSignature();
		 }
		  
	     for(SourceleadSignature signature : signatureList){
	    	 if(signature.getLabelName().equalsIgnoreCase("Sign Off") && signature.getStatus() == 1){
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
	    		 if(!isNullOrEmpty(signature.getFontStyle())){
		    		 String[] fontStyle = signature.getFontStyle().split(",");
		    		 for(int i = 0; i < fontStyle.length; i++){
		    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
		    				 fontWeight = "font-weight:bold;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
		    				 underline = "text-decoration: underline;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
		    				 startem = "<em>";
		    				 endem = "</em>";
		    			 }
		    		 }
	    		 }
	    		 
	    		 signatureHTML.append("<p style='margin:0'>"+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+signature.getLabelText()+",</span></span>"+endem+"</p>") ; 
	    	 }
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Name") && signature.getStatus() == 1){
	    		 
	    		 StringBuilder nameBuilder = new StringBuilder();
	    		 StringBuilder name = new StringBuilder();
	    		 if(!isNullOrEmpty(user.getLastName())){
	    			 name = nameBuilder.append(user.getFirstName()).append(" ").append(user.getLastName());
			   	 }else{
			   		name = nameBuilder.append(user.getFirstName());
			   	 }
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
	    		 if(!isNullOrEmpty(signature.getFontStyle())){
		    		 String[] fontStyle = signature.getFontStyle().split(",");
		    		 for(int i = 0; i < fontStyle.length; i++){
		    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
		    				 fontWeight = "font-weight:bold;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
		    				 underline = "text-decoration: underline;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
		    				 startem = "<em>";
		    				 endem = "</em>";
		    			 }
		    		 }
	    		 }
	    		 signatureHTML.append("<p style='margin:0'>"+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+name+"</span></span>"+endem+" ");
	    	 }
	    	 
           if(signature.getLabelName().equalsIgnoreCase("Mobile") && signature.getStatus() == 1 && !isNullOrEmpty(user.getMobilePhone())){
	    		 
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
		    		 if(!isNullOrEmpty(signature.getFontStyle())){
			    		 String[] fontStyle = signature.getFontStyle().split(",");
			    		 for(int i = 0; i < fontStyle.length; i++){
			    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
			    				 fontWeight = "font-weight:bold;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
			    				 underline = "text-decoration: underline;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
			    				 startem = "<em>";
			    				 endem = "</em>";
			    			 }
			    		 }
		    		 }
		    		 signatureHTML.append("| <span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" font-size: "+signature.getFontSize()+";'>Direct:</span> "+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+user.getMobilePhone()+"</span></span>"+endem+" ");	    		
	    		
	    	 }
	    	 
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Designation") && signature.getStatus() == 1 && !isNullOrEmpty(userRole.getDesignation())){
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
	    		 if(!isNullOrEmpty(signature.getFontStyle())){
		    		 String[] fontStyle = signature.getFontStyle().split(",");
		    		 for(int i = 0; i < fontStyle.length; i++){
		    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
		    				 fontWeight = "font-weight:bold;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
		    				 underline = "text-decoration: underline;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
		    				 startem = "<em>";
		    				 endem = "</em>";
		    			 }
		    		 }
	    		 }
	    		 signatureHTML.append("<p style='margin:0'>"+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+userRole.getDesignation()+"</span></span>"+endem+"</p>");
	    	 }
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Logo") && signature.getStatus() == 1 && !isNullOrEmpty(orgLocations.getOrg().getFileContentType())){
	    		 
	    		 signatureHTML.append("<p><img src='"+orgLocations.getOrg().getFileUrl()+"'  style='height:60px; width: 100px; '></p>");
	    	 
	    	 }
	     
	    	 if(signature.getLabelName().equalsIgnoreCase("Company Name") && signature.getStatus() == 1){
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
	    		 if(!isNullOrEmpty(signature.getFontStyle())){
		    		 String[] fontStyle = signature.getFontStyle().split(",");
		    		 for(int i = 0; i < fontStyle.length; i++){
		    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
		    				 fontWeight = "font-weight:bold;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
		    				 underline = "text-decoration: underline;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
		    				 startem = "<em>";
		    				 endem = "</em>";
		    			 }
		    		 }
	    		 }
	    		 signatureHTML.append("<p style='margin:0'>"+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+orgLocations.getOrgLocationName()+"</span></span>"+endem+"</p>");	    	 }
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Address") && signature.getStatus() == 1 && !isNullOrEmpty(orgLocations.getOrgLocationAddress())){
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
	    		 if(!isNullOrEmpty(signature.getFontStyle())){
		    		 String[] fontStyle = signature.getFontStyle().split(",");
		    		 for(int i = 0; i < fontStyle.length; i++){
		    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
		    				 fontWeight = "font-weight:bold;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
		    				 underline = "text-decoration: underline;";
		    			 }
		    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
		    				 startem = "<em>";
		    				 endem = "</em>";
		    			 }
		    		 }
	    		 }
	    		 signatureHTML.append("<p style='margin:0'>"+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+orgLocations.getOrgLocationAddress()+"</span></span>"+endem+"</p>");
	    	 }
	     
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Phone") && signature.getStatus() == 1 && !isNullOrEmpty(orgLocations.getPhone())){
	    		 
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
		    		 if(!isNullOrEmpty(signature.getFontStyle())){
			    		 String[] fontStyle = signature.getFontStyle().split(",");
			    		 for(int i = 0; i < fontStyle.length; i++){
			    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
			    				 fontWeight = "font-weight:bold;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
			    				 underline = "text-decoration: underline;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
			    				 startem = "<em>";
			    				 endem = "</em>";
			    			 }
			    		 }
		    		 }
		    		 signatureHTML.append("<span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" font-size: "+signature.getFontSize()+";'>Phone:</span> "+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+orgLocations.getPhone()+"</span></span>"+endem+" | ");	    		
	    		
	    	 }
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Fax") && signature.getStatus() == 1 && !isNullOrEmpty(orgLocations.getFax())){
	    		 
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
		    		 if(!isNullOrEmpty(signature.getFontStyle())){
			    		 String[] fontStyle = signature.getFontStyle().split(",");
			    		 for(int i = 0; i < fontStyle.length; i++){
			    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
			    				 fontWeight = "font-weight:bold;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
			    				 underline = "text-decoration: underline;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
			    				 startem = "<em>";
			    				 endem = "</em>";
			    			 }
			    		 }
		    		 }
		    		 signatureHTML.append("<span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" font-size: "+signature.getFontSize()+";'>Fax:</span> "+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+orgLocations.getFax()+"</span></span>"+endem+"");	    		
	    		
	    	 }
	    	 
	    	 
	    	 if(signature.getLabelName().equalsIgnoreCase("Email") && signature.getStatus() == 1){
	    		 
	    		 
	    		 String fontWeight = "";
	    		 String underline = "";
	    		 String startem ="";
	    		 String endem ="";
	    		 
		    		 if(!isNullOrEmpty(signature.getFontStyle())){
			    		 String[] fontStyle = signature.getFontStyle().split(",");
			    		 for(int i = 0; i < fontStyle.length; i++){
			    			 if(fontStyle[i].equalsIgnoreCase("Bold")){
			    				 fontWeight = "font-weight:bold;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
			    				 underline = "text-decoration: underline;";
			    			 }
			    			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
			    				 startem = "<em>";
			    				 endem = "</em>";
			    			 }
			    		 }
		    		 }
		    		 signatureHTML.append("<p style='margin:0'><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" font-size: "+signature.getFontSize()+";'>Email:</span> "+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>"+user.getEmail()+"</span></span>"+endem+"");	    		
	    		
	    	 }


	   if(signature.getLabelName().equalsIgnoreCase("Website") && signature.getStatus() == 1 && !isNullOrEmpty(orgLocations.getWebsite())){
		 
		 
		 String fontWeight = "";
		 String underline = "";
		 String startem ="";
		 String endem ="";
		 
			 if(!isNullOrEmpty(signature.getFontStyle())){
	   		 String[] fontStyle = signature.getFontStyle().split(",");
	   		 for(int i = 0; i < fontStyle.length; i++){
	   			 if(fontStyle[i].equalsIgnoreCase("Bold")){
	   				 fontWeight = "font-weight:bold;";
	   			 }
	   			 else if(fontStyle[i].equalsIgnoreCase("Underline")){
	   				 underline = "text-decoration: underline;";
	   			 }
	   			 else if(fontStyle[i].equalsIgnoreCase("Italic")){
	   				 startem = "<em>";
	   				 endem = "</em>";
	   			 }
	   		 }
			 }
			 signatureHTML.append(" | <span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" font-size: "+signature.getFontSize()+";'>Website:<span> "+startem+"<span style='color: "+signature.getFontColor()+"; '><span style='font-family: "+signature.getFontFamily()+"; "+fontWeight+" "+underline+" font-size: "+signature.getFontSize()+";'>www."+orgLocations.getWebsite()+"</span></span>"+endem+"<p>");	    		
		
			}
		    	 
		     }
	     
	   if(!isNullOrEmpty(user.getFacebook())){
       	 signatureHTML.append("<a href="+user.getFacebook()+"><img src='"+sourceleadUrl+"/resources/images/fb.png'</img></a> ");
       }
       if(!isNullOrEmpty(user.getLinkedIn())){
      	 signatureHTML.append("<a href="+user.getLinkedIn()+"><img src='"+sourceleadUrl+"/resources/images/linked.png'</img></a>  ");
       }
       if(!isNullOrEmpty(user.getTwitter())){
      	 signatureHTML.append("<a href="+user.getTwitter()+"><img src='"+sourceleadUrl+"/resources/images/twitter.png'</img></a>  ");
       }
       if(!isNullOrEmpty(user.getGooglePlus())){
      	 signatureHTML.append("<a href="+user.getGooglePlus()+"><img src='"+sourceleadUrl+"/resources/images/google.png'</img></a>  ");
       }
  
		 return signatureHTML.toString();
			
	}

	@Override
	public void restoreSignature(User user,OrgLocations orgLocations) throws Exception {
		
		
		List<SourceleadSignature> orgSignatureSignaturesList = sourceleadSignatureDao.getSourceLeadSignatureByLocationId(orgLocations.getId());
		if(orgSignatureSignaturesList.size() > 0){
			List<SourceleadSignature> signaturesList = sourceleadSignatureDao.getSourceLeadSignature();
		    for(SourceleadSignature signature : signaturesList){
			SourceleadSignature orgSign = sourceleadSignatureDao.getSourceleadSignatureByLabelNameAndOrgLocationId(signature.getLabelName(), orgLocations.getId());
			orgSign.setLabelText(signature.getLabelText());
			orgSign.setFontFamily(signature.getFontFamily());
			orgSign.setFontSize(signature.getFontSize());
			orgSign.setFontColor(signature.getFontColor());
			orgSign.setFontStyle(signature.getFontStyle());
			orgSign.setStatus(signature.getStatus());
			orgSign.setCreatedBy(user.getUserCode());
			orgSign.setCreatedDate(new Date());
			orgSign.setUpdatedBy(user.getUserCode());
			orgSign.setUpdatedDate(new Date());
			sourceleadSignatureDao.saveSourceleadSignature(orgSign);
		}
		
		}
		
	}
	
	

}
