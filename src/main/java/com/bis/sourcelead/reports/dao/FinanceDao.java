package com.bis.sourcelead.reports.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.MissingInvoiceDTO;

public interface FinanceDao {

	APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO);

	ResponseList<Invoice> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

   ResponseList<SubmissionApproverHierarchy> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto, OrgLocations orgLocationId);

   List<Contracts> getRecentSowcontractListByAssociateId(Long candidateId, OrgLocations orgLocation, OrgRelations orgRelationId,String contractType);

}
