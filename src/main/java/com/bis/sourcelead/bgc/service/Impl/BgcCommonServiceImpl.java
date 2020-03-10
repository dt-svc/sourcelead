package com.bis.sourcelead.bgc.service.Impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.bis.sourcelead.bgc.dao.BgcCommonDao;
import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.dto.BgcComponentDTO;
import com.bis.sourcelead.bgc.dto.BgcStatusDTO;
import com.bis.sourcelead.bgc.dto.BgcTemplateDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.entity.BgcComponent;
import com.bis.sourcelead.bgc.entity.BgcStatus;
import com.bis.sourcelead.bgc.entity.BgcTemplate;
import com.bis.sourcelead.bgc.service.BgcCommonService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.BackgroundCheckExportUtil;


/**
 * @author Srinivas.Sabbu
 *
 */
@Service
public class BgcCommonServiceImpl implements BgcCommonService{
   
	private static final Logger logger = LoggerFactory.getLogger(BgcCommonServiceImpl.class);
	
	
	@Autowired
    BgcCommonDao bgcCommonDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	BackgroundCheckExportUtil backgroundCheckExportUtil;
	
	@Autowired
	UserService userService;
	
	@Override
	public BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetailsDTO bgcCandidateDetailsDto, User user, OrgLocations orgLocations) {
		
	    try {
		BgcCandidateDetails bgcCandidateDetails = new BgcCandidateDetails();
		
		CandidateGlobal candidate = candidateGlobalService.getCandidateGlobalByCandidateId(bgcCandidateDetailsDto.getGlobalCandidateId());
		bgcCandidateDetails.setCandidate(candidate);
		bgcCandidateDetails.setComments(bgcCandidateDetailsDto.getComments());
		bgcCandidateDetails.setCreatedBy(user);  
		
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		
		bgcCandidateDetails.setCreatedDate(currentDate);
		bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDto.getCrimeCheckStatus());
		
		OrgSpoc orgSpoc = orgSpocService.getSpocByClientOrVendorUserId(bgcCandidateDetailsDto.getBgcVendorContact());
		bgcCandidateDetails.setBgcOrgContactId(orgSpoc);
	
		bgcCandidateDetails.setCurrentBgcStatus(bgcCandidateDetailsDto.getCurrentBgcStatus());
		bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDto.getEducationCheckStaus());
		bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDto.getHealthCheckStatus());
		
		OrgLocations sourceOrgLocations = orgLocationService.getOrgLocations(bgcCandidateDetailsDto.getSourceOrglocationId());
		bgcCandidateDetails.setSourceOrglocationId(sourceOrgLocations);
		
		bgcCandidateDetails.setOrgLocationId(orgLocations);
		bgcCandidateDetails.setOthersInfoStatus(bgcCandidateDetailsDto.getOthersInfoStatus());
		bgcCandidateDetails.setPersonalInfoStatus(bgcCandidateDetailsDto.getPersonalInfoStatus());
		bgcCandidateDetails.setPriorEmployersCheckStatus(bgcCandidateDetailsDto.getPriorEmployersCheckStatus());
		bgcCandidateDetails.setStatus(Constants.RECORD_ACTIVE);
		bgcCandidateDetails.setUpdatedBy(user);
		bgcCandidateDetails.setUpdatedDate(currentDate); 
		bgcCandidateDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID()); // need to add random UUID for the BGC Candidate.
		bgcCandidateDetails.setGeneratedId(getBgcGeneratedId(orgLocations)); // setting generated id for the BGC candidate for ORG specific.
		
		return bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		
	    }catch(Exception e) {
	      e.printStackTrace();
	      logger.error("Exception caught  in saveBgcCandidateDetails service "+e.getMessage());
	      return null;
	    }
	}
	@Override
	public String getBgcGeneratedId(OrgLocations orgLocations) {
		String companyShortCode = null;
		Long number = 1L;
		String immigrationId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.BGC_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = bgcCommonDao.getBgcIdByOrgLocationId(orgLocations,
				generatedIdcount);
		if (checkNumber == null) {
			immigrationId = number.toString();
		} else {
			Long reqId = checkNumber + 1;
			immigrationId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(immigrationId));
		StringBuffer sb = new StringBuffer();
		if (orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())) {
			companyShortCode = orgLocations.getGeneratedUserId();
		}
		return sb.append(companyShortCode).append(Constants.BGC_ENTITY).append(convetredRequirementsId)
				.toString();
	}

	@Override
	public ResponseList<BgcCandidateDetailsDTO> getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) {
		try {
			orgLocations = orgLocationService.getOrgLocations(orgLocations.getId()); // loading orgLocations for the isBackGroundCheck property.
			return   bgcCommonDao.getBgcCadidateListByOrgLocationId(bgcCandidateDetailsDTO,searchValue,orgLocations,  filterVal,orgTypeVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String deleteBgcCandidateDetailsbyUuiId(String uuid) {
		return  bgcCommonDao.deleteBgcCandidateDetailsbyUuiId(uuid);
	}
	
	@Override
	public BgcCandidateDetailsDTO getBgcCandidateDetailsByBgcUuid(String bgcUuid) {
		try{
		BgcCandidateDetailsDTO bgcCandidateDetailsDTO = new BgcCandidateDetailsDTO();
		StringBuilder fullName = new StringBuilder();
		BgcCandidateDetails bgcCandidateDetails = bgcCommonDao.getBgcCandidateDetailsByBgcUuid(bgcUuid);
		if(bgcCandidateDetails != null && bgcCandidateDetails.getCandidate() != null){
			BeanUtils.copyProperties(bgcCandidateDetails, bgcCandidateDetailsDTO);
			if(bgcCandidateDetails.getCandidate().getLastName() != null){
				fullName.append(bgcCandidateDetails.getCandidate().getFirstName()).append(" ").append(bgcCandidateDetails.getCandidate().getLastName());
			}else{
				fullName.append(bgcCandidateDetails.getCandidate().getFirstName());
			}
			
			bgcCandidateDetailsDTO.setFullName(fullName.toString());
			bgcCandidateDetailsDTO.setGeneratedId(bgcCandidateDetails.getGeneratedId());
			bgcCandidateDetailsDTO.setAadharNumber(bgcCandidateDetails.getCandidate().getAadharNumber());
			bgcCandidateDetailsDTO.setPanNumber(bgcCandidateDetails.getCandidate().getPanNumber());
			bgcCandidateDetailsDTO.setAddress(bgcCandidateDetails.getCandidate().getAddress());
			bgcCandidateDetailsDTO.setEmail(bgcCandidateDetails.getCandidate().getEmail());
			bgcCandidateDetailsDTO.setCandidateId(bgcCandidateDetails.getCandidate().getId());
			bgcCandidateDetailsDTO.setUpdatedPercentage(bgcCandidateDetails.getupdatedPercentage());
		}
		return bgcCandidateDetailsDTO;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateDetailsByBgcUuid Service");
			return null;
		}
		}


	@Override
	public List<CandidateEducation> getBgcCandidateEducationSummaryByCandidateId(Long candidateId)  {
		try{
		List<CandidateEducation> candidateEducationList = new ArrayList<CandidateEducation>();
		if(candidateId != null){
			StringBuilder academicYearBuilder = new StringBuilder("");
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			candidateEducationList = bgcCommonDao.getBgcCandidateEducationSummaryListByCandidateId(candidateId);
			if(candidateEducationList.size() > 0){
			for(int i=0; i<candidateEducationList.size(); i++){
				Date startDate = candidateEducationList.get(i).getStartDate();
				Date endDate = candidateEducationList.get(i).getEndDate();
				if(startDate != null && endDate != null) {
				academicYearBuilder.append(formatter.format(startDate)).append(" to ").append(formatter.format(endDate));
				candidateEducationList.get(i).setAcademicYear(academicYearBuilder.toString());
				academicYearBuilder.delete(0, academicYearBuilder.length());
			   }
			}
			}
		}
		return candidateEducationList;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEducationSummaryByCandidateUuid Service");
			return null;
		}
	}


	@Override
	public List<CandidateEmployers> getBgcCandidateEmploymentSummary(String dateFormat, Long candidateId) {
		try{
		List<CandidateEmployers> candidateEmploymentList = new ArrayList<CandidateEmployers>();
		if(candidateId != null){
			candidateEmploymentList = bgcCommonDao.getBgcCandidateEmploymentSummaryListByCandidateId(candidateId);
			if(candidateEmploymentList.size() > 0){
			for(int i=0; i<candidateEmploymentList.size(); i++){
				if(candidateEmploymentList.get(i).getStartDate() == null && candidateEmploymentList.get(i).getEndDate() == null){
					if(StringUtils.isNotBlank(candidateEmploymentList.get(i).getStartYear()) && StringUtils.isNotBlank(candidateEmploymentList.get(i).getStartMonth() )
							&& StringUtils.isNotBlank(candidateEmploymentList.get(i).getEndYear()) && StringUtils.isNotBlank(candidateEmploymentList.get(i).getEndMonth())) {
					Integer startMonth = Integer.parseInt(candidateEmploymentList.get(i).getStartMonth().split("&")[0]);
					Integer startYear = Integer.parseInt(candidateEmploymentList.get(i).getStartYear());	
					Integer endMonth = Integer.parseInt(candidateEmploymentList.get(i).getEndMonth().split("&")[0]);
					Integer endYear = Integer.parseInt(candidateEmploymentList.get(i).getEndYear());
						StringBuilder startMonthBuilder = new StringBuilder("");
						StringBuilder endMonthBuilder = new StringBuilder("");
						if(startMonth < 10){
							startMonthBuilder.append(Constants.RECORD_INACTIVE).append(startMonth);
						}else {
							startMonthBuilder.append(startMonth);
						}
						if(endMonth < 10){
							endMonthBuilder.append(Constants.RECORD_INACTIVE).append(endMonth);
						}else {
							endMonthBuilder.append(endMonth);
						}
						Date startDate = commonUtil.orgLocationDateFormat("yyyy-MM-dd", startYear+"-"+startMonthBuilder.toString()+"-"+"01");
						Date endDate = commonUtil.orgLocationDateFormat("yyyy-MM-dd", endYear+"-"+endMonthBuilder.toString()+"-"+"01");
						Integer difference = commonUtil.diffBtwTowDates(startDate, endDate);
						Integer period = difference / 365;
						candidateEmploymentList.get(i).setPeriod(period.toString());
					}
				}else{
					Date startDate = candidateEmploymentList.get(i).getStartDate();
					Date endDate = candidateEmploymentList.get(i).getEndDate();
					if(startDate != null && endDate!= null) {
						Integer difference = commonUtil.diffBtwTowDates(startDate, endDate);
						Integer period = difference / 365;
						candidateEmploymentList.get(i).setPeriod(period.toString());	
					}
				}
			}
			}
		}
		return candidateEmploymentList;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEmploymentSummary Service");
			return null;
		}
	}



	@Override
	public BgcCandidateDetails saveEditBgcCandidate(BgcCandidateDetailsDTO bgcCandidateDetailsDTO)  {
		try{
		LinkedList<Double> statusList = new LinkedList<Double>();	
		BgcCandidateDetails bgcCandidateDetails = bgcCommonDao.getBgcCandidateDetailsByBgcUuid(bgcCandidateDetailsDTO.getUuid());
		if(bgcCandidateDetailsDTO != null && bgcCandidateDetails != null){
			bgcCandidateDetails.setEducationCheckStaus(bgcCandidateDetailsDTO.getEducationCheckStaus());
			bgcCandidateDetails.setPriorEmployersCheckStatus(bgcCandidateDetailsDTO.getPriorEmployersCheckStatus());
			bgcCandidateDetails.setPersonalInfoStatus(bgcCandidateDetailsDTO.getPersonalInfoStatus());
			bgcCandidateDetails.setHealthCheckStatus(bgcCandidateDetailsDTO.getHealthCheckStatus());
			bgcCandidateDetails.setCrimeCheckStatus(bgcCandidateDetailsDTO.getCrimeCheckStatus());
			bgcCandidateDetails.setCurrentBgcStatus(bgcCandidateDetailsDTO.getCurrentBgcStatus());
			bgcCandidateDetails.setComments(bgcCandidateDetailsDTO.getComments());
			
			//26-dec-2019
			//percentage values are predefined in a map for each of 6 status in bgc.
			//values for each status is added into a list and percentage is calculated. 
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getCurrentBgcStatus()) != null){
				statusList.add(0, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getCurrentBgcStatus()));
			}
			
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getPersonalInfoStatus()) != null){
				statusList.add(1, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getPersonalInfoStatus()));
			}
			
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getEducationCheckStaus()) != null){
				statusList.add(2, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getEducationCheckStaus()));
			}
			
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getPriorEmployersCheckStatus()) != null){
				statusList.add(3, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getPriorEmployersCheckStatus()));
			}
			
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getHealthCheckStatus()) != null){
				statusList.add(4, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getHealthCheckStatus()));
			}
			
			if(Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getCrimeCheckStatus()) != null){
				statusList.add(5, Constants.BACKGROUND_CHECK_STATUS_MAP.get(bgcCandidateDetails.getCrimeCheckStatus()));
			}
			
			if(statusList != null && statusList.size() == 6){
				Double reducedPercentage = statusList.stream().reduce(0.00, Double::sum);
				Integer percentage = (reducedPercentage.intValue() * 100) / 100;
				if(percentage <= 100){
					bgcCandidateDetails.setUpdatedPercentage(String.valueOf(percentage));
				}
			}
		}
				bgcCommonDao.saveBgcCandidateDetails(bgcCandidateDetails);
		return bgcCandidateDetails;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e+" in saveEditBgcCandidate Service");
			return null;
		}
	}
	
	@Override
	public void exportBgcToPdfOrExcel(BgcCandidateDetailsDTO bgcCandidateDetailsDTO, ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList,
			OrgLocations orgLocations, HttpServletResponse response) {
		try{
		File file = null;
  		
	    if(bgcCandidateDetailsDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file = backgroundCheckExportUtil.exportBgcToPdf(bgcCandidateDetailsDTOList, orgLocations);
	    }else {
	    	 file = backgroundCheckExportUtil.exportBgcToExcel(bgcCandidateDetailsDTOList, orgLocations);
	    }
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	@Override
	public List<BgcStatus> getBgcStatusListByOrgLocationId(Long orgLocationId) {
		return bgcCommonDao.getBgcStatusListByOrgLocationId(orgLocationId);
	}
	@Override
	public ResponseList<BgcComponentDTO> getBgcComponentListByOrgLocationId(BgcComponentDTO bgcComponentDTO,String searchValue,
			OrgLocations orgLocations) {
		return bgcCommonDao.getBgcComponentListByOrgLocationId(bgcComponentDTO, searchValue, orgLocations);
	}
	@Override
	public ResponseList<BgcTemplateDTO> getBgcOrgcTemplateListByOrgLocationId(BgcTemplateDTO bgcTemplateDTO,String searchValue, OrgLocations orgLocations) {
		return bgcCommonDao.getBgcOrgcTemplateListByOrgLocationId(bgcTemplateDTO, searchValue, orgLocations) ;
	}
	@Override
	public String saveOrUpdateBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO, User user, OrgLocations orgLocations) {
		
		
		BgcStatus  bgcStatus = new BgcStatus();
		if(bgcStatusDTO.getId() != null) {
			bgcStatus.setId(bgcStatusDTO.getId());
		}
		bgcStatus.setOrgLocationId(orgLocations);
		Date date = new Date();
        Date currentDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        bgcStatus.setStatusName(bgcStatusDTO.getStatusName());
        bgcStatus.setColor(bgcStatusDTO.getColor());
        bgcStatus.setDescription(bgcStatusDTO.getDescription());
        
		if(bgcStatus.getId() == null) { // for new record creation 
			bgcStatus.setCreatedBy(user);
			bgcStatus.setCreatedDate(currentDate);
			bgcStatus.setUpdatedBy(user);
			bgcStatus.setUpdatedDate(currentDate);
		}else { // for update record 
			bgcStatus.setId(bgcStatusDTO.getId());
			bgcStatus.setUpdatedBy(user);
			bgcStatus.setUpdatedDate(currentDate);
		}
		return bgcCommonDao.saveOrUpdateBgcStatusListByOrgLocationId(bgcStatus) ;
	}
	@Override
	public ResponseList<BgcStatusDTO> getBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO, String searchValue,
			OrgLocations orgLocations) {
		return  bgcCommonDao.getBgcStatusListByOrgLocationId(bgcStatusDTO, searchValue,orgLocations);
	}
	@Override
	public String deleteBgcStatusListById(Long statusId) {
		return bgcCommonDao.deleteBgcStatusListByOrgLocationId(statusId);
	}
	@Override
	public BgcStatusDTO getBgcStatusDetailsById(Long statusId) {
		return bgcCommonDao.getBgcStatusDetailsById(statusId);
	}
	@Override
	public String saveOrUpdateBgcComponentListByOrgLocationId(BgcComponentDTO bgcComponentDTO, User user,
			OrgLocations orgLocations) {
		BgcComponent  bgcComponent = new BgcComponent();
		if(bgcComponentDTO.getId() != null) {
			bgcComponent.setId(bgcComponentDTO.getId());
		}
		bgcComponent.setOrgLocationId(orgLocations);
		Date date = new Date();
        Date currentDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        bgcComponent.setName(bgcComponentDTO.getName());
        bgcComponent.setDescription(bgcComponentDTO.getDescription());
        
		if(bgcComponent.getId() == null) { // for new record creation 
			bgcComponent.setCreatedBy(user);
			bgcComponent.setCreatedDate(currentDate);
			bgcComponent.setUpdatedBy(user);
			bgcComponent.setUpdatedDate(currentDate);
		}else { // for update record 
			bgcComponent.setId(bgcComponentDTO.getId());
			bgcComponent.setUpdatedBy(user);
			bgcComponent.setUpdatedDate(currentDate);
		}
		return bgcCommonDao.saveOrUpdateBgcComponentListByOrgLocationId(bgcComponent) ;
	}
	@Override
	public BgcComponentDTO getBgcComponentDetailsById(Long componentId) {
		return bgcCommonDao.getBgcComponentDetailsById(componentId);
	}
	@Override
	public String deleteBgcComponentById(Long componentId) {
		return bgcCommonDao.deleteBgcComponentById(componentId);
	}
	@Override
	public List<BgcStatus> getBgcUniqueStatusListByOrgLocationId(Long orgLocationId) {
		return bgcCommonDao.getBgcUniqueStatusListByOrgLocationId(orgLocationId);
	}
	@Override
	public List<BgcComponent> getBgcUniqueComponentListByOrgLocationId(Long orgLocationId) {
		return bgcCommonDao.getBgcUniqueComponentListByOrgLocationId(orgLocationId);
	}
	@Override
	public BgcTemplate saveOrUpdateBgcTemplateByOrgLocationId(BgcTemplateDTO bgcTemplateDTO, User user,
			OrgLocations orgLocations) {
		BgcTemplate  bgcTemplate = new BgcTemplate();
		if(bgcTemplateDTO.getId() != null) {
			bgcTemplate = bgcCommonDao.getBgcTemplateDetailsById(bgcTemplateDTO.getId());
		}
		
		Date date = new Date();
		bgcTemplate.setOrgLocationId(orgLocations);
        Date currentDate = commonUtil.orgLocationCommonDate(date, orgLocations);
        bgcTemplate.setName(bgcTemplateDTO.getName());
        bgcTemplate.setDescription(bgcTemplateDTO.getDescription());
        bgcTemplate.setStatusString(bgcTemplateDTO.getStatusString());
        bgcTemplate.setComponentString(bgcTemplateDTO.getComponentString());
        bgcTemplate.setConfigureType(bgcTemplateDTO.getConfigureType());
        bgcTemplate.setStatus(Constants.RECORD_ACTIVE);
		if(bgcTemplateDTO.getId() == null) { // for new record creation 
			bgcTemplate.setCreatedBy(user);
			bgcTemplate.setCreatedDate(currentDate);
			bgcTemplate.setOrgLocationId(orgLocations);
			bgcTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID()); // need to add random UUID for the BGC Template Details.
			bgcTemplate.setUpdatedBy(user);
			bgcTemplate.setUpdatedDate(currentDate);
		}else { // for update record 
			bgcTemplate.setUpdatedBy(user);
			bgcTemplate.setUpdatedDate(currentDate);
		}
		return bgcCommonDao.saveOrUpdateBgcTemplateByOrgLocationId(bgcTemplate, orgLocations);
	}
	@Override
	public BgcTemplateDTO getBgcTemplateDetailsByUuid(String uuid) {
		return bgcCommonDao.getBgcTemplateDetailsByUuid(uuid);
	}
	@Override
	public String deleteBgcTemplateByUuid(String templateUuid) {
		return bgcCommonDao.deleteBgcTemplateByUuid(templateUuid);
	}
}
