package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);

	Immigration savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations) throws Exception;

	ImmigrationDTO getImmigrationViewList(String immigrationId, OrgLocations orgLocations);
	
	String saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDTO, OrgLocations orgLocations, User user) throws Exception;
<<<<<<< HEAD
	CandidatePassportVisa savePassportDetails(ImmigrationPassportVisaDTO immigrationPassportVisaDTO, User user, OrgLocations orgLocations)throws Exception;
=======
>>>>>>> 0444616901f1c75cfde9ace818727693459d4d0e
}
