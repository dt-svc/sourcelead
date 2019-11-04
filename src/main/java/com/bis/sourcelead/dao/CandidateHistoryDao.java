package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.CandidateCertificationsHistory;
import com.bis.sourcelead.entity.CandidateDocumentsHistory;
import com.bis.sourcelead.entity.CandidateEducationHistory;
import com.bis.sourcelead.entity.CandidateEmployersHistory;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateReferencesHistory;
import com.bis.sourcelead.entity.CandidateResumesHistory;

public interface CandidateHistoryDao {
	public CandidateHistory storeCandidateHistory(CandidateHistory candidateHistory) throws Exception;
	
	public CandidateEducationHistory storeCandidateEducationHistory(CandidateEducationHistory candidateEducationHistory) throws Exception;
	public List<CandidateEducationHistory> storeCandidateEducationHistoryList(Set<CandidateEducationHistory> candidateEducationHistoryList) throws Exception;
	
	public CandidateEmployersHistory storeCandidateEmployersHistory(CandidateEmployersHistory candidateEmployersHistory) throws Exception;
	public List<CandidateEmployersHistory> storeCandidateEmployersHistoryList(Set<CandidateEmployersHistory> candidateEmployersHistoryList) throws Exception;
	
	public CandidateResumesHistory storeCandidateResumesHistory(CandidateResumesHistory candidateResumesHistory) throws Exception;
	public List<CandidateResumesHistory> storeCandidateResumesHistoryList(Set<CandidateResumesHistory> candidateResumesHistoryList) throws Exception;
	
	public List<CandidateHistory> getCandidateHistoryListByCandidateId(Long candidateId) throws Exception;
	
	public CandidateCertificationsHistory storeCandidateCertificationsHistory(CandidateCertificationsHistory candidateCertificationsHistory) throws Exception;
	
	public CandidateDocumentsHistory storeCandidateDocumentsHistory(CandidateDocumentsHistory candidateDocumentsHistory) throws Exception;
	
	public CandidateReferencesHistory storeCandidateReferencesHistory(CandidateReferencesHistory candidateReferencesHistory) throws Exception;
	
	List<CandidateHistory> getCandidateHistoryListByGlobalCandidateId(Long candidateGlobalId) throws Exception;
}