package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.CandidateHotList;

public interface CandidateHotListService {
	
	public CandidateHotList saveCandidateHotList(CandidateHotList candidateHotList) throws Exception;
	
	public Boolean isCandidateIsFavourite(Long candidateId,Long orgLocationId) throws Exception;
	
	public CandidateHotList getCandidateHotListByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId);

}
