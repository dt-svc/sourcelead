package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ContractRequestDocumentsService {
	ContractRequestDocuments saveContractRequestDocuments(ContractRequestDocuments contractRequestDocuments) throws Exception;
	Integer getCountOfDocByContractedId(Long contractId);
	List<ContractRequestDocuments> getAllDocByContractId(Long contractId);
	ContractRequestDocuments getContractRequestDocumentsById(Long docId);
	ContractRequestDocuments getContractRequestDocumentsByContractUUID(String contractUUID);
}
