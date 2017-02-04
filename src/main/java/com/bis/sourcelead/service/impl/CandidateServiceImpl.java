package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.regex.RegularExpression;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author prasad K
 * @date 25th Aug 2016
 *
 */

@Service
public class CandidateServiceImpl implements CandidateService {
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateEducationService candidateEducationService;
	
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Autowired
	CandidateSkillsService candidateSkillsService;
	
	@Autowired @Lazy
	CandidateResumesService candidateResumesService;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;
	
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	private UniversalSearchHelper universalSearchHelper;
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeCandidate method
	 * @param orgLocations This is the second paramter to storeCandidate method
	 * @param user This is the third paramter to storeCandidate method
	 * @param multiFile This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		
        
        Candidate candidate = null;
		if(jsonObj.has("candidateId") && jsonObj.getString("candidateId") != null && !"".equals(jsonObj.getString("candidateId"))){
			candidate = candidateDao.getCandidateByCandidateId(Long.parseLong(jsonObj.getString("candidateId")));
		}else{
			candidate = new Candidate();
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setCreatedBy(user.getUserCode());
			
			if(jsonObj.has("consultantUser") && jsonObj.getString("consultantUser") !=null){
				User consultantUser = new User();
				consultantUser.setId(Long.valueOf(jsonObj.getString("consultantUser")));
				candidate.setConsultantUser(consultantUser);
				candidate.setCandidateType("Consultant");
			}else{
				candidate.setCandidateType("Candidate");
			}
			
		}
        
		if(orgLocations != null){
		candidate.setOrgLocationId(orgLocations);
		}
		if(jsonObj.has("firstName") && jsonObj.getString("firstName") !=null){
		candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && jsonObj.getString("lastName") !=null){
		candidate.setLastName(jsonObj.getString("lastName") );
		}
		
		if(jsonObj.has("country") && jsonObj.getString("country") != null && jsonObj.getString("country") !="" && jsonObj.getString("country")!="undefined"){
		candidate.setCountry(jsonObj.getString("country"));
		}
		if(jsonObj.has("state") && jsonObj.getString("state") !=null){
		candidate.setState(jsonObj.getString("state"));
		}
		if(jsonObj.has("city") && jsonObj.getString("city") !=null){
		candidate.setCity(jsonObj.getString("city"));
		}
		if(jsonObj.has("mobilePhone") && jsonObj.getString("mobilePhone") !=null){
		candidate.setMobilePhone(jsonObj.getString("mobilePhone"));
		}
		if(jsonObj.has("alternateMobilePhone") && jsonObj.getString("alternateMobilePhone") !=null){
			candidate.setAlternateMobilePhone(jsonObj.getString("alternateMobilePhone"));
		}
		if(jsonObj.has("email") && jsonObj.getString("email") !=null){
		candidate.setEmail(jsonObj.getString("email"));
		}
		if(jsonObj.has("currentRate") && jsonObj.getString("currentRate") !=null){
		candidate.setCurrentRate(jsonObj.getString("currentRate"));
		}
		
		if(jsonObj.has("currentRateType") && jsonObj.getString("currentRateType") !=null){
			candidate.setCurrentRateType(jsonObj.getString("currentRateType"));
			}
		
