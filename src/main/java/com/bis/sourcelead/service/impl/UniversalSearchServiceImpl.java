package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.UniversalSearchService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.UniversalSearchHelper;

@Service
public class UniversalSearchServiceImpl implements UniversalSearchService{

	@Autowired
	RequirementDao requirementDao;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserService userService;
			
	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	RequirementApproverHierarchyDao requirementApproverHierarchyDao;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	private UserRoleService userRoleService;
	
    @Autowired
    private ProjectsService projectsService;
	
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 LookUpDataService lookUpDataService;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private UniversalSearchHelper universalSearchHelper;
	
	@Override
	public List<Object> univesalSearch(OrgLocations orgLocation, String searchType, String searchValue, Org org,String userRole, User user,String isAssociate) throws Exception {

		List<Object>   searchList = new ArrayList<>();
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
	
		// Elk for Requirement search                              
		if(searchType !=null && !"".equalsIgnoreCase(searchType) && Constants.REQUIREMENT.equalsIgnoreCase(searchType))
		{
			 Long maxResults = requirementDao.getRequirementListByOrgLocationID(orgLocation.getId());
			//Any Keyword
			 if(searchValue != null && !"".equalsIgnoreCase(searchValue)){
				 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<QueryBuilder> QueryBuilderList =universalSearchHelper.getQueryBuilderListForRequirementSearch(keywd);
							if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
								for(QueryBuilder QueryBuilder :QueryBuilderList){
									anyBoolSubQueryBuilder.should(QueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(anyBoolQueryBuilder);
					} 
			 }
			 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				
				if(searchHits != null && searchHits.length >0){
					for (SearchHit hit : searchHits) {
						String sourceAsString = hit.getSourceAsString();
						ObjectMapper mapper = new ObjectMapper();
						if (sourceAsString != null) {
							Requirement requirement = null;
							RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
			            	if(userRole != null&&( "ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	else if(requirementIndex.getReqCreatedBy().equals(user.getId()) && requirementIndex.getIsTemplate()!=null && requirementIndex.getIsTemplate().equalsIgnoreCase("Y"))
			            	{
			            		requirement = getData(requirementIndex,user);
			            	}
			            	
			            	else if((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
			            			||((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString()))
			            			&& ("APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus()) || "OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
			            			)){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	else if(requirementIndex.getReqLevel2ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel2ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())
			            			&&(requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&"APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
			            			|| (requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())
			            			&&("APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus())||"OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
			            			)){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	
			            	else if(requirementIndex.getManagerUserId().equalsIgnoreCase(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	
			            	else if(requirementIndex.getTeamOwnerUserId()!=null&&Arrays.asList(requirementIndex.getTeamOwnerUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	else if(requirementIndex.getTeamAssignUserId()!=null&&Arrays.asList(requirementIndex.getTeamAssignUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
			            		requirement = getData(requirementIndex,user);
			            	}
			            	if(null !=requirement){
			            		searchList.add(requirement);
			            	}
			            	
			            }
					}
				}
				
			return searchList;
		}
		// ELK list for the user
		if(searchType != null && !"".equalsIgnoreCase(searchType) && Constants.USERS.equalsIgnoreCase(searchType))
		{
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));
			 Long maxResults = userRoleService.getUsersCountByOrgLocationID(orgLocation.getId());
				//Any Keywords
				ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
				if (null != anyKeyWordArray && !anyKeyWordArray.isEmpty()) {
					BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : anyKeyWordArray) {
						BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getUsersQueryBuilderList(keywd);

						if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.must(anyBoolQueryBuilder);
				}

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				if(null != searchHits && searchHits.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHits) {
						String json = hit.getSourceAsString();
						mapper.setDateFormat(df);
						UserIndex userIndex = mapper.readValue(json, UserIndex.class);
						searchList.add(userIndex);
					}
				}
			
			return searchList;
		}
		// ELK list for the Clients and Vendors
		if(searchType !=null && !"".equalsIgnoreCase(searchType)&&(Constants.CLIENT.equalsIgnoreCase(searchType)||Constants.VENDOR.equalsIgnoreCase(searchType)))
		{
			String responderType = null;
			
			BoolQueryBuilder boolQueryBuilderClientVendor = new BoolQueryBuilder();
			 
			if(Constants.VENDOR.equalsIgnoreCase(searchType)){
				 responderType = Constants.CLIENT;
			 }else if(Constants.CLIENT.equalsIgnoreCase(searchType)){
				 responderType = Constants.VENDOR; 
			 }
			 
			 BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();
			 
			 BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
			 initiatorQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
			 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));
			 
			 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
			 responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", orgLocation.getId()));
			 responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));
			 
			 combinedQueryBuilder.should(initiatorQueryBuilder);
			 combinedQueryBuilder.should(responderQueryBuilder);
			 
			 boolQueryBuilderClientVendor.must(combinedQueryBuilder);
			 
			 
			 
			 Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,0,boolQueryBuilderClientVendor).getTotalHits();
//			 Integer maxResults = orgRelationsService.getAllVendorsCountByOrgLocationId(orgLocation.getId(),responderType);
				//Any Keywords
			 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getQueryBuilderListForClientVendor(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilderClientVendor.must(anyBoolQueryBuilder);
				}

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,maxResults.intValue(),boolQueryBuilderClientVendor).getHits();
				if(searchHits != null && searchHits.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHits) {
								String json = hit.getSourceAsString();
								mapper.setDateFormat(df);
								ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);
								
								if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == orgLocation.getId().longValue()){
									
								    StringBuilder website=new StringBuilder();
								    clientVendor.setWebsite(website.append("www.").append(clientVendor.getResponderWebSite()).toString());
									clientVendor.setOrgName(clientVendor.getResponderOrgName());
									clientVendor.setCountry(clientVendor.getResponderCountry());
									clientVendor.setState(clientVendor.getResponderState());
									clientVendor.setCity(clientVendor.getResponderCity());
									
									if(clientVendor.getInitiatorLevel()!=null){
										clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
									}
								}
							  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == orgLocation.getId().longValue()){
								 
								  StringBuilder website=new StringBuilder();
								  clientVendor.setWebsite(website.append("www.").append(clientVendor.getInitiatorWebSite()).toString());
								  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
								  clientVendor.setCountry(clientVendor.getInitiatorCountry());
								  clientVendor.setState(clientVendor.getInitiatorState());
								  clientVendor.setCity(clientVendor.getInitiatorCity());
								  
								  if(clientVendor.getResponderLevel()!=null){
									  clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
								  }
								}
							  if(!responderType.isEmpty() &&  Constants.VENDOR.equalsIgnoreCase(responderType)){
								  if(null !=clientVendor.getCreatedBy() && !clientVendor.getCreatedBy().isEmpty()){
									  clientVendor.setCreatedBy(clientVendor.getCreatedBy());
								  }
								 
							  }
							  
							  searchList.add(clientVendor);
					}	
				}
			
