package com.bis.sourcelead.rest;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UniversalSearchService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class UniversalSearchRestController {
	
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	 @Autowired
	 private UniversalSearchService universalSearchService;
	 
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 RequirementService requirementService;
	 
	 /**
      * 
		 * used for individual search users,clients,vendors,projects and requirement
		 * @param json
		 * @param request
		 * @param response
		 * @return json
		 * @throws Exception
		 */
     @SuppressWarnings("unchecked")
		@RequestMapping(value = "/universalSearch",method = RequestMethod.GET)
		public @ResponseBody  String universalSearch(@RequestParam(value = "searchType", required = false) String searchType,@RequestParam(value = "searchValue", required = false) String searchValue,@RequestParam(value = "isAssociate", required = false) String isAssociate,HttpServletRequest request,HttpServletResponse response) throws Exception {
			String resultJson = "";
			User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			//List<Object> searchList = requirementService.univesalSearchRequirement(orgLocation, searchType,searchValue,org,userRole,user);
			List<Object> searchList = universalSearchService.univesalSearch(orgLocation, searchType,searchValue,org,userRole,user,isAssociate);
			for(Object obj : searchList){
				if(obj instanceof Requirement){
					List<Requirement> searchReqList = (List<Requirement>)(Object)searchList; 
					return commonUtil.toJSON(searchReqList);
				}else if(obj instanceof Candidate){
					List<CandidateIndex> searchCandidateList = (List<CandidateIndex>)(Object)searchList; 
					return commonUtil.toJSON(searchCandidateList);
				}else if(obj instanceof ClientVendorIndex){
					List<ClientVendorIndex> clientVendorList = (List<ClientVendorIndex>)(Object)searchList; 
					return commonUtil.toJSON(clientVendorList);
				}else if(obj instanceof ProjectsIndex){
					List<ProjectsIndex> searchProjectsList = (List<ProjectsIndex>)(Object)searchList; 
					return commonUtil.toJSON(searchProjectsList);
				}else if(obj instanceof UserIndex){
					List<UserIndex> searchUserList = (List<UserIndex>)(Object)searchList; 
					return commonUtil.toJSON(searchUserList);
				}
			}
			resultJson = commonUtil.toJSON(searchList);
			return resultJson;
     }
     
     /**
		 * Universal Search for All (users,clients,vendors,projects and requirement)
		 * @param json
		 * @param request
		 * @param response
		 * @return json
		 * @throws Exception
		 */
     @SuppressWarnings("unchecked")
		@RequestMapping(value = "/universalSearchForAll",method = RequestMethod.GET)
		public @ResponseBody  String universalSearchForAll(@RequestParam(value = "searchType", required = false) String searchType,@RequestParam(value = "searchValue", required = false) String searchValue,HttpServletRequest request,HttpServletResponse response) throws Exception {
			String resultJson = "";
			User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			Collection<Tabs> tabList= (Collection<Tabs>) SourceLeadSessionManager.getSessionAttribute(request, "tabsList");
			
			List<List<Object>> searchList = universalSearchService.univesalSearchForAll(orgLocation, searchType,searchValue,org,userRole,user,tabList);
			for (List<Object> list : searchList) {
				for(Object obj : list){
					if(obj instanceof Requirement){
						List<Requirement> searchReqList = (List<Requirement>)(Object)searchList; 
						commonUtil.toJSON(searchReqList);
					}else if(obj instanceof Candidate){
						List<CandidateIndex> searchCandidateList = (List<CandidateIndex>)(Object)searchList; 
						 commonUtil.toJSON(searchCandidateList);
					}else if(obj instanceof ClientVendorIndex){
						 List<ClientVendorIndex> clientVendorList = (List<ClientVendorIndex>)(Object)searchList; 
						 commonUtil.toJSON(clientVendorList);
					}else if(obj instanceof ProjectsIndex){
						List<ProjectsIndex> searchProjectsList = (List<ProjectsIndex>)(Object)searchList; 
						 commonUtil.toJSON(searchProjectsList);
					}else if(obj instanceof UserIndex){
						List<UserIndex> searchUserList = (List<UserIndex>)(Object)searchList; 
						 commonUtil.toJSON(searchUserList);
					}
			  }
			
			}
			resultJson = commonUtil.toJSON(searchList);
			return resultJson;
     }
}
