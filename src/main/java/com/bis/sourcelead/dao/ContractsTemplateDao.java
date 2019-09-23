package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ContractTemplateDTO;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ContractEditHistory;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractsTemplateDao {

	ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception;

	//ContractsTemplate saveContractTemplate(Long contractTemplateId);

	ResponseList<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation, ContractTemplateDTO contractTemplateDTO)throws Exception;

	List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType)throws Exception;

	ContractsTemplate getContractTemplateById(long parseLong)throws Exception;

	Contracts saveNewContract(Contracts contracts)throws Exception;

	List<Contracts> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, User user, ContractsDTO contractsDTO)throws Exception;

	Integer getAllContractsCountByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName)throws Exception;
	
	Contracts getContractById(Long contractId);
	
	Contracts getContractByContractUUID(String contractUUID);

	Contracts getChildContractById(Long contractId);
	
	ContractsTemplate findByContractTemplateName(String templateName,String contractType, Long orgLocationId, String templateUUID) throws Exception;

	List<Contracts> getListOfContractsEnvId();

	Contracts findContractTitleByOrgLocationAndContractType(String contractTitle,String contractType,Long orgLocationId, Long contractId);

	ContractsTemplate getContractTemplateByTemplateUUID(String tempUUID);
	
	ContractsHistory saveContractsHistory(ContractsHistory contractsHistory) throws Exception ;

	List<Contracts> getAllContractsBasedOnTimeFrequency(Date startDateUTC, Date endDateUTC);

	List<Contracts> getAllClientOrVendorContractsBasedOnLocationId(OrgLocations orgLocations, String responderType, User user, String userRoleName, String contractStatus, Long orgRelationId) throws Exception;

	ContractsHistory getAllContractsHistoryByContractId(Long contractId);

	ContractsHistory getContractsHistoryByContractId(Long contractId);

	Contracts getContractByReqSubmissionSowTypeAndOrgLocId(Long reqSub, OrgLocations loginOrgLocation, String contractType);

	Boolean getVendorMsaCreatedOrNot(Long vendorLocationId, Long loginLocatonId);

	List<Contracts> getContractByLocationId(Long orgLocationId);

	List<User> getSigningAuthoritiesForContract(Long orgLocationsId, Long tabId);

	List<Contracts> validateAlreadyGeneratedContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation) throws Exception;

	ContractEditHistory saveContractEditHistory(ContractEditHistory contractEditHistory);

	Contracts getMaxNoOfContract(Long locationId);

	List<Contracts> getContractsBylocationIdForReminder(Long locationId, String status, Date date);

	Integer deleteContractOwnerHierarchyListByContractId(Long id);
	
	List<Contracts> getAllContractsBasedOnConfigureDate(Date updatedDate) throws Exception;
}