		if(jsonObj.has("currentRateCurrency") && jsonObj.getString("currentRateCurrency") !=null){
		candidate.setCurrentRateCurrency(jsonObj.getString("currentRateCurrency"));
		}
		if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
		candidate.setDesignation(jsonObj.getString("designation"));
		}
		
		if(jsonObj.has("designationOtherType") && jsonObj.has("designationType") && "Other".equalsIgnoreCase(jsonObj.getString("designationType"))){
			candidate.setDesignationOtherType(jsonObj.getString("designationOtherType"));
		}else{
			candidate.setDesignationOtherType(null);
		}
		
		if(jsonObj.has("expectedRate") && jsonObj.getString("expectedRate") !=null ){
		candidate.setExpectedRate(jsonObj.getString("expectedRate"));
		}
		
		if(jsonObj.has("rateType") && jsonObj.getString("rateType") !=null ){
			candidate.setRateType(jsonObj.getString("rateType"));
			}
		
		if(jsonObj.has("expectedRateCurrencey") && jsonObj.getString("expectedRateCurrencey") !=null ){
			candidate.setExpectedRateCurrencey(jsonObj.getString("expectedRateCurrencey"));
		}
		if(jsonObj.has("summary") && jsonObj.getString("summary") !=null){
		candidate.setSummary(jsonObj.getString("summary"));
		}
		if(jsonObj.has("designationType") && jsonObj.getString("designationType") !=null){
		candidate.setDesignationType(jsonObj.getString("designationType"));
		}
		if(jsonObj.has("experienceYears") && jsonObj.getString("experienceYears") !=null){
		candidate.setExperienceYears(jsonObj.getString("experienceYears"));
		}
		
		
		if(jsonObj.has("experienceMonths") && jsonObj.getString("experienceMonths") !=null){
			candidate.setExperienceMonths(jsonObj.getString("experienceMonths"));	
		}else{
			candidate.setExperienceMonths("");
		}
		
		if(jsonObj.has("noticePeriod") && jsonObj.getString("noticePeriod") !=null){
			candidate.setNoticePeriod(jsonObj.getString("noticePeriod"));	
		}
		
		if(jsonObj.has("address") && jsonObj.getString("address") !=null){
			candidate.setAddress(jsonObj.getString("address"));
		}
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(jsonObj,orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		HashSet<CandidateSkills> candidateSkillsList = candidateSkillsService.getCandidateSkillsDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateSkills(candidateSkillsList);
		
		HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(orgLocations,user.getUserCode(),candidate,multiFile);
		candidate.setCandidateResumes(candidateResumesList);
		/*if(multiFile != null){
			if(multiFile.getOriginalFilename()!=null){
				candidate.setFileName(multiFile.getOriginalFilename());
			}
		}*/
		
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.currentDate());
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setIsFavourite(0);
		
		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		
		/*
		 * Saving Candidate DB data as ElasticSearch Documents
		 */
		saveOrUpdateCandidateElkDocument(savedCandidate);
		return savedCandidate;
	}

	@Override
	public Candidate addCandidate(Candidate candidate) throws Exception {
		return candidateDao.storeCandidate(candidate);
	}


	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * 
	 * @author David Sammeta
	 * 
	 * @param request
	 * 
	 * @return String
	 * 
	 * @throws Exception
    */
	@Override
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange,String candidateType) throws Exception {
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationID(orgLocationId,orgId,candidateStatus,updatedDateRange,candidateType);
		for(Candidate can :candidateList){
			if(can.getUpdatedDate() != null){
			 can.setCandidateUpdateDateToString(Constants.DF_DMY.format(can.getUpdatedDate()));
			}
		}
		return candidateList;
	}
	
	/**
	 * to get Candidate By CandidateId
	 * 
	 * @author Venkat Kesav
	 * 
	 * @param candidateId
	 * 
	 * @return Candidate
	 * 
	 * @throws Exception
    */
	@Override
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		return candidateDao.getCandidateByCandidateId(candidateId);
	}
	
	/**
	 * @author  prasad.k
	 * 
	 * This method is used to get Candidate By CandidateId And OrgLocationId
	 * 
	 * @param candidateId
	 * @param orgLocationId
	 * 
	 * @return candidate
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception {
	
		 Candidate  candidate = candidateDao.getCandidateByCandidateIdAndOrgLocationId(candidateId,orgLocationId);
		 return candidate;
	}

	/**
	 * @author prasad.k
	 * 
	 * This mothod is used to get CandidateEmail
	 * 
	 * @param email
	 * 
	 * @return CandidateEmail
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception {
		logger.debug("In CandidateService class...");
		return candidateDao.findByCandidateEmail(email,orgLocationId);
	}
	
	
	/**
	 * @author Venkat Kesav
	 * 
	 * This method is used to save Or Update Candidate
	 * 
	 * @param candidate
	 * 
	 * @return candidate
	 * 
	 * @throws Exception
	 */
	@Override
	public Candidate saveOrUpdateCandidate(Candidate candidate) throws Exception {
		logger.debug("In CandidateService class...");
		return candidateDao.storeCandidate(candidate);
	}

	@Override
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception {
		return candidateDao.getCandidatesByOrgLocationID(orgLocationId,requirementId,candidateType);
	}

	@Override
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllCandidatesByLocation(orgLocationId);
	}
