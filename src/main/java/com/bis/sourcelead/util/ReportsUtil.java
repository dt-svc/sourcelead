package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsUtil.class);
	
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
	
	/**
	 * Get from and to date of last 7 days
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last7Days() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, -7);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 15 days
	 * @return Date[]  Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last15DaysDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, -15);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 1 Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last1MonthDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 3 Months
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last3MonthDates() {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -3);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}

	/**
	 * Get from and to date of last 1 Year
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] lastYearDates() {
		Date[] dates = new Date[2];
		Calendar calendarStart = Calendar.getInstance();
		int previousYear = calendarStart.get(Calendar.YEAR) - 1;
		calendarStart.set(Calendar.YEAR, previousYear);
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = calendarStart.getTime();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, previousYear);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		dates[1] = calendarEnd.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;

	}
	
	/**
	 * Get from and to date of current  Date
	 * 
	 * @return Date[] Date[0] = Start Date ,Date[1] = End date
	 */
	public Date[] yearToDate() {
		Date[] dates = new Date[2];
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dates[1] = cal.getTime();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;

	}
	
}