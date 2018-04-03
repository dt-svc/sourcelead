package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadNotes;

public interface SourceleadNotesDao {
	
	public SourceleadNotes storeComments(SourceleadNotes sourceleadNotes) throws Exception;
	
	public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId,Long orgLocationId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId,Long orgLocationId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByAssetId(Long assetId,Long orgLocationId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId,Long orgLocationId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByContractId(Long contractId,Long orgLocationId) throws Exception;

	public List<SourceleadNotes> getNotesListByImmigrationId(Long immigrationId, Long orgLocationId) throws Exception;
}