/**
 * @author Rajashekar
 * Write candidate data to xls format
 */
	@Override
	public File getCandidateExcelFile(List<Candidate> candidatesList,String orgName) throws Exception {
		
		Workbook createExcelworkbook =  new XSSFWorkbook();
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 StringBuilder excelSheet = new StringBuilder();
		 Sheet createExcelsheet = createExcelworkbook.createSheet(excelSheet.append(orgName).append("_candidate_").append(dateFormat.format(new Date())).toString());
    	Row writeRow = null;
		String headers [] = Constants.CANDIDATE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
		 int column=0;
	        for(Candidate candidate : candidatesList){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,candidate);
	       	 column++;
	        }
	        StringBuilder orgNameSB = new StringBuilder();
	        File file = File.createTempFile(orgNameSB.append(orgName).append("_candidates_").append(dateFormat.format(new Date())).toString(),".xls");
	        file.setReadable(true);
	        /*File file = new File(orgName+"_candidates_"+dateFormat.format(new Date())+".xls");*/
			 FileOutputStream fos = new FileOutputStream(file);
			 try {
				 createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	 	
		return file;
	}

	/**
	 * @author Rajashekar
	 * writes candidate data to excel
	 * @param createExcelsheet
	 * @param writeRow
	 * @param candidate
	 * @return
	 */
private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow,Candidate candidate) {
	if(candidate.getFirstName() !=null && !"".equalsIgnoreCase(candidate.getFirstName())){
		 writeRow.createCell(0).setCellValue(candidate.getFirstName());
	}
	if(candidate.getLastName() !=null && !"".equalsIgnoreCase(candidate.getLastName())){
		writeRow.createCell(1).setCellValue(candidate.getLastName());
	}
	if(candidate.getEmail() !=null && !"".equalsIgnoreCase(candidate.getEmail())){
		writeRow.createCell(2).setCellValue(candidate.getEmail());
	}
	if(candidate.getMobilePhone() !=null && !"".equalsIgnoreCase(candidate.getMobilePhone())){
		writeRow.createCell(3).setCellValue(candidate.getMobilePhone());
	}
	
	if(candidate.getCountry() !=null && !"".equalsIgnoreCase(candidate.getCountry())){
		writeRow.createCell(4).setCellValue(candidate.getCountry());
	}
	if(candidate.getDesignation() !=null && !"".equalsIgnoreCase(candidate.getDesignation())){
		writeRow.createCell(5).setCellValue(candidate.getDesignation());
	}
	if(candidate.getCandidateEducation() !=null && candidate.getCandidateEducation().size() >0 ){
		Set<CandidateEducation> candidateEducation = candidate.getCandidateEducation();
		StringBuilder educations = new StringBuilder();
		for(CandidateEducation candidateEdu : candidateEducation){
			educations.append(candidateEdu.getName()).append(",");
		}
		String educationList = educations.toString();
		writeRow.createCell(6).setCellValue(educationList.substring(0, educations.length()-1));
	}
	 StringBuilder exp = new StringBuilder();
	if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
		writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceYears()).append(".").append(candidate.getExperienceMonths()).append("years").toString());
	}else{
		if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears())){
			writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceYears()).append("years").toString());
		}else{
			if(candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				writeRow.createCell(7).setCellValue(exp.append(candidate.getExperienceMonths()).append("Months").toString());
			}
		}
	}
	if(candidate.getCandidateSkills() !=null && candidate.getCandidateSkills().size() >0){
		StringBuilder skills = new StringBuilder();
		Set<CandidateSkills> candidateSkillsList = candidate.getCandidateSkills();
	    for(CandidateSkills candidateSkills : candidateSkillsList){
	    	skills.append(candidateSkills.getName()).append(",");
	    }
	    writeRow.createCell(8).setCellValue(skills.toString().substring(0, skills.length()-1));
	}
	if(candidate.getExpectedRate() !=null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
		StringBuilder rateBuilder = new StringBuilder();
		writeRow.createCell(9).setCellValue(rateBuilder.append(candidate.getExpectedRate()).append("/").append(candidate.getRateType()).toString());
	}else{
		writeRow.createCell(9).setCellValue(candidate.getExpectedRate());
	}
	return createExcelsheet;
}

	@Override
	public File getCandidatePdf(List<Candidate> candidatesList, String orgName) throws Exception {
		return pdfUtil.createCandidatePdfTable(candidatesList,orgName);
	}

	@Override
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId, String candidateType)
			throws Exception {
		return candidateDao.getHotListCandidatesListByOrgLocationID(orgLocationId, candidateType);
	}


	@Override
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception {
		return candidateDao.getAllRequestedCandidatesForCampaign(candidateId);
	}
	
	@Override
	public void saveOrUpdateCandidateElkDocument(Candidate candidate) throws Exception{
		 //Elastic Search Document Saving
		CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
	}

	@Override
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId,String candidateStatus) {
		return candidateDao.getCandidatesCountByOrgLocationID(orgLocationId,candidateStatus);
	}


	@Override
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception {
		return candidateDao.getHotlistCandidatesCountByTeamIdAndOrgLocationId(teamId, orgLocationId);
	}

	@Override
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId, Long orgLocationId)
			throws Exception {
		return candidateDao.getHotListCandidatesByTeamAndOrgLocationId(teamId, orgLocationId);
	}

	@Override
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId, String type) throws Exception {
		return candidateDao.getAllActiveCandidatesCountForCampaignByLocation(orgLocationId, type);
	}

	@Override
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId) throws Exception {
		return candidateDao.getAllHotlistCandidatesCountNotAssignedToTeamByLocation(orgLocationId);
	}

	@Override
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId, String type,Long orgLocationId) throws Exception {
		return candidateDao.getAllCandidatesCountForCampaignByTeam(teamId, type,orgLocationId);
	}

	@Override
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId, Integer datRange)
			throws Exception {
		return candidateDao.getAllCandidatesForHotListingWhoAreTerminating(orgLocationId,datRange);
	}
	
	@Override
	public List<Object> getAllUniversalSearchCandidateList(OrgLocations orgLocation,String searchValue)throws Exception {
		List<Object>   searchList = new ArrayList<>();
		 Long maxResults = getCandidatesCountByOrgLocationID(orgLocation.getId(),null);
		 ArrayList<String> allKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();;
			if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
				 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
				 for(String keywd : allKeyWordArray){
					 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper.getMatchQueryBuilderListForCandidate(keywd);
					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
							allBoolSubQueryBuilder.should(matchQueryBuilder);
						}
						allBoolQueryBuilder.must(allBoolSubQueryBuilder);
					}
				}
				 boolQueryBuilder.must(allBoolQueryBuilder);
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
			    	    	candidateIndex.setCandidateUpdateDateToString(Constants.DF_YMD.format(candidateIndex.getUpdatedDate()));
			   			}
			    	    searchList.add(candidateIndex);
			    	} 
			    	
			    }
		return searchList;
			
	}
