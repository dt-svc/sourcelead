package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateHotListController {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/app/getCandidatesForCampaign")
	public @ResponseBody String getCandidatesForCampaign(@RequestParam(value = "candidatesListType", required = false) String candidatesListType,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    List<Long> teamIdsList = null;
	    
	    List<CandidateIndex> candidateList = new ArrayList<CandidateIndex>();
	    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocations.getId()));
		boolQueryBuilder.must(QueryBuilders.matchQuery("status", Constants.RECORD_ACTIVE));
		Integer maxResults = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    if(!isNullOrEmpty(candidatesListType)){
	    	if(candidatesListType.equalsIgnoreCase("consultants")){
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("isFavouriteAssociate", Constants.RECORD_INACTIVE));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("teamId", Constants.RECORD_INACTIVE ));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", Constants.CANDIDATE));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", Constants.YES));
	    		 maxResults = candidateService.getAllActiveCandidatesCountForCampaignByLocation(orgLocations.getId(),Constants.ASSOCIATE);
	    		 
	    	}else if(candidatesListType.equalsIgnoreCase("candidates")){
	    		
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("isFavourite", Constants.RECORD_INACTIVE));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("teamId", Constants.RECORD_INACTIVE ));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", Constants.CANDIDATE));
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", Constants.NO));
	    		 
	    		 maxResults = candidateService.getAllActiveCandidatesCountForCampaignByLocation(orgLocations.getId(),Constants.CANDIDATE);
	    		 
	    	}
	    	else if(candidatesListType.equalsIgnoreCase("openHotlist")){
	    		
	    		 BoolQueryBuilder anyCandidateTypeBoolQueryBuilder = QueryBuilders.boolQuery();
	    		 anyCandidateTypeBoolQueryBuilder.should(QueryBuilders.matchQuery("isFavourite", Constants.RECORD_ACTIVE));
	    		 anyCandidateTypeBoolQueryBuilder.should(QueryBuilders.matchQuery("isFavouriteAssociate", Constants.RECORD_ACTIVE));
		    	 boolQueryBuilder.must(anyCandidateTypeBoolQueryBuilder);
	    		 boolQueryBuilder.must(QueryBuilders.matchQuery("teamId", Constants.RECORD_INACTIVE ));
	    		 maxResults = candidateService.getAllHotlistCandidatesCountNotAssignedToTeamByLocation(orgLocations.getId());
	    		 
	    	}
	    	
	    	else if(candidatesListType.equalsIgnoreCase("hotlistConsultants")){
	    		UserRole userRole = userRoleService.getUserByUserId(user.getId());
	    		if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN") || userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
	    			teamIdsList = teamService.getTeamIdsListByOrgLocationID(orgLocations.getId());
	    		}else{
	    			teamIdsList = teamService.getUserAssignedTeamIdsListByOrgLocationId(user.getId(), orgLocations.getId());
	    		}
	    		if(null != teamIdsList && teamIdsList.size() > 0){
	    			BoolQueryBuilder boolSubQueryBuilder = new BoolQueryBuilder();
	    			for(Long teamId : teamIdsList){
	    				boolSubQueryBuilder.should(QueryBuilders.matchQuery("teamId", teamId));
	    			}
	    				boolQueryBuilder.must(boolSubQueryBuilder);
	    			
	    			if(candidatesListType.equalsIgnoreCase("hotlistConsultants")){
	    				 boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", Constants.CANDIDATE));
	    				 boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", Constants.YES));
	    				 maxResults = candidateService.getAllCandidatesCountForCampaignByTeam(teamIdsList, Constants.ASSOCIATE, orgLocations.getId());
		    		}
	    		}
	    		
	    		
	    	}
	    	
	    	SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
		    if(null != searchHits && searchHits.length >0){
		    	ObjectMapper mapper = new ObjectMapper();
		    	mapper.setDateFormat(df);
		    	for (SearchHit hit : searchHits) {
		    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
		    	    CandidateIndex candidateIndex = mapper.readValue(json.toString(), CandidateIndex.class);
		    	    candidateList.add(candidateIndex);
		    	}
		    	
		    }
	    	
	    }
	    
	    resultJson = commonUtil.toJSON(candidateList);
		return resultJson;
	}

}
