package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateHotListDao;
import com.bis.sourcelead.entity.CandidateHotList;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateHotListService;

@Service
public class CandidateHotListServiceImpl implements CandidateHotListService{
	
	@Autowired
	CandidateHotListDao candidateHotListDao;

	@Override
	public CandidateHotList saveCandidateHotList(CandidateHotList candidateHotList) throws Exception {
		return candidateHotListDao.saveCandidateHotList(candidateHotList);
	}

	@Override
	public Boolean isCandidateIsFavourite(Long candidateId, Long orgLocationId) throws Exception {
		return candidateHotListDao.isCandidateIsFavourite(candidateId, orgLocationId);
	}

	@Override
	public CandidateHotList getCandidateHotListByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId) {
		return candidateHotListDao.getCandidateHotListByCandidateIdAndOrgLocationId(candidateId, orgLocationId);
	}

	@Override
	public List<CandidateHotList> getHotCandidateListForTeamByTeamId(Long teamId, Long orgLocationsId)
			throws Exception {
		return candidateHotListDao.getHotCandidateListForTeamByTeamId(teamId, orgLocationsId);
	}

}
