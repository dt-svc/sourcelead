package com.bis.sourcelead.dao.impl;

import java.io.StringReader;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.JobBoardConfigurationDAO;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.RequirementAssignToDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.RecruiterRrportDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.JobBoard;
import com.bis.sourcelead.entity.JobBoardData;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.JobFairRequirements;
import com.bis.sourcelead.entity.OrgJobBoardAccount;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementJobBoardInfo;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PasswordSecurity;
import com.bis.sourcelead.util.ReportsUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 
 * @author Prasad K
 * @date 29th Mar 2016
 *
 */
@Repository
public class RequirementDaoImpl implements RequirementDao {

	private static final Logger logger = LoggerFactory.getLogger(RequirementDaoImpl.class);
        
        public static final MediaType XML = MediaType.parse("text/xml; charset=utf-8");

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private RequirementLocationsService requirementLocationsService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	UserRoleDao userRoleDao;
        
    @Autowired
    private JobBoardConfigurationDAO jobBoardConfigurationDAO;
    
    @Autowired
    private OrgRelationsDao orgRelationsDao;
        
    @Autowired
    SourceleadELKBaseIndexer sourceleadELKBaseIndexer;
    
    @Autowired
    private RequirementSubmissionDao requirementSubmissionDao;
    
    @Autowired
    private OrgLocationsDao orgLocationsDao;
        
    @Value("${static.path}")
	private String staticPath;
	
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

