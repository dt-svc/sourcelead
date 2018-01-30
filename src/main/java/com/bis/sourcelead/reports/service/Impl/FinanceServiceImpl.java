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

import org.apache.commons.lang.StringUtils;
import org.hibernate.boot.model.relational.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
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
	public ResponseList<MissingInvoiceDTO> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto,OrgLocations orgLocation, String contractType) {
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
								StringBuilder name = new StringBuilder();
								if(StringUtils.isNotBlank(candidate.getLastName())) {
									name.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
								}else {
									name.append(candidate.getFirstName());
								}
								missingInvoice.setCandidateName(name.toString());
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
								}
								if(sah.getFrequencyStartDate() != null && sah.getFrequencyEndDate() !=null ) {
									StringBuilder sb = new StringBuilder();
									String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyStartDate());
									String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), sah.getFrequencyEndDate());
									sb.append(startDate).append(" to ").append(endDate);
									missingInvoice.setFrequencyDate(sb.toString());
								}
								List<Contracts> contractList = financeDao.getRecentSowcontractListByAssociateId(candidate.getId(), orgLocation, candidate.getOrgRelationId(), contractType);
								double amount = 0;
								if(contractList != null && contractList.size() >0) {
									 amount = getAmount(sah.getBillableHours(),contractList.get(0).getBillingAmount());
								}
								missingInvoice.setBillAmount(amount);
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
							Candidate candidate  = candidateService.getCandidateByCandidateId(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getId());
							StringBuilder name = new StringBuilder();
							if(StringUtils.isNotBlank(candidate.getLastName())) {
								name.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
							}else {
								name.append(candidate.getFirstName());
							}
							
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
							
							List<Contracts> contractList = financeDao.getRecentSowcontractListByAssociateId(candidate.getId(), orgLocation, orgRelations, contractType);
							double amount = 0;
							if(contractList != null && contractList.size() >0) {
								 amount = getAmount(sah.getBillableHours(),contractList.get(0).getBillingAmount());
							}
							missingInvoice.setInvoiceAmount(amount);
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
	
	
	private double getAmount(float hours, String payRate) {
		double amount = 0;
//		if(Constants.RATE_TYPE_HOURLY.equalsIgnoreCase(pm.getPayRateType())){
			double rate = Double.parseDouble(payRate);
			String hr = String.valueOf(hours);
			String[] hh = hr.split("\\.");
			double hrs = Double.parseDouble(hh[0]);
			double minutes = Double.parseDouble(hh[1]);
			double totalHourMin = hrs * 60;
			double totMinutes = totalHourMin + minutes;
			amount = totMinutes * (rate/60);
//		}else if(Constants.RATE_TYPE_MONTHLY.equalsIgnoreCase(pm.getPayRateType())){
//			double rate = pm.getPayRate();
//			amount = rate/2;
//		}
		return amount;
	}

	@Override
	public ResponseList<MissingInvoiceDTO> getMissingInvoiceGraphData(MissingInvoiceDTO missingInvoiceDto,OrgLocations orgLocation,String contractType) {
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
								SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
								String date = "";
								if (sah.getBillDate() != null) {
									 date = month_date.format(sah.getBillDate());
								}else {
									 date = month_date.format(sah.getUpdatedDate());
								}
								List<Contracts> contractList = financeDao.getRecentSowcontractListByAssociateId(candidate.getId(), orgLocation, orgRelations, contractType);
								double amount = 0;
								if(contractList != null && contractList.size() >0) {
									 amount = getAmount(sah.getBillableHours(),contractList.get(0).getBillingAmount());
								}
								missingInvoice.setInvoiceAmount(amount);
								
								 if(map.containsKey(date)) {
							    	   List<MissingInvoiceDTO> amountList = map.get(date);
							    	   amountList.add(missingInvoice);
							    	   map.put(date, amountList);
							    	   }else {
							    	   List<MissingInvoiceDTO> amountList = new ArrayList<>();
							    	   amountList.add(missingInvoice);
							    	   map.put(date, amountList);
							    	   }
								count++;
							}
						}
						
					}
				}else {
					 int amount = 100;
					for (SubmissionApproverHierarchy sah : sahList.getData()) {
						MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
						if (sah.getHeirarchyId().getProjectClientsId().getOrgRelationId() != null) {
							OrgRelations orgRelations = orgRelationsService.getOrgRelationById(sah.getHeirarchyId().getProjectClientsId().getOrgRelationId().getId());
							Candidate candidate  = candidateService.getCandidateByCandidateId(sah.getSubmissionId().getProjectMembers().getProjectMemberCandidteId().getId());
							String date = "";
							if (sah.getInvoiceDate() != null) {
								 SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
								 date = month_date.format(sah.getInvoiceDate());
							}else {
								SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
								 date = month_date.format(sah.getUpdatedDate());
							}
							
							List<Contracts> contractList = financeDao.getRecentSowcontractListByAssociateId(candidate.getId(), orgLocation, orgRelations, contractType);
							double iamount = 0;
							if(contractList != null && contractList.size() >0) {
								iamount = getAmount(sah.getBillableHours(),contractList.get(0).getBillingAmount());
							}
							missingInvoice.setInvoiceAmount(iamount);
							
					    	   if(map.containsKey(date)) {
					    	   List<MissingInvoiceDTO> amountList = map.get(date);
					    	   amountList.add(missingInvoice);
					    	   map.put(date, amountList);
					    	   }else {
					    	   List<MissingInvoiceDTO> amountList = new ArrayList<>();
					    	   amountList.add(missingInvoice);
					    	   map.put(date, amountList);
					    	   }
							count++;
						}
					}

				}
				
				 for (Map.Entry<String,List<MissingInvoiceDTO>> entry : map.entrySet()) {
					 MissingInvoiceDTO missingInvoice = new MissingInvoiceDTO();
					 missingInvoice.setInvoiceDate(entry.getKey());
					 double amount = entry.getValue().stream().mapToDouble(am->am.getInvoiceAmount()).sum();
					 missingInvoice.setInvoiceAmount(amount);
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
				 List<MissingInvoiceDTO> list = new ArrayList<>();
				 for(MissingInvoiceDTO missingInvoice : missingInvoiceDTOList) {
					 MissingInvoiceDTO missingInvoiceDTO = new MissingInvoiceDTO();
					 int monthDigit =  getMonthNumber(missingInvoice.getInvoiceDate());
					 missingInvoiceDTO.setInvoiceDate(getStartDate(monthDigit,orgLocation));
					 missingInvoiceDTO.setInvoiceAmount(missingInvoice.getInvoiceAmount());
					 list.add(missingInvoiceDTO);
				 }
				 
				 
				response.setData(list);
				response.setRecordsFiltered(count);
				response.setRecordsTotal(count);
				response.setStatusCode("200");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;

	}

	private int getMonthNumber(String key) {
		int number = 0;
		switch(key){  
	    case "Jan": number = 0;
	     break; 
	    case "Feb": number = 1;
	     break;  
	    case "Mar": number = 2;
	     break;  
	    case "Apr": number = 3;
	     break;  
	    case "May": number = 4;
	     break;  
	    case "Jun": number = 5;
	     break;  
	    case "Jul": number = 6;
	     break;  
	    case "Aug": number = 7;
	     break;  
	    case "Sep": number = 8;
	     break;  
	    case "Oct": number = 9;
	     break;  
	    case "Nov": number = 10;
	     break;  
	    case "Dec": number = 11;
	     break;  
	 
	    default: number = 0;
	    	  
	    }  
		return number;
	}

	private String getStartDate(int currentMonth, OrgLocations orgLocation) {
		Date d = new Date();
	    Calendar calendar = Calendar.getInstance();      
	    calendar.set(Calendar.MONTH,currentMonth);
	    calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	    Date monthStartDate = calendar.getTime();
	    System.out.println(monthStartDate);
	    return commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", monthStartDate);
		
	}

}
