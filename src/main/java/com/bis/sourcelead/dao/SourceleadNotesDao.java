package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadNotes;

public interface SourceleadNotesDao {
	
	public SourceleadNotes storeComments(SourceleadNotes sourceleadNotes) throws Exception;
	
	public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId) throws Exception;
	
	public List<SourceleadNotes> getNotesListByContractId(Long contractId) throws Exception;
}