package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;

@Component
public class ReportsUtil {

	@Autowired
	ApplicantsBySourceDao applicantsBySourceDao;
	
	@Autowired
	private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	public List<RequirementDTO> getAllRequirementsByOrgLocationId(Long orgLocationId,User user,String userRole){
		
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		try {
			
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			
			if(userRole != null&& !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
				BoolQueryBuilder assignedBoolQueryBuilder = QueryBuilders.boolQuery();
				
				//For Level1 Approver
				BoolQueryBuilder reqLevel1ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
				reqLevel1ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel1ApproverUserId", user.getId().toString()));
				reqLevel1ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(reqLevel1ApproverUserIdQueryBuilder);
				
				//For Requirement Manager
				BoolQueryBuilder reqManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
				reqManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("managerUserId",user.getId().toString()));
				reqManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(reqManagerUserIdQueryBuilder);
				
				//For Team Owner
				BoolQueryBuilder teamOwnerQueryBuilder = QueryBuilders.boolQuery();
				teamOwnerQueryBuilder.must(QueryBuilders.termQuery("teamOwnerUserId", user.getId().toString()));
				teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(teamOwnerQueryBuilder);
				
				//For Team AssignedUser
				BoolQueryBuilder teamAssignedUserQueryBuilder = QueryBuilders.boolQuery();
				teamAssignedUserQueryBuilder.must(QueryBuilders.termQuery("teamAssignUserId", user.getId().toString()));
				teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(teamAssignedUserQueryBuilder);
				
				
				boolQueryBuilder.must(assignedBoolQueryBuilder);
			}
			
			Integer maxResults = applicantsBySourceDao.getRequirementsCount(orgLocationId);
			
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults,boolQueryBuilder).getHits();
			if(searchHits != null && searchHits.length >0){
				for (SearchHit hit : searchHits) {
					String sourceAsString = hit.getSourceAsString();
					ObjectMapper mapper = new ObjectMapper();
					if (sourceAsString != null) {
						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
						RequirementDTO requirementDTO = new RequirementDTO();
						requirementDTO.setRequirementId(String.valueOf(requirementIndex.getRequirementId()));
						requirementDTO.setJobTitle(requirementIndex.getJobTitle());
						requirementDTOList.add(requirementDTO);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requirementDTOList;
	}
}
