package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.CandidateHotList;
import com.bis.sourcelead.entity.User;

public interface CandidateHotListService {
	
	public CandidateHotList saveCandidateHotList(CandidateHotList candidateHotList) throws Exception;
	
	public Boolean isCandidateIsFavourite(Long candidateId,Long orgLocationId) throws Exception;
	
	public CandidateHotList getCandidateHotListByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId);
	
	List<CandidateHotList> getHotCandidateListForTeamByTeamId(Long teamId ,Long orgLocationsId) throws Exception;
}
