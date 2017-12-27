package com.bis.sourcelead.reports.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.reports.dao.FinanceDao;
import com.bis.sourcelead.reports.service.FinanceService;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
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
	
	@Override
	public ResponseList<APAgingDTO> getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {

		ResponseList<APAgingDTO> res = financeDao.getApAgingListByLocation(orgLocation, apAgingDTO);

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
					apagingDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
				}
				apagingDTO.setDueDate(
						commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getDueDate()));
				apagingDTO.setBillDate(
						commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoice.getInvoiceDate()));
				apagingDTO.setTotalAmount(Double.parseDouble(invoice.getTotal()));
				apagingDTO.setDueAmount(Double.parseDouble(invoice.getDueAmount()));
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

}
