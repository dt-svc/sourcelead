package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.MissingInvoiceDTO;
import com.bis.sourcelead.reportsDTO.PaidBillDTO;

public interface FinanceDao {

	APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO);

	ResponseList<Invoice> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

   ResponseList<SubmissionApproverHierarchy> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto, OrgLocations orgLocationId);

   List<Contracts> getRecentSowcontractListByAssociateId(Long candidateId, OrgLocations orgLocation, OrgRelations orgRelationId,String contractType);

   ResponseList<Invoice> getPaidVendorslist(PaidBillDTO paidBillDTO, OrgLocations orgLocation);

   ResponseList<Invoice> getPaidBillDetailReport(PaidBillDTO paidBillDTO, OrgLocations orgLocation);

   Date getPaymentDateByInvoiceId(Long invoiceId);

   List<Invoice> getInvoicesByOrgRelationId(Long orgRelationId);

List<InvoicePayment> getInvoicePaidAmountByInvoiceIds(List<Long> invoiceIds);
   

}
