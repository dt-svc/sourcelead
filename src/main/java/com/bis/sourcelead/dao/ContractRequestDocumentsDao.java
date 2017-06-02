package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ContractRequestDocuments;

public interface ContractRequestDocumentsDao {
	ContractRequestDocuments saveContractRequestDocuments(ContractRequestDocuments contractRequestDocuments) throws Exception;

	Integer getCountOfDocByContractedId(Long contractId);

	List<ContractRequestDocuments> getAllDocByContractId(Long contractId);

	ContractRequestDocuments getContractRequestDocumentsById(Long docId);
}
