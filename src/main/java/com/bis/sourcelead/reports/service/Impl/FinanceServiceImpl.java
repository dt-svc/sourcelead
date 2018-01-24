package com.bis.sourcelead.reports.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.bis.sourcelead.reportsDTO.AparagingDate;
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
							}else {
								missingInvoice.setInvoiceDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getUpdatedDate()));
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
				
				
				
				
//				Map<Integer,List<Double>> map = new HashMap<>();
//				 for(AparagingDate aparagingdate : apagingdto.getAbove365Dates()) {
//					 Calendar cal = Calendar.getInstance(); 
//					 cal.setTime(aparagingdate.getDate());
//					 int year = cal.get(Calendar.YEAR);
//		    	   if(map.containsKey(year)) {
//		    	   List<Double> amountList = map.get(year);
//		    	   amountList.add(aparagingdate.getAmount());
//		    	   map.put(year, amountList);
//		    	   }else {
//		    	   List<Double> amountList = new ArrayList<>();
//		    	   amountList.add(aparagingdate.getAmount());
//		    	   map.put(year, amountList);
//		    	   }
//		       }
//				 for (Map.Entry<Integer,List<Double>> entry : map.entrySet()) {
//					 AparagingDate apar = new AparagingDate();
//					 apar.setYear(entry.getKey());
//					 double amount = entry.getValue().stream().mapToDouble(am->am.doubleValue()).sum();
//					 apar.setAmount(amount);
//					 aparagingDate.add(apar);
//					}
				
				
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
		ResponseList<MissingInvoiceDTO> response = new ResponseList<>();
		List<MissingInvoiceDTO> missingInvoiceDTOList = new ArrayList<>();
		try {
			if (sahList != null) {
				int count = 0;
				Map<String,List<MissingInvoiceDTO>> map = new HashMap<>();
				if(!"invoice".equalsIgnoreCase(missingInvoiceDto.getInvoiceType())) {
					for (SubmissionApproverHierarchy sah : sahList.getData()) {
						MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
						if(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId() != null) {
							Candidate candidate  = candidateService.getCandidateByParentCandidateId(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getId());
							if(candidate.getOrgRelationId() != null) {
								OrgRelations orgRelations = orgRelationsService.getOrgRelationById(candidate.getOrgRelationId().getId());
								
//								if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
//									missingInvoice.setOrgName(orgRelations.getLocationId().getOrgLocationName());
//								}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
//									missingInvoice.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
//								}
//								missingInvoice.setProjectName(sah.getSubmissionId().getProjects().getProjectName());
								
								if (sah.getBillDate() != null) {
									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getBillDate()));
								}else {
									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getUpdatedDate()));
								}
								
//								if (sah.getBillId() != null) {
//									missingInvoice.setBillReferId(String.valueOf(sah.getBillId().getId()));
//									missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
//									missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
//								}else {
//									double payRate = sah.getSubmissionId().getProjectMembers().getPayRate();
//								}
//								if(sah.getFrequencyStartDate() != null && sah.getFrequencyEndDate() !=null ) {
//									StringBuilder sb = new StringBuilder();
//									String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyStartDate());
//									String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyEndDate());
//									sb.append(startDate).append(" / ").append(endDate);
//									missingInvoice.setFrequencyDate(sb.toString());
//								}
								
								missingInvoiceDTOList.add(missingInvoice);
								count++;
							}
							
							
						}
						
					}
				}else {
					 int amount = 100;
					for (SubmissionApproverHierarchy sah : sahList.getData()) {
						MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
						if (sah.getHeirarchyId().getProjectClientsId().getOrgRelationId() != null) {
//							OrgRelations orgRelations = orgRelationsService.getOrgRelationById(sah.getHeirarchyId().getProjectClientsId().getOrgRelationId().getId());
//							
//							if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
//								missingInvoice.setOrgName(orgRelations.getLocationId().getOrgLocationName());
//							}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
//								missingInvoice.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
//							}
							
//							missingInvoice.setProjectName(sah.getSubmissionId().getProjects().getProjectName());
							String date = "";
							if (sah.getInvoiceDate() != null) {
								SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
								 date = month_date.format(sah.getInvoiceDate());
								 System.out.println("*************************"+date);
//								date = commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", sah.getInvoiceDate());
								missingInvoice.setInvoiceAmount(String.valueOf(amount+50));
							}else {
								SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
								 date = month_date.format(sah.getUpdatedDate());
								 System.out.println("*************************"+date);
//								date = commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", sah.getUpdatedDate());	
								missingInvoice.setInvoiceAmount(String.valueOf(amount+50));
							}
							
							
							
//							 for(MissingInvoiceDTO missingInvoiceDTO : apagingdto.getAbove365Dates()) {
//								 Calendar cal = Calendar.getInstance(); 
//								 cal.setTime(aparagingdate.getDate());
//								 int year = cal.get(Calendar.YEAR);
					    	   if(map.containsKey(date)) {
					    	   List<MissingInvoiceDTO> amountList = map.get(date);
					    	   amountList.add(missingInvoice);
					    	   map.put(date, amountList);
					    	   }else {
					    	   List<MissingInvoiceDTO> amountList = new ArrayList<>();
					    	   amountList.add(missingInvoice);
					    	   map.put(date, amountList);
					    	   }
							
//							if (sah.getBillDate() != null) {
//								missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getBillDate()));
//							}
//							if (sah.getInvoiceId() != null) {
//								missingInvoice.setInvoiceReferId(String.valueOf(sah.getInvoiceId().getId()));
//								missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
//								missingInvoice.setInvoiceDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
//							}
//							if (sah.getBillId() != null) {
//								missingInvoice.setBillReferId(String.valueOf(sah.getBillId().getId()));
//								missingInvoice.setTotalAmount(sah.getInvoiceId().getTotal());
//								missingInvoice.setBillDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getInvoiceId().getInvoiceDate()));
//							}
//							if(sah.getFrequencyStartDate() != null && sah.getFrequencyEndDate() !=null ) {
//								StringBuilder sb = new StringBuilder();
//								String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyStartDate());
//								String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyEndDate());
//								sb.append(startDate).append(" / ").append(endDate);
//								missingInvoice.setFrequencyDate(sb.toString());
//							}
							count++;
						}
					}
					
//					 for (Map.Entry<String,List<MissingInvoiceDTO>> entry : map.entrySet()) {
//						 System.out.println(entry.getKey() + "/" + entry.getValue().size());
//						}

				}
				
				 for (Map.Entry<String,List<MissingInvoiceDTO>> entry : map.entrySet()) {
					 MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
					 missingInvoice.setInvoiceDate(entry.getKey());
					 double amount = entry.getValue().stream().mapToDouble(am->Double.parseDouble(am.getInvoiceAmount())).sum();
					 System.out.println("////////////////////////////////////"+amount);
					 missingInvoice.setInvoiceAmount(String.valueOf(amount));
					 missingInvoiceDTOList.add(missingInvoice);
					}
				 
				 Collections.sort(missingInvoiceDTOList, new Comparator<Object>() {  
			            @Override  
			            public int compare(Object obj1, Object obj2) {  
			            	MissingInvoiceDTO candidate1 = (MissingInvoiceDTO)obj1;  
			            	MissingInvoiceDTO candidate2 = (MissingInvoiceDTO)obj2; 
			            	return candidate2.getInvoiceDate().compareToIgnoreCase(candidate1.getInvoiceDate());  
			            }  
			        });
				 
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

}