			return searchList;
		}
		// ELK list for the projects
		if(searchType !=null && !"".equalsIgnoreCase(searchType) && Constants.PROJECT.equalsIgnoreCase(searchType))
		{
                boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
//                boolQueryBuilder.must(QueryBuilders.matchQuery("userId", user.getId()));
			    Integer maxResults = projectsService.getProjectCountByOrgLocationIdAndUserCode(orgLocation.getId(),user.getUserCode());
				//Any Keywords
			    ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getProjectsQueryBuilderList(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
//						for(String keywd : invoiceFrequencyArray){
						if(keywd.equalsIgnoreCase("Weekly")){
							anyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Weekly"));
						}else if(keywd.equalsIgnoreCase("Monthly")){
							anyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Monthly"));
							anyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Semi-Monthly"));
						}else if(keywd.equalsIgnoreCase("Bi-Weekly")) {
							anyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Bi-Weekly"));
						}else if(keywd.equalsIgnoreCase("Semi-Monthly")) {
							anyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Semi-Monthly"));
						}
//						else{
//							invoiceFrequencyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", keywd));
//						}
//					}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				}

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				if(searchHits != null && searchHits.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHits) {
						String json = hit.getSourceAsString();
						mapper.setDateFormat(df);
						ProjectsIndex projectsIndex = mapper.readValue(json, ProjectsIndex.class);
						searchList.add(projectsIndex);
					}
				}
			
			return searchList;
		}
		/// ELK list for the Candidate
		if(searchType !=null && !"".equalsIgnoreCase(searchType)&&Constants.CANDIDATE.equalsIgnoreCase(searchType))
		{
			
			
			// Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocation.getId(),null);\
			
			 Long maxResults = candidateService.getCandidatesCountByOrgLocationIDAndStatus(orgLocation.getId(),null,isAssociate);
				//Any Keywords
			 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getQueryBuilderListForCandidate(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				}
				
				 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));
		
				
				if(null != isAssociate && isAssociate.equalsIgnoreCase(Constants.YES)){
					boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", isAssociate));
				}
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				    if(null != searchHits && searchHits.length >0){
				    	ObjectMapper mapper = new ObjectMapper();
				    	for (SearchHit hit : searchHits) {
				    		String json = hit.getSourceAsString();
				    		mapper.setDateFormat(df);
				    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
				    	    if(candidateIndex.getUpdatedDate() != null){
				    	    	candidateIndex.setCandidateUpdateDateToString(candidateIndex.getUpdatedDate());
				   			}
				    	    StringBuilder sb = new StringBuilder();
				    	    if(candidateIndex.getFirstName()!=null && candidateIndex.getLastName() !=null && !"".equalsIgnoreCase(candidateIndex.getFirstName()) && !"".equalsIgnoreCase(candidateIndex.getLastName())){
				    	    	sb.append(candidateIndex.getFirstName()).append(" ").append(candidateIndex.getLastName());
				    	    }else{
				    	    	sb.append(candidateIndex.getFirstName());
				    	    }
				    	    candidateIndex.setCandidateName(sb.toString());
				    	   if(candidateIndex.getSkillsName() != null){
				    		   String skillArray[] = candidateIndex.getSkillsName().split(",");
				    		   String skills = getReqSkills(skillArray);
				    		   candidateIndex.setSkillSet(skills);
				    	   }
				    	    searchList.add(candidateIndex);
				    	}
				    	
				    }
			return searchList;
		}
		return searchList;
	
	}
	/**
	 * populating requirement pojo
	 * @param requirementIndex
	 * @param user 
	 * @return Requirement
	 */
	 private Requirement getData(RequirementIndex requirementIndex, User user) {
		 Requirement requirement = new Requirement();
		requirement.setId(requirementIndex.getRequirementId());
		requirement.setRequirementUUID(requirementIndex.getRequirementUUID());
     	requirement.setJobId(requirementIndex.getJobId());
     	requirement.setJobType(requirementIndex.getJobType());
     	requirement.setJobTitle(requirementIndex.getJobTitle());
     	if(requirementIndex.getMinExperience() !=null){
     		requirement.setMinExp(requirementIndex.getMinExperience().toString());	
     	}
     	if(requirementIndex.getSkills() !=null){
     		String skills = getReqSkills(requirementIndex.getSkills());
     		requirement.setSkills(skills);
     	}
     	if(requirementIndex.getMaxExperience() !=null){
     		requirement.setMaxExp(requirementIndex.getMaxExperience().toString());
     	}
     	requirement.setClientName(requirementIndex.getClient());
     	requirement.setCountry(requirementIndex.getCountry());
     	requirement.setState(requirementIndex.getState());
     	requirement.setCity(requirementIndex.getCity());
     	requirement.setSearchType(Constants.REQUIREMENT);
     	requirement.setPositions(requirementIndex.getNumberOfPositions());
     	if(null != requirementIndex.getNumberOfApplicants()){
     		requirement.setTotalApplicants(Integer.parseInt(requirementIndex.getNumberOfApplicants()));
     	}
     	requirement.setManager(requirementIndex.getManager());
     	if(requirementIndex.getRequirementStatus() !=null && !"".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
         	requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.valueOf(requirementIndex.getRequirementStatus()));
     	}	
     	
     	if(null != requirementIndex.getReleasedBy()){
			List<Long> userId = new ArrayList<Long>();
			for (String s : requirementIndex.getReleasedBy().split(",")){
				userId.add(Long.parseLong(s));
			 }
			boolean flag = userId.contains(user.getId());
     		if(flag == true){
     			requirement.setIsRequirementReleasedByuser("Y");
     		}else{
     			requirement.setIsRequirementReleasedByuser("N");
     		}
		}
     	
     	
     	return requirement;
	}
	 /**
	  * converting  Strin[] of skills to string skills
	  * @param skills
	  * @return
	  */
	 private String getReqSkills(String[] skills) {
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<skills.length;i++){
				sb.append(skills[i]).append(",");
			}
			String str = sb.toString();
			return str.substring(0, str.length()-1);
		}

	/**
	 * removed quotes from string
	 * @param searchValue
	 * @return ArrayList<String>
	 */
	 private ArrayList<String> getSearchValuesFromChosenSelectField(String searchValue) {
		 ArrayList<String> keyWordList = null;
		 if(searchValue !=null && !"".equalsIgnoreCase(searchValue)){
			 String searchKeywordString = searchValue.replaceAll("\\[", "").replaceAll("\\]","");
		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		 }
        return keyWordList;
	}
	
	
	@Override
	public List<List<Object>> univesalSearchForAll(OrgLocations orgLocation, String searchType, String searchValue,Org org, String userRole, User user,Collection<Tabs> tabList) throws Exception {

		List<Object>   searchListForRequirement = new ArrayList<>();
		List<Object>   searchListForUsers = new ArrayList<>();
		List<Object>   searchListForProjects = new ArrayList<>();
		List<Object>   searchListForCandidates = new ArrayList<>();
		List<Object>   searchListForClient = new ArrayList<>();
		List<Object>   searchListForVendor = new ArrayList<>();
		
		
		List<List<Object>>   searchListForAll = new ArrayList<>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		for(Tabs tabs :tabList){
			if(Constants.JOBS.equals(tabs.getTabName())){
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
				boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				
					 Long maxResultsForRequirement = requirementDao.getRequirementListByOrgLocationID(orgLocation.getId());
					//Elk for Requirement
					 if(searchValue != null && !"".equalsIgnoreCase(searchValue)){
						 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
						 if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
							 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
							 for(String keywd : anyKeyWordArray){
								 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
								ArrayList<QueryBuilder> QueryBuilderList =universalSearchHelper.getQueryBuilderListForRequirementSearch(keywd);
								if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
									for(QueryBuilder QueryBuilder :QueryBuilderList){
										anyBoolSubQueryBuilder.should(QueryBuilder);
									}
									anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
								}
							}
							 boolQueryBuilder.must(anyBoolQueryBuilder);
						} 
					
					 }
					 
					 
					 
					 
					 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResultsForRequirement.intValue(),boolQueryBuilder).getHits();
						
						if(searchHits != null && searchHits.length >0){
							for (SearchHit hit : searchHits) {
								String sourceAsString = hit.getSourceAsString();
								ObjectMapper mapper = new ObjectMapper();
								if (sourceAsString != null) {
									Requirement requirement = null;
									RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
					            	if(userRole != null&&( "ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	else if(requirementIndex.getReqCreatedBy().equals(user.getId()) && requirementIndex.getIsTemplate()!=null && requirementIndex.getIsTemplate().equalsIgnoreCase("Y"))
					            	{
					            		requirement = getData(requirementIndex,user);
					            	}
					            	
					            	else if((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
					            			||((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString()))
					            			&& ("APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus()) || "OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
					            			)){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	else if(requirementIndex.getReqLevel2ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel2ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())
					            			&&(requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&"APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
					            			|| (requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())
					            			&&("APPROVED".equalsIgnoreCase(requirementIndex.getRequirementStatus())||"OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
					            			)){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	
					            	else if(requirementIndex.getManagerUserId().equalsIgnoreCase(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	
					            	else if(requirementIndex.getTeamOwnerUserId()!=null&&Arrays.asList(requirementIndex.getTeamOwnerUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	else if(requirementIndex.getTeamAssignUserId()!=null&&Arrays.asList(requirementIndex.getTeamAssignUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
					            		requirement = getData(requirementIndex,user);
					            	}
					            	if(null !=requirement){
					            		searchListForRequirement.add(requirement);	
					            	}
					            }
							}
						}	
			}
			if("Associates".equals(tabs.getTabName())){
				// ELK list for the user
				BoolQueryBuilder userBoolQueryBuilder = new BoolQueryBuilder();
				userBoolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
				userBoolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));
				 Long maxResultsForUser = userRoleService.getUsersCountByOrgLocationID(orgLocation.getId());
				 
					ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
					if (null != anyKeyWordArray && !anyKeyWordArray.isEmpty()) {

						BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						for (String keywd : anyKeyWordArray) {
							BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getUsersQueryBuilderList(keywd);

							if (null != QueryBuilderList && !QueryBuilderList.isEmpty()) {
								for (QueryBuilder QueryBuilder : QueryBuilderList) {
									anyBoolSubQueryBuilder.should(QueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						userBoolQueryBuilder.must(anyBoolQueryBuilder);
					}
					SearchHit[] searchHitsForUser = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_USER_INDEX, Constants.ELK_USER_TYPE,0,maxResultsForUser.intValue(),userBoolQueryBuilder).getHits();
					if(null != searchHitsForUser && searchHitsForUser.length >0){
						ObjectMapper mapper = new ObjectMapper();
						for (SearchHit hit : searchHitsForUser) {
							String json = hit.getSourceAsString();
							mapper.setDateFormat(df);
							UserIndex userIndex = mapper.readValue(json, UserIndex.class);
							userIndex.setSearchType(Constants.USER);
							if(null != userIndex){
								searchListForUsers.add(userIndex);
							}
						}
					}	
			}
			if("Clients".equals(tabs.getTabName())){
				// ELK list for the Clients and Vendors
				String responderTypeAsVendor = Constants.VENDOR; 
				 searchListForClient =	getSearchListForClientsVendors(searchType,orgLocation,df,searchListForClient,searchValue,responderTypeAsVendor);
				 
			}
			if("Vendors".equals(tabs.getTabName())){
				 String responderTypeAsClient = Constants.CLIENT;
				 searchListForVendor =  getSearchListForClientsVendors(searchType,orgLocation,df,searchListForVendor,searchValue,responderTypeAsClient);
			
			}
			if("Candidates".equals(tabs.getTabName())){
				// ELK list for the Candidate
				BoolQueryBuilder candidateBoolQueryBuilder = new BoolQueryBuilder();
				candidateBoolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
			 Long maxResultsForCandidate = candidateService.getCandidatesCountByOrgLocationID(orgLocation.getId(),null);
			 ArrayList<String> allKeyWordArrayForCandidate = getSearchValuesFromChosenSelectField(searchValue);
			 
				if(allKeyWordArrayForCandidate != null && !allKeyWordArrayForCandidate.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : allKeyWordArrayForCandidate){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getQueryBuilderListForCandidate(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 candidateBoolQueryBuilder.must(anyBoolQueryBuilder);
				}
				
				candidateBoolQueryBuilder.mustNot(QueryBuilders.matchQuery("userId", user.getId()));

				
				df.setTimeZone(TimeZone.getTimeZone("GMT"));
				
				SearchHit[] searchHitsForCandidate = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResultsForCandidate.intValue(),candidateBoolQueryBuilder).getHits();
				    if(null != searchHitsForCandidate && searchHitsForCandidate.length >0){
				    	ObjectMapper mapper = new ObjectMapper();
				    	for (SearchHit hit : searchHitsForCandidate) {
				    		String json = hit.getSourceAsString();
				    		mapper.setDateFormat(df);
				    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
				    	    if(candidateIndex.getUpdatedDate() != null){
				    	    	candidateIndex.setCandidateUpdateDateToString(candidateIndex.getUpdatedDate());
				   			}
				    	    StringBuilder sb = new StringBuilder();
				    	    if(candidateIndex.getFirstName()!=null && candidateIndex.getLastName() !=null && !"".equalsIgnoreCase(candidateIndex.getFirstName()) && !"".equalsIgnoreCase(candidateIndex.getLastName())){
				    	    	sb.append(candidateIndex.getFirstName()).append(" ").append(candidateIndex.getLastName());
				    	    }else{
				    	    	sb.append(candidateIndex.getFirstName());
				    	    }
				    	    candidateIndex.setCandidateName(sb.toString());
				    	   if(candidateIndex.getSkillsName() != null){
				    		   String skillArray[] = candidateIndex.getSkillsName().split(",");
				    		   String skills = getReqSkills(skillArray);
				    		   candidateIndex.setSkillSet(skills);
				    	   }
				    	   candidateIndex.setSearchType(Constants.CANDIDATE);
				    	    searchListForCandidates.add(candidateIndex);
				    	}
				    	
				    }
			}
			if("Projects".equals(tabs.getTabName())){
				 // ELK list for the projects
				BoolQueryBuilder projectBoolQueryBuilder = new BoolQueryBuilder();
				projectBoolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
			 
			 //Integer maxResults = projectsService.getAllProjects().size();
			 
			 Integer maxResultsForProjects = projectsService.getProjectCountByOrgLocationId(orgLocation.getId());
				
				//All Keywords
//				ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
				 ArrayList<String> allKeyWordArrayForProjects = getSearchValuesFromChosenSelectField(searchValue);
					if(allKeyWordArrayForProjects != null && !allKeyWordArrayForProjects.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : allKeyWordArrayForProjects){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getProjectsQueryBuilderList(keywd);
							if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
								for(QueryBuilder QueryBuilder :QueryBuilderList){
									anyBoolSubQueryBuilder.should(QueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
							if(Constants.WEEKLY.equalsIgnoreCase(keywd)){
								anyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Weekly"));
								anyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Bi-Weekly"));
							}else if(Constants.MONTHLY.equalsIgnoreCase(keywd)){
								anyBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Monthly"));
								anyBoolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("invoiceFrequency", "Semi-Monthly"));
							}
						}
						 projectBoolQueryBuilder.must(anyBoolQueryBuilder);
					}
			
				SearchHit[] searchHitsForProjects = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_PROJECT_INDEX, Constants.ELK_PROJECT_TYPE,0,maxResultsForProjects.intValue(),projectBoolQueryBuilder).getHits();
				if(searchHitsForProjects != null && searchHitsForProjects.length >0){
					ObjectMapper mapper = new ObjectMapper();
					for (SearchHit hit : searchHitsForProjects) {
						String json = hit.getSourceAsString();
						ProjectsIndex projectsIndex = mapper.readValue(json, ProjectsIndex.class);
						projectsIndex.setSearchType(Constants.PROJECT);
						searchListForProjects.add(projectsIndex);
					}
				}
			}
		}
				    searchListForAll.add(searchListForClient) ;
				    searchListForAll.add(searchListForVendor);
				    searchListForAll.add(searchListForProjects) ;
				    searchListForAll.add(searchListForUsers) ;
				    searchListForAll.add(searchListForRequirement);
				    searchListForAll.add(searchListForCandidates);
				    
					return searchListForAll;
	}
	/**
	 * preparing bool query for client and vendor
	 * @param searchType
	 * @param loginOrgLocation
	 * @param df
	 * @param searchList
	 * @param searchValue
	 * @param responderType
	 * @return List<Object>
	 * @throws Exception
	 */
	private List<Object> getSearchListForClientsVendors(String searchType, OrgLocations loginOrgLocation, DateFormat df, List<Object> searchList, String searchValue, String responderType) throws Exception {
		
		BoolQueryBuilder clientVendorBoolQueryBuilder = new BoolQueryBuilder();
		
		 BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();
		 
		 BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
		 initiatorQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		 initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));
		 
		 BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
		 responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", loginOrgLocation.getId()));
		 responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));
		 
		 combinedQueryBuilder.should(initiatorQueryBuilder);
		 combinedQueryBuilder.should(responderQueryBuilder);
		 
		 clientVendorBoolQueryBuilder.must(combinedQueryBuilder);
		
		
		
		 Integer maxResultsClientAndVendor = orgRelationsService.getAllVendorsCountByOrgLocationId(loginOrgLocation.getId(),responderType);
			//All Keywords
		 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
			
		 if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
			 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			 for(String keywd : anyKeyWordArray){
				 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper.getQueryBuilderListForClientVendor(keywd);
				if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
					for(QueryBuilder QueryBuilder :QueryBuilderList){
						anyBoolSubQueryBuilder.should(QueryBuilder);
					}
					anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
				}
			}
			 clientVendorBoolQueryBuilder.must(anyBoolQueryBuilder);
		}
			SearchHit[] searchHitsForClientVendor = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,0,maxResultsClientAndVendor.intValue(),clientVendorBoolQueryBuilder).getHits();
			if(searchHitsForClientVendor != null && searchHitsForClientVendor.length >0){
				ObjectMapper mapper = new ObjectMapper();
				for (SearchHit hit : searchHitsForClientVendor) {
								String json = hit.getSourceAsString();
								mapper.setDateFormat(df);
								ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);
							if(Constants.VENDOR.equalsIgnoreCase(responderType)){
								clientVendor.setSearchType(Constants.CLIENT);	
							}if(Constants.CLIENT.equalsIgnoreCase(responderType)){
								clientVendor.setSearchType(Constants.VENDOR);	
							}
								if(null != clientVendor.getOrgLocationId() && clientVendor.getOrgLocationId().longValue() == loginOrgLocation.getId().longValue()){
									
								    StringBuilder website=new StringBuilder();
								    clientVendor.setWebsite(website.append("www.").append(clientVendor.getResponderWebSite()).toString());
									clientVendor.setOrgName(clientVendor.getResponderOrgName());
									clientVendor.setCountry(clientVendor.getResponderCountry());
									clientVendor.setState(clientVendor.getResponderState());
									clientVendor.setCity(clientVendor.getResponderCity());
									
									if(clientVendor.getInitiatorLevel()!=null){
										clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
									}
								}
							  if(null != clientVendor.getResponderLocationId()  && clientVendor.getResponderLocationId().longValue() == loginOrgLocation.getId().longValue()){
								 
								  StringBuilder website=new StringBuilder();
								  clientVendor.setWebsite(website.append("www.").append(clientVendor.getInitiatorWebSite()).toString());
								  clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
								  clientVendor.setCountry(clientVendor.getInitiatorCountry());
								  clientVendor.setState(clientVendor.getInitiatorState());
								  clientVendor.setCity(clientVendor.getInitiatorCity());
								  
								  if(clientVendor.getResponderLevel()!=null){
									  clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
								  }
								}
							  if(!responderType.isEmpty() &&  Constants.VENDOR.equalsIgnoreCase(responderType)){
								  if(null != clientVendor.getCreatedBy() && !clientVendor.getCreatedBy().isEmpty()){
									  clientVendor.setCreatedBy(clientVendor.getCreatedBy());
								  }
								 
							  }
							  
							  searchList.add(clientVendor);

				}
			}
			return searchList;
		
	}
}
