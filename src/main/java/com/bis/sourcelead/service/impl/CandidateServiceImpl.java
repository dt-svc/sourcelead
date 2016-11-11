package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;

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
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	PdfUtil pdfUtil;
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
		if(jsonObj.has("email") && jsonObj.getString("email") !=null){
		candidate.setEmail(jsonObj.getString("email"));
		}
		if(jsonObj.has("currentRate") && jsonObj.getString("currentRate") !=null){
		candidate.setCurrentRate(jsonObj.getString("currentRate"));
		}
		if(jsonObj.has("currentRateCurrency") && jsonObj.getString("currentRateCurrency") !=null){
		candidate.setCurrentRateCurrency(jsonObj.getString("currentRateCurrency"));
		}
		if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
		candidate.setDesignation(jsonObj.getString("designation"));
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
		if(multiFile != null){
			if(multiFile.getOriginalFilename()!=null){
				candidate.setFileName(multiFile.getOriginalFilename());
			}
		}
		
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.currentDate());
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		
		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange) throws Exception {
		List<Candidate> candidateList = candidateDao.getCandidatesListByOrgLocationID(orgLocationId,orgId,candidateStatus,updatedDateRange);
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
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId) throws Exception {
		return candidateDao.getCandidatesByOrgLocationID(orgLocationId,requirementId);
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
		CellStyle style = getCellStyle(createExcelworkbook);
        
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgName+"_candidate_"+dateFormat.format(new Date()));
		Row writeRow = null;
		createExcelsheet = setExcelHeaders(createExcelsheet,style);
		 int column=0;
	        for(Candidate candidate : candidatesList){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,candidate);
	       	 column++;
	        }
	        
	        File file = new File(orgName+"_candidates_"+dateFormat.format(new Date())+".xls");
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
 * @param createExcelworkbook
 * @return Style
 */
private CellStyle getCellStyle(Workbook createExcelworkbook) {
	CellStyle style = createExcelworkbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    Font font = createExcelworkbook.createFont();
   font.setColor(IndexedColors.BLACK.getIndex());
   style.setFont(font);
   return style;
}
/**
 * @author Rajashekar
 * It resturns sheet with Headers
 * @param createExcelsheet
 * @param style
 * @return Sheet
 */
private Sheet setExcelHeaders(Sheet createExcelsheet, CellStyle style) {
	String headers [] = Constants.CANDIDATE_EXCEL_HEADERS;
	Row excelHeader = createExcelsheet.createRow(0);
	 for (int i=0; i<headers.length;i++){
	         Cell cell = excelHeader.createCell(i);
	         cell.setCellValue(headers[i]);
	         cell.setCellStyle(style);
	     }
	return createExcelsheet;
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
	if(candidate.getCity() !=null && !"".equalsIgnoreCase(candidate.getCity())){
		writeRow.createCell(4).setCellValue(candidate.getCity());
	}
	if(candidate.getDesignation() !=null && !"".equalsIgnoreCase(candidate.getDesignation())){
		writeRow.createCell(5).setCellValue(candidate.getDesignation());
	}
	if(candidate.getCandidateEducation() !=null && candidate.getCandidateEducation().size() >0 ){
		Set<CandidateEducation> candidateEducation = candidate.getCandidateEducation();
		StringBuilder educations = new StringBuilder();
		for(CandidateEducation candidateEdu : candidateEducation){
			educations.append(candidateEdu.getName()+",");
		}
		String educationList = educations.toString();
		writeRow.createCell(6).setCellValue(educationList.substring(0, educations.length()-1));
	}
	if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
		writeRow.createCell(7).setCellValue(candidate.getExperienceYears()+"."+candidate.getExperienceMonths()+"years");
	}else{
		if(candidate.getExperienceYears() !=null && !"".equalsIgnoreCase(candidate.getExperienceYears())){
			writeRow.createCell(7).setCellValue(candidate.getExperienceYears()+"years");
		}else{
			if(candidate.getExperienceMonths() !=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				writeRow.createCell(7).setCellValue(candidate.getExperienceMonths()+"Months");
			}
		}
	}
	if(candidate.getCandidateSkills() !=null && candidate.getCandidateSkills().size() >0){
		StringBuilder skills = new StringBuilder();
		Set<CandidateSkills> candidateSkillsList = candidate.getCandidateSkills();
	    for(CandidateSkills candidateSkills : candidateSkillsList){
	    	skills.append(candidateSkills.getName()+",");
	    }
	    writeRow.createCell(8).setCellValue(skills.toString().substring(0, skills.length()-1));
	}if(candidate.getExpectedRate() !=null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
		 writeRow.createCell(9).setCellValue(candidate.getExpectedRate()+"/"+candidate.getRateType());
	}else{
		writeRow.createCell(9).setCellValue(candidate.getExpectedRate());
	}
	return createExcelsheet;
}

	@Override
	public File getCandidatePdf(List<Candidate> candidatesList, String orgName) throws Exception {
		return pdfUtil.createCandidatePdfTable(candidatesList,orgName);
	}
}
