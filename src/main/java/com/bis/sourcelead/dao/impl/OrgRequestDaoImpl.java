package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.dao.OrgRequestDao;
import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;

@Repository
public class OrgRequestDaoImpl implements OrgRequestDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
    @Autowired
    CommonUtil commonUtil;
    
    @Autowired
    LookUpDataDao lookUpDataDao;
	
    @Autowired
	ReportsUtil reportsUtil;
    
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrgRequestDaoImpl.class);

	@Override
	@Transactional
	public OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgRequest != null) {
				session.saveOrUpdate(orgRequest);
			}
			logger.debug("Saving OrgRequest");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"OrgRequest"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgRequest;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean validateOrgRequestCompanyNameByName(String companyName) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(companyName != null){
				String query = "from OrgRequest orgRequeat where orgRequeat.companyName = :companyName ";
				List<OrgRequest> orgRequestList = session.createQuery(query.toString()).setString("companyName", companyName).list();
				if (orgRequestList != null && orgRequestList.size() > 0) {
					flag = true;
				}
				
		 }
		return flag;
	}
	
	
	@Override
	@Transactional
	public OrgRequest getOrgRequestById(Long id) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRequest> orgRequest = new ArrayList<OrgRequest>();
		logger.debug("getOrgRequestById start");
		orgRequest = session.createQuery("from OrgRequest orgReq where orgReq.id = :id" ).setLong( "id", id ).list();
		logger.debug("getOrgRequestById end");
		if (orgRequest.size() > 0) {
			return orgRequest.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public ResponseList<EmployerRegistrationDTO> getDemoRequestsList(EmployerRegistrationDTO employerRegistrationDTO,OrgLocations orgLocations, String orgType)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String approverQuery = "";
		String projectModeQuery = "";
		String query = null;
		String dateFilterQuery = "";
		String filterStatusQuery = "";
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<EmployerRegistrationDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<OrgRequest> orgDemoRequestsList = null;
		Date[] searchDates = null;
		List<String> filterStatusList = null;
		
	
	   Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
	   Matcher matcher = pattern.matcher(employerRegistrationDTO.getSearchValue());
	   java.sql.Date sqlDate = null;
	    if(matcher.matches() == true) {
					if(employerRegistrationDTO.getSearchValue().contains("/")||(employerRegistrationDTO.getSearchValue().contains("-"))) {
						String replacedValue = employerRegistrationDTO.getSearchValue().replaceAll("/", "-");
							SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
	    }
	    
	    StringBuilder query1 = new StringBuilder("");
		if(employerRegistrationDTO.getSearchValue() !=null && !"".equalsIgnoreCase(employerRegistrationDTO.getSearchValue())) {
			 query1.append(" orgReq.companyName like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.companywebsite like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.email like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.phoneNumber like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.firstName like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.lastName like '%" + employerRegistrationDTO.getSearchValue() + "%' ")
					.append("OR orgReq.country like '%" + employerRegistrationDTO.getSearchValue() + "%' ");
					 if(orgType.equalsIgnoreCase("Demo")) {
					    if(sqlDate != null) {
					    	query1.append("OR orgReq.demoDate like '%" + sqlDate + "%' ");
						 } 
					 }
			}
		
		
		if(employerRegistrationDTO.getRequestedStatus() != null  && !employerRegistrationDTO.getRequestedStatus().equalsIgnoreCase("All") && !employerRegistrationDTO.getRequestedStatus().isEmpty()) {
			if(!employerRegistrationDTO.getRequestedStatus().equalsIgnoreCase(Constants.ORG_REQUEST_FILTER_TODAY)) {
				searchDates = reportsUtil.requestedOrgFilterDate(employerRegistrationDTO.getRequestedStatus(),orgLocations); 
				dateFilterQuery = " and date(orgReq.createdDate) BETWEEN '"+searchDates[0]+"' and '"+searchDates[1]+"' ";
			}else{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date today = Calendar.getInstance().getTime();        
				String reportDate = df.format(today);
				
				dateFilterQuery = " and orgReq.createdDate = '"+reportDate+"' ";
			}
			
		}
		
		if(employerRegistrationDTO.getFilterStatus() != null && !employerRegistrationDTO.getFilterStatus().isEmpty()) {
			String[] filterStatus = employerRegistrationDTO.getFilterStatus().split(",");
			filterStatusList = new ArrayList<>();
			for(int i=0 ; i < filterStatus.length ; i++) {
				filterStatusList.add(filterStatus[i]);
			}
			filterStatusQuery = " and orgReq.requestStatus IN (:filterStatusList)";
			query = "from OrgRequest orgReq where orgReq.orgType = :orgType and orgReq.status = '1' "+dateFilterQuery+"  "+filterStatusQuery+" ";
			
		}else {
			query = "from OrgRequest orgReq where orgReq.orgType = :orgType and orgReq.status = '1' "+dateFilterQuery+" ";
		}
		
		
		 
	     
	     if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" ORDER BY orgReq.id DESC");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" ORDER BY orgReq.id DESC");
		}
		
		queryObj.setFirstResult(employerRegistrationDTO.getStart());
		queryObj.setMaxResults(employerRegistrationDTO.getLength());
		if(employerRegistrationDTO.getFilterStatus() != null && !employerRegistrationDTO.getFilterStatus().isEmpty()) {
			queryObj.setString("orgType", orgType).setParameterList("filterStatusList", filterStatusList).list();
		}else {
			queryObj.setString("orgType", orgType).list();
		}
		
		orgDemoRequestsList = queryObj.list();
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			
			if(employerRegistrationDTO.getFilterStatus() != null && !employerRegistrationDTO.getFilterStatus().isEmpty()) {
				countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+")").setString("orgType", orgType).setParameterList("filterStatusList", filterStatusList).list();
			}else {
				countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+")").setString("orgType", orgType).list();
			}
			
			filterCount = countlist.iterator().next().intValue();
		}else {
			if(employerRegistrationDTO.getFilterStatus() != null && !employerRegistrationDTO.getFilterStatus().isEmpty()) {
				countlist = session.createQuery("select count(*) " + query).setString("orgType", orgType).setParameterList("filterStatusList", filterStatusList).list();
			}else {
				countlist = session.createQuery("select count(*) " + query).setString("orgType", orgType).list();
			}
			
			filterCount = countlist.iterator().next().intValue();
		}
		
         int totalCount = 0;
		
		if (employerRegistrationDTO != null) {
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
		
		response.setData(getDemoRequestedOrgsList(orgDemoRequestsList,orgLocations));
		return response;
		
	}
	
	private List<EmployerRegistrationDTO> getDemoRequestedOrgsList(List<OrgRequest> orgDemoRequestsList,OrgLocations orgLocations) throws Exception {
		
		List<EmployerRegistrationDTO> demoOrgDTOList = new ArrayList<>();
		
		for(OrgRequest orgRequest : orgDemoRequestsList) {
			OrgRequest orgReq = getOrgRequestById(orgRequest.getId());
			if(orgReq != null) {
				EmployerRegistrationDTO companyDTO = new EmployerRegistrationDTO();
				companyDTO.setId(orgRequest.getId());
				companyDTO.setCompanyName(orgRequest.getCompanyName());
				companyDTO.setFirstName(orgRequest.getFirstName());
				companyDTO.setLastName(orgRequest.getLastName());
				companyDTO.setEmail(orgRequest.getEmail());
				companyDTO.setPhoneNumber(orgRequest.getPhoneNumber());
				companyDTO.setCompanyWebsite(orgRequest.getCompanywebsite());
				companyDTO.setCompanyAddress(orgRequest.getCompanyAddress());
				companyDTO.setCountry(orgRequest.getCountry());
				
				if(orgRequest.getDemoDate() != null) {
					companyDTO.setDemoDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),orgRequest.getDemoDate()));
				}
				
				if(orgRequest.getDemoTime() != null) {
					companyDTO.setDemoTime(orgRequest.getDemoTime());
				}
				
				if(orgRequest.getRequestStatus() != null && !orgRequest.getRequestStatus().isEmpty()) {
					companyDTO.setOrgRequestStatus(orgRequest.getRequestStatus());
				}
				
				
				StringBuilder requestedBy = new StringBuilder();
				if(StringUtils.isNotBlank(orgRequest.getLastName())) {
					requestedBy = requestedBy.append(orgRequest.getFirstName()).append(" ").append(orgRequest.getLastName());
				}else {
					requestedBy = requestedBy.append(orgRequest.getFirstName());
				}
				companyDTO.setRequestedBy(requestedBy.toString());
				
				StringBuilder modulesList = new StringBuilder();
				
				if(orgRequest.getModules() != null && !orgRequest.getModules().isEmpty()) {
					String modules[] = orgRequest.getModules().split(",");
						for(int i= 0 ; i < modules.length; i++) {
							LookUpData lookupData = lookUpDataDao.getTasksById(Long.valueOf(modules[i]));
							if(lookupData != null) {
								modulesList = modulesList.append(lookupData.getUniqueKey()).append(",");
							}
					}	
						String requestedModules = modulesList.toString();
						requestedModules = requestedModules.substring(0,requestedModules.length()-1);
						companyDTO.setRequestedModules(requestedModules);
				}
				
				
				
				demoOrgDTOList.add(companyDTO);
			}
		}
		
		return demoOrgDTOList;
	}

	

	
}
