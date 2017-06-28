package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;

public interface SourceleadNotesService {

public SourceleadNotes storeComments(JSONObject jsonObj,User user) throws Exception;

public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId) throws Exception;
	
public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId) throws Exception;

public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId) throws Exception;

public List<SourceleadNotes> getNotesListByContractId(Long contractId) throws Exception;

}
