package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dto.CompanyRegistrationDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrgDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.FileUploadUtil;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	OrgDao orgDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;

	@Override
	public Org addOrganization(Org org) throws Exception {
		return orgDao.addOrganization(org);

	}

	@Override
	public Org updateOrg(Org org) throws Exception {
		return orgDao.updateOrg(org);
	}

	@Override
	public Org findOrgByID(Long id) throws Exception {
		return orgDao.findOrgByID(id);
	}

	@Override
	public Boolean deleteOrg(Long id) throws Exception {
		return orgDao.deleteOrg(id);
	}

	@Override
	public List<Org> getOrgs() throws Exception {
		return orgDao.getOrgs();
	}
	
	@Override
	public List<Org> getOpenJobsOrgs() throws Exception {
		return orgDao.getOrgs();
	}

	@Override
	public String websiteValidation(String website,String sourceleadBLockedwebsites) {
		return orgDao.websiteValidation(website,sourceleadBLockedwebsites);
	}

	@Override
	public Org saveOrgSettings(CompanyRegistrationDTO companyRegistrationDTO, Org org) throws Exception {
		
		
        if(companyRegistrationDTO.getFileInfo() != null){
        	FileInfo fileInfo = companyRegistrationDTO.getFileInfo().get(0);
    	  try {
    	   		org.setFileContentType(fileInfo.getContentType());
    	   		org.setFileName(fileInfo.getFileName());
    	   		org.setFileUrl(fileInfo.getFileURL());
    	   		FileUploadUtil.moveFile(companyRegistrationDTO.getStaticPath(), fileInfo, "profile");
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
        orgDao.updateOrg(org);
        
        return org;
	}

	@Override
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		return orgDao.getOrgByOrgCode(orgCode);
	}
	
	@Override
	public Org getOrgByCareerPageKey(String careerPageKey) throws Exception {
		return orgDao.getOrgByCareerPageKey(careerPageKey);
	}

	@Override
	public boolean isClientNameValid(String clientName ,Long orgLocationId,String responderType) throws Exception {
		return orgDao.isClientNameValid(clientName,orgLocationId,responderType);
	}

	@Override
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName,Integer offSet) throws Exception {
		return orgDao.getEnrolledOrgs(orgId, userState, searchByName,offSet);
	}

	@Override
	public boolean isOrgNameExisted(String orgName) throws Exception {
		return orgDao.isOrgNameExisted(orgName);
	}

	@Override
	public List<Org> isSpocExistedTheOwner(String clientName, Long orgLocationId, String responderType) throws Exception {
		
		return orgDao.isSpocExistedTheOwner(clientName, orgLocationId, responderType);
	}

	@Override
	public Org getOrgByOrgName(String orgName) {
		return orgDao.getOrgByOrgName(orgName);
		
	}

	@Override
	public Boolean isLoginUserIsSuperAdmin(Long userId,Long orgId) throws Exception
	{
		return orgDao.isLoginUserIsSuperAdmin(userId,orgId);
	}
	
	@Override
	public List<UserDTO> getUserListForOrgChart(Long orgLocationId) throws Exception
	{
		return orgDao.getuserListForOrgChart(orgLocationId);
	}

	@Override
	public List<Org> getAllOpenJobsOrgsStartsWithAlphabet(String alphabet)
	{
		return orgDao.getAllOpenJobsOrgsStartsWithAlphabet(alphabet);
	}

	@Override

	public ResponseList<Org> getDynamicEnrolledOrgs(Long orgId, OrgDTO orgDTO) throws Exception {
		return orgDao.getDynamicEnrolledOrgs(orgId, orgDTO);
	}

	@Override
    public String generateCareerPageKey(Long id, String orgName) throws Exception {
        return orgDao.generateCareerPageKey(id, orgName);
    }

    @Override
    public int changeCareerPageKeyStatus(Long id) throws Exception {
        return orgDao.changeCareerPageKeyStatus(id);
    }

    @Override
    public long savePaymentAccount(PaymentAccountDTO account) {
        return orgDao.savePaymentAccount(account);
    }
    
    @Override
    public List<PaymentAccountDTO> getPaymentAccountList(long orgId, long orgLocactionId,boolean onlyEnabledAccounts) {
        return orgDao.getPaymentAccountList(orgId, orgLocactionId,onlyEnabledAccounts);
    }
	
    @Override
    public long changePaymentAccountToAccess(long id, long orgLocactionId) {
        return orgDao.changePaymentAccountToAccess(id, orgLocactionId);
    }
    
    @Override
    public int changeDefaultPaymentAccount(long id, long orgLocationId, boolean defaultAccount) {
        return orgDao.changeDefaultPaymentAccount(id, orgLocationId, defaultAccount);
    }

    @Override
    public PaymentAccountDTO getPaymentAccount(long id, Long orgId) {
        return orgDao.getPaymentAccount(id, orgId);
    }

	@Override
	public int changePaymentAccountStatus(long id, long orgLocationId) {
		return orgDao.changePaymentAccountStatus(id, orgLocationId);
		
	}

	@Override
	public List<PaymentAccountDTO> getPaymentAccountListByUserId(long userId, boolean onlyEnabledAccounts) {
		 return orgDao.getPaymentAccountListByUserId(userId, onlyEnabledAccounts);
	}

	@Override
	public List<PaymentAccountDTO> getPaymentAccountListByCandidateId(long candidateId, boolean onlyEnabledAccounts) {
		return orgDao.getPaymentAccountListByCandidateId(candidateId, onlyEnabledAccounts);
	}

	@Override
	public PaymentAccount getCandidateAcitivatePaymentAccount(long candidateId) {
		return orgDao.getCandidateAcitivatePaymentAccount(candidateId);
	}

	@Override
	public int changeAssociateDefaultPaymentAccount(long id,long candidateId, boolean defaultAccount) {
		return orgDao.changeAssociateDefaultPaymentAccount(id,candidateId, defaultAccount);
	}
}
