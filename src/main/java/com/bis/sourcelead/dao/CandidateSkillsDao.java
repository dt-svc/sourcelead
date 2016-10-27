package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateSkills;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateSkillsDao {
	
	/**
	 * @param candidateSkills
	 * @return CandidateSkills
	 * @throws Exception
	 */
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception;
	
	public CandidateSkills getCandidateSkillBySkillId(Long candidateSkillId) throws Exception;
	
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception;

	public List<CandidateSkills> getSkillByCandidateId(Long candidateId) throws Exception;

}
