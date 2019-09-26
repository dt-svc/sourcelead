package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateHistoryDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateHistoryService;

@Service
public class CandidateHistoryServiceImpl implements CandidateHistoryService{
	
	@Autowired
	CandidateHistoryDao candidateHistoryDao;

	@Override
	public HashSet<CandidateHistory> getCandidateHistoryDetails(Candidate candidate, User user) throws Exception {
		
		HashSet<CandidateHistory> candidatehistoryList = new HashSet<CandidateHistory>(); 
		
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
		candidateHistory.setCreatedBy(user.getUserCode());
		candidatehistoryList.add(candidateHistory);
		return candidatehistoryList;
	}

	@Override
	public List<CandidateHistory> getCandidateHistoryListByGlobalCandidateId(Long candidateGlobalId) throws Exception {
		return candidateHistoryDao.getCandidateHistoryListByGlobalCandidateId(candidateGlobalId);
	}

	@Override
	public CandidateHistory storeCandidateHistory(CandidateHistory candidateHistory) throws Exception {
		return candidateHistoryDao.storeCandidateHistory(candidateHistory);
	}

}
