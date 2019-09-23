package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.OrgDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.PaymentAccount;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
public interface OrgDao {

	public Org addOrganization(Org org) throws Exception;

	public Org updateOrg(Org o) throws Exception;

	public Org findOrgByID(Long id) throws Exception;

	public Boolean deleteOrg(Long id) throws Exception;

	public List<Org> getOrgs() throws Exception;
	
	public List<Org> getOpenJobsOrgs() throws Exception;
	
	String websiteValidation(String website,String sourceleadBLockedwebsites);
	
	public Org getOrgByOrgCode(String orgCode) throws Exception;
	
	/**
	 * Handles whether the given client is duplicate or not
	 * @param orgRelationId 
	 * @param clienName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isClientNameValid(String clientName ,Long orgLocationId,String responderType) throws Exception;
	
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName,Integer offSet) throws Exception;
	
	public ResponseList<Org> getDynamicEnrolledOrgs(Long orgId,OrgDTO orgDTO) throws Exception;
	
	public boolean isOrgNameExisted(String orgName) throws Exception;
	
	 List<Org> isSpocExistedTheOwner(String clientName,Long orgLocationId,String responderType) throws Exception ;

	public Org getOrgByOrgName(String orgName);
	
	public Boolean isLoginUserIsSuperAdmin(Long userId,Long orgId) throws Exception;

	public List<UserDTO> getuserListForOrgChart(Long orgLocationId) throws Exception;
	
	public List<Org> getAllOpenJobsOrgsStartsWithAlphabet(String alphabet);

	public Org getOrgByCareerPageKey(String careerPageKey) throws Exception;

    public String generateCareerPageKey(Long id, String orgName) throws Exception;

    public int changeCareerPageKeyStatus(Long id) throws Exception;	

    public long savePaymentAccount(PaymentAccountDTO account);
    
    public List<PaymentAccountDTO> getPaymentAccountList(long orgId, long orgLocactionId,boolean onlyEnabledAccounts);

    public long changePaymentAccountToAccess(long id, long orgId);

    public PaymentAccountDTO getPaymentAccount(long id, Long orgId);

    public int changeDefaultPaymentAccount(long id, long orgLocationId, boolean defaultAccount);
    
    public int changeAssociateDefaultPaymentAccount(long id,long candidateId, boolean defaultAccount);

    public int changePaymentAccountStatus(long id, long orgLocationId);

    public List<PaymentAccountDTO> getPaymentAccountListByUserId(long userId,boolean onlyEnabledAccounts);
    
    public List<PaymentAccountDTO> getPaymentAccountListByCandidateId(long candidateId,boolean onlyEnabledAccounts);
    
    public PaymentAccount getCandidateAcitivatePaymentAccount(long candidateId);

}
