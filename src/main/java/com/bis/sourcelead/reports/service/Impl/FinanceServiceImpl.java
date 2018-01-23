package com.bis.sourcelead.reports.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.reports.dao.FinanceDao;
import com.bis.sourcelead.reports.service.FinanceService;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.MissingInvoiceDTO;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class FinanceServiceImpl implements FinanceService{

	
	@Autowired
	FinanceDao financeDao;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Override
	public APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {

		APAgingDTO res = financeDao.getApAgingListByLocation(orgLocation, apAgingDTO);

		return res;
	}

	@Override
	public ResponseList<APAgingDTO> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO) {
		ResponseList<Invoice> res = financeDao.getApAgingBillListByLocation(orgLocation, aPAgingDTO);
		ResponseList<APAgingDTO> responseList = new ResponseList<>();

		List<Invoice> invoiceList = res.getData();
		List<APAgingDTO> apagingDTOList = new ArrayList<>();
		try {
			for (Invoice invoice : invoiceList) {
				APAgingDTO apagingDTO = new APAgingDTO();
				OrgRelations orgRelations = orgRelationsService.getOrgRelationById(invoice.getOrgRelationID().getId());
				if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					apagingDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
				}
				if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					apagingDTO.setOrgName(orgRelations.getLocationId().getOrgLocationName());
				}
				apagingDTO.setDueDate(
						commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getDueDate()));
				apagingDTO.setBillDate(
						commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getInvoiceDate()));
				apagingDTO.setTotalAmount(Double.parseDouble(invoice.getTotal()));
				apagingDTO.setDueAmount(Double.parseDouble(invoice.getTotal()));
				apagingDTO.setReferId(invoice.getInvoiceId());
				apagingDTOList.add(apagingDTO);
			}
			responseList.setData(apagingDTOList);
			responseList.setRecordsFiltered(res.getRecordsFiltered());
			responseList.setRecordsTotal(res.getRecordsTotal());
		} catch (Exception e) {
		}

		return responseList;
	}

	@Override
	public ResponseList<MissingInvoiceDTO> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto,
			OrgLocations orgLocation) {
		ResponseList<SubmissionApproverHierarchy> sahList = financeDao.getMissingInvoiceData(missingInvoiceDto,orgLocation);
		ResponseList<MissingInvoiceDTO> response = new ResponseList<>();
		List<MissingInvoiceDTO> missingInvoiceDTOList = new ArrayList<>();
		try {
			if (sahList != null) {
				int count = 0;
				if(!"invoice".equalsIgnoreCase(missingInvoiceDto.getInvoiceType())) {
					for (SubmissionApproverHierarchy sah : sahList.getData()) {
						MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
						if(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId() != null) {
							Candidate candidate  = candidateService.getCandidateByParentCandidateId(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getId());
							if(candidate.getOrgRelationId() != null) {
								OrgRelations orgRelations = orgRelationsService.getOrgRelationById(candidate.getOrgRelationId().getId());
								
								if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
									missingInvoice.setOrgName(orgRelations.getLocationId().getOrgLocationName());
								}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
									missingInvoice.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
								}
								missingInvoice.setProjectName(sah.getSubmissionId().getProjects().getProjectName());
								
								if (sah.getBillDate() != null) {
									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getBillDate()));
								}else {
									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getUpdatedDate()));
								}
								
								if (sah.getBillId() != null) {
									missingInvoice.setBillReferId(String.valueOf(sah.getBillId().getId()));
									missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
								}else {
									double payRate = sah.getSubmissionId().getProjectMembers().getPayRate();
								}
								if(sah.getFrequencyStartDate() != null && sah.getFrequencyEndDate() !=null ) {
									StringBuilder sb = new StringBuilder();
									String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyStartDate());
									String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyEndDate());
									sb.append(startDate).append(" / ").append(endDate);
									missingInvoice.setFrequencyDate(sb.toString());
								}
								
								missingInvoiceDTOList.add(missingInvoice);
								count++;
							}
							
							
						}
						
					}
				}else {
					for (SubmissionApproverHierarchy sah : sahList.getData()) {
						MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
						if (sah.getHeirarchyId().getProjectClientsId().getOrgRelationId() != null) {
							OrgRelations orgRelations = orgRelationsService.getOrgRelationById(sah.getHeirarchyId().getProjectClientsId().getOrgRelationId().getId());
							
							if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
								missingInvoice.setOrgName(orgRelations.getLocationId().getOrgLocationName());
							}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
								missingInvoice.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
							}
							
							missingInvoice.setProjectName(sah.getSubmissionId().getProjects().getProjectName());
							if (sah.getInvoiceDate() != null) {
								missingInvoice.setInvoiceDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceDate()));
							}
							if (sah.getBillDate() != null) {
								missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getBillDate()));
							}
							if (sah.getInvoiceId() != null) {
								missingInvoice.setInvoiceReferId(String.valueOf(sah.getInvoiceId().getId()));
								missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
								missingInvoice.setInvoiceDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
							}
							if (sah.getBillId() != null) {
								missingInvoice.setBillReferId(String.valueOf(sah.getBillId().getId()));
								missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
								missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
							}
							if(sah.getFrequencyStartDate() != null && sah.getFrequencyEndDate() !=null ) {
								StringBuilder sb = new StringBuilder();
								String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyStartDate());
								String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyEndDate());
								sb.append(startDate).append(" / ").append(endDate);
								missingInvoice.setFrequencyDate(sb.toString());
							}
							missingInvoiceDTOList.add(missingInvoice);
							count++;
						}
					}

				}
				
				
				response.setData(missingInvoiceDTOList);
				response.setRecordsFiltered(count);
				response.setRecordsTotal(count);
				response.setStatusCode("200");
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ResponseList<MissingInvoiceDTO> getMissingInvoiceGraphData(MissingInvoiceDTO missingInvoiceDto,OrgLocations orgLocation) {
		ResponseList<SubmissionApproverHierarchy> sahList = financeDao.getMissingInvoiceData(missingInvoiceDto,orgLocation);
		System.out.println(sahList.getData().size());
		return null;
	}

}
