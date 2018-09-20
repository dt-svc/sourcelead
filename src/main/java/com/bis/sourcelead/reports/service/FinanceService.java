package com.bis.sourcelead.reports.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.MissingInvoiceDTO;
import com.bis.sourcelead.reportsDTO.PaidBillDTO;

public interface FinanceService {

	APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

	ResponseList<APAgingDTO> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

	ResponseList<MissingInvoiceDTO> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto,OrgLocations orgLocation, String contractType);

	ResponseList<MissingInvoiceDTO> getMissingInvoiceGraphData(MissingInvoiceDTO missingInvoiceDto,OrgLocations orgLocation, String contractType);

	ResponseList<PaidBillDTO> getPaidVendorsList(PaidBillDTO paidBillDTO, OrgLocations orgLocation);

	ResponseList<PaidBillDTO> getPaidBillDetailReport(PaidBillDTO paidBillDTO, OrgLocations orgLocation);

	ResponseList<PaidBillDTO> getPaidBillGraphReport(PaidBillDTO paidBillDTO, OrgLocations orgLocation);

	PaidBillDTO getTotalAmountPerYear(PaidBillDTO paidBillDTO, OrgLocations orgLocation) throws Exception;

}
