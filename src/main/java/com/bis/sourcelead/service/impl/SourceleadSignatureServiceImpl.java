package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadSignatureDao;
import com.bis.sourcelead.entity.SourceleadSignature;
import com.bis.sourcelead.service.SourceleadSignatureService;

@Service
public class SourceleadSignatureServiceImpl implements SourceleadSignatureService{
	
	@Autowired
	SourceleadSignatureDao sourceleadSignatureDao;

	@Override
	public List<SourceleadSignature> getSourceLeadSignature() throws Exception {
		return sourceleadSignatureDao.getSourceLeadSignature();
	}

}