//	private ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForAll(String keywd) {
//		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
//	    matchQueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
//	    matchQueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateEmail", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("address", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("designation", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("designationType", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateType", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateCurrency", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rateType", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRateCurrencey", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("summary", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("employer", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("certifications", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("candidateType", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("noticePriod", keywd));
//		//matchQueryBuilderList.add(QueryBuilders.matchQuery("currentResumeName", keywd));
//		matchQueryBuilderList.add(QueryBuilders.matchQuery("resumeContent", keywd));
//		
//		
//		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
//		if(matcher.find()){
//			Float expYears = Float.valueOf(matcher.group());
//			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceYears", expYears));
//			//matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceMonths", keywd));
//			
//			String regex = "^(\\+[9]\\d|[1]\\d{2})$";
//			RegularExpression regExp = new RegularExpression(regex);
//			String mobileKeywd =matcher.group().replaceAll(regExp.toString(), "");
//			matchQueryBuilderList.add(QueryBuilders.matchQuery("alternateMobilePhone", mobileKeywd));
//			matchQueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
//		}
//		if(StringUtils.isNumeric(keywd)){
//			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRate", keywd));
//			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRate", keywd));
//		}
//		
//			
//		return matchQueryBuilderList; 
//
//	}

//	private ArrayList<String> getSearchValuesFromChosenSelectField(String searchValue) {
//		 ArrayList<String> keyWordList = null;
//		 if(searchValue !=null && !"".equalsIgnoreCase(searchValue)){
//			 String searchKeywordString = searchValue.replaceAll("\\[", "").replaceAll("\\]","");
//		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
//		 }
//        return keyWordList;
//	}
}
