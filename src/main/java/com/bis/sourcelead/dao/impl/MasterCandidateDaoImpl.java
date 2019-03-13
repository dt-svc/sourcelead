package com.bis.sourcelead.dao.impl;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.InvoiceDocument;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class MasterCandidateDaoImpl implements MasterCandidateDao{
	
	
private static final Logger logger = LoggerFactory.getLogger(MasterCandidateDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${SourceLead.100}")
	private String notFound;
	
	@Value("${SourceLead.101}")
	private String noRecords;
	
	@Value("${SourceLead.102}")
	private String statusUpdate;
	
	@Value("${SourceLead.103}")
	private String statusDelete;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	@Transactional
	public MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception
	{
		logger.debug("Storing Master Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (masterCandidate != null) {
				session.saveOrUpdate(masterCandidate);
			}
			logger.debug("Master Candidate Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MasterCandidate"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return masterCandidate;
	}

	@Override
	@Transactional
	public MasterCandidate getMasterCandidateByEmailOrPhoneNumber(String email, String phoneNumber) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List masterCandidateList = null;
		
/*		if(!isNullOrEmpty(phoneNumber)){
			query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email  OR  masterCandidate.phoneNumber =:phoneNumber )";
			masterCandidateList = session.createQuery(query).setString("email", email)
																 .setString("phoneNumber", phoneNumber).list();
		}else{
			query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email)";
			masterCandidateList = session.createQuery(query).setString("email", email).list();
		}*/
		
		query = "from MasterCandidate masterCandidate  where (masterCandidate.email =:email)";
		masterCandidateList = session.createQuery(query).setString("email", email).list();
		
		MasterCandidate masterCandidate = null;
		if(masterCandidateList != null && !masterCandidateList.isEmpty())
		{
			masterCandidate = (MasterCandidate) masterCandidateList.iterator().next();
		}
		return masterCandidate;
	}


	@Override
	@Transactional
	public Long getMasterCandidateIdByEmail(String email) throws Exception {
		
		Long masterCandidateId = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" MC.ID "
				+" FROM MASTER_CANDIDATE MC "
				+" WHERE "
				+" MC.EMAIL = :email ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("email", email);	
		BigInteger id = (BigInteger) queryObj.uniqueResult();
		if(id != null) {
			masterCandidateId = id.longValue();
		}
		
		return masterCandidateId;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void changeDocumentNamesWithWhiteSpacesToUnderScore(String staticPath) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			System.out.println("Folder 1");
			System.out.println("Started fetching documents from CandidateResumes....");
			List<CandidateResumes> candidateResumesList = session.createQuery("from CandidateResumes").list();
			if(candidateResumesList != null && !candidateResumesList.isEmpty()) {
				for(CandidateResumes candidateResumes : candidateResumesList) {
					String newFileName = getDocumentNewName(staticPath, candidateResumes.getId(), candidateResumes.getFileURL());
					if(newFileName != null) {
						candidateResumes.setFileURL(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from CandidateResumes....\n");
			
			System.out.println("Folder 2");
			System.out.println("Started fetching documents from CandidateDocuments....");
			List<CandidateDocuments> candidateDocumentsList = session.createQuery("from CandidateDocuments").list();
			if(candidateDocumentsList != null && !candidateDocumentsList.isEmpty()) {
				for(CandidateDocuments candidateDocuments : candidateDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, candidateDocuments.getId(), candidateDocuments.getFileURL());
					if(newFileName != null) {
						candidateDocuments.setFileURL(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from CandidateDocuments....\n");
			
			System.out.println("Folder 3");
			System.out.println("Started fetching documents from OrgDocuments....");
			List<OrgDocuments> orgDocumentsList = session.createQuery("from OrgDocuments").list();
			if(orgDocumentsList != null && !orgDocumentsList.isEmpty()) {
				for(OrgDocuments orgDocuments : orgDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, orgDocuments.getId(), orgDocuments.getFileUrl());
					if(newFileName != null) {
						orgDocuments.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from OrgDocuments....\n");
			
			System.out.println("Folder 4");
			System.out.println("Started fetching documents from Requirement....");
			List<Requirement> requirementList = session.createQuery("from Requirement").list();
			if(requirementList != null && !requirementList.isEmpty()) {
				for(Requirement requirement : requirementList) {
					String newFileName = getDocumentNewName(staticPath, requirement.getId(), requirement.getFileURL());
					if(newFileName != null) {
						requirement.setFileURL(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from Requirement....\n");

			System.out.println("Folder 5");
			System.out.println("Started fetching documents from OffBoardingDocuments....");
			List<OffBoardingDocuments> offBoardingDocumentsList = session.createQuery("from OffBoardingDocuments").list();
			if(offBoardingDocumentsList != null && !offBoardingDocumentsList.isEmpty()) {
				for(OffBoardingDocuments onBoardingTemplateDocuments : offBoardingDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, onBoardingTemplateDocuments.getId(), onBoardingTemplateDocuments.getFileURL());
					if(newFileName != null) {
						onBoardingTemplateDocuments.setFileURL(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from OffBoardingDocuments....\n");

			System.out.println("Folder 6");
			System.out.println("Started fetching documents from ProjectDocuments....");
			List<ProjectDocuments> projectDocumentsList = session.createQuery("from ProjectDocuments").list();
			if(projectDocumentsList != null && !offBoardingDocumentsList.isEmpty()) {
				for(ProjectDocuments projectDocuments : projectDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, projectDocuments.getId(), projectDocuments.getFileUrl());
					if(newFileName != null) {
						projectDocuments.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from ProjectDocuments....\n");

			System.out.println("Folder 7");
			System.out.println("Started fetching documents from TimesheetSubmissionsDocuments....");
			List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = session.createQuery("from TimesheetSubmissionsDocuments").list();
			if(timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()) {
				for(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments : timesheetSubmissionsDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, timesheetSubmissionsDocuments.getId(), timesheetSubmissionsDocuments.getFileUrl());
					if(newFileName != null) {
						timesheetSubmissionsDocuments.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from TimesheetSubmissionsDocuments....\n");

			System.out.println("Folder 8");
			System.out.println("Started fetching documents from ContractRequestDocuments....");
			List<ContractRequestDocuments> contractRequestDocumentsList = session.createQuery("from ContractRequestDocuments").list();
			if(contractRequestDocumentsList != null && !contractRequestDocumentsList.isEmpty()) {
				for(ContractRequestDocuments contractRequestDocuments : contractRequestDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, contractRequestDocuments.getId(), contractRequestDocuments.getFileURL());
					if(newFileName != null) {
						contractRequestDocuments.setFileURL(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from ContractRequestDocuments....\n");

			System.out.println("Folder 9");
			System.out.println("Started fetching documents from InvoiceDocument....");
			List<InvoiceDocument> invoiceDocumentList = session.createQuery("from InvoiceDocument").list();
			if(invoiceDocumentList != null && !invoiceDocumentList.isEmpty()) {
				for(InvoiceDocument invoiceDocument : invoiceDocumentList) {
					String newFileName = getDocumentNewName(staticPath, invoiceDocument.getId(), invoiceDocument.getFileUrl());
					if(newFileName != null) {
						invoiceDocument.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from InvoiceDocument....\n");

			System.out.println("Folder 10");
			System.out.println("Started fetching documents from Leave....");
			List<Leave> leaveList = session.createQuery("from Leave").list();
			if(leaveList != null && !leaveList.isEmpty()) {
				for(Leave leave : leaveList) {
					String newFileName = getDocumentNewName(staticPath, leave.getId(), leave.getFileUrl());
					if(newFileName != null) {
						leave.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from Leave....\n");

			System.out.println("Folder 11");
			System.out.println("Started fetching documents from ImmigrationCandidateDocuments....");
			List<ImmigrationCandidateDocuments> immigrationCandidateDocumentsList = session.createQuery("from ImmigrationCandidateDocuments").list();
			if(immigrationCandidateDocumentsList != null && !immigrationCandidateDocumentsList.isEmpty()) {
				for(ImmigrationCandidateDocuments immigrationCandidateDocuments : immigrationCandidateDocumentsList) {
					String newFileName = getDocumentNewName(staticPath, immigrationCandidateDocuments.getId(), immigrationCandidateDocuments.getUplodedFileUrl());
					if(newFileName != null) {
						immigrationCandidateDocuments.setUplodedFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from ImmigrationCandidateDocuments....\n");

			System.out.println("Folder 12");
			System.out.println("Started fetching documents from ImmigrationTemplateForms....");
			List<ImmigrationTemplateForms> immigrationTemplateFormsList = session.createQuery("from ImmigrationTemplateForms").list();
			if(immigrationTemplateFormsList != null && !immigrationTemplateFormsList.isEmpty()) {
				for(ImmigrationTemplateForms immigrationTemplateForms : immigrationTemplateFormsList) {
					String newFileName = getDocumentNewName(staticPath, immigrationTemplateForms.getId(), immigrationTemplateForms.getFileUrl());
					if(newFileName != null) {
						immigrationTemplateForms.setFileUrl(newFileName);
					}
				}
			}
			System.out.println("Completed fetching documents from ImmigrationTemplateForms....\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDocumentNewName(String staticPath, long id, String fileURL) {
		String newName = null;
		try {
			if (fileURL != null && !fileURL.isEmpty() && fileURL.contains(" ")) {
				fileURL = fileURL.replace("/slead-static", "");
				String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				String folder = fileURL.substring(0, fileURL.lastIndexOf("/"));
				File file = new File(staticPath + fileURL.substring(0, fileURL.lastIndexOf("/")), fileName);
				if (file.exists()) {
					File newFile = new File(staticPath + fileURL.substring(0, fileURL.lastIndexOf("/")), fileName.replaceAll(" ", "_"));
					Files.move(file.toPath(), newFile.toPath());
					newName = "/slead-static" + fileURL.substring(0, fileURL.lastIndexOf("/")) + "/" + fileName.replaceAll(" ", "_");
					System.out.println("id : " + id + " folder : " + folder + " oldName : " + fileName + " newName : " + newName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newName;
	}
}