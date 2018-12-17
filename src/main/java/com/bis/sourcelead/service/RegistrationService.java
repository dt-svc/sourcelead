package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CompanyRegistrationDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserRegistrationDTO;
import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.Registration;

public interface RegistrationService {
	
	public void addRegistration(Registration registration,User user,Long orgLocationsId,CandidateDTO candidateDTO) throws Exception;
	
	public OrgLocationDTO addCompanyRegistration(Registration registration,User user,Long orgLocationsId,CandidateDTO candidateDTO) throws Exception;
	
	public void sendEmail(Registration registration,Long locationsId) throws Exception;
	
	public void sendResetPasswordEmail(User user,Long locationsId, String verificationCode) throws Exception;
	
	public void sendUpdatePasswordEmail(User user,Long locationsId) throws Exception;
	
	public String addPasswordResets(User user) throws Exception;
	
	public void resetPassword(PasswordResets passwordResets,String password) throws Exception;
	
	public void deletePasswordResets(PasswordResets passwordResets) throws Exception;
	
	public void addUserBySourcelead(User user,AddUserDTO addUserDTO,CandidateDTO candidateDTO) throws Exception;
	
	public String saveProfessional(CrowdSourcingUserRegistrationDTO csuRegistrationDTO,String contactUUID) throws Exception;
	
	public String saveIndividualRecruiter(CrowdSourcingUserRegistrationDTO csuRegistrationDTO) throws Exception;
	
	public String saveIndividualEmployer(EmployerRegistrationDTO employerRegistrationDTO) throws Exception;
	
    public void saveProfessionalFromSocialInviter(JSONObject jsonObj) throws Exception;
	
	public void saveIndividualRecruiterFromSocialInviter(JSONObject jsonObj) throws Exception;
	
	public void sendEmailToProfessional(User user) throws Exception;

	public void sendActivationEmail(User user,OrgLocations orgLocations) throws Exception;
	
	public void sendEmailToIndividualRecruiter(User user) throws Exception;

	public String sendOtpToUser(User user, String type) throws Exception;
	
    Boolean	validateOrgRequestCompanyNameByName(String companyName)throws Exception;
    

}
