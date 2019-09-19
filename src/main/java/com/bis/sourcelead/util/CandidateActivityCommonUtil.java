package com.bis.sourcelead.util;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateHistoryDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateCertificationsHistory;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateDocumentsHistory;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEducationHistory;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateEmployersHistory;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.CandidateReferencesHistory;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateResumesHistory;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;
/**
 * This is the commonutil for saving candidate activities throughout the
 * application
 * 
 * @author: Venkat Kesav
 * 
 */
@Component
public class CandidateActivityCommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CandidateActivityCommonUtil.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateTimelineActivityDao candidateTimelineActivityDao;
	
	@Autowired
	private CandidateHistoryDao candidateHistoryDao;
	
	public void storeCandidateActivity(User user, OrgLocations orgLocations, Candidate candidate, String actionOn, String actionDesc, String actionSummary, String actionIcon) throws Exception
	{
		CandidateTimelineActivity candidateTimelineActivity = new CandidateTimelineActivity();
		
		candidateTimelineActivity.setCandidate(candidate);
		candidateTimelineActivity.setOrgLocationId(orgLocations);
		if(user !=null){
			candidateTimelineActivity.setActionBy(user.getFullName());
			candidateTimelineActivity.setCreatedUser(user);
			candidateTimelineActivity.setCreatedBy(user.getUserCode());
			candidateTimelineActivity.setUpdatedBy(user.getUserCode());
		}
		else{
			candidateTimelineActivity.setActionBy(null);
			candidateTimelineActivity.setCreatedUser(null);
			candidateTimelineActivity.setCreatedBy(null);
			candidateTimelineActivity.setUpdatedBy(null);
		}
		candidateTimelineActivity.setActionOn(actionOn);
		candidateTimelineActivity.setActionDesc(actionDesc);
		candidateTimelineActivity.setActionSummary(actionSummary);
		candidateTimelineActivity.setActionIcon(actionIcon);
		candidateTimelineActivity.setStatus(Constants.RECORD_ACTIVE);
		candidateTimelineActivity.setCreatedDate(commonUtil.currentDate());
		candidateTimelineActivity.setUpdatedDate(commonUtil.currentDate());
		
		candidateTimelineActivityDao.storeCandidateTimelineActivity(candidateTimelineActivity);
	}
	
	public void storeCandidateHistory(Candidate candidate, User user) throws Exception
	{
		CandidateHistory candidateHistory = new CandidateHistory();
		candidateHistory.setCandidateId(candidate);
		candidateHistory.setCandidateType(candidate.getCandidateType());
		candidateHistory.setCandidateUUID(candidate.getCandidateUUID());
		candidateHistory.setIsFavourite(candidate.getIsFavourite());
		candidateHistory.setIsFavouriteAssociate(candidate.getIsFavouriteAssociate());
		candidateHistory.setFirstName(candidate.getFirstName());
		candidateHistory.setLastName(candidate.getLastName());
		candidateHistory.setCountry(candidate.getCountry());
		candidateHistory.setState(candidate.getState());
		candidateHistory.setCity(candidate.getCity());
		candidateHistory.setLocation(candidate.getLocation());
		candidateHistory.setZipCode(candidate.getZipCode());
		candidateHistory.setLatitude(candidate.getLatitude());
		candidateHistory.setLongitude(candidate.getLongitude());
		candidateHistory.setMobilePhone(candidate.getMobilePhone());
		candidateHistory.setAlternateMobilePhone(candidate.getAlternateMobilePhone());
		candidateHistory.setEmail(candidate.getEmail());
		candidateHistory.setCurrentRate(candidate.getCurrentRate());
		candidateHistory.setCurrentRateType(candidate.getCurrentRateType());
		candidateHistory.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
		candidateHistory.setDesignation(candidate.getDesignation());
		candidateHistory.setDesignationOtherType(candidate.getDesignationOtherType());
		candidateHistory.setExpectedRate(candidate.getExpectedRate());
		candidateHistory.setRateType(candidate.getRateType());
		candidateHistory.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
		candidateHistory.setSummary(candidate.getSummary());
		candidateHistory.setDesignationType(candidate.getDesignationType());
		candidateHistory.setExperienceYears(candidate.getExperienceYears());
		candidateHistory.setExperienceMonths(candidate.getExperienceMonths());
		candidateHistory.setNoticePeriod(candidate.getNoticePeriod());
		candidateHistory.setAddress(candidate.getAddress());
		candidateHistory.setSkillsName(candidate.getSkillsName());
		candidateHistory.setVisaType(candidate.getVisaType());
		candidateHistory.setPrimarySkill(candidate.getPrimarySkill());
		candidateHistory.setIsAssociate(candidate.getIsAssociate());
		candidateHistory.setStatus(candidate.getStatus());
		candidateHistory.setCreatedUser(user);
		if(user != null){
		candidateHistory.setCreatedBy(user.getUserCode());
		}
		else{
			candidateHistory.setCreatedBy(null);	
		}
		candidateHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateHistory(candidateHistory);
		storeCandidateEducationListHistory(candidate.getCandidateEducation(), user);
		storeCandidateEmployersListHistory(candidate.getCandidateEmployers(), user);
		storeCandidateResumesListHistory(candidate.getCandidateResumes(), user);
	}
	
	
	public void storeCandidateEducationListHistory(Set<CandidateEducation> candidateEducationList, User user) throws Exception
	{
		HashSet<CandidateEducationHistory> candidateEducationHistorySet = new HashSet<CandidateEducationHistory>();
		for(CandidateEducation candidateEducation : candidateEducationList){
			CandidateEducationHistory candidateEducationHistory = new CandidateEducationHistory();
			candidateEducationHistory.setCandidateEducation(candidateEducation);
			candidateEducationHistory.setName(candidateEducation.getName());
			candidateEducationHistory.setSpecialization(candidateEducation.getSpecialization());
			candidateEducationHistory.setStartDate(candidateEducation.getStartDate());
			candidateEducationHistory.setEndDate(candidateEducation.getEndDate());
			candidateEducationHistory.setGrade(candidateEducation.getGrade());
			candidateEducationHistory.setGradeType(candidateEducation.getGradeType());
			candidateEducationHistory.setInstitute(candidateEducation.getInstitute());
			candidateEducationHistory.setAddress(candidateEducation.getAddress());
			candidateEducationHistory.setStatus(candidateEducation.getStatus());
			candidateEducationHistory.setCreatedUser(user);
			candidateEducationHistory.setCreatedBy(user.getUserCode());
			candidateEducationHistory.setCreatedDate(commonUtil.currentDate());
			candidateEducationHistorySet.add(candidateEducationHistory);
		}
		candidateHistoryDao.storeCandidateEducationHistoryList(candidateEducationHistorySet);
	}
	
	
	public void storeCandidateEmployersListHistory(Set<CandidateEmployers> candidateEmployersList, User user) throws Exception
	{
		HashSet<CandidateEmployersHistory> candidateEmployersHistorySet = new HashSet<CandidateEmployersHistory>();
		for(CandidateEmployers candidateEmployers : candidateEmployersList){
			CandidateEmployersHistory candidateEmployersHistory = new CandidateEmployersHistory();
			candidateEmployersHistory.setCandidateEmployers(candidateEmployers);
			candidateEmployersHistory.setName(candidateEmployers.getName());
			candidateEmployersHistory.setIsCurrentPosition(candidateEmployers.getIsCurrentPosition());
			candidateEmployersHistory.setDesignation(candidateEmployers.getDesignation());
			candidateEmployersHistory.setAddress(candidateEmployers.getAddress());
			candidateEmployersHistory.setStartMonth(candidateEmployers.getStartMonth());
			candidateEmployersHistory.setStartYear(candidateEmployers.getStartYear());
			candidateEmployersHistory.setStartDate(candidateEmployers.getStartDate());
			candidateEmployersHistory.setEndDate(candidateEmployers.getEndDate());
			candidateEmployersHistory.setEndMonth(candidateEmployers.getEndMonth());
			candidateEmployersHistory.setEndYear(candidateEmployers.getEndYear());
			candidateEmployersHistory.setStatus(candidateEmployers.getStatus());
			candidateEmployersHistory.setCreatedUser(user);
			candidateEmployersHistory.setCreatedBy(user.getUserCode());
			candidateEmployersHistory.setCreatedDate(commonUtil.currentDate());
			candidateEmployersHistorySet.add(candidateEmployersHistory);
		}
		candidateHistoryDao.storeCandidateEmployersHistoryList(candidateEmployersHistorySet);
	}
	
	
	public void storeCandidateResumesListHistory(Set<CandidateResumes> candidateResumesList, User user) throws Exception
	{
		HashSet<CandidateResumesHistory> candidateResumesHistorySet = new HashSet<CandidateResumesHistory>();
		for(CandidateResumes candidateResumes : candidateResumesList){
			CandidateResumesHistory candidateResumesHistory = new CandidateResumesHistory();
			candidateResumesHistory.setCandidateResumes(candidateResumes);
			candidateResumesHistory.setTitle(candidateResumes.getTitle());
			candidateResumesHistory.setDescription(candidateResumes.getDescription());
			candidateResumesHistory.setFileURL(candidateResumes.getFileURL());
			candidateResumesHistory.setFileContentType(candidateResumes.getFileContentType());
			candidateResumesHistory.setFileName(candidateResumes.getFileName());
			candidateResumesHistory.setResumeStatus(candidateResumes.getResumeStatus());
			candidateResumesHistory.setResumeUUID(candidateResumes.getResumeUUID());
			candidateResumesHistory.setStatus(candidateResumes.getStatus());
			candidateResumesHistory.setCreatedUser(user);
			candidateResumesHistory.setCreatedBy(user.getUserCode());
			candidateResumesHistory.setCreatedDate(commonUtil.currentDate());
			candidateResumesHistorySet.add(candidateResumesHistory);
		}
		candidateHistoryDao.storeCandidateResumesHistoryList(candidateResumesHistorySet);
	}
	
	
	
	public void storeCandidateEducationHistory(CandidateEducation candidateEducation, User user) throws Exception
	{
		CandidateEducationHistory candidateEducationHistory = new CandidateEducationHistory();
		candidateEducationHistory.setCandidateEducation(candidateEducation);
		candidateEducationHistory.setName(candidateEducation.getName());
		candidateEducationHistory.setSpecialization(candidateEducation.getSpecialization());
		candidateEducationHistory.setStartDate(candidateEducation.getStartDate());
		candidateEducationHistory.setEndDate(candidateEducation.getEndDate());
		candidateEducationHistory.setGrade(candidateEducation.getGrade());
		candidateEducationHistory.setGradeType(candidateEducation.getGradeType());
		candidateEducationHistory.setInstitute(candidateEducation.getInstitute());
		candidateEducationHistory.setAddress(candidateEducation.getAddress());
		candidateEducationHistory.setStatus(candidateEducation.getStatus());
		if(user !=null){
		candidateEducationHistory.setCreatedUser(user);
		candidateEducationHistory.setCreatedBy(user.getUserCode());
		}
		else{
			candidateEducationHistory.setCreatedUser(null);
			candidateEducationHistory.setCreatedBy(null);	
			
		}
		candidateEducationHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateEducationHistory(candidateEducationHistory);
	}
	
	
	public void storeCandidateEmployersHistory(CandidateEmployers candidateEmployers, User user) throws Exception
	{
		CandidateEmployersHistory candidateEmployersHistory = new CandidateEmployersHistory();
		candidateEmployersHistory.setCandidateEmployers(candidateEmployers);
		candidateEmployersHistory.setName(candidateEmployers.getName());
		candidateEmployersHistory.setIsCurrentPosition(candidateEmployers.getIsCurrentPosition());
		candidateEmployersHistory.setDesignation(candidateEmployers.getDesignation());
		candidateEmployersHistory.setAddress(candidateEmployers.getAddress());
		candidateEmployersHistory.setStartMonth(candidateEmployers.getStartMonth());
		candidateEmployersHistory.setStartYear(candidateEmployers.getStartYear());
		candidateEmployersHistory.setStartDate(candidateEmployers.getStartDate());
		candidateEmployersHistory.setEndDate(candidateEmployers.getEndDate());
		candidateEmployersHistory.setEndMonth(candidateEmployers.getEndMonth());
		candidateEmployersHistory.setEndYear(candidateEmployers.getEndYear());
		candidateEmployersHistory.setStatus(candidateEmployers.getStatus());
		if(user != null){
			candidateEmployersHistory.setCreatedUser(user);
			candidateEmployersHistory.setCreatedBy(user.getUserCode());
		}
		else{
			candidateEmployersHistory.setCreatedUser(null);
			candidateEmployersHistory.setCreatedBy(null);
		}
		candidateEmployersHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateEmployersHistory(candidateEmployersHistory);
	}
	
	
	public void storeCandidateResumesHistory(CandidateResumes candidateResumes, User user) throws Exception
	{
		CandidateResumesHistory candidateResumesHistory = new CandidateResumesHistory();
		candidateResumesHistory.setCandidateResumes(candidateResumes);
		candidateResumesHistory.setTitle(candidateResumes.getTitle());
		candidateResumesHistory.setDescription(candidateResumes.getDescription());
		candidateResumesHistory.setFileURL(candidateResumes.getFileURL());
		candidateResumesHistory.setFileContentType(candidateResumes.getFileContentType());
		candidateResumesHistory.setFileName(candidateResumes.getFileName());
		candidateResumesHistory.setResumeStatus(candidateResumes.getResumeStatus());
		candidateResumesHistory.setResumeUUID(candidateResumes.getResumeUUID());
		candidateResumesHistory.setStatus(candidateResumes.getStatus());
		if(user !=null){
		candidateResumesHistory.setCreatedUser(user);
		candidateResumesHistory.setCreatedBy(user.getUserCode());
		}
		else{
			candidateResumesHistory.setCreatedUser(null);
			candidateResumesHistory.setCreatedBy(null);
			
		}
		candidateResumesHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateResumesHistory(candidateResumesHistory);
	}
	
	public void storeCandidateCertificationsHistory(CandidateCertifications candidateCertifications, User user) throws Exception
	{
		CandidateCertificationsHistory candidateCertificationsHistory = new CandidateCertificationsHistory();
		candidateCertificationsHistory.setCandidateCertifications(candidateCertifications);
		candidateCertificationsHistory.setName(candidateCertifications.getName());
		candidateCertificationsHistory.setProvider(candidateCertifications.getProvider());
		candidateCertificationsHistory.setScore(candidateCertifications.getScore());
		candidateCertificationsHistory.setScoreType(candidateCertifications.getScoreType());
		candidateCertificationsHistory.setDescription(candidateCertifications.getDescription());
		candidateCertificationsHistory.setValidFrom(candidateCertifications.getValidFrom());
		candidateCertificationsHistory.setValidTo(candidateCertifications.getValidTo());
		candidateCertificationsHistory.setStatus(candidateCertifications.getStatus());
		candidateCertificationsHistory.setCreatedUser(user);
		candidateCertificationsHistory.setCreatedBy(user.getUserCode());
		candidateCertificationsHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateCertificationsHistory(candidateCertificationsHistory);
	}
	
	
	public void storeCandidateDocumentsHistory(CandidateDocuments candidateDocuments, User user) throws Exception
	{
		CandidateDocumentsHistory candidateDocumentsHistory = new CandidateDocumentsHistory();
		candidateDocumentsHistory.setCandidateDocuments(candidateDocuments);
		candidateDocumentsHistory.setIsRequestedDocRequired(candidateDocuments.getIsRequestedDocRequired());
		candidateDocumentsHistory.setDocUploadStatus(candidateDocuments.getDocUploadStatus());
		candidateDocumentsHistory.setRequestedDocStatus(candidateDocuments.getRequestedDocStatus());
		candidateDocumentsHistory.setRequestedDocRejectedReason(candidateDocuments.getRequestedDocRejectedReason());
		candidateDocumentsHistory.setDocumentType(candidateDocuments.getDocumentType());
		candidateDocumentsHistory.setDocumentOtherType(candidateDocuments.getDocumentOtherType());
		candidateDocumentsHistory.setFileContentType(candidateDocuments.getFileContentType());
		candidateDocumentsHistory.setFileName(candidateDocuments.getFileName());
		candidateDocumentsHistory.setDescription(candidateDocuments.getDescription());
		candidateDocumentsHistory.setDocumentUUID(candidateDocuments.getDocumentUUID());
		candidateDocumentsHistory.setIsConfidential(candidateDocuments.getIsConfidential());
		candidateDocumentsHistory.setStatus(candidateDocuments.getStatus());
		candidateDocumentsHistory.setCreatedUser(user);
		candidateDocumentsHistory.setCreatedBy(user.getUserCode());
		candidateDocumentsHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateDocumentsHistory(candidateDocumentsHistory);
	}
	
	
	public void storeCandidateReferencesHistory(CandidateReferences candidateReferences, User user) throws Exception
	{
		CandidateReferencesHistory candidateReferencesHistory = new CandidateReferencesHistory();
		candidateReferencesHistory.setCandidateReferences(candidateReferences);
		candidateReferencesHistory.setName(candidateReferences.getName());
		candidateReferencesHistory.setEmail(candidateReferences.getEmail());
		candidateReferencesHistory.setMobilePhone(candidateReferences.getMobilePhone());
		candidateReferencesHistory.setRelation(candidateReferences.getRelation());
		candidateReferencesHistory.setDesignation(candidateReferences.getDesignation());
		candidateReferencesHistory.setComment(candidateReferences.getComment());
		candidateReferencesHistory.setStatus(candidateReferences.getStatus());
		candidateReferencesHistory.setCreatedUser(user);
		candidateReferencesHistory.setCreatedBy(user.getUserCode());
		candidateReferencesHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateReferencesHistory(candidateReferencesHistory);
	}
	
	
	public void storeCandidateGlobalActivity(User user, OrgLocations orgLocations, CandidateGlobal candidate, String actionOn, String actionDesc, String actionSummary, String actionIcon) throws Exception
	{
		CandidateTimelineActivity candidateTimelineActivity = new CandidateTimelineActivity();
		
		candidateTimelineActivity.setCandidateGlobalId(candidate);
		candidateTimelineActivity.setOrgLocationId(orgLocations);
		if(user !=null){
			candidateTimelineActivity.setActionBy(user.getFullName());
			candidateTimelineActivity.setCreatedUser(user);
			candidateTimelineActivity.setCreatedBy(user.getUserCode());
			candidateTimelineActivity.setUpdatedBy(user.getUserCode());
		}
		else{
			candidateTimelineActivity.setActionBy(null);
			candidateTimelineActivity.setCreatedUser(null);
			candidateTimelineActivity.setCreatedBy(null);
			candidateTimelineActivity.setUpdatedBy(null);
		}
		candidateTimelineActivity.setActionOn(actionOn);
		candidateTimelineActivity.setActionDesc(actionDesc);
		candidateTimelineActivity.setActionSummary(actionSummary);
		candidateTimelineActivity.setActionIcon(actionIcon);
		candidateTimelineActivity.setStatus(Constants.RECORD_ACTIVE);
		candidateTimelineActivity.setCreatedDate(commonUtil.currentDate());
		candidateTimelineActivity.setUpdatedDate(commonUtil.currentDate());
		
		candidateTimelineActivityDao.storeCandidateTimelineActivity(candidateTimelineActivity);
	}
	
	public void storeCandidateGlobalHistory(CandidateGlobal candidate, User user) throws Exception
	{
		CandidateHistory candidateHistory = new CandidateHistory();
		candidateHistory.setCandidateGlobalId(candidate);
		candidateHistory.setCandidateType(candidate.getCandidateType());
		candidateHistory.setCandidateUUID(candidate.getCandidateUUID());
		candidateHistory.setIsFavourite(candidate.getIsFavourite());
		candidateHistory.setIsFavouriteAssociate(candidate.getIsFavouriteAssociate());
		candidateHistory.setFirstName(candidate.getFirstName());
		candidateHistory.setLastName(candidate.getLastName());
		candidateHistory.setCountry(candidate.getCountry());
		candidateHistory.setState(candidate.getState());
		candidateHistory.setCity(candidate.getCity());
		candidateHistory.setLocation(candidate.getLocation());
		candidateHistory.setZipCode(candidate.getZipCode());
		candidateHistory.setLatitude(candidate.getLatitude());
		candidateHistory.setLongitude(candidate.getLongitude());
		candidateHistory.setMobilePhone(candidate.getMobilePhone());
		candidateHistory.setAlternateMobilePhone(candidate.getAlternateMobilePhone());
		candidateHistory.setEmail(candidate.getEmail());
		candidateHistory.setCurrentRate(candidate.getCurrentRate());
		candidateHistory.setCurrentRateType(candidate.getCurrentRateType());
		candidateHistory.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
		candidateHistory.setDesignation(candidate.getDesignation());
		candidateHistory.setDesignationOtherType(candidate.getDesignationOtherType());
		candidateHistory.setExpectedRate(candidate.getExpectedRate());
		candidateHistory.setRateType(candidate.getRateType());
		candidateHistory.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
		candidateHistory.setSummary(candidate.getSummary());
		candidateHistory.setDesignationType(candidate.getDesignationType());
		candidateHistory.setExperienceYears(candidate.getExperienceYears());
		candidateHistory.setExperienceMonths(candidate.getExperienceMonths());
		candidateHistory.setNoticePeriod(candidate.getNoticePeriod());
		candidateHistory.setAddress(candidate.getAddress());
		candidateHistory.setSkillsName(candidate.getSkillsName());
		candidateHistory.setVisaType(candidate.getVisaType());
		candidateHistory.setPrimarySkill(candidate.getPrimarySkill());
		candidateHistory.setIsAssociate(candidate.getIsAssociate());
		candidateHistory.setStatus(candidate.getStatus());
		candidateHistory.setCreatedUser(user);
		if(user != null){
		candidateHistory.setCreatedBy(user.getUserCode());
		}
		else{
			candidateHistory.setCreatedBy(null);	
		}
		candidateHistory.setCreatedDate(commonUtil.currentDate());
		candidateHistoryDao.storeCandidateHistory(candidateHistory);
		storeCandidateEducationListHistory(candidate.getCandidateEducation(), user);
		storeCandidateEmployersListHistory(candidate.getCandidateEmployers(), user);
		storeCandidateResumesListHistory(candidate.getCandidateResumes(), user);
	}
}
