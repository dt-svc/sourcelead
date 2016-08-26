package com.bis.sourcelead.service;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.CandidateEmployers;

@Service
public interface CandidateEmployersService {

	public CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception; ; 
}
