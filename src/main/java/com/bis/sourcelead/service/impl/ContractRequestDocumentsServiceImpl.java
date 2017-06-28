package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractRequestDocumentsDao;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SignatureRequestUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ContractRequestDocumentsServiceImpl implements ContractRequestDocumentsService {
	
	@Autowired
	private ContractRequestDocumentsDao contractRequestDocumentsDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Override
	public ContractRequestDocuments saveContractRequestDocuments(ContractRequestDocuments contractRequestDocuments) throws Exception
	{
		return contractRequestDocumentsDao.saveContractRequestDocuments(contractRequestDocuments);
	}

	@Override
	public Integer getCountOfDocByContractedId(Long contractId)
	{
		return contractRequestDocumentsDao.getCountOfDocByContractedId(contractId);
	}

	@Override
	public List<ContractRequestDocuments> getAllDocByContractId(Long contractId)
	{
		return contractRequestDocumentsDao.getAllDocByContractId(contractId);
	}

	@Override
	public ContractRequestDocuments getContractRequestDocumentsById(Long docId)
	{
		return contractRequestDocumentsDao.getContractRequestDocumentsById(docId);
	}

	@Override
	public ContractRequestDocuments getContractRequestDocumentsByContractUUID(String contractUUID)
	{
		return contractRequestDocumentsDao.getContractRequestDocumentsByContractUUID(contractUUID);
	}

	@Override
	public ContractRequestDocuments getContractByContractId(Long contractId)
	{
		return contractRequestDocumentsDao.getContractByContractId(contractId);
	}

	
}
