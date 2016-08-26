package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.service.CandidateCertificationsService;

/**
 * @author shivayogi
 * @Date 25-August-2016
 */
@Service
public class CandidateCertificationsServiceImpl implements CandidateCertificationsService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsServiceImpl.class);
	@Autowired
	private CandidateCertificationsDao candidateCertificationsDao;

	@Override
	public CandidateCertifications storeCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception {
		return null;
	}

	@Override
	public CandidateCertifications updateCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception {
		return null;
	}

	@Override
	public Boolean deleteCandidateCertifications(Long id) throws Exception {
		return null;
	}

	@Override
	public List<CandidateCertifications> getAllCandidateCertifications() throws Exception {
		return null;
	}

}
