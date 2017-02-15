package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.util.CommonUtil;

@RestController
public class CrowdSourcingRequirementsRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/getCrowdSourcingRequirements")
	public @ResponseBody String getCrowdSourcingRequirements(@RequestParam(value = "requirementType", required = false) String requirementType,
			@RequestParam(value = "offset", required = false) Integer offset,
			HttpServletRequest request) throws Exception {

	    String resultJson;
	    List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
	    Long maxResults = null;
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	     BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
	     if(requirementType.equalsIgnoreCase("OpenJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));
	     }else if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
	     }
		 
		 boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
		
	   
	    try {
	    	 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,offset, Constants.MAX_RESULTS_TEN,boolQueryBuilder).getHits();
	 	     if(null != searchHits && searchHits.length >0){
	 	    	ObjectMapper mapper = new ObjectMapper();
	 	    	for (SearchHit hit : searchHits) {
	 	    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
	 	    		mapper.setDateFormat(df);
	 	    	    RequirementIndex requirementIndex = mapper.readValue(json.toString(), RequirementIndex.class);
	 	    	   requirementList.add(requirementIndex);
	 	    	}
	 	    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
	    if(requirementList != null){
			calculateDaysAndSkills(requirementList);
		}
	    resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	}
	
	
	public void calculateDaysAndSkills(List<RequirementIndex> requirementList) throws ParseException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (RequirementIndex requirement : requirementList) {
			long diff = commonUtil.currentDate().getTime() - formatter.parse(requirement.getCreatedDate()).getTime();
			
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			
			if(diffDays == 0){
				requirement.setCalDays("Today");
			}
			else if(diffDays < 7){
				requirement.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(requirement.getCreatedDate());
				requirement.setCalDays(commonDateFormat.toString());
			} 
			
			
			if(null != requirement.getSkills()){
				List<String> skillsList = new ArrayList<String>();
				String[] skills = requirement.getSkills();
				for(int i=0; i< skills.length; i++){
					skillsList.add(skills[i]);
				}
				requirement.setSkillsList(skillsList);
			}

		}

	}
	
}
