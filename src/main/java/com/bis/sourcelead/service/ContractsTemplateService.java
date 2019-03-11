package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.FullTimeContractsDTO;
import com.bis.sourcelead.dto.ClientContractsDTO;
import com.bis.sourcelead.dto.ContractTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.VendorContractsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractEditHistory;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractsTemplateService {

	ContractsTemplate saveContractTemplate(ContractTemplateDTO contractTemplateDTO, JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception;

	ContractsTemplate getContractTemplateById(Long contractTemplateId) throws Exception;

	ResponseList<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation, ContractTemplateDTO contractTemplateDTO)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	Contracts saveNewContract(ContractsDTO contractsDTO, OrgLocations orgLocation, String saveType, User user, Org org, String contractType)throws Exception;

	ResponseList<VendorContractsDTO> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus,User user,String userRoleName, String searchValue)throws Exception;
	
	ResponseList<ClientContractsDTO> getAllClientContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus,User user,String userRoleName, String searchValue)throws Exception;
	
	ResponseList<FullTimeContractsDTO> getAllApplicantContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus,User user,String userRoleName, String searchValue)throws Exception;
	
	Contracts getAllContractsCountByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName)throws Exception;

	Contracts getContractById(Long contractId);
	
	Contracts getContractByContractUUID(String contractUUID);

	Contracts saveNewContract(Contracts updateContracts) throws Exception;

	Contracts getChildContractById(Long contractId);

	Contracts approveContracts(long parseLong, String status, OrgLocations orgLocation, User user, Org org, String rejectReason)throws Exception;
	

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	ContractsTemplate findByContractTemplateName(String templateName,String contractType, Long orgLocationId, String templateUUID) throws Exception;

	List<Contracts> getListOfContractsEnvId();
	
	Contracts findContractTitleByOrgLocationAndContractType(String contractTitle,String contractType,Long orgLocationId, Long contractId);

	Contracts saveExistigContract(ContractsDTO contractsDTO, OrgLocations orgLocation, User user, Org org) throws Exception;

	ContractsTemplate getContractTemplateByTemplateUUID(String tempUUID);

	List<Contracts> getAllContractsBasedOnTimeFrequency(Date startDateUTC, Date endDateUTC) throws Exception;

	void documentsReceivedMailToOwner(Contracts contractId, User user, Org org) throws Exception;

	List<Contracts> getAllClientOrVendorContractsBasedOnLocationId(OrgLocations orgLocations, String responderType, User user, String userRoleName, String contractStatus, Long orgRelationId)throws Exception;

	ContractsHistory getAllContractsHistoryByContractId(Long contractId);


	Contracts getContractByReqSubmissionSowTypeAndOrgLocId(Long reqSub, OrgLocations loginOrgLocation, String sowType);

	void sendFinalisedMailToReceiver(Contracts contracts, OrgLocations orgLocation, User user) throws Exception;

	Boolean getVendorMsaCreatedOrNot(Long vendorLocationId, Long loginLocatonId);

	void sendMSAEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation, Date previousEndDate, User user) throws Exception;

	void sendSOWEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation, User user) throws Exception;

	List<Contracts> getContractByLocationId(Long orgLocationId);

	List<User> getSigningAuthoritiesForContract(Long locationId, Long tabId) throws Exception;

	boolean sendContractFromOwnertoReceiver(ContractsDTO contractsDTO, User loginUser, OrgLocations loginOrgLocation, String roleName) throws Exception;

	List<Contracts> validateAlreadyGeneratedContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation) throws Exception;

	boolean reviseContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation, User loginUser) throws Exception;
	
	List<Contracts> getContractsBylocationIdForReminder(Long locationId, String status, Date date);

	ContractEditHistory saveContractEditHistory(ContractEditHistory contractEditHistory);
	
	Contracts getMaxNoOfContract(Long locationId);

	Contracts updateContractSigner(ContractsDTO contractsDTO, OrgLocations loginOrgLocation, User loginUser) throws NumberFormatException, Exception;
	
	Integer deleteContractOwnerHierarchyListByContractId(Long Id);

	Contracts getAllVendorClientAndApplicantCountByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName) throws Exception;
	
	List<Contracts> getAllContractsBasedOnConfigureDate(Date updatedDate) throws Exception;
}
