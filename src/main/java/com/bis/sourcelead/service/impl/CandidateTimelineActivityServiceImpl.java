package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.service.CandidateTimelineActivityService;

/**
 * 
 * @author Venkat Kesav
 * @date 17th July 2017
 *
 */
@Service
public class CandidateTimelineActivityServiceImpl implements CandidateTimelineActivityService {
	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityServiceImpl.class);
	@Autowired
	CandidateTimelineActivityDao candidateTimelineActivityDao;
	
	@Override
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception
	{
		return candidateTimelineActivityDao.getCandidateTimelineActivities(candidateId, orgLocationId);
	}
}