	@Value("${jdbc.schema}")
	private String schema;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@Autowired
	RequirementAssignToDao requirementAssignToDao;

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	/**
	 * @author prasad k
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement table.
	 * 
	 * @param requirement
	 *            This is the first paramter to storeRequirement method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement storeRequirement(Requirement requirement) throws Exception {
            logger.debug("Storing Requirement");
            Session session = this.sessionFactory.getCurrentSession();
            try {
                List<RequirementJobBoardInfo> list = new ArrayList<>();
                if (requirement != null) {
                    List<RequirementJobBoardInfo> list1 = requirement.getRequirementJobBoardInfoList();
                    if (requirement.getJobBoardAccountIdList() != null) {
                        for (Long l : requirement.getJobBoardAccountIdList()) {
                            RequirementJobBoardInfo r = new RequirementJobBoardInfo();
                            boolean flag = false;
                            if (list1 != null) {
                                for (RequirementJobBoardInfo re : list1) {
                                    if (re.getOrgJobBoardAccount().getId() == l) {
                                        r = re;
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            
                            OrgJobBoardAccount orgJobBoardAccount = (OrgJobBoardAccount) session.get(OrgJobBoardAccount.class, l);
                            JobBoard jobBoard = orgJobBoardAccount.getOrgJobBoard().getJobBoard();
                            r.setJobBoard(jobBoard);
                            r.setOrgJobBoardAccount(orgJobBoardAccount);
                            r.setRequirement(requirement);
                            if (jobBoard.getName().equalsIgnoreCase("Monster")) {
                                String bodyContent = FileUploadUtil.getFileContent(staticPath, jobBoard.getContentFileUrl());
                                if (!bodyContent.isEmpty()) {
                                    bodyContent = bodyContent.replace("{username}", orgJobBoardAccount.getUsername());
                                    bodyContent = bodyContent.replace("{password}", new PasswordSecurity().decrypt(orgJobBoardAccount.getPassword()));
                                    String jobTitle = requirement.getJobTitle();
                                    while(jobTitle.length() < 25) {
                                    	jobTitle += " " + jobTitle;
                                    }
                                    bodyContent = bodyContent.replace("{title}", jobTitle);
                                    bodyContent = bodyContent.replace("{jobDetail}", requirement.getDescription() == null ? jobTitle : requirement.getDescription());
                                    String loc[] = requirement.getLocationsList() == null || requirement.getLocationsList().isEmpty() ? "Hyderabad, Telangana, IN".split(", ") : requirement.getLocationsList().get(0).split(", ");
                                    bodyContent = bodyContent.replace("{city}", loc.length > 0 ? loc[0] : "Hyderabad");
                                    bodyContent = bodyContent.replace("{state}", loc.length > 1 ? loc[1] : "Telangana");
                                    bodyContent = bodyContent.replace("{country}", loc.length > 2 ? loc[2] : "IN");
                                    bodyContent = bodyContent.replace("{jobRefCode}", requirement.getGeneratedId());
//                                        bodyContent = bodyContent.replace("{postatlCode}", "10001"/*requirement.getPostatlCode()*/);
                                    Date today = requirement.getCreatedDate() == null ? new Date() : requirement.getCreatedDate();
                                    Constants.TZ_DF1.setTimeZone(TimeZone.getTimeZone("GMT"));
                                    String dateString = Constants.TZ_DF1.format(today);
                                    bodyContent = bodyContent.replace("{dateTime1}", dateString);
                                    Constants.FULL_DATE.setTimeZone(TimeZone.getTimeZone("GMT"));
                                    String dateString1 = Constants.FULL_DATE.format(today);
                                    bodyContent = bodyContent.replace("{dateTime2}", dateString1);

                                    String jobStatusCode = "4";
                                    JobBoardData jobStatusCodeData = requirement.getJobType() == null ? null : jobBoardConfigurationDAO.getJobBoardDataList(jobBoard.getId(), "JobStatus", "jobType", requirement.getJobType(), requirement.getJobType());
                                    if (jobStatusCodeData != null) {
                                        jobStatusCode = jobStatusCodeData.getJobBoardValue();
                                    }
                                    bodyContent = bodyContent.replace("{jobStatusCode}", jobStatusCode);

                                    String jobCateogoryCode = "660";
                                    JobBoardData jobCateogoryData = requirement.getIndustryName() == null ? null : jobBoardConfigurationDAO.getJobBoardDataList(jobBoard.getId(), "JobCategory", "industryName", requirement.getIndustryName(), requirement.getIndustryName());
                                    if (jobCateogoryData != null) {
                                        jobCateogoryCode = jobCateogoryData.getJobBoardValue();
                                    }
                                    bodyContent = bodyContent.replace("{jobCategoryCode}", jobCateogoryCode);

                                    String industryCode = "0";
                                    JobBoardData industryData = requirement.getIndustryName() == null ? null : jobBoardConfigurationDAO.getJobBoardDataList(jobBoard.getId(), "Industry", "industryName", requirement.getIndustryName(), requirement.getIndustryName());
                                    if (industryData != null) {
                                        industryCode = industryData.getJobBoardValue();
                                    }
                                    bodyContent = bodyContent.replace("{industryCode}", industryCode);
                                    
                                    bodyContent = bodyContent.replace("{companyName}", orgJobBoardAccount.getOrg().getOrgName());
                                    bodyContent = bodyContent.replace("{companyAddress}", orgJobBoardAccount.getOrg().getWebSite() != null ? orgJobBoardAccount.getOrg().getWebSite() : orgJobBoardAccount.getOrg().getOrgName());
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody body = RequestBody.create(XML, bodyContent);
                                    Request req = new Request.Builder().url(jobBoard.getPostUrl())
                                                    .addHeader("Content-Type", "application/xml")
                                                    .post(body)
                                                    .build();
                                    Response res = client.newCall(req).execute();
                                    System.out.println("Response Body : " + res.body());
                                    if(res.isSuccessful()) {
                                        String resBody = res.body().string();
                                        System.out.println(resBody);
                                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                        DocumentBuilder builder = factory.newDocumentBuilder();
                                        Document doc = builder.parse(new InputSource(new StringReader(resBody))); 
                                        NodeList nodeList = doc.getElementsByTagName("ReturnCode");
                                        int c = nodeList.getLength();
                                        for(int i = 0; i < c;i++) {
                                            Element ele = (Element) nodeList.item(i);
                                            String status = ele.getTextContent();
                                            String statusText = ele.getAttribute("returnCodeType");
                                            if(status.equals("1") || statusText.equals("failure")) {
                                                NodeList nodeList1 = doc.getElementsByTagName("Descriptions").item(0).getChildNodes();
                                                int c1 = nodeList1.getLength();
                                                StringBuilder error = new StringBuilder();
                                                for(int j = 0; j < c1;j++) {
                                                    Element ele1 = (Element) nodeList1.item(j);
                                                    if(ele1.getAttribute("descriptionType").equals("error")) {
                                                        error.append("[Error]").append(ele1.getTextContent()).append("<br/>");
                                                    }
                                                    if(ele1.getAttribute("descriptionType").equals("warning")) {
                                                        error.append("[Warning]").append(ele1.getTextContent()).append("<br/>");
                                                    }
                                                }
                                                throw new Exception(new Exception(error.toString()));
                                            }
                                        }
                                        Element ele = (Element) doc.getElementsByTagName("JobPostingResponse").item(0); 
                                        String postingId = ele.getAttribute("postingId");
                                        r.setJobId(postingId);
                                        r.setJobUrl("http://jobview.monster.com/getjob.aspx?jobid="+postingId);
                                        session.saveOrUpdate(r);
                                    }
                                }
                            } else if (jobBoard.getName().equalsIgnoreCase("Dice")) {

                            } else if (jobBoard.getName().equalsIgnoreCase("Naukri")) {

                            }
                            
                            list.add(r);
                        }
                    }
                    requirement.setRequirementJobBoardInfoList(list);
                    session.saveOrUpdate(requirement);
                }
                logger.debug("Requirement Stored Successfully");
            } catch (Exception exception) {
                exception.printStackTrace();
                StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "Requirement"));
                throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
            }
            return requirement;
	}

	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get RequirementList by orgLocationId .
	 * 
	 * @param orgLocationId
	 *            This is the first paramter to getRequirementListByOrgLocationID
	 *            method
	 * 
	 * @param status
	 *            This is the second paramter to getRequirementListByOrgLocationID
	 *            method
	 * 
	 * @param clients
	 *            This is the third paramter to getRequirementListByOrgLocationID
	 *            method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId, String status, String clients,
			User user, String userRoleName) throws Exception {

		List<Requirement> reqList = null;
		Session session = this.sessionFactory.getCurrentSession();
		// User user = userDao.getUserById(userId);

		// UserRole userRole1 =
		// userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);

		Map<String, Object> params = new HashMap<String, Object>();

		String from = "from Requirement requirement  left join fetch requirement.parentRequirement  left join fetch requirement.clientId left join fetch requirement.spocId left join fetch requirement.submissionTemplateId left join fetch requirement.clientSubmissionTemplateId left join fetch requirement.parentRequirement";
		String subQuery = "";

		if (userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName)
				|| "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
			subQuery = " where requirement.orgLocationId.id =:orgLocationId and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("orgLocationId", orgLocationId);
		} else {
			String subQuery1 = "requirement.createdBy.id =:userId and (requirement.isTemplate IS NULL OR requirement.isTemplate <> 'Y') ";
			params.put("userId", user.getId());

			String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
			params.put("userId", user.getId());
			params.put("level", 1);

			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);

			String subQuery4 = "requirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT'";
			params.put("managerUserId", user.getId());

			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", user.getId());

			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", user.getId());

			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != ""
					|| subQuery6 != "") {
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (requirement.releaseJobToAllUsers = 'Y') OR ("
						+ subQuery1 + ") OR (" + subQuery2 + ") OR (" + subQuery3 + ") OR (" + subQuery4 + ") OR ("
						+ subQuery5 + ") OR (" + subQuery6 + ") )";
				params.put("orgLocationId", orgLocationId);
			}
		}

		String[] client = null;
		if (null != clients && !"".equals(clients)) {
			client = clients.split(",");
		}
		List<Long> clientsList = new ArrayList<>();
		if (client != null) {
			for (int i = 0; i < client.length; i++) {
				clientsList.add(Long.parseLong(client[i]));
			}
		}

		String subQuery7 = "";
		String[] subStatus = status.split(",");
		if ((null == status || "".equals(status)) && (null == clients || "".equals(clients))) {
			subQuery7 = " and (requirement.requirementSubmissionStatus ='NEW' or requirement.requirementSubmissionStatus = 'SUBMITTED' or requirement.requirementSubmissionStatus = 'DRAFT' or requirement.requirementSubmissionStatus = 'OPEN') ";
		} else {
			if (Arrays.asList(subStatus).contains("NEW")) {
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'NEW' ";
			}
			if (Arrays.asList(subStatus).contains("DRAFT")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'DRAFT' ";
			}
			if (Arrays.asList(subStatus).contains("SUBMITTED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'SUBMITTED' ";
			}
			if (Arrays.asList(subStatus).contains("APPROVED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'APPROVED' ";
			}
			if (Arrays.asList(subStatus).contains("REJECTED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'REJECTED' ";
			}
			if (Arrays.asList(subStatus).contains("OPEN")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'OPEN' ";
			}

			if (Arrays.asList(subStatus).contains("FILLEDCLOSED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'FILLEDCLOSED' ";
			}
			if (Arrays.asList(subStatus).contains("UNFILLEDCLOSED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'UNFILLEDCLOSED' ";
			}
			if (Arrays.asList(subStatus).contains("ONHOLD")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'ONHOLD' ";
			}
			if (Arrays.asList(subStatus).contains("CANCEL")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'CANCEL' ";
			}
			if (Arrays.asList(subStatus).contains("CLOSED")) {
				if (StringUtils.isNotEmpty(subQuery7)) {
					subQuery7 = subQuery7 + " OR ";
				}
				subQuery7 = subQuery7 + " requirement.requirementSubmissionStatus = 'CLOSED' ";
			}
			subQuery7 = " and ( " + subQuery7 + " ) ";
		}

		Query query = null;
		if (clientsList.isEmpty()) {
			query = session.createQuery(from + " " + subQuery + " " + subQuery7 + " ORDER BY requirement.id DESC");
		} else {
			query = session.createQuery(from + " " + subQuery + " " + subQuery7
					+ "and requirement.clientId.id IN (:clientList) ORDER BY requirement.id DESC");
			params.put("clientsList", clientsList);
		}

		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")
					|| str.equalsIgnoreCase("approverUserId") || str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			}
			if (!clientsList.isEmpty()) {
				query.setParameterList("clientList", clientsList);
			} else {
				query.setParameter(str, params.get(str));
			}

		}

		reqList = query.list();
		return reqList;

	}

	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get Requirement by requirementId .
	 * 
	 * @param requirementId
	 *            This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch requirement.orgLocationId  where requirement.id =:requirementId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
			// System.out.println(requirement.getParentRequirement());
		}
		return requirement;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.parentRequirement.id =:requirementId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;

	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementIdAndSearchValue(Long requirementId,String searchValue) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query1 = null;
		Query finalQuery = null;
		Requirement requirement = null;
		if(searchValue !=null && StringUtils.isNotBlank(searchValue)){
		 query1 = " and ((requirement.jobTitle like '" + searchValue + "%') or (requirement.generatedId like '" + searchValue + "%'))";
		}
		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch requirement.orgLocationId  where requirement.id =:requirementId";
		if(searchValue !=null && StringUtils.isNotBlank(searchValue)){
		finalQuery = session.createQuery(query + query1);
		}else{
			finalQuery = session.createQuery(query);	
		}
		requirement = (Requirement)finalQuery.setLong("requirementId", requirementId).uniqueResult();
		return requirement;

	}

	/**
	 * @author venkat kesav
	 * @since oct 21 2016
	 * @version 0.0.1 This method for validate requirement by requirementId and
	 *          SpocId. It returns boolean value
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		if (requirementId != null && spocId != null) {
			String query = "from Requirement requirement where requirement.parentRequirement.id = :requirementId and requirement.spocId = :spocId ";
			List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId)
					.setLong("spocId", spocId).list();
			if (requirementList != null && requirementList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.parentRequirement.id = :requirementId and requirement.spocId = :spocId ";
		List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId)
				.setLong("spocId", spocId).list();
		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement  req  left join fetch req.parentRequirement  where req.isTemplate = 'Y' and req.orgLocationId =:orgLocationId and req.status<>'0'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo reqassignto left join fetch reqassignto.teamId left join fetch reqassignto.roleId left join fetch reqassignto.assignUserId left join fetch reqassignto.requirementId  where reqassignto.assignUserId.id =:userId and reqassignto.roleId.orgLocations.id =:orgLocationId and reqassignto.status <> '0' ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType,
			Long userId, String userCode) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		Map<String, Object> params = new HashMap<String, Object>();

		/* String subQueryRole = ""; */
		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery = "";
		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId", userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", userId);

			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "") {
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
						+ subQuery2 + ")  or (" + subQuery3 + ") ) ";
				params.put("orgLocationId", orgLocationId);
			}
		} else {
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "") {
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
						+ subQuery2 + ")) ";
				params.put("orgLocationId", orgLocationId);
			}
		}

		String whereCondition = subQuery;
		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
				query.setLong(str, (long) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(
			Long orgLocationId, String userType, Long userId, String userCode, boolean isWalkIn, String walkinDay) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		Map<String, Object> params = new HashMap<String, Object>();

		/* String subQueryRole = ""; */
		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery = "";
		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0')   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId", userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", userId);
			
			String subQuery4 = " requirement.primaryRecruiterUserId.id =:userId ";
			params.put("userId", userId);
			
			/*String subQuery5 = "requirement.id IN ( select approver.requirementId from RequirementApproverHierarchy approver where approver.approverUserId.id = :approverUserId )";
			params.put("approverUserId", userId);*/
			
			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0')   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ";
			params.put("userId", user.getId());
			params.put("level", 1);

			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0')   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) )  )";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);

			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 !="") {
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
						+ subQuery2 + ")  or (" + subQuery3 + ")  or (" + subQuery4 + ") or (" + subQuery5 + ") or ("+subQuery6+")) ";
				params.put("orgLocationId", orgLocationId);
			}
		} else {
			
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT' and rah.status <> '0') ";

			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "") {
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
						+ subQuery2 + ")) ";
				params.put("orgLocationId", orgLocationId);
			}
		}
		
		subQuery += " and requirement.walkIn=:isWalkIn";
		params.put("isWalkIn", isWalkIn);
		if(isWalkIn) {
			subQuery += " and requirement.walkInDays like '%" + walkinDay + "%'";
		}
		
		String whereCondition = subQuery;
		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
		query.setMaxResults(10);
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
				query.setLong(str, (long) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Requirement> getAssignedJobsPerformanceList(Long orgLocationId, String userType, Long userId, String userCode,Date startDateUTC,Date endDateUTC,JobFilterDTO jobFilterDTO) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);
		OrgLocations orgLocation = orgLocationsDao.getOrgLocations(orgLocationId);

		Map<String, Object> params = new HashMap<String, Object>();
		List<Requirement> reqList = null;
		List<Long> countlist = null;
		ResponseList<Requirement> response = new ResponseList<>();

		/* String subQueryRole = ""; */
		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery = "";
		String countQuery = "from Requirement requirement";
		String countQuerySub ="";
		String jobTypeVal ="";
		String clientVal ="";
		//List<Long> data = new ArrayList<Long>;
		
		//List sorting order
				String sortingQry = "";
				if(StringUtils.isNotBlank(jobFilterDTO.getSortColumn())) {
				sortingQry = " order by requirement."+ jobFilterDTO.getSortColumn() +" "+jobFilterDTO.getSortOrder();
				}
				
		StringBuilder query1 = new StringBuilder("");
		if(StringUtils.isNotBlank(jobFilterDTO.getJobType())){
			if(jobFilterDTO.getJobType().equalsIgnoreCase("internal")){
				jobTypeVal = " requirement.isClientJob = 'N'";	
			}
			else if(jobFilterDTO.getJobType().equalsIgnoreCase("client")){
				jobTypeVal = " requirement.isClientJob = 'Y' and requirement.clientId IS NOT NULL";	
			}else{
				jobTypeVal = " (requirement.isClientJob = 'N') or (requirement.isClientJob = 'Y')";	
			}
			
		}
		if(StringUtils.isNotBlank(jobFilterDTO.getClientIds())){
			
			clientVal = "requirement.clientId IN("+jobFilterDTO.getClientIds()+")";	
		}
		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0' and rah.createdDate BETWEEN :startDateUTC and :endDateUTC)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId", userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId) and rah.createdDate BETWEEN :startDateUTC and :endDateUTC) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", userId);

			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "" || jobTypeVal !="") {
				if(clientVal != ""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ("+ clientVal +") and ("+ jobTypeVal +") and ( (" + subQuery1 + ")  or ("
						+ subQuery2 + ")  or (" + subQuery3 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";
				}else{
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ("+ jobTypeVal +") and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")  or (" + subQuery3 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";	
				}
				params.put("orgLocationId", orgLocationId);
				params.put("startDateUTC", startDateUTC);
				params.put("endDateUTC", endDateUTC);
				
				if(clientVal != ""){
				countQuerySub = "where requirement.orgLocationId.id =:orgLocationId and ("+ clientVal +") and ("+ jobTypeVal +") and ( (" + subQuery1 + ")  or ("
						+ subQuery2 + ")  or (" + subQuery3 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";
				}else{
					countQuerySub = "where requirement.orgLocationId.id =:orgLocationId and ("+ jobTypeVal +") and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")  or (" + subQuery3 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC ";
				}
				params.put("orgLocationId", orgLocationId);
				params.put("startDateUTC", startDateUTC);
				params.put("endDateUTC", endDateUTC);
			}
			
		} else {
			
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT' and rah.status <> '0' and rah.createdDate BETWEEN :startDateUTC and :endDateUTC) ";

			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "" || jobTypeVal !="") {
				if(clientVal != ""){
				subQuery = "where requirement.orgLocationId.id =:orgLocationId and ("+ clientVal +") and ("+ jobTypeVal +") and ( (" + subQuery1 + ")  or ("
						+ subQuery2 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";
				}else{
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ("+ jobTypeVal +") and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";	
				}
				params.put("orgLocationId", orgLocationId);
				params.put("startDateUTC", startDateUTC);
				params.put("endDateUTC", endDateUTC);
				
				if(clientVal != ""){
				countQuerySub = "where requirement.orgLocationId.id =:orgLocationId and ("+ clientVal +") and ("+ jobTypeVal +") and ( (" + subQuery1 + ")  or ("
						+ subQuery2 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";
				}else{
				countQuerySub = "where requirement.orgLocationId.id =:orgLocationId and ("+ jobTypeVal +") and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ") ) and requirement.createdDate BETWEEN :startDateUTC and :endDateUTC";
						
				}
				params.put("orgLocationId", orgLocationId);
				params.put("startDateUTC", startDateUTC);
				params.put("endDateUTC", endDateUTC);
				
			}
		}
		if(!jobFilterDTO.getSearchValue().equals("'")){
		if (jobFilterDTO.getSearchValue() != null && !"".equalsIgnoreCase(jobFilterDTO.getSearchValue())) {
			query1.append(" and (requirement.jobTitle like '%" + jobFilterDTO.getSearchValue() + "%' ")
					.append("OR requirement.jobId like '%" + jobFilterDTO.getSearchValue() + "%' ")
					.append("OR requirement.priority like '%" + jobFilterDTO.getSearchValue() + "%' ")
					.append("OR requirement.generatedId like '%" + jobFilterDTO.getSearchValue() + "%' )");

		}
		}
		int count = 0;
		String whereCondition = subQuery+query1;
		Query query = session.createQuery(from + " " + whereCondition + sortingQry);
		for (String str : query.getNamedParameters()) {
		if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("")) {
			query.setLong(str, (long) params.get(str));
		}else{
			query.setParameter(str, params.get(str));
		}
	}
		
		reqList = query.setFirstResult(jobFilterDTO.getStart()).setMaxResults(jobFilterDTO.getLength()).list();
		
		for(Requirement requirement : reqList){
			 
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
					requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
					requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
				}
			}
			/*Integer requirementAssignToListCount = requirementAssignToDao.getRequirementAssignToListCountByRequirementId(requirement.getId());
    		requirement.setAssignedMembersCount(requirementAssignToListCount.toString());*/
			List<String> requirementLocationsList = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
    		List<RequirementLocations> locationList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
    		locationList.forEach(loclist->{
    			requirementLocationsList.add(loclist.getLocation());
    			if(sb.length() > 0) 
    				sb.append(",");
    			sb.append(loclist.getLocation()).toString();
    		});
    		requirement.setLocation(sb.toString());
    		requirement.setLocationsList(requirementLocationsList);
    		RequirementSubmissionsCountDTO requirementSubmissionsCountDTO= requirementSubmissionDao.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(),user,orgLocation,"All");
    		requirement.setAssignedMembersCount(requirementSubmissionsCountDTO.getApplicantsCount().toString());
    		requirement.setShortlistCount(requirementSubmissionsCountDTO.getShortlistCount().toString());
    		requirement.setOffersCount(requirementSubmissionsCountDTO.getOffersCount().toString());
    		requirement.setSubmitToClientCount(requirementSubmissionsCountDTO.getSubmitToClientCount().toString());
    		requirement.setJoinedCount(requirementSubmissionsCountDTO.getJoinedCount().toString());
    		requirement.setBackoutCount(requirementSubmissionsCountDTO.getBackoutCount().toString());
    		requirement.setInterviewCount(requirementSubmissionsCountDTO.getInterviewCount().toString());
    		requirement.setRejectedCount(requirementSubmissionsCountDTO.getRejectedCount().toString());
    		
		}
		
		
		Query countQueryFinal = session.createQuery("select count(*) " + countQuery + " " +countQuerySub +query1);
		for (String str : countQueryFinal.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("")) {
				countQueryFinal.setLong(str, (long) params.get(str));
			}else {
				countQueryFinal.setParameter(str, params.get(str));
			}
		}
		countlist=countQueryFinal.list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		response.setRecordsFiltered(count);
		
		response.setData(reqList);
		return response;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getJobFairDaysListByOrgLocationIDAndUserId(Long orgLocationId, String userType,
			Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			User user = userDao.getUserById(userId);
			Map<String, Object> params = new HashMap<String, Object>();

			/* String subQueryRole = ""; */
			String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
			String subQuery = "";
			if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

				String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId and rah.status <> '0')   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
				params.put("userId", userId);

				String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
				params.put("managerUserId", userId);

				String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
				params.put("userId", userId);

				if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "") {
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")  or (" + subQuery3 + ") ) ";
					params.put("orgLocationId", orgLocationId);
				}
			} else {
				String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT' and rah.status <> '0') ";

				String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
				params.put("managerUserId", userId);

				if (subQuery1 != "" || subQuery2 != "") {
					subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1 + ") or ("
							+ subQuery2 + ")) ";
					params.put("orgLocationId", orgLocationId);
				}
			}

			subQuery += " and requirement.walkIn=:isWalkIn";
			params.put("isWalkIn", true);

			String whereCondition = subQuery;
			Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
			query.setMaxResults(10);
			for (String str : query.getNamedParameters()) {
				if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
					query.setLong(str, (long) params.get(str));
				} else {
					query.setParameter(str, params.get(str));
				}

			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,
			String userType, Long userId, String userCode, String statusType) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		String subQuery1 = null;
		String subQuery2 = null;
		Map<String, Object> params = new HashMap<String, Object>();

		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery3 = "";
		String finalQuery = "";
		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			if (StringUtils.equalsIgnoreCase(statusType, "SUBMITTED")) {

				subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus <> 'APPROVED' and  (requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus = '"
						+ statusType + "' )) ";
				params.put("userId", userId);
				params.put("level", 1);

				subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus <> 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' AND requirement.requirementSubmissionStatus <> 'OPEN' ) and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
				params.put("approverUserId", userId);
				params.put("level1", 1);
				params.put("level2", 2);

			}
			if (StringUtils.equalsIgnoreCase(statusType, "APPROVED")) {

				subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus = '"
						+ statusType + "' and  (requirement.requirementSubmissionStatus <> 'DRAFT')) ";
				params.put("userId", userId);
				params.put("level", 1);

				subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus = 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT') and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
				params.put("approverUserId", userId);
				params.put("level1", 1);
				params.put("level2", 2);
			}
			if (StringUtils.equalsIgnoreCase(statusType, "REJECTED")) {

				subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus = 'REJECTED' and  (requirement.requirementSubmissionStatus <> 'DRAFT')) ";
				params.put("userId", userId);
				params.put("level", 1);

				subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and rah.updatedBy = :userCode and  rah.requirementApproverStatus = 'REJECTED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' ) )";
				params.put("approverUserId", userId);
				params.put("level2", 2);
				params.put("userCode", userCode);
			}

		} else {
			if (StringUtils.equalsIgnoreCase(statusType, "REJECTED")) {
				statusType = "REJECTED";
			}
			if (StringUtils.equalsIgnoreCase(statusType, "APPROVED")) {
				statusType = "OPEN";
			}
			if (StringUtils.equalsIgnoreCase(statusType, "SUBMITTED")) {

				subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  rah.requirementApproverStatus <> 'APPROVED' and  (requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus = '"
						+ statusType + "' )) ";
				params.put("userId", userId);
				params.put("level", 1);

				subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and  rah.requirementApproverStatus <> 'APPROVED'  and  (requirement.requirementSubmissionStatus <> 'DRAFT' AND requirement.requirementSubmissionStatus <> 'OPEN' ) and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED')) )";
				params.put("approverUserId", userId);
				params.put("level1", 1);
				params.put("level2", 2);

			}
			subQuery3 = " requirement.requirementSubmissionStatus <> 'DRAFT' and  requirement.requirementSubmissionStatus = '"
					+ statusType + "' ";
		}

		if (subQuery1 != null || subQuery2 != null) {

			StringBuilder sb = new StringBuilder();
			finalQuery = sb.append("where requirement.orgLocationId.id =:orgLocationId and ").append("( (")
					.append(subQuery1).append(")").append("or").append("(").append(subQuery2).append(") )").toString();

			// finalQuery = "where requirement.orgLocationId.id =:orgLocationId and (
			// ("+subQuery1+") or ("+subQuery2+")) ";
			params.put("orgLocationId", orgLocationId);
		} else {
			StringBuilder sb1 = new StringBuilder();
			finalQuery = sb1.append("where requirement.orgLocationId.id =:orgLocationId and ").append("(")
					.append(subQuery3).append(")").toString();
			// finalQuery = "where requirement.orgLocationId.id =:orgLocationId and
			// ("+subQuery3+") ";
			params.put("orgLocationId", orgLocationId);
		}

		String whereCondition = finalQuery;
		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {

			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")
					|| str.equalsIgnoreCase("approverUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		Map<String, Object> params = new HashMap<String, Object>();

		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";

		String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level )";
		params.put("userId", userId);
		params.put("level", 1);

		String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2 and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))) ";
		params.put("approverUserId", userId);
		params.put("level1", 1);
		params.put("level2", 2);

		String subQuery = "";
		if (subQuery2 != "" || subQuery3 != "") {
			subQuery = "where requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery2 + ") or (" + subQuery3
					+ ") )";
			params.put("orgLocationId", orgLocationId);
		}
		String whereCondition = subQuery;

		Query query = session.createQuery(from + " " + whereCondition);
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")
					|| str.equalsIgnoreCase("approverUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			}

			else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListById(Long requirementId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "from Requirement requ  left join fetch requ.parentRequirement left join fetch requ.spocId spoc    left join fetch requ.parentRequirement where requ.parentRequirement.id =:requirementId";
		return session.createQuery(query).setLong("requirementId", requirementId).list();

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement validateExistingJobId(String jobId, Long requirementId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.parentRequirement where requirement.id = :requirementId and requirement.jobId = :jobId and requirement.orgLocationId.id =:orgLocationId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId)
				.setString("jobId", jobId).setLong("orgLocationId", orgLocationId).list();
		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer validateJobId(String id, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "select count(*)from Requirement requirement where requirement.jobId = :id and requirement.orgLocationId.id =:orgLocationId";

		List<Long> jobCount = session.createQuery(query.toString()).setString("id", id)
				.setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if (jobCount != null && !jobCount.isEmpty()) {
			count = jobCount.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement checkRequirementIsReleasedOrNot(Long requirementId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.parentRequirement  where requirement.parentRequirement.id =:requirementId";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateRequirementTitle(String jobName, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Requirement requirement left join fetch requirement.parentRequirement  where requirement.jobTitle = :jobName and requirement.isTemplate = 'Y' and requirement.orgLocationId.id =:orgLocationId";
		List<Requirement> requirementList = session.createQuery(query).setString("jobName", jobName)
				.setLong("orgLocationId", orgLocationId).list();
		if (requirementList != null && requirementList.size() > 0) {
			flag = true;
			return flag;
		} else {
			return flag;
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user,
			OrgLocations loginOrgLocations) throws Exception {

		logger.debug("getAllReleasedJobsForVendor start");
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if (orgRelationId != null && orgLocationId != null) {

			// UserRole userRole =
			// userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(),
			// loginOrgLocations.getId());
			/* if("Admin".equalsIgnoreCase(userRole.getUserType())){ */
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId  and requirement.clientId.id IN (  select orgRelation.id from OrgRelations orgRelation where orgRelation.locationId.id =:orgLocationId )and requirement.parentRequirement IS NOT NULL group by requirement.clientId.id, requirement.parentRequirement.id";
			releasedJobsList = session.createQuery(query).setLong("orgLocationId", orgLocationId)
					.setLong("orgRelationId", orgRelationId).list();
			/*
			 * }else{ query =
			 * "from Requirement requirement where requirement.orgLocationId.id =:orgLocationId and requirement.clientId.id =:orgRelationId and parentRequirement !='null' and requirement.spocId.createdBy =:loginUserCode"
			 * ; releasedJobsList = session.createQuery(query).setLong( "orgLocationId",
			 * orgLocationId ).setLong( "orgRelationId", orgRelationId ).setString(
			 * "loginUserCode", user.getUserCode() ).list(); }
			 */
		}
		return releasedJobsList;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		String query = "from Requirement requirement where requirement.id = :requirementId and requirement.managerUserId.id =:userId";
		List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId)
				.setLong("userId", userId).list();
		if (requirementList != null && requirementList.size() > 0) {
			flag = true;
			return flag;
		} else {
			return flag;
		}

	}

	/**
	 * prasad K This Method for to get All Template Requirements
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement req left join fetch req.createdBy  left join fetch req.parentRequirement where req.isTemplate = 'Y' and req.orgLocationId.id =:orgLocationId and req.status <>'0'  ORDER BY req.id DESC ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementTemplateByRequirementId(Long requirementId, Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.createdBy left join fetch requirement.parentRequirement  where requirement.id =:requirementId and requirement.isTemplate = 'Y' and requirement.orgLocationId.id =:orgLocationId ";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId)
				.setLong("orgLocationId", orgLocationId).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional
	public Integer getManagerAssignedRequirementsCount(Long userId, Long orgLocationId) throws Exception {
		logger.debug("getManagerAssignedRequirementsCount start");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(requirement.id) from Requirement requirement "
				+ "where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId ";

		List<Long> jobCount = session.createQuery(query.toString()).setLong("userId", userId)
				.setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if (jobCount != null && !jobCount.isEmpty()) {
			count = jobCount.get(0).intValue();
		}
		return count;

	}

	@Override
	@Transactional
	public Integer getRequirementsCountAsApprover(Long userId, Long orgLocationId) throws Exception {
		logger.debug("getRequirementsCountAsApprover start");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(requirement.id) from Requirement requirement "
				+ "where requirement.id IN (select hierarchy.requirementId.id from RequirementApproverHierarchy hierarchy where hierarchy.approverUserId.id = :userId) and requirement.orgLocationId.id = :orgLocationId ";

		List<Long> jobCount = session.createQuery(query.toString()).setLong("userId", userId)
				.setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if (jobCount != null && !jobCount.isEmpty()) {
			count = jobCount.get(0).intValue();
		}
		return count;

	}

	@Override
	@Transactional
	public List<Requirement> getManagerAssignedRequirementsList(Long userId, Long orgLocationId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join f" + "etch requirement.parentRequirement "
				+ "where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId";
		return session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();

	}

	@Override
	@Transactional
	public Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId,
			Long orgLocationId) throws Exception {
		Boolean check = false;
		Session session = sessionFactory.getCurrentSession();
		String subQuery = "requirement.id IN ( select rs.requirement.id from RequirementSubmission rs where  rs.submissionRateStatus = 'REQUEST' or rs.submissionRateStatus = 'ACCEPTED' ) ";
		String query = "from Requirement requirement left join fetch requirement.parentRequirement   where requirement.managerUserId.id = :userId and requirement.orgLocationId.id = :orgLocationId and "
				+ subQuery + " ";
		List requirementList = session.createQuery(query).setLong("userId", userId)
				.setLong("orgLocationId", orgLocationId).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			check = true;
		}
		return check;

	}

	@Override
	@Transactional
	public Requirement getRequirementByRequirementIdOrgLocationsId(String id, Long requirementId, Long orgLocationsId)
			throws Exception {
		logger.debug("In EmployeesDaoImpl class getEmployeeByIdAndUserIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.parentRequirement where requirement.orgLocationId = :orgLocationsId and requirement.jobId = :id and requirement.id = :requirementId ";
		List<Requirement> requirementList = session.createQuery(query).setString("id", id)
				.setLong("requirementId", requirementId).setLong("orgLocationsId", orgLocationsId).list();
		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception {
		logger.debug("In Employee getEmployeeByEmployeeId method");
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = false;
		if (id != null && orgLocationsId != null) {
			String query = "from Requirement requirement where requirement.orgLocationId = :orgLocationsId and requirement.jobId = :id ";
			List<Requirement> requirementList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId)
					.setString("id", id).list();

			if (requirementList != null && requirementList.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.skills from Requirement req where req.id =:requirementId";
		List<String> skillNames = session.createQuery(query).setLong("requirementId", requirementId).list();
		return skillNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.industryName from Requirement req where req.id =:requirementId";
		List<String> industryNames = session.createQuery(query).setLong("requirementId", requirementId).list();
		return industryNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getRequirementListByOrgLocationID(Long orgLocationId) {
		logger.debug("..getCandidatesCount() Method Started..");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "select count(*) from Requirement requirement  where requirement.orgLocationId.id =:orgLocationId and requirement.status = '1'";
		List<Long> results = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		logger.debug("..getCandidatesCount() Method Ended..");
		return results.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.orgLocationId loc left join fetch loc.org left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.requirementUUID =:requirementUUID";
		List requirementList = session.createQuery(query).setString("requirementUUID", requirementUUID).list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.orgLocationId.id = :orgLocationId";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.parentRequirement.id = :parentRequirementId";
		return session.createQuery(query).setLong("parentRequirementId", parentRequirementId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String getNoticePeriodByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.noticePeriod from Requirement req where req.id =:requirementId";
		List<String> noticePeriodList = session.createQuery(query).setLong("requirementId", requirementId).list();
		return noticePeriodList.get(0);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId, User user)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String requirementQuery = "from Requirement req where  ( (req.managerUserId.id =:userId)  or (req.createdBy =:userCode) or (req.id IN (select  reqAssignTo.requirementId.id  from RequirementAssignTo reqAssignTo  where  reqAssignTo.assignUserId.id =:userId ) ) or (req.id IN (select  reqApproverHirerarchy.requirementId.id  from RequirementApproverHierarchy reqApproverHirerarchy  where  reqApproverHirerarchy.approverUserId.id =:userId ) ) ) and req.orgLocationId.id =:orgLocationId  ";
		List<Requirement> requirementList = session.createQuery(requirementQuery.toString())
				.setLong("userId", user.getId()).setLong("orgLocationId", orgLocationId)
				.setString("userCode", user.getUserCode())

				.list();

		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId, User user)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String requirementQuery = "from Requirement req where  (req.id IN (select  reqAssignTo.requirementId.id  from RequirementAssignTo reqAssignTo  where  reqAssignTo.teamId.id IN ( select tm.id from Team  tm where tm.ownerUserId.id =:userId and tm.orgLocationsId.id =:orgLocationsId )) ) ";
		List<Requirement> requirementList = session.createQuery(requirementQuery.toString())
				.setLong("userId", user.getId()).setLong("orgLocationId", orgLocationId)

				.list();

		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		java.sql.Date sqlCurrentDate = null;
		Date currentDate = commonUtil.currentDate();
		// for current Date Converting to Sql Date Format
		if (currentDate != null) {
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
			java.sql.Timestamp sq = new java.sql.Timestamp(currentDate.getTime());
		}

		String requirementQuery = "select count(*)  from Requirement req where   req.spocId.clientLocationId.id =:orgLocationId and  req.spocId.clientUser.id =:userId  and DATE(req.createdDate) =:sqlCurrentDate GROUP BY req.id";
		List<Long> requirementList = session.createQuery(requirementQuery.toString()).setLong("userId", user.getId())
				.setDate("sqlCurrentDate", sqlCurrentDate).setLong("orgLocationId", orgLocationId)

				.list();
		Integer count = 0;
		if (requirementList != null && !requirementList.isEmpty()) {
			count = requirementList.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(
			Long orgLocationId, TeamMembers teamMembers) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.parentRequirement where   requirement.orgLocationId.id =:orgLocationId and  requirement.id  IN (select reqassign.requirementId.id  from RequirementAssignTo reqassign where reqassign.assignUserId.id =:userId  and reqassign.teamId.id =:teamId)";

		List<Requirement> requirementList = session.createQuery(query)
				.setLong("userId", teamMembers.getMemberUserId().getId())
				.setLong("teamId", teamMembers.getTeamId().getId()).setLong("orgLocationId", orgLocationId).list();
		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BigInteger getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC,
			String endDateUTC,String isGlobalUser,Long orgId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		BigInteger count = null;
		try {
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			query ="SELECT"
					+ " COUNT(REQ.ID) AS REQ_COUNT"
					+ " FROM"
					    + " REQUIREMENT REQ,"
					    + " ORG_LOCATIONS OL"
					    
						+ " WHERE  OL.ID = REQ.ORG_LOCATION_ID AND "
					    + " REQ.CREATED_BY = :userId"
					    + " AND DATE_FORMAT(REQ.CREATED_DATE ,'%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					    + " AND  OL.ORG_ID = :orgId"
					    + " GROUP BY REQ.CREATED_BY, OL.ORG_ID, REQ.ORG_LOCATION_ID";
				 
			        
					Query queryString = session.createSQLQuery(query);
					queryString.setParameter("userId", user.getId()).setParameter("orgId", orgId);
					count  = (BigInteger) queryString.uniqueResult();
		}else {
			query ="SELECT"
					+ " COUNT(REQ.ID) AS REQ_COUNT"
					+ " FROM"
					    + " REQUIREMENT REQ "
						+ " WHERE"
					    + " REQ.CREATED_BY = :userId"
					    + " AND DATE_FORMAT(REQ.CREATED_DATE ,'%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					    + " AND REQ.ORG_LOCATION_ID = :orgLocationId"
					    + " GROUP BY REQ.CREATED_BY, REQ.ORG_LOCATION_ID";
			

					Query queryString = session.createSQLQuery(query);
					queryString.setParameter("userId", user.getId()).setParameter("orgLocationId", orgLocationId);
					count  = (BigInteger) queryString.uniqueResult();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC,
			Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.parentRequirement where   requirement.clientId.id =:relationId and  requirement.spocId.id =:spocId and ( requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC )";
		List<Requirement> requirementList = session.createQuery(query).setLong("relationId", relationId)
				.setLong("spocId", spocId).setParameter("startDateUTC", startDateUTC)
				.setParameter("endDateUTC", endDateUTC).list();
		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		if (sowType.equalsIgnoreCase("Client SOW")) {
			subQuery = "and requirement.isClientJob = 'Y' and requirement.clientId <> null ";
		} else if (sowType.equalsIgnoreCase("Vendor SOW")) {
			subQuery = " ";
		}

		String query = "from Requirement requirement left join fetch requirement.parentRequirement where (requirement.isTemplate = null or requirement.isTemplate = 'N') and requirement.orgLocationId.id =:orgLocationId "
				+ subQuery + " ORDER BY requirement.id DESC";

		List<Requirement> requirementList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return requirementList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementEducationByRequirementId(Long requirementId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.education from Requirement req where req.id =:requirementId";
		List<String> educationList = session.createQuery(query).setLong("requirementId", requirementId).list();
		return educationList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementDetailsById(long requirementId) {

		Session session = this.sessionFactory.getCurrentSession();
		Requirement requirement = null;
		List<Requirement> requirementList = session.createQuery("from Requirement req where req.id =:requirementId")
				.setLong("requirementId", requirementId).list();
		if (null != requirementList && requirementList.size() > 0) {
			requirement = requirementList.get(0);
		}
		return requirement;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId,
			String userType, Long userId, String userCode, String numberOfDays) throws Exception {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date todayDate = c.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String startTimeTS = Constants.TZ_DF.format(todayDate);
		String startDateString = CommonUtil.convertTimeUTC(Constants.UTC_TIMEZONE, startTimeTS);
		Date startUTCDate = dateFormat.parse(startDateString);
		
/*		//@Mahaveer Manoj commented above code for testing jobFairs, uncomment this in commit
		Date startUTCDate = todayDate;*/

		int hrs;
		switch (numberOfDays) {
		case "1":
			hrs = (1 * 24);
			break;
		case "2":
			hrs = (2 * 24);
			break;
		case "7":
			hrs = (7 * 24);
			break;
		case "14":
			hrs = (15 * 24);
			break;
		case "30":
			hrs = (30 * 24);
			break;
		case "60":
			hrs = (60 * 24);
			break;
		default:
			hrs = (1 * 24);
		}

		Date nHrsUTCDate = DateUtils.addHours(startUTCDate, -hrs);

		/*
		 * Date currentDate = commonUtil.currentDate(); java.sql.Date sqlCurrentDate =
		 * null;
		 * 
		 * if(currentDate != null){ sqlCurrentDate = new
		 * java.sql.Date(currentDate.getTime()); }
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * Calendar c = Calendar.getInstance(); c.setTime(new Date()); // Now use today
		 * date. c.add(Calendar.DATE, -Integer.parseInt(numberOfDays)); // Adding The
		 * DateRange days String updatedDate = dateFormat.format(c.getTime()); Date
		 * parsed = dateFormat.parse(updatedDate); java.sql.Date endDate = new
		 * java.sql.Date(parsed.getTime());
		 */

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		Map<String, Object> params = new HashMap<String, Object>();

		/* String subQueryRole = ""; */
		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery = "";
		// String dateBetween = "(requirement.createdDate BETWEEN :sqlCurrentDate AND
		// :endDate) and ";

		String dateBetween = "DATE(requirement.createdDate) >=:nHrsUTCDate and DATE(requirement.createdDate) <= :startUTCDate and ";

		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId", userId);

			String subQuery2 = "(requirement.managerUserId.id = :managerUserId or requirement.primaryRecruiterUserId.id = :managerUserId) and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);
			
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", userId);
			
			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "") {
				subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1
						+ ") or (" + subQuery2 + ")  or (" + subQuery3 + ") ) ";
				params.put("orgLocationId", orgLocationId);
				params.put("startUTCDate", startUTCDate);
				params.put("nHrsUTCDate", nHrsUTCDate);
			}
		} else {
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";

			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "") {
				subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1
						+ ") or (" + subQuery2 + ")) ";
				params.put("orgLocationId", orgLocationId);
				params.put("startUTCDate", startUTCDate);
				params.put("nHrsUTCDate", nHrsUTCDate);
			}
		}

		String whereCondition = subQuery;
		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
				query.setLong(str, (long) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getRequirementListByPostedDateAndSelectedClients(Long orgLocationId, Long userId,
			int numberOfDays, String selectedClients) {
		try {

			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date todayDate = c.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			
			String startTimeTS = Constants.TZ_DF.format(todayDate);
			String startDateString = CommonUtil.convertTimeUTC(Constants.UTC_TIMEZONE, startTimeTS);
			Date startUTCDate = dateFormat.parse(startDateString);
			 

/*			// @Mahaveer Manoj commented above code for testing jobFairs, uncomment this in commit
			Date startUTCDate = todayDate;*/

			int hrs = numberOfDays * 24;

			Date nHrsUTCDate = null;
			if (hrs > 0) {
				nHrsUTCDate = DateUtils.addHours(startUTCDate, -hrs);
			}

			Session session = this.sessionFactory.getCurrentSession();
			// User user = userDao.getUserById(userId);

			Map<String, Object> params = new HashMap<String, Object>();

			String from = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId ";
			String subQuery = "";
			String dateBetween = "";
			if (hrs > 0) {
				dateBetween = "DATE(requirement.createdDate) >=:nHrsUTCDate and DATE(requirement.createdDate) <= :startUTCDate and ";
			}

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";

			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "") {
				subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId and ( (" + subQuery1
						+ ") or (" + subQuery2 + ")) ";
			}

			if (selectedClients != null && !selectedClients.isEmpty()) {
				subQuery += (subQuery.contains("where") ? " and " : " where ")
						+ "(requirement.clientId.locationId.org.orgName in (" + selectedClients
						+ ") or requirement.clientId.responderLocationId.org.orgName in (" + selectedClients + "))";
			}

			params.put("orgLocationId", orgLocationId);
			params.put("startUTCDate", startUTCDate);
			if (hrs > 0) {
				params.put("nHrsUTCDate", nHrsUTCDate);
			}

			String whereCondition = subQuery;
			Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
			for (String str : query.getNamedParameters()) {
				if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
					query.setLong(str, (long) params.get(str));
				} else {
					query.setParameter(str, params.get(str));
				}

			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId,
			Date startDateUTC, Date endDateUTC) {
		logger.debug("getAllReleasedJobsForVendor start");
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if (orgRelationId != null && orgLocationId != null) {
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and (date(requirement.createdDate) BETWEEN  :startDateUTC and :endDateUTC)  and requirement.parentRequirement IS NOT NULL group by requirement.clientId.id, requirement.parentRequirement.id";
			releasedJobsList = session.createQuery(query).setLong("orgRelationId", orgRelationId)
					.setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}
		return releasedJobsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if (user != null && orgLocations != null) {
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.orgLocationId =:orgLocationId and :userId IN (requirement.releasedBy)";
			releasedJobsList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId())
					.setLong("userId", user.getId()).list();
		}
		return releasedJobsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName,
			User user, String status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId";
		String subQuery = "";
		if (userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName)
				|| "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
			subQuery = " where requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("locationId", locationId);
			params.put("relationId", relationId);
		} else {
			String subQuery2 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("userId", user.getId());
			params.put("level", 1);
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ) and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y'";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery4 = "requirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("managerUserId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery5 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED' and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y' ";
			params.put("userId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			String subQuery6 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  and requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and requirement.isClientJob = 'Y'";
			params.put("userId", user.getId());
			params.put("locationId", locationId);
			params.put("relationId", relationId);
			if (subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 != "") {
				subQuery = "where requirement.orgLocationId.id =:locationId and requirement.clientId.id =:relationId and (("
						+ subQuery2 + ") OR (" + subQuery3 + ") OR (" + subQuery4 + ") OR (" + subQuery5 + ") OR ("
						+ subQuery6 + ") )";
				params.put("locationId", locationId);
				params.put("relationId", relationId);
			}
		}
		String[] requirementStatus = status.split(",");
		String subQuery8 = "";
		if (Arrays.asList(requirementStatus).contains("NEW")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'NEW' or ";
		}
		if (Arrays.asList(requirementStatus).contains("OPEN")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'OPEN' or ";
		}
		if (Arrays.asList(requirementStatus).contains("SUBMITTED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'SUBMITTED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("FILLEDCLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'FILLEDCLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("UNFILLEDCLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'UNFILLEDCLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("ONHOLD")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'ONHOLD' or ";
		}
		if (Arrays.asList(requirementStatus).contains("CLOSED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'CLOSED' or ";
		}
		if (Arrays.asList(requirementStatus).contains("CANCEL")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'CANCEL' or ";
		}
		if (Arrays.asList(requirementStatus).contains("REJECTED")) {
			subQuery8 = subQuery8 + "requirement.requirementSubmissionStatus = 'REJECTED' or ";
		}
		if ((requirementStatus[0] != null && requirementStatus[0].length() > 0)) {
			subQuery8 = "and (" + subQuery8.substring(0, subQuery8.length() - 4) + ")";
		}
		/*
		 * String subQuery7 = ""; if(null !=status && !"".equalsIgnoreCase(status)){
		 * subQuery7 =
		 * "and (requirement.requirementSubmissionStatus ='NEW' or requirement.requirementSubmissionStatus = 'SUBMITTED' or requirement.requirementSubmissionStatus = 'DRAFT' or requirement.requirementSubmissionStatus = 'OPEN')"
		 * ; }
		 */
		Query query = null;
		query = session.createQuery(from + " " + subQuery + " " + subQuery8 + "  ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("relationId") || str.equalsIgnoreCase("locationId")
					|| str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")
					|| str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}
		}
		releasedJobsList = query.list();
		return releasedJobsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds,
			Long relationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if (parentreqIds != null && !parentreqIds.isEmpty() && relationId != null) {
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.parentRequirement.id IN (:parentreqIds) and requirement.clientId.id =:relationId";
			releasedJobsList = session.createQuery(query).setParameterList("parentreqIds", parentreqIds)
					.setLong("relationId", relationId).list();
		}
		return releasedJobsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement where ( requirement.closedDate >= :startDateUTC and requirement.closedDate <= :endDateUTC ) ";
		return session.createQuery(query).setParameter("startDateUTC", startDateUTC)
				.setParameter("endDateUTC", endDateUTC).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId, Long orgRelationId, String userRoleName,
			User user, String status) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String[] reqStatus = null;
		if (null != status && !"".equals(status)) {
			reqStatus = status.split(",");
		}
		List<REQUIREMENT_SUBMISSION_STATUS> statusList = new ArrayList<>();
		if (reqStatus != null) {
			for (int i = 0; i < reqStatus.length; i++) {
				statusList.add(REQUIREMENT_SUBMISSION_STATUS.getNameByValue(reqStatus[i]));
			}
		}
		String from = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId";
		String subQuery = "";
		if (userRoleName != null && "ROLE_ADMIN".equalsIgnoreCase(userRoleName)
				|| "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRoleName)) {
			subQuery = " where requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
		} else {
			String subQuery2 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:userId and rah.level = :level and  requirement.requirementSubmissionStatus <> 'DRAFT'  ) or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("level", 1);
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery3 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementApproverHierarchy rah where rah.approverUserId.id =:approverUserId and rah.level = :level2  and  requirement.requirementSubmissionStatus <> 'DRAFT' and ( requirement.parentRequirement.id IN (select rah1.requirementId from RequirementApproverHierarchy rah1 where rah1.level =:level1 and rah1.requirementApproverStatus = 'APPROVED'))  or (requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'OPEN' ) ) ) and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("approverUserId", user.getId());
			params.put("userId", user.getId());
			params.put("level1", 1);
			params.put("level2", 2);
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery4 = "requirement.parentRequirement.managerUserId.id = :managerUserId and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("managerUserId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery5 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			String subQuery6 = "requirement.parentRequirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)  and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.clientId.id =:orgRelationId and requirement.parentRequirement IS NOT NULL and requirement.isClientJob ='Y'";
			params.put("userId", user.getId());
			params.put("orgRelationId", orgRelationId);
			params.put("locationId", locationId);
			if (subQuery2 != "" || subQuery3 != "" || subQuery4 != "" || subQuery5 != "" || subQuery6 != "") {
				subQuery = "where  ((" + subQuery2 + ") OR (" + subQuery3 + ")  OR (" + subQuery4 + ") OR (" + subQuery5
						+ ") OR (" + subQuery6 + "))";
				params.put("orgRelationId", orgRelationId);
				params.put("locationId", locationId);
			}
		}
		Query query = null;
		if (null != statusList && !statusList.isEmpty()) {
			query = session.createQuery(from + " " + subQuery
					+ " and requirement.parentRequirement.requirementSubmissionStatus IN (:statusList) ORDER BY requirement.id DESC , requirement.parentRequirement.id");
			params.put("statusList", statusList);
		} else {
			query = session.createQuery(from + " " + subQuery + "  ORDER BY requirement.id DESC");
		}
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgRelationId") || str.equalsIgnoreCase("locationId")
					|| str.equalsIgnoreCase("userId") || str.equalsIgnoreCase("approverUserId")
					|| str.equalsIgnoreCase("managerUserId")) {
				query.setLong(str, (long) params.get(str));
			}
			if (str.equalsIgnoreCase("level") || str.equalsIgnoreCase("level1") || str.equalsIgnoreCase("level2")) {
				query.setInteger(str, (int) params.get(str));
			}
			if (!statusList.isEmpty()) {
				query.setParameterList("statusList", statusList);
			} else {
				query.setParameter(str, params.get(str));
			}
		}
		releasedJobsList = query.list();
		return releasedJobsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getAllRequirementVisaByRequirementI(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req.visaType from Requirement req where req.id =:requirementId";
		List<String> skillNames = session.createQuery(query).setLong("requirementId", requirementId).list();
		return skillNames;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Set<RequirementAssignTo> getRequirementAssignedToTeamByRequirementId(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementAssignTo reqAssignedTo left join fetch reqAssignedTo.requirementId  where reqAssignedTo.requirementId =:requirementId";
		List<RequirementAssignTo> reqAssignedTo = session.createQuery(query).setLong("requirementId", id).list();
		Set<RequirementAssignTo> requirementAssignedTo = new HashSet<RequirementAssignTo>(reqAssignedTo);
		return requirementAssignedTo;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement checkRequirementIdNumberInDatebase(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.id =(SELECT MAX(req.id) FROM Requirement req where req.orgLocationId.id =:orgLocationId)";
		List<Requirement> requirement = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if (requirement.isEmpty()) {
			return null;
		}
		return requirement.iterator().next();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getRequirementGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdCount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount + "') AS UNSIGNED)) FROM "
				+ schema + ".REQUIREMENT WHERE ORG_LOCATION_ID = '" + orgLocationId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getJobFairGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdCount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount + "') AS UNSIGNED)) FROM "
				+ schema + ".JOB_FAIR WHERE ORG_LOCATION_ID = '" + orgLocationId + "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean checkForOldrecordsUsingOrgLocationid(Long orglocationId, String tableName) {
		Session session = this.sessionFactory.getCurrentSession();
		Boolean data = true;
		if (StringUtils.isNotEmpty(tableName)) {
			String query = "select table.generatedId from " + tableName
					+ " table where table.orgLocationId.id =:orglocationId";
			List<String> requirement = session.createQuery(query).setLong("orglocationId", orglocationId).list();
			data = requirement.contains(null);
		} else {
			LinkedList<String> tableNames = new LinkedList<String>();
			tableNames.add("Requirement");
			tableNames.add("Projects");
			tableNames.add("Candidate");
			l1: {
				for (String tabName : tableNames) {
					if (tabName != "Projects") {
						String query = "select table.generatedId from " + tabName
								+ " table where table.orgLocationId.id =:orglocationId";
						List<String> result = session.createQuery(query).setLong("orglocationId", orglocationId).list();
						data = result.contains(null);
					} else {
						String query = "select table.generatedId from " + tabName
								+ " table where table.orgLocationsId.id =:orglocationId";
						List<String> result = session.createQuery(query).setLong("orglocationId", orglocationId).list();
						data = result.contains(null);
					}

					if (!data) {
						break l1;
					}
				}
			}
		}

		return data;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Requirement> addGeneratedIdRecordsToJobsForOldRecords(Long orglocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.orgLocationId orgLocation where orgLocation.id =:orglocationId";
		List<Requirement> requirement = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		if (!requirement.isEmpty()) {
			return requirement;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Projects> addGeneratedIdRecordsToProjectsForOldRecords(Long orglocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select projects from Projects projects left join fetch projects.orgLocationsId orgLocation where orgLocation.id =:orglocationId";
		List<Projects> projects = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		if (!projects.isEmpty()) {
			return projects;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Candidate> addGeneratedIdRecordsToCandidatesForOldRecords(Long orglocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select candidate from Candidate candidate left join fetch candidate.orgLocationId orgLocation where orgLocation.id =:orglocationId";
		List<Candidate> candidate = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		if (!candidate.isEmpty()) {
			return candidate;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Requirement> getAllRequirementsByOrlocationIdAndOpenSubmissionStatus(Long orglocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.orgLocationId orgLocation where orgLocation.id =:orglocationId and requirement.requirementSubmissionStatus = 'OPEN' ";
		List<Requirement> requirement = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		if (!requirement.isEmpty()) {
			return requirement;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllAssinedRequirementsOrgLocationIdAndUserId(Long orglocationId, Long userId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.orgLocationId orgLocation where orgLocation.id = :orglocationId "
				+ "and requirement.id IN (select rat.requirementId.id from RequirementAssignTo rat where rat.assignUserId.id = :userId)";
		List<Requirement> requirement = session.createQuery(query).setLong("orglocationId", orglocationId)
				.setLong("userId", userId).list();
		if (!requirement.isEmpty()) {
			return requirement;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<RequirementDTO> getAllRequirementsForJobCampaing(RequirementDTO requirementDTO,
			Long orglocationId, Long userId, String dateFormat) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> requirementList = null;

		Query queryObj = null;
		Integer filterCount = 0;
		StringBuilder query1 = new StringBuilder("");
		ResponseList<RequirementDTO> response = new ResponseList<>();
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		List<Long> countlist = null;

		if (requirementDTO.getSearchValue() != null && !"".equalsIgnoreCase(requirementDTO.getSearchValue())) {
			query1.append(" requirement.jobId like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.jobTitle like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.jobType like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.rate like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.rate like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.postedOrgName like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.priority like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.createdDate like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.location like '%" + requirementDTO.getSearchValue() + "%' ");

		}

		if (requirementDTO.getJobCampaignType().equalsIgnoreCase("openJobs")) {
			String query = "from Requirement requirement  where requirement.orgLocationId.id =:orglocationId and (requirement.requirementSubmissionStatus = 'OPEN' or requirement.requirementSubmissionStatus = 'NEW')";

			if (query1.toString() != null && !query1.toString().isEmpty()) {
				queryObj = session.createQuery(query + "and " + "(" + query1.toString() + ")");
			} else {
				queryObj = session.createQuery(query + query1.toString());
			}

			queryObj.setFirstResult(requirementDTO.getStart());
			queryObj.setMaxResults(requirementDTO.getLength());

			queryObj.setLong("orglocationId", orglocationId).list();
			requirementList = queryObj.list();

			countlist = session.createQuery("select count(*) " + query).setLong("orglocationId", orglocationId).list();
			if (query1.toString() != null && !query1.toString().isEmpty()) {
				filterCount = requirementList.size();
			}

		} else {

			String query = "from Requirement requirement where requirement.orgLocationId.id = :orglocationId "
					+ "and requirement.id IN (select rat.requirementId.id from RequirementAssignTo rat where rat.assignUserId.id = :userId)";
			List<Requirement> requirement = session.createQuery(query).setLong("orglocationId", orglocationId)
					.setLong("userId", userId).list();

			if (query1.toString() != null && !query1.toString().isEmpty()) {
				queryObj = session.createQuery(query + "and " + "(" + query1.toString() + ")");
			} else {
				queryObj = session.createQuery(query + query1.toString());
			}

			queryObj.setFirstResult(requirementDTO.getStart());
			queryObj.setMaxResults(requirementDTO.getLength());

			queryObj.setLong("orglocationId", orglocationId).setLong("userId", userId).list();
			requirementList = queryObj.list();

			countlist = session.createQuery("select count(*) " + query).setLong("orglocationId", orglocationId)
					.setLong("userId", userId).list();
			if (query1.toString() != null && !query1.toString().isEmpty()) {
				filterCount = requirementList.size();
			}

		}

		int totalCount = 0;

		if (requirementDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		response.setRecordsTotal(totalCount);

		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(filterCount);
		} else {
			response.setRecordsFiltered(totalCount);
		}

		for (Requirement req : requirementList) {

			RequirementDTO reqDTO = new RequirementDTO();
			List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList = requirementLocationsService
					.getRequirementLocationsListByRequirementId(req.getId());
			if (locationsList != null && !locationsList.isEmpty()) {
				for (RequirementLocations location : locationsList) {
					requirementLocationsList.add(location.getLocation());
				}
			}
			reqDTO.setRequirementId(req.getId().toString());
			reqDTO.setJobId(req.getJobId());
			reqDTO.setJobTitle(req.getJobTitle());
			reqDTO.setJobType(req.getJobType());
			reqDTO.setRate(req.getRate());
			reqDTO.setLocationsList(requirementLocationsList);
			// reqDTO.setLocation(req.getLocation());
			reqDTO.setPriority(req.getPriority());
			if (StringUtils.isNotBlank(dateFormat)) {
				reqDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, req.getCreatedDate()));
			} else {
				reqDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter("dd-MM-yyyy", req.getCreatedDate()));
			}

			requirementDTOList.add(reqDTO);
		}

		response.setData(requirementDTOList);
		return response;
	}

	@Override
	@Transactional
	public List<RequirementDTO> getAllCampaignRequirementsByIdsList(List<Long> requirementIds) throws Exception {
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.id IN (:requirementIds) )";
		List<Requirement> requirementList = session.createQuery(query)
				.setParameterList("requirementIds", requirementIds).list();

		if (requirementList != null && requirementList.size() > 0) {
			for (Requirement requirement : requirementList) {
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setJobTitle(requirement.getJobTitle());
				requirementDTO.setLocation(requirement.getLocation());
				requirementDTO.setJobType(requirement.getJobType());
				requirementDTO.setSkills(requirement.getSkills());
				requirementDTO.setRequirementUUID(requirement.getRequirementUUID());
				requirementDTOList.add(requirementDTO);
			}
		}

		return requirementDTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByShortURL(String shortURL) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select r from Requirement r where r.requirementUUID =:shortURL";
		List<Requirement> requirements = session.createQuery(query).setString("shortURL", shortURL).list();
		if (!requirements.isEmpty()) {
			return requirements.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementIdAndJobTitle(Long requirementId, String url) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from Requirement requirement left join fetch requirement.createdBy left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch requirement.orgLocationId  where requirement.id =:requirementId and requirement.url =:url";
		List requirementList = session.createQuery(query).setLong("requirementId", requirementId).setString("url", url)
				.list();

		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(
			Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays, Long managerId)
			throws Exception {

		Date currentDate = commonUtil.currentDate();
		java.sql.Date sqlCurrentDate = null;
		if (currentDate != null) {
			sqlCurrentDate = new java.sql.Date(currentDate.getTime());
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, -Integer.parseInt(numberOfDays)); // Adding The DateRange days
		String updatedDate = dateFormat.format(c.getTime());
		Date parsed = dateFormat.parse(updatedDate);
		java.sql.Date endDate = new java.sql.Date(parsed.getTime());

		Session session = this.sessionFactory.getCurrentSession();
		User user = userDao.getUserById(userId);

		Map<String, Object> params = new HashMap<String, Object>();

		/* String subQueryRole = ""; */
		String from = "from Requirement requirement left join fetch requirement.parentRequirement ";
		String subQuery = "";
		// String dateBetween = "(requirement.createdDate BETWEEN :sqlCurrentDate AND
		// :endDate) and ";
		String dateBetween = "requirement.createdDate >=:endDate and requirement.createdDate <= :sqlCurrentDate and ";

		String managerQury = "and requirement.managerUserId.id = :managerId";
		params.put("managerId", managerId);

		if (!StringUtils.equalsIgnoreCase(userType, "ADMIN")) {

			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.assignUserId.id =:userId)   and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT'  ";
			params.put("userId", userId);

			String subQuery2 = "requirement.managerUserId.id = :managerUserId and ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			String subQuery3 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where rah.teamId.id IN ( select rah.id from Team rah where rah.ownerUserId.id =:userId)) and requirement.requirementSubmissionStatus <> 'DRAFT' and requirement.requirementSubmissionStatus <> 'SUBMITTED'  ";
			params.put("userId", userId);

			if (subQuery1 != "" || subQuery2 != "" || subQuery3 != "") {
				if (managerId != null) {
					subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId " + managerQury
							+ " and ( (" + subQuery1 + ") or (" + subQuery2 + ")  or (" + subQuery3 + ") ) ";
					params.put("orgLocationId", orgLocationId);
					params.put("sqlCurrentDate", sqlCurrentDate);
					params.put("endDate", endDate);
					params.put("managerId", managerId);
				} else {
					subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId and ( ("
							+ subQuery1 + ") or (" + subQuery2 + ")  or (" + subQuery3 + ") ) ";
					params.put("orgLocationId", orgLocationId);
					params.put("sqlCurrentDate", sqlCurrentDate);
					params.put("endDate", endDate);
				}
			}
		} else {
			String subQuery1 = "requirement.id IN ( select rah.requirementId from RequirementAssignTo rah where  ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  and  requirement.requirementSubmissionStatus <> 'DRAFT') ";

			String subQuery2 = " ( requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' )  ";
			params.put("managerUserId", userId);

			if (subQuery1 != "" || subQuery2 != "") {
				if (managerId != null) {
					subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId " + managerQury
							+ " and ( (" + subQuery1 + ") or (" + subQuery2 + ")) ";
					params.put("orgLocationId", orgLocationId);
					params.put("sqlCurrentDate", sqlCurrentDate);
					params.put("endDate", endDate);
					params.put("managerId", managerId);
				} else {
					subQuery = "where " + dateBetween + " requirement.orgLocationId.id =:orgLocationId and ( ("
							+ subQuery1 + ") or (" + subQuery2 + ")) ";
					params.put("orgLocationId", orgLocationId);
					params.put("sqlCurrentDate", sqlCurrentDate);
					params.put("endDate", endDate);
				}

			}
		}

		String whereCondition = subQuery;
		Query query = session.createQuery(from + " " + whereCondition + " ORDER BY requirement.id DESC");
		for (String str : query.getNamedParameters()) {
			if (str.equalsIgnoreCase("orgLocationId") || str.equalsIgnoreCase("userId")) {
				query.setLong(str, (long) params.get(str));
			} else {
				query.setParameter(str, params.get(str));
			}

		}
		return query.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByAccessibleUserIdAndRequirementUUID(Long userId, String requirementUUID)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select req from Requirement req where req.requirementUUID =:requirementUUID and (req.releaseJobToAllUsers = 'Y' OR req.managerUserId.id = :userId OR req.primaryRecruiterUserId.id = :userId OR req.createdBy.id = :userId or req.id IN(select rat.requirementId.id from RequirementAssignTo rat where rat.assignUserId.id = :userId and rat.requirementId.requirementUUID =:requirementUUID and rat.status <> '0') or req.id IN (select rah.requirementId.id from RequirementApproverHierarchy rah where rah.approverUserId.id = :userId and rah.requirementId.requirementUUID =:requirementUUID))";
		List requirementList = session.createQuery(query).setLong("userId", userId)
				.setString("requirementUUID", requirementUUID).list();
		Requirement requirement = null;
		if (requirementList != null && !requirementList.isEmpty()) {
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<RequirementDTO> getRequirementList(Long id, String search, Long location, String employmentType,
			int pageNo, int pageSize) {

		ResponseList<RequirementDTO> response = new ResponseList<>();

		try {

			Session session = this.sessionFactory.getCurrentSession();

			String query = "select distinct req from Requirement req join req.orgLocationId l where l.org.id=:id and req.closedDate is null and (req.isPublic is not null or req.isCrowdSourcing is not null)";

			if (location != null && location > 0) {

				query += " and l.id =:location";

			}

			if (search != null && !search.isEmpty()) {

				query += " and (req.jobTitle like :search1 or req.skills like :search2 or req.description like :search3)";

			}

			if (employmentType != null && !employmentType.isEmpty()) {

				query += " and req.jobType =:jobType";

			}

			Query queryObj = session.createQuery(query);

			queryObj.setLong("id", id);

			if (location != null && location > 0) {

				queryObj.setLong("location", location);

			}

			if (search != null && !search.isEmpty()) {

				queryObj.setString("search1", "%" + search + "%").setString("search2", "%" + search + "%")
						.setString("search3", "%" + search + "%");

			}

			if (employmentType != null && !employmentType.isEmpty()) {

				queryObj.setString("jobType", employmentType);

			}

			queryObj.setFirstResult(pageNo * pageSize);

			queryObj.setMaxResults(pageSize);

			List<Requirement> requirementList = queryObj.list();

			List<RequirementDTO> reqList = new ArrayList<>();

			SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_DD_MM_YYYY);

			if (requirementList != null && !requirementList.isEmpty()) {

				for (Requirement req : requirementList) {

					List<RequirementLocations> reqLocationsList = requirementLocationsService
							.getRequirementLocationsListByRequirementId(req.getId());

					RequirementDTO dto = new RequirementDTO();

					for (RequirementLocations rloc : reqLocationsList) {

						dto.setCity(rloc.getCity());

						dto.setState(rloc.getState());

						dto.setCountry(rloc.getCountry());

					}

					dto.setId(req.getId());

					dto.setJobTitle(req.getJobTitle());

					dto.setMinExp(req.getMinExp());

					dto.setMaxExp(req.getMaxExp());

					dto.setCreatedDate(format.format(req.getCreatedDate()));

					// dto.setCity(req.getCity());

					// dto.setState(req.getState());

					dto.setDescription(req.getDescription());

					// dto.setCountry(req.getCountry());

					dto.setPositions(req.getPositions());

					dto.setUrl(req.getUrl());

					dto.setRequirementUUID(req.getRequirementUUID());

					reqList.add(dto);

				}

			}

			int total = 0;

			Query queryObj1 = session.createQuery(query.replace("distinct req", "count(distinct req)"));

			queryObj1.setLong("id", id);

			if (location != null && location > 0) {

				queryObj1.setLong("location", location);

			}

			if (search != null && !search.isEmpty()) {

				queryObj1.setString("search1", "%" + search + "%").setString("search2", "%" + search + "%")
						.setString("search3", "%" + search + "%");

			}

			if (employmentType != null && !employmentType.isEmpty()) {

				queryObj1.setString("jobType", employmentType);

			}

			total = Integer.parseInt(queryObj1.list().get(0).toString());

			response.setData(reqList);

			response.setStatusCode("200");

			response.setRecordsTotal(total);

		} catch (Exception ex) {

			ex.printStackTrace();

			response.setError("Operation failed, please try again");

		}

		return response;

	}

	@Override
	@Transactional
	public List<RequirementDTO> getAllManagerRequirementForJobSharing(Date[] searchDates, Long managerUserId,
			Long orgLocationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Requirement> requirementList = null;
		List<RequirementDTO> requirementDTOList = new ArrayList<>();

		query = "from Requirement requirement where  (requirement.requirementSubmissionStatus = 'APPROVED' or requirement.requirementSubmissionStatus = 'NEW' or requirement.requirementSubmissionStatus = 'OPEN' ) "
				+ " and requirement.managerUserId.id = :managerId and requirement.orgLocationId.id = :orgLocationId and date(requirement.createdDate) BETWEEN :startDate and :endDate";

		requirementList = session.createQuery(query.toString()).setLong("managerId", managerUserId)
				.setLong("orgLocationId", orgLocationId).setParameter("startDate", searchDates[0])
				.setParameter("endDate", searchDates[1]).list();

		if (requirementList != null && !requirementList.isEmpty()) {
			for (Requirement requirement : requirementList) {
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setId(requirement.getId());
				requirementDTO.setGeneratedId(requirement.getGeneratedId());
				requirementDTO.setJobTitle(requirement.getJobTitle());
				requirementDTO.setPostedDateStr(requirement.getPostedOnStr());
				requirementDTOList.add(requirementDTO);
			}
		}
		return requirementDTOList;
	}

	@Override
	@Transactional
	public Requirement getRequirementByGeneratedIdAndLocationId(String generatedId, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement where requirement.generatedId =:generatedId and requirement.orgLocationId = :orgLocationId";
		Requirement requirement = (Requirement)session.createQuery(query).setString("generatedId", generatedId)
				.setLong("orgLocationId", orgLocationId).uniqueResult();

		return requirement;
	}

	@Override
	@Transactional
	public List<RecruiterReportAssociateCountView> getAssociateCountsCreatedUserList(Long orgLocationId,
			List<Long> userIdsList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<RecruiterReportAssociateCountView> associatesViewList = null;
		try {
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			
			 query ="SELECT "
						+" USER_ID AS associateUserId,SUM(REQ_COUNT) AS jobCount"
						+" FROM"
						+" RECRUITER_REPORT_ASSOCIATE_COUNT_VIEW"
						+" WHERE"
						+" ORG_ID = :orgId AND USER_ID IN (:userIdsList)"
						+" AND CREATED_DATE BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
						+" GROUP BY USER_ID";
			        
			
	            Query queryObj = session.createSQLQuery(query);
				queryObj.setParameterList("userIdsList", userIdsList).setParameter("orgId", orgId);
				queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportAssociateCountView.class));
				associatesViewList = queryObj.list();
				
		}
		else {
			         query ="SELECT "
					+" USER_ID AS associateUserId,SUM(REQ_COUNT) AS jobCount"
					+" FROM"
					+" RECRUITER_REPORT_ASSOCIATE_COUNT_VIEW"
					+" WHERE"
					+" ORG_LOCATION_ID = :orgLocationId AND USER_ID IN (:userIdsList)"
					+" AND CREATED_DATE BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"
					+" GROUP BY USER_ID";
		
			
		
            Query queryObj = session.createSQLQuery(query);
			queryObj.setParameterList("userIdsList", userIdsList).setParameter("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterReportAssociateCountView.class));
			associatesViewList = queryObj.list();
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return  associatesViewList;
		
	}
	
	@Override
	@Transactional
	public List<RecruiterRrportDTO> getAssociateCountsCreatedUserListByLocation (Long orgLocationId,
			List<Long> userIdsList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception{

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<RecruiterRrportDTO> associatesViewList = null;
		try {
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			
			query = "SELECT REQ.ID as requirementId,REQ.MANAGER_USER_ID AS managerUserId,REQ.PRIMARY_RECRUITER_USER_ID AS primaryUserId,REQA.ASSIGN_USER_ID AS assignedUserId,REQ.ORG_LOCATION_ID AS orglocationId,OL.ORG_ID AS orgId,REQA.STATUS AS assignedUserStatus,RAH.APPROVER_USER_ID AS approverUserId "
					+" FROM REQUIREMENT REQ LEFT JOIN REQUIREMENT_ASSIGN_TO REQA ON REQ.ID = REQA.REQUIREMENT_ID LEFT JOIN ORGLOCATIONS OL ON REQ.ORG_LOCATION_ID = OL.ID LEFT JOIN REQUIREMENT_APPROVER_HIERARCHY RAH ON REQ.ID = RAH.REQUIREMENT_ID WHERE OL.ORG_ID =:orgId AND REQ.REQUIREMENT_SUBMISSION_STATUS = 'OPEN'  AND (REQ.MANAGER_USER_ID IN (:userIdsList) OR REQ.PRIMARY_RECRUITER_USER_ID IN (:userIdsList) "
					+ "OR (REQA.ASSIGN_USER_ID IN (:userIdsList) AND REQA.STATUS = '1') OR REQ.ID IN (SELECT RAH.REQUIREMENT_ID  from REQUIREMENT_APPROVER_HIERARCHY RAH where RAH.REQUIREMENT_ID = REQ.ID and RAH.APPROVER_USER_ID IN (:userIdsList))) AND DATE_FORMAT(REQ.CREATED_DATE, '%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'";
			
	            Query queryObj = session.createSQLQuery(query);
				queryObj.setParameterList("userIdsList", userIdsList).setParameter("orgId", orgId);
				queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterRrportDTO.class));
				associatesViewList = queryObj.list();
				
		}
		else {
			         query ="SELECT REQ.ID as requirementId,REQ.MANAGER_USER_ID AS managerUserId,REQ.PRIMARY_RECRUITER_USER_ID AS primaryUserId,REQA.ASSIGN_USER_ID AS assignedUserId,REQA.STATUS AS assignedUserStatus,RAH.APPROVER_USER_ID AS approverUserId"
					+" FROM  REQUIREMENT REQ LEFT JOIN REQUIREMENT_ASSIGN_TO REQA ON REQ.ID = REQA.REQUIREMENT_ID LEFT JOIN REQUIREMENT_APPROVER_HIERARCHY RAH ON REQ.ID = RAH.REQUIREMENT_ID WHERE REQ.ORG_LOCATION_ID =:orgLocationId AND REQ.REQUIREMENT_SUBMISSION_STATUS = 'OPEN'  AND (REQ.MANAGER_USER_ID IN (:userIdsList) OR REQ.PRIMARY_RECRUITER_USER_ID IN (:userIdsList) "
					+ "OR (REQA.ASSIGN_USER_ID IN (:userIdsList) AND REQA.STATUS = '1') OR REQ.ID IN (SELECT RAH.REQUIREMENT_ID  FROM REQUIREMENT_APPROVER_HIERARCHY RAH where RAH.REQUIREMENT_ID = REQ.ID and RAH.APPROVER_USER_ID IN (:userIdsList))) AND DATE_FORMAT(REQ.CREATED_DATE, '%Y-%m-%d') BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"'"; 
		
			
		
            Query queryObj = session.createSQLQuery(query);
			queryObj.setParameterList("userIdsList", userIdsList).setParameter("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(RecruiterRrportDTO.class));
			associatesViewList = queryObj.list();
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return  associatesViewList;
		
	}

	@Override
	@Transactional
	public ResponseList<SubmissionRequirementListView> getAllRequirementsForCandidateSubmission(RequirementDTO requirementDTO,
			Long userId, OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<SubmissionRequirementListView> response = new ResponseList<>();
		Query queryObj = null;
		String querySeachStr = "";
		StringBuilder advSeach = new StringBuilder("");
		String advSeachStr = "";
		List<SubmissionRequirementListView> requirementList = null;
		boolean flag = false;
		
		
		  StringBuilder query1 = new StringBuilder("");
			if(requirementDTO.getSearchValue() != null && !"".equalsIgnoreCase(requirementDTO.getSearchValue())) {
				 query1.append(" Q1.generatedId like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR Q1.jobTitle like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR Q1.profilePercentage like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR Q1.clientName like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR Q1.createdDate like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR Q1.location like '%" + requirementDTO.getSearchValue() + "%' ");
				 
			}
			
			if(StringUtils.isNotBlank(requirementDTO.getGeneratedId())) {
				String genertatedIds [] = requirementDTO.getGeneratedId().split(",");
				String genertatedIdsStr = "'" + StringUtils.join(genertatedIds,"','") + "'";
				advSeach.append(" Q1.generatedId IN ("+genertatedIdsStr+") ");
				flag = true;
			}
			
			if(StringUtils.isNotBlank(requirementDTO.getJobTitle())) {
				String jobTitles [] = requirementDTO.getJobTitle().split(",");
				String jobTitlesStr = "'" + StringUtils.join(jobTitles,"','") + "'";
				if(flag == false) {
					advSeach.append(" Q1.jobTitle IN ("+jobTitlesStr+") ");
					flag = true;
				}else {
					advSeach.append(" OR Q1.jobTitle IN ("+jobTitlesStr+") ");
				}
				
			}
			
			if(requirementDTO.getNoticePeriod() != null && !requirementDTO.getNoticePeriod().isEmpty()) {
				String[] noticePeriod = requirementDTO.getNoticePeriod().toArray(new String[requirementDTO.getNoticePeriod().size()]);
				String noticePeriodStr = "'" + StringUtils.join(noticePeriod,"','") + "'";
				if(flag == false) {
					advSeach.append(" Q1.noticePeriod IN ("+noticePeriodStr+") ");
					flag = true;
				}else {
					advSeach.append(" OR Q1.noticePeriod IN ("+noticePeriodStr+") ");
				}
				
			}
			
			if(StringUtils.isNotBlank(requirementDTO.getClient())) {
				String orgRelationIds [] = requirementDTO.getClient().split(",");
				String orgRelationIdStr = "'" + StringUtils.join(orgRelationIds,"','") + "'";
				if(flag == false) {
					advSeach.append(" Q1.orgRelationId IN ("+orgRelationIdStr+") ");
					flag = true;
				}else {
					advSeach.append(" OR Q1.orgRelationId IN ("+orgRelationIdStr+") ");
				}
				
			}
			
			if(StringUtils.isNotBlank(requirementDTO.getCreatedDate()) && !requirementDTO.getCreatedDate().equalsIgnoreCase("All")) {
				Date[] searchDates  = reportsUtil.getJobFilterDate(requirementDTO.getCreatedDate(),orgLocations);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fromDate = sdf.format(searchDates[0]);
				String toDate = sdf.format(searchDates[1]);
				
				if(flag == false) {
					advSeach.append(" (Q1.createdDate BETWEEN '"+fromDate+"' AND '"+toDate+"') ");
					flag = true;
				}else {
					advSeach.append(" OR (Q1.createdDate BETWEEN '"+fromDate+"' AND '"+toDate+"') ");
				}
				
			 }
			
			if(StringUtils.isNotBlank(requirementDTO.getSkills())) {
				String skills [] = requirementDTO.getSkills().split(",");
				String skillsStr = "'" + StringUtils.join(skills,"','") + "'";
				if(flag == false) {
					advSeach.append(" Q1.skills IN ("+skillsStr+") ");
					flag = true;
				}else {
					advSeach.append(" OR Q1.skills IN ("+skillsStr+") ");
				}
				
			}
			
			if(StringUtils.isNotBlank(requirementDTO.getCity())) {
				String cities [] = requirementDTO.getCity().split(",");
				String citiesStr = "'" + StringUtils.join(cities,"','") + "'";
				if(flag == false) {
					advSeach.append(" Q1.city IN ("+citiesStr+") ");
					flag = true;
				}else {
					advSeach.append(" OR Q1.city IN ("+citiesStr+") ");
				}
				
			}
			
			
			String sortingQry = "";
			if(StringUtils.isNotBlank(requirementDTO.getSortColumn()))
			{
				sortingQry = " order by Q1."+ requirementDTO.getSortColumn() +" "+requirementDTO.getSortOrder();
			}
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				querySeachStr = "where "+"("+query1.toString()+")";
			}
			
			if((query1.toString() == null || query1.toString().isEmpty()) && advSeach.toString() != null && !advSeach.toString().isEmpty()){
				advSeachStr = "where "+"("+advSeach.toString()+")";
			}else if(query1.toString() != null && !query1.toString().isEmpty() && advSeach.toString() != null && !advSeach.toString().isEmpty()){
				advSeachStr = "and "+"("+advSeach.toString()+")";
			}
			
			try {
			 query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"REQ.ID AS id," + 
					"REQ.GENERATED_ID AS generatedId," + 
					"REQ.JOB_TITLE AS jobTitle, " + 
					"DATE_FORMAT(REQ.CREATED_DATE ,\"%Y-%m-%d\") AS createdDate, " + 
					"REQ.REQUIREMENT_SUBMISSION_STATUS AS status, " +
					"REQ.PROFILE_PERCENTAGE AS profilePercentage, " + 
					"REQ.SKILLS AS skills, " + 
					"REQ.NOTICEPERIOD AS noticePeriod, " + 
					"REQ.ORG_RELATION_ID AS orgRelationId, " + 
					"REQ.CREATED_BY AS userId, " + 
					"REQ.ORG_LOCATION_ID AS orgLocationId, " + 
					"OL.ORG_ID AS orgId, " + 
					"GROUP_CONCAT(DISTINCT RL.CITY " + 
					"SEPARATOR ',') AS city, " + 
					"GROUP_CONCAT(DISTINCT RL.LOCATION " + 
					"SEPARATOR '|') AS location, " + 
					 "(SELECT " + 
						 "ORG_NAME " + 
						 "FROM " + 
						 "ORG_LOCATIONS OL,ORG_RELATIONS ORL " + 
						 "WHERE " + 
						 "(OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != :orgLocationId AND ORL.ID = REQ.ORG_RELATION_ID) AS clientName " + 
							             
					"FROM " + 
					"REQUIREMENT REQ ," + 
					"REQUIREMENT_LOCATIONS RL, " + 
					"ORG_LOCATIONS OL " + 
					"WHERE REQ.ID = RL.REQUIREMENT_ID " + 
					"AND  REQ.ORG_LOCATION_ID = OL.ID " + 
					"AND REQ.ORG_LOCATION_ID = :orgLocationId AND REQ.REQUIREMENT_SUBMISSION_STATUS = 'OPEN' " + 
					" GROUP BY  REQ.ID, "+
					" DATE_FORMAT(REQ.CREATED_DATE ,\"%Y-%m-%d\"))Q1 "+querySeachStr+advSeachStr+sortingQry;
	
			
			  String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
					" FROM" + 
					"(" + query +
					")Q2;" ;
			
			
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocations.getId());
			queryObj.setResultTransformer(Transformers.aliasToBean(SubmissionRequirementListView.class));
			queryObj.setFirstResult(requirementDTO.getStart());
			queryObj.setMaxResults(requirementDTO.getLength());
			requirementList = queryObj.list();
			
			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			int totalCount = 0;

			
			if (requirementDTO != null) {
				if(countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
				}
			} 
			response.setRecordsTotal(totalCount);
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				response.setRecordsFiltered(filterCount);
			}else {
				response.setRecordsFiltered(totalCount);
			}
			
			response.setData(requirementList);
			return response;
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long saveJobFair(JobFair jobFair) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(jobFair);
			return jobFair.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public JobFair getJobFair(long jobFairId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(JobFair.class, jobFairId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public JobFair getJobFairByName(String jobFairName, long userId, long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from JobFair jobFair where jobFair.name=:jobFairName and jobFair.user.id = :userId and jobFair.orgLocations.id = :orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("jobFairName", jobFairName);
			queryObj.setLong("userId", userId);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<JobFair> jobFairList = queryObj.list();
			if (jobFairList != null && !jobFairList.isEmpty())
				return jobFairList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public JobFair getJobFairByUUID(String uuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from JobFair jobFair where jobFair.uuid=:uuid";
			Query queryObj = session.createQuery(query);
			queryObj.setString("uuid", uuid);
			List<JobFair> jobFairList = queryObj.list();
			if (jobFairList != null && !jobFairList.isEmpty())
				return jobFairList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<JobFair> getJobFairList(JobFairDTO jobFairDTO, boolean includeInactive) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<JobFair> jobFairList = null;
			List<Long> countlist = null;
			ResponseList<JobFair> response = new ResponseList<>();
			StringBuilder query1 = new StringBuilder("");
			if(jobFairDTO.getSearchValue() != null && !"".equalsIgnoreCase(jobFairDTO.getSearchValue())) {
			 query1.append("and (jobFair.name like '%" + jobFairDTO.getSearchValue() + "%' ")
				   .append("OR jobFair.venue like '%" + jobFairDTO.getSearchValue() + "%' )");
			}
			query1.append(" order by " + (jobFairDTO.getSortColumn() != null && !jobFairDTO.getSortColumn().isEmpty() ? jobFairDTO.getSortColumn() : "id") + " " + (jobFairDTO.getSortOrder() != null && !jobFairDTO.getSortOrder().isEmpty() ? jobFairDTO.getSortOrder() : "desc"));
			
			String query = "from JobFair jobFair where jobFair.orgLocations.id=:orgLocationId";
			if(!jobFairDTO.isAdminUser()) {
				query += " and jobFair.user.id=:userId";
			}
			query += " and jobFair.status in " + (includeInactive ? "(0, 1)" : "(1)");
			Query queryObj = session.createQuery(query + " " + query1);
			if(!jobFairDTO.isAdminUser()) {
				queryObj.setLong("userId", jobFairDTO.getUserId());
			}
			queryObj.setLong("orgLocationId", jobFairDTO.getOrgLocationId());
			if(!jobFairDTO.isToExport()) {
				queryObj.setFirstResult(jobFairDTO.getStart());
				queryObj.setMaxResults(jobFairDTO.getLength());
			}
			jobFairList = queryObj.list();
			
			String  countQuery = "select count(*) from JobFair jobFair where jobFair.orgLocations.id=:orgLocationId";
			if(!jobFairDTO.isAdminUser()) {
				countQuery += " and jobFair.user.id=:userId ";
			}
			countQuery += " and jobFair.status in " + (includeInactive ? "(0, 1)" : "(1)");
			Query countQueryObj = session.createQuery(countQuery + query1);
			if(!jobFairDTO.isAdminUser()) {
				countQueryObj.setLong("userId", jobFairDTO.getUserId());
			}
			countlist = countQueryObj.setLong("orgLocationId", jobFairDTO.getOrgLocationId()).list();
			
			int totalCount = 0;
			if (jobFairDTO != null) {
				if(countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
				}
			}
			response.setRecordsFiltered(totalCount);
			response.setRecordsTotal(totalCount);
			response.setData(jobFairList);
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean changeJobFairStatus(Long jobFairId, int status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			JobFair jobFair = session.get(JobFair.class, jobFairId);
			if (jobFair != null) {
				jobFair.setStatus(status);
				session.saveOrUpdate(jobFair);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

	@Override
	@Transactional
	public long saveJobFairRequirements(JobFairRequirements jobFairRequirements) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(jobFairRequirements);
			return jobFairRequirements.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getJobFairDatesForCalendar(long userId, long orgLocationId, String dateFormat, boolean adminFlag) {
		try {
			SimpleDateFormat dateFormatSDF = new SimpleDateFormat(dateFormat);
			
			Session session = this.sessionFactory.getCurrentSession();
			
			List<String> finalDatesList = new LinkedList<>();
			
			// Checking for jobFairRequirements with no date selected, set all dates in between startDate and endDate
			String query1 = "select jobFairRequirements.jobFair.id from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirements";
			query1 += " where ((";
			if(!adminFlag) {
				query1 += "jobFair.user.id=:userId and";
			}
			query1 += " jobFair.orgLocations.id = :orgLocationId)";
			query1 += " or jobFairRequirements.requirement.id in ";
			query1 += " (select rat.requirementId.id from RequirementAssignTo rat where rat.status in (1)";
			if(!adminFlag) {
				query1 += " and rat.assignUserId.id = :userId ";
			}
			query1 += " and rat.requirementId.orgLocationId = :orgLocationId))";
			query1 += " and (jobFairRequirements.fairDates = null or jobFairRequirements.fairDates = '')";
			query1 += " and jobFair.status in (1)";
			Query queryObj1 = session.createQuery(query1);
			if(!adminFlag) {
				queryObj1.setLong("userId", userId);
			}
			queryObj1.setLong("orgLocationId", orgLocationId);
			List<Long> jobFairIdList = queryObj1.list();
			if(jobFairIdList != null && !jobFairIdList.isEmpty()) {
				for (Long jobFairId : jobFairIdList) {
					JobFair jobFair = getJobFair(jobFairId);
					if (jobFair != null) {
						Calendar startDate = Calendar.getInstance();
						startDate.setTime(jobFair.getStartDate());

						Calendar endDate = Calendar.getInstance();
						endDate.setTime(jobFair.getEndDate());
						endDate.roll(Calendar.DATE, true);

						while (startDate.before(endDate)) {
							finalDatesList.add(dateFormatSDF.format(startDate.getTime()));
							startDate.add(Calendar.DATE, 1);
						}
					}
				}
			}
			
			// Checking jobFairRequirements with dates selected
			String query2 = "select jobFairRequirementList.fairDates from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirementList";
			query2 += " where ((";
			if(!adminFlag) {
				query2 += "jobFair.user.id=:userId and";
			}
			query2 += " jobFair.orgLocations.id = :orgLocationId)";
			query2 += " or jobFairRequirementList.requirement.id in ";
			query2 += "(select rat.requirementId.id from RequirementAssignTo rat where rat.status in (1)";
			if(!adminFlag) {
				query2 += " and rat.assignUserId.id = :userId";
			}
			query2 += " and rat.requirementId.orgLocationId = :orgLocationId))";
			query2 += " and jobFair.status in (1)";
			Query queryObj2 = session.createQuery(query2);
			if(!adminFlag) {
				queryObj2.setLong("userId", userId);
			}
			queryObj2.setLong("orgLocationId", orgLocationId);
			List<String> datesList = queryObj2.list();
			for (String date : datesList) {
				finalDatesList.add(date);
			}
			
			/*// Checking for jobFairRequirements with no date selected, set all dates in between startDate and endDate
			String query2 = "select jobFair.id from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirementList where jobFair.status in (1) and jobFair.user.id=:userId and jobFair.orgLocations.id=:orgLocationsId and (jobFairRequirementList.fairDates = '' or jobFairRequirementList.fairDates = null)";
			Query queryObj2 = session.createQuery(query2);
			queryObj2.setLong("userId", userId);
			queryObj2.setLong("orgLocationsId", orgLocationId);
			List<Long> jobFairIdList = queryObj2.list();
			if (jobFairIdList != null && !jobFairIdList.isEmpty()) {
				System.out.println("Actual JobFairIds");
				
				for (Long jobFairId : jobFairIdList) {
					System.out.println("JobFairId : " + jobFairId);
					JobFair jobFair = getJobFair(jobFairId);
					if (jobFair != null) {
						Calendar startDate = Calendar.getInstance();
						startDate.setTime(jobFair.getStartDate());

						Calendar endDate = Calendar.getInstance();
						endDate.setTime(jobFair.getEndDate());
						endDate.roll(Calendar.DATE, true);
						System.out.println("Start Date : " + startDate.getTime() + " ,End Date : " + endDate.getTime());

						while (startDate.before(endDate)) {
							finalDatesList.add(dateFormatSDF.format(startDate.getTime()));
							System.out.println("Date : " + startDate.getTime() + ", Converted Date : " + startDate.getTime().toString());
//							startDate.roll(Calendar.DATE, true);
							startDate.add(Calendar.DATE, 1);
						}
					}
				}
			}
			
			// Checking jobFairRequirements with dates selected
			String query1 = "select jobFairRequirementList.fairDates from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirementList where jobFair.status in (1) and jobFair.user.id=:userId and jobFair.orgLocations.id=:orgLocationsId";
			Query queryObj1 = session.createQuery(query1);
			queryObj1.setLong("userId", userId);
			queryObj1.setLong("orgLocationsId", orgLocationId);
			List<String> datesList = queryObj1.list();
			for (String date : datesList) {
				finalDatesList.add(date);
			}*/
			
			if (finalDatesList != null && !finalDatesList.isEmpty() && !finalDatesList.get(0).isEmpty()) {
				return finalDatesList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public JobFairRequirements getJobFairRequirementsById(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(JobFairRequirements.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public long getJobFairRequirementsIdByJobFairIdAndRequirementId(long jobFairId, long requirementId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select jobFairRequirements.id from JobFairRequirements jobFairRequirements where jobFairRequirements.jobFair.id=:jobFairid and jobFairRequirements.requirement.id=:requirementId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("jobFairid", jobFairId);
			queryObj.setLong("requirementId", requirementId);
			/*List<JobFairRequirements> jobFairRequirementList = queryObj.list();
			if (jobFairRequirementList != null && !jobFairRequirementList.isEmpty()) {
				return jobFairRequirementList.get(0).getId();
			}*/
			List<Long> jobFairRequirementIdList = queryObj.list();
			if (jobFairRequirementIdList != null && !jobFairRequirementIdList.isEmpty()) {
				return jobFairRequirementIdList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public List<JobFair> getJobFairListByRequirementId(Long requirementId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select jobFair from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirements where jobFair.status in (1) and jobFairRequirements.requirement.id = :requirementId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("requirementId", requirementId);
			List<JobFair> jobFairList = queryObj.list();
			if (jobFairList != null && !jobFairList.isEmpty())
				return jobFairList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Requirement> getRequirementsByJobFairDate(long userId, long orgLocationId, String dateFormat, String selectedDate, boolean adminFlag) {
		List<Requirement> requirementList = new LinkedList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Set<Long> requirementIds = new HashSet<>();
			
			String year = "", month = "", date = "";
			if (selectedDate != null && !selectedDate.isEmpty()) {				
				switch (dateFormat) {
				case "yyyy-MM-dd":
					year = selectedDate.split("-")[0];
					month = selectedDate.split("-")[1];
					date = selectedDate.split("-")[2];
					break;
				case "dd-MM-yyyy":
					year = selectedDate.split("-")[2];
					month = selectedDate.split("-")[1];
					date = selectedDate.split("-")[0];
					break;
				case "MM-dd-yyyy":
					year = selectedDate.split("-")[2];
					month = selectedDate.split("-")[0];
					date = selectedDate.split("-")[1];
					break;
				case "yyyy-dd-MM":
					year = selectedDate.split("-")[0];
					month = selectedDate.split("-")[2];
					date = selectedDate.split("-")[1];
					break;
				default:
					break;
				}
			}
			String finalDate = year + "-" + month + "-" + date;
			
			// getting the jobFairRequirements with no dates selected
			String newQuery1 = "select jobFairRequirements.id from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirements";
			newQuery1 += " where ((";
			if(!adminFlag) {
				newQuery1 += "jobFair.user.id=:userId and ";
			}
			newQuery1 += " jobFair.orgLocations.id = :orgLocationId)";
			newQuery1 += " or jobFairRequirements.requirement.id in ";
			newQuery1 += " (select rat.requirementId.id from RequirementAssignTo rat where rat.status in (1)";
			if(!adminFlag) {
				newQuery1 += " and rat.assignUserId.id = :userId";
			}
			newQuery1 += " and rat.requirementId.orgLocationId = :orgLocationId))";
			newQuery1 += " and jobFair.startDate <= '" + finalDate + "' and jobFair.endDate >= '" + finalDate + "' and (jobFairRequirements.fairDates = null or jobFairRequirements.fairDates = '')";
			newQuery1 += " and jobFair.status = 1";
			
			requirementList = getRequirementsForSelectedDate(session, userId, orgLocationId, newQuery1, requirementIds, requirementList, adminFlag);
			
			// getting the jobFairRequirements with selected date
			String newQuery2 = "select jobFairRequirements.id from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirements";
			newQuery2 += " where ((";
			if(!adminFlag) {
				newQuery2 += "jobFair.user.id=:userId and ";
			}
			newQuery2 += " jobFair.orgLocations.id = :orgLocationId)";
			newQuery2 += " or jobFairRequirements.requirement.id in";
			newQuery2 += " (select rat.requirementId.id from RequirementAssignTo rat where rat.status in (1)";
			if(!adminFlag) {
				newQuery2 += " and rat.assignUserId.id = :userId";
			}
			newQuery2 += " and rat.requirementId.orgLocationId = :orgLocationId))";
			newQuery2 += " and jobFairRequirements.fairDates like '%" + selectedDate + "%'";
			newQuery2 += " and jobFair.status = 1";
			
			requirementList = getRequirementsForSelectedDate(session, userId, orgLocationId, newQuery2, requirementIds, requirementList, adminFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requirementList;
	}

	@SuppressWarnings("unchecked")
	private List<Requirement> getRequirementsForSelectedDate(Session session, long userId, long orgLocationId, String query,
			Set<Long> requirementIds, List<Requirement> requirementList, boolean adminFlag) {
		try {
			Query queryObj = session.createQuery(query);
			if(!adminFlag) {
				queryObj.setLong("userId", userId);
			}
			queryObj.setLong("orgLocationId", orgLocationId);
			List<Long> idList = queryObj.list();
			for (Long id : idList) {
				JobFairRequirements jobFairRequirements = getJobFairRequirementsById(id);
				if (jobFairRequirements != null) {
					boolean addFlag = true;
					Requirement requirement = jobFairRequirements.getRequirement();
					List<JobFair> jobFairList = new LinkedList<>();
					if (!requirementIds.contains(requirement.getId())) {
						requirementIds.add(requirement.getId());
						jobFairList = new LinkedList<>();
					} else {
						jobFairList = requirement.getJobFairList();
						addFlag = false;
					}

					JobFair jobFair = jobFairRequirements.getJobFair();
					// System.out.println("JOBFAIR_ID : " + jobFair.getId() + ":" +
					// jobFair.getName());
					jobFairList.add(jobFair);

					requirement.setJobFairList(jobFairList);
					if (requirement != null && addFlag) {
						requirementList.add(requirement);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requirementList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Requirement> getRequirementsListByJobFairId(long userId, long orgLocationId, long jobFairId, boolean adminFlag) {
		List<Requirement> requirementList = new LinkedList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select jobFairRequirements.id from JobFair jobFair join jobFair.jobFairRequirementList jobFairRequirements";
			query += " where jobFair.orgLocations.id = :orgLocationId and jobFair.id = :jobFairId";
			if(!adminFlag) {
				query += " and jobFair.user.id = :userId";
			}
			Query queryObj = session.createQuery(query);
			if(!adminFlag) {
				queryObj.setLong("userId", userId);
			}
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setLong("jobFairId", jobFairId);
			List<Long> idList = queryObj.list();
			for (Long id : idList) {
				JobFairRequirements jobFairRequirements = getJobFairRequirementsById(id);
				if (jobFairRequirements != null) {
					Requirement requirement = jobFairRequirements.getRequirement();
					requirementList.add(requirement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requirementList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public String getJobFairDatesByRequirementIdAndJobFairId(long requirementId, long jobFairId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select jobFairRequirements.fairDates from JobFairRequirements jobFairRequirements where jobFairRequirements.requirement.id =:requirementId and jobFairRequirements.jobFair.id =:jobFairId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("requirementId", requirementId);
			queryObj.setLong("jobFairId", jobFairId);
			List<String> fairDatesList = queryObj.list();
			if (fairDatesList != null && !fairDatesList.isEmpty()) {
				return fairDatesList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Long> getJobFairRequirementsIdListByRequirementId(long requirementId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select jobFairRequirements.id from JobFairRequirements jobFairRequirements where jobFairRequirements.requirement.id=:requirementId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("requirementId", requirementId);
			List<Long> jobFairRequirementsIdListList = queryObj.list();
			return jobFairRequirementsIdListList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteJobFairRequirement(long jobFairRequirementId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			JobFairRequirements jobFairRequirements = session.get(JobFairRequirements.class, jobFairRequirementId);
			if(jobFairRequirements!=null) {
				session.delete(jobFairRequirements);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<JobFair> getJobFairWithNullGeneratedIdList() {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from JobFair jobFair where jobFair.generatedId = null";
			List<JobFair> jobFairList = session.createQuery(query).list();
			if (jobFairList != null && !jobFairList.isEmpty()) {
				return jobFairList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<RequirementDTO> getJobListOfRecruiter(RequirementDTO requirementDTO, Long recruiterUserId,Date[] searchDates,List<Long> teamsList) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> requirementList = null;

		Query queryObj = null;
		Integer filterCount = 0;
		StringBuilder query1 = new StringBuilder("");
		StringBuilder subQueryTeam = new StringBuilder("");
		ResponseList<RequirementDTO> response = new ResponseList<>();
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		List<Long> countlist = null;
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = sdf.format(searchDates[0]);
		String toDate = sdf.format(searchDates[1]);*/

		if (requirementDTO.getSearchValue() != null && !"".equalsIgnoreCase(requirementDTO.getSearchValue())) {
			query1.append(" requirement.generatedId like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.jobTitle like '%" + requirementDTO.getSearchValue() + "%' ")
					.append("OR requirement.location like '%" + requirementDTO.getSearchValue() + "%' ");
		}
		if(teamsList.size()>0 && !teamsList.isEmpty()){
			subQueryTeam.append(" and requirement.id IN (SELECT rato.requirementId.id FROM  RequirementAssignTo rato WHERE rato.teamId.id IN (:teamsList) and rato.status='1')");
		}
			String query = "from Requirement requirement  where (requirement.orgLocationId.id =:orglocationId and (date(requirement.createdDate) BETWEEN :fromDate AND :toDate) "+subQueryTeam.toString()+") "
					+ "and (requirement.requirementSubmissionStatus = 'OPEN' or requirement.requirementSubmissionStatus = 'NEW') and "
					+ "( requirement.primaryRecruiterUserId.id =:recruiterUserId or requirement.managerUserId.id =:recruiterUserId "
					+ "or :recruiterUserId IN (select ra.assignUserId.id from RequirementAssignTo ra where ra.requirementId=requirement and ra.status='1' and (date(ra.createdDate) BETWEEN :fromDate AND :toDate)) )";
			if (query1.toString() != null && !query1.toString().isEmpty()) {
				queryObj = session.createQuery(query + "and " + "(" + query1.toString() + ")");
			} else {
				queryObj = session.createQuery(query + query1.toString());
			}

			queryObj.setFirstResult(requirementDTO.getStart());
			queryObj.setMaxResults(requirementDTO.getLength());

			queryObj.setLong("orglocationId", requirementDTO.getOrgLocationId()).setLong("recruiterUserId", recruiterUserId).setParameter("fromDate", searchDates[0]).setParameter("toDate", searchDates[1]);
			if (subQueryTeam.toString() != null && !subQueryTeam.toString().isEmpty()) {
				queryObj.setParameterList("teamsList", teamsList);
			}
			requirementList = queryObj.list();

			if (subQueryTeam.toString() != null && !subQueryTeam.toString().isEmpty()) {
				countlist = session.createQuery("select count(*) " + query).setLong("orglocationId", requirementDTO.getOrgLocationId()).setLong("recruiterUserId", recruiterUserId).setParameter("fromDate", searchDates[0]).setParameter("toDate", searchDates[1]).setParameterList("teamsList", teamsList).list();
			}else {
				countlist = session.createQuery("select count(*) " + query).setLong("orglocationId", requirementDTO.getOrgLocationId()).setLong("recruiterUserId", recruiterUserId).setParameter("fromDate", searchDates[0]).setParameter("toDate", searchDates[1]).list();
			}
			if (query1.toString() != null && !query1.toString().isEmpty()) {
				filterCount = requirementList.size();
			}

		int totalCount = 0;
		if (requirementDTO != null) {
			if (countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		}
		response.setRecordsTotal(totalCount);
		if (query1.toString() != null && !query1.toString().isEmpty()) {
			response.setRecordsFiltered(filterCount);
		} else {
			response.setRecordsFiltered(totalCount);
		}

		for (Requirement req : requirementList) {
			RequirementDTO reqDTO = new RequirementDTO();
			List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList = requirementLocationsService
					.getRequirementLocationsListByRequirementId(req.getId());
			if (locationsList != null && !locationsList.isEmpty()) {
				for (RequirementLocations location : locationsList) {
					requirementLocationsList.add(location.getLocation());
				}
			}
			reqDTO.setId(req.getId());
			reqDTO.setRequirementId(req.getId().toString());
			reqDTO.setJobId(req.getJobId());
			reqDTO.setGeneratedId(req.getGeneratedId());
			reqDTO.setJobTitle(req.getJobTitle());
			reqDTO.setJobType(req.getJobType());
			reqDTO.setRate(req.getRate());
			reqDTO.setLocationsList(requirementLocationsList);
			reqDTO.setPriority(req.getPriority());
			/*if (StringUtils.isNotBlank(dateFormat)) {
				reqDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, req.getCreatedDate()));
			} else {
				reqDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter("dd-MM-yyyy", req.getCreatedDate()));
			}*/
			if(req.getClientId() != null){
				if(req.getClientId().getResponderType().equalsIgnoreCase("Client")){
					reqDTO.setClient(req.getClientId().getResponderLocationId().getOrgLocationName());
				}
				if(req.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					reqDTO.setClient(req.getClientId().getLocationId().getOrgLocationName());
				}
			}
			requirementDTOList.add(reqDTO);
		}
		response.setData(requirementDTOList);
		return response;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(sdf.format(new Date(1519216393485L)));
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean forceDeleteForSelectedJobs(String[] uuids) {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 
		 if(uuids.length > 0) {
				for(String uuid : uuids) {
					Requirement req;
					try {
						req = this.getRequirementByRequirementUUID(uuid);
					
						/*String query = "DELETE rah rl rcs rat r FROM Requirement r JOIN RequirementApproverHierarchy rah ON r.id = rah.requirementId.id "
								+ "JOIN RequirementLocations rl ON r.id = rl.requirementId.id JOIN RecommendedCustomizeScore rcs ON r.id = rcs.reqirementId.id "
								+ "JOIN RequirementAssignTo rat ON r.id = rat.requirementId.id WHERE r.requirementUUID =:uuid";*/
						List<String> queryList = new ArrayList<String>();
						
						/*queryList.add("DELETE FROM REQUIREMENT_LOCATIONS INNER JOIN REQUIREMENT ON REQUIREMENT_LOCATIONS.REQUIREMENT_ID = REQUIREMENT.ID WHERE REQUIREMENT.REQUIREMENT_UUID =:uuid");*/
						
						queryList.add("DELETE FROM RequirementLocations rl WHERE rl.requirementId.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM RequirementApproverHierarchy rah WHERE rah.requirementId.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM RecommendedCustomizeScore rcs WHERE rcs.reqirementId.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM RequirementAssignTo rat WHERE rat.requirementId.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM JobCustomFieldData jcfd WHERE jcfd.requirement.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM RequirementIncentive ri WHERE ri.requirementId.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM JobFairRequirements jfr WHERE jfr.requirement.id = "+req.getId()+" ");
						
						queryList.add("DELETE FROM Requirement r  WHERE r.id = "+req.getId()+ " ");
						
						for(String query : queryList) {
							session.createQuery(query).executeUpdate();
						}
						sourceleadELKBaseIndexer.deleteElkDocument(null, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE, req.getId().toString());
						flag = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementIncentive> getRequirementIncentiveListByRequirementId(long requirementId) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementIncentive incentive where incentive.requirementId.id =:requirementId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("requirementId", requirementId);
			List<RequirementIncentive> requirementIncentiveList = queryObj.list();
			return requirementIncentiveList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void deleteRequirementIncentiveByRequirementIdOrIds(long requirementId, String ids) throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(requirementId > 0) {
				session.createQuery("DELETE FROM RequirementIncentive ri WHERE ri.requirementId.id = "+requirementId).executeUpdate();
			}
			if(StringUtils.isNotEmpty(ids)) {
				session.createQuery("DELETE FROM RequirementIncentive ri WHERE ri.id IN("+ids+")").executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Requirement> getJobsListByOrgRelationAndLocation(Long orglocationId, String orgRelationIds,	Date startDateUTC, Date endDateUTC) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Requirement> reqList = null;
		if(StringUtils.isNotBlank(orgRelationIds)){
		String[] idsValues = orgRelationIds.split(",");
		// convert string array to long array
		Long[] data = new Long[idsValues.length];
		for (int i = 0; i < idsValues.length; i++) {
		  data[i] = Long.valueOf(idsValues[i]);
		}
		query = "from Requirement req where req.orgLocationId.id =:orglocationId and req.clientId.id IN (:orgRelationIds) and req.createdDate BETWEEN :startDateUTC and :endDateUTC ";
		 reqList = session.createQuery(query).setParameterList("orgRelationIds", data).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setParameter("orglocationId", orglocationId).list();
		}else{
		query = "from Requirement req where req.orgLocationId.id =:orglocationId and req.createdDate BETWEEN :startDateUTC and :endDateUTC ";
		 reqList = session.createQuery(query).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setParameter("orglocationId", orglocationId).list();
			
		}
		 return reqList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsForVendorBasedOnOrgRelationId(Long orgLocationId, Long orgRelationId) {
		List<Requirement> releasedJobsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if (orgRelationId != null && orgLocationId != null) {
				String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.clientId.id =:orgRelationId and requirement.clientId.id IN (select orgRelation.id from OrgRelations orgRelation where orgRelation.locationId.id =:orgLocationId ) and requirement.parentRequirement IS NOT NULL";
				releasedJobsList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasedJobsList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllReleasedJobsBasedOnSpocId(long spocId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "";
			query = "from Requirement requirement left join fetch requirement.parentRequirement where requirement.spocId.id=:spocId and requirement.parentRequirement IS NOT NULL";
			List<Requirement> releasedJobsList = session.createQuery(query).setLong("spocId", spocId).list();
			return releasedJobsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public long getQualityCandidatesForVendors(List<Long> requirementIdList, long orgRelationId, long spocId,
			String status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();

			String query = "select count(*) from RequirementSubmission rs join rs.requirement req where req.id in (select r.parentRequirement.id from Requirement r where r.parentRequirement IS NOT NULL";
			if (spocId > 0) {
				query += " and r.spocId.id=" + spocId;
			} else {
				query += " and r.clientId.id=" + orgRelationId;
			}
			query += ")";
			OrgRelations orgRelations = orgRelationsDao.getOrgRelationById(orgRelationId);
			query += " and rs.vendorLocationId.id = " + orgRelations.getResponderLocationId().getId();

			/*
			 * String query =
			 * "select count(*) from RequirementSubmission rs join rs.requirement req where req.id in (:reqIds)"
			 * ; if (spocId > 0) { query += " and req.spocId.id=" + spocId; } else { query
			 * += " and req.clientId.id=" + orgRelationId; }
			 */
			query += " and rs.submissionStatus='" + status + "'";
			Query queryObj = session.createQuery(query);
			// queryObj.setParameterList("reqIds", requirementIdList);
			List<Long> countList = queryObj.list();
			if (countList != null && !countList.isEmpty()) {
				return countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<RequirementSubmission> getApplicantDeclineCountsForVendor(List<Long> requirementIdList,
			long orgRelationId, long spocId, String submissionStatus) {
		try {
			List<RequirementSubmission> list = null;
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from RequirementSubmission rs left join fetch rs.requirement req where req.id in (select r.parentRequirement.id from Requirement r where r.parentRequirement IS NOT NULL";
			if (spocId > 0) {
				query += " and r.spocId.id=" + spocId;
			} else {
				query += " and r.clientId.id=" + orgRelationId;
			}
			query += ")";
			OrgRelations orgRelations = orgRelationsDao.getOrgRelationById(orgRelationId);
			query += " and rs.vendorLocationId.id = " + orgRelations.getResponderLocationId().getId();
			query += " and rs.submissionStatus='" + submissionStatus + "'";
			// queryObj.setParameterList("reqIds", requirementIdList);
			list = session.createQuery(query).list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public long getVendorSubmittedCandidates(long requirementId, long orgRelationId, long spocId, String status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();

			String query = "select count(*) from RequirementSubmission rs join rs.requirement req where req.id in (select r.parentRequirement.id from Requirement r where r.parentRequirement IS NOT NULL and r.parentRequirement.id =:requirementId";
			if (spocId > 0) {
				query += " and r.spocId.id=" + spocId;
			} else {
				query += " and r.clientId.id=" + orgRelationId;
			}
			query += ")";
			OrgRelations orgRelations = orgRelationsDao.getOrgRelationById(orgRelationId);
			query += " and rs.vendorLocationId.id = " + orgRelations.getResponderLocationId().getId();
			if (!status.equalsIgnoreCase("ALL")) {
				query += " and rs.submissionStatus='" + status + "'";
			}
			Query queryObj = session.createQuery(query);
			queryObj.setParameter("requirementId", requirementId);
			List<Long> countList = queryObj.list();
			if (countList != null && !countList.isEmpty()) {
				return countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}