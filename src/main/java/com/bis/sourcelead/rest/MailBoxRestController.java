package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class MailBoxRestController {
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	HotListEmailCampaignService hotListEmailCampaignService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	
	
	@RequestMapping(value = "/getClientContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody String getClientContactsEmailsByOrgRelationId(@RequestParam(value = "orgRelationIds", required = false) String orgRelationIds) throws Exception {
		
		String[] relationsId = orgRelationIds.split(",");
		StringBuilder emails  = new StringBuilder();
		for(int i=0 ; i < relationsId.length; i++){
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.valueOf(relationsId[i]));
			for(OrgSpoc orgSpoc :orgSpocList){
				emails.append(orgSpoc.getSpocUser().getEmail()).append(",");
			}
		}
		emails.toString();
		
		 String spocEmails =  emails.substring(0,emails.length()-1);
		 System.out.println(spocEmails);
		
		return commonUtil.toJSON(spocEmails);
	}
	
	
	@RequestMapping(value = "/sendHotListCampaign", method = RequestMethod.POST)
	public String sendHotListCampaign(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		hotListEmailCampaignService.sendHotListEmailCampaign(jsonObj, user, orgLocation, multiFile);
		
		
		
		return "success";
	}
	
	
	@RequestMapping(value = "/saveUserSignature", method = RequestMethod.POST)
	public String saveUserSignature(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		userSignatureService.setUserSignatureDetails(jsonObj, user, orgLocation);
		
		
		return "success";
	}
	
	

	 @RequestMapping(value = "/getSignatures", method = RequestMethod.POST)
		public @ResponseBody String getSignatures(HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<UserSignature> singaturesList = userSignatureService.getUserSignatureListByUserIdAndLocationId(user.getId(), orgLocations.getId());
			resultJson = toJSON(singaturesList);
			return resultJson;
		}

	 
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
