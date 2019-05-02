package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PayrollPayheadDao;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateSalaryHistoryInd;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPayheadSalarySetup;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@SuppressWarnings("unchecked")
public class PayrollPayheadDaoImpl implements PayrollPayheadDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil payrollCommonUtil;
	
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

	private static final Logger logger = LoggerFactory.getLogger(PayrollPayheadDaoImpl.class);

	@Override
	@Transactional
	public PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception {
		logger.debug("Adding payrollPayhead");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollPayhead != null) {
				session.saveOrUpdate(payrollPayhead);
			}
			logger.debug("payrollPayhead Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_PAYHEAD"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollPayhead;
	}

	@Override
	@Transactional
	public List<PayrollPayhead> getPayrollPayheadList(OrgLocations orgLocations) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = " FROM PayrollPayhead payhead WHERE  payhead.orgLocationId.id=:orgLocationId and payhead.status = '1'";
		
		List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
		
		return payrollPayheadList;
	}
	
	@Override
	@Transactional
	public List<PayrollPayhead> getAllPayrollPayheadListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = " FROM PayrollPayhead payhead WHERE  payhead.orgLocationId.id=:orgLocationId ";
		
		List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		
		return payrollPayheadList;
	}
	
	@Override
	@Transactional
	public List<PayrollPayhead> getPayrollPayheadListByPayheadComponentType(OrgLocations orgLocations, String component) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = " FROM PayrollPayhead payhead WHERE  payhead.orgLocationId.id=:orgLocationId and payhead.component=:component and payhead.status = '1'";
		
		List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setString("component", component).list();
		
		return payrollPayheadList;
	}
	
	
	@Override
	@Transactional
	public ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<PayrollPayheadDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PayrollPayhead> payrollPayheadList = null;
		 java.sql.Date sqlDate = null;
		if(payrollPayheadDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollPayheadDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(payrollPayheadDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(payrollPayheadDTO.getSearchValue().contains("/")||(payrollPayheadDTO.getSearchValue().contains("-"))) {
							String replacedValue = payrollPayheadDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		
		StringBuilder query1 = new StringBuilder("");
		if(payrollPayheadDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollPayheadDTO.getSearchValue())) {
			 if(payrollPayheadDTO.getListType().equalsIgnoreCase("PHList")) {
				 	 query1.append(" payhead.name like '" + payrollPayheadDTO.getSearchValue() + "%' ")
				 	.append("OR payhead.shortCode like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
				    .append("OR payhead.type like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
				    .append("OR payhead.component like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
					.append("OR payhead.tax like '%" + payrollPayheadDTO.getSearchValue() + "%' ");
			 }
			 if(payrollPayheadDTO.getListType().equalsIgnoreCase("SSList")) {
				 	 query1.append(" payhead.name like '" + payrollPayheadDTO.getSearchValue() + "%' ")
				 	.append("OR payhead.method like '%" + payrollPayheadDTO.getSearchValue() + "%' ")
				    .append("OR payhead.formula like '%" + payrollPayheadDTO.getSearchValue() + "%' ");
			 }
		}
		
		String sortingQry = "";
		/*if(StringUtils.isNotBlank(payrollPayheadDTO.getSortColumn())) {
				sortingQry = " order by payhead."+ payrollPayheadDTO.getSortColumn() +" "+payrollPayheadDTO.getSortOrder();
		}*/
		
		/*query = " FROM PayrollPayhead payhead WHERE  payhead.id NOT IN(SELECT ph1.parentPayhead FROM PayrollPayhead ph1 where ph1.parentPayhead IN (SELECT ph2.id FROM PayrollPayhead ph2) and (ph1.orgLocationId.id=:orgLocationId OR ph1.orgLocationId.id='1') and ph1.status = '1') and (payhead.orgLocationId.id=:orgLocationId OR payhead.orgLocationId.id='1') and payhead.status = '1'";*/
		query = " FROM PayrollPayhead payhead WHERE  payhead.orgLocationId.id=:orgLocationId and payhead.status = '1'";
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		queryObj.setFirstResult(payrollPayheadDTO.getStart());
		queryObj.setMaxResults(payrollPayheadDTO.getLength());
		queryObj.setLong("orgLocationId", orgLocations.getId()).list();
		payrollPayheadList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		int totalCount = 0;
		
		if (payrollPayheadDTO != null) {
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
		response.setData(payrollCommonUtil.setPayrollPayheadList(payrollPayheadList,orgLocations));
		return response;
	}
	
	
	@Override
	@Transactional
	public PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPayhead payhead where payhead.uuid = :uuid and payhead.status = '1' ";  
		List<PayrollPayhead> payrollPayheadList =  session.createQuery(query).setString("uuid", uuid).list();
		PayrollPayhead payrollPayhead = null;
		if (payrollPayheadList != null && payrollPayheadList.size() > 0) {
			payrollPayhead = (PayrollPayhead) payrollPayheadList.iterator().next();
		}
		return payrollPayhead;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 
			if(payrollPayheadDTO.getListType().equalsIgnoreCase("ByName")){
				if(payrollPayheadDTO.getName() != null && payrollPayheadDTO.getOrgLocationId() != null){
					String query = " FROM PayrollPayhead payhead WHERE payhead.orgLocationId.id=:orgLocationId and lower(payhead.name) =:name and payhead.status = '1' ";
					List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setString("name", payrollPayheadDTO.getName().trim().toLowerCase()).setLong("orgLocationId", Long.parseLong(payrollPayheadDTO.getOrgLocationId())).list();
					if (payrollPayheadList != null && payrollPayheadList.size() > 0) {
						flag = true;
					}
			 }
	 		}
	 		if(payrollPayheadDTO.getListType().equalsIgnoreCase("ByShortCode")){
	 			if(payrollPayheadDTO.getShortCode() != null && payrollPayheadDTO.getOrgLocationId() != null){
					String query = " FROM PayrollPayhead payhead WHERE payhead.orgLocationId.id=:orgLocationId and lower(payhead.shortCode) =:shortCode and payhead.status = '1' ";
					List<PayrollPayhead> payrollPayheadList = session.createQuery(query).setString("shortCode", payrollPayheadDTO.getShortCode().trim().toLowerCase()).setLong("orgLocationId", Long.parseLong(payrollPayheadDTO.getOrgLocationId())).list();
					if (payrollPayheadList != null && payrollPayheadList.size() > 0) {
						flag = true;
					}
			 }
	 		}
		return flag;
	}
	
	
	@Override
	@Transactional
	public PayrollPayheadSalarySetup savePayrollPayheadSalarySetup(PayrollPayheadSalarySetup payrollPayheadSalarySetup) throws Exception {
		logger.debug("Adding PayrollPayheadSalarySetup");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollPayheadSalarySetup != null) {
				session.saveOrUpdate(payrollPayheadSalarySetup);
			}
			logger.debug("payrollPayheadSalarySetup Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_PAYHEAD_SALARY_SETUP"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollPayheadSalarySetup;
	}
	
	
	/*@Override
	@Transactional
	public ResponseList<PayrollPayheadSalarySetupDTO> getPayrollPayheadSalarySetupListByPayheadUuidAndLocationId(String uuid, OrgLocations orgLocations) throws Exception {
		ResponseList<PayrollPayheadSalarySetupDTO> payrollPayheadSalarySetupDTOList = new ResponseList<>();
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = " FROM PayrollPayheadSalarySetup ppss WHERE ppss.payhead.orgLocationId.id =:orgLocationId and ppss.payhead.uuid =:uuid and ppss.status = '1'";
		
		List<PayrollPayheadSalarySetup> payrollPayheadSalarySetupList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setString("uuid", uuid).list();
		
		payrollPayheadSalarySetupDTOList.setData(setPayrollPayheadSalarySetupList(payrollPayheadSalarySetupList, orgLocations));
		return payrollPayheadSalarySetupDTOList;
	}*/
	
	/*private List<PayrollPayheadSalarySetupDTO> setPayrollPayheadSalarySetupList(List<PayrollPayheadSalarySetup> payrollPayheadSalarySetupList, OrgLocations orgLocations) throws Exception {
		List<PayrollPayheadSalarySetupDTO> payrollPayheadSalarySetupDTOList = new ArrayList<>();
		if(payrollPayheadSalarySetupList != null && !payrollPayheadSalarySetupList.isEmpty()) {
			for(PayrollPayheadSalarySetup payrollPayheadSalarySetup : payrollPayheadSalarySetupList) {
				PayrollPayheadSalarySetupDTO payrollPayheadSalarySetupDTO = new PayrollPayheadSalarySetupDTO();
				payrollPayheadSalarySetupDTO.setId(payrollPayheadSalarySetup.getId());
				payrollPayheadSalarySetupDTO.setPayhead(payrollPayheadSalarySetup.getPayhead().getId());
				payrollPayheadSalarySetupDTO.setUuid(payrollPayheadSalarySetup.getUuid());
				payrollPayheadSalarySetupDTO.setFormulaPercentage(payrollPayheadSalarySetup.getFormulaPercentage());
				if(payrollPayheadSalarySetup.getFormulaPayhead() != null) {
					payrollPayheadSalarySetupDTO.setFormulaPayhead(payrollPayheadSalarySetup.getFormulaPayhead().getId());
				}
				payrollPayheadSalarySetupDTOList.add(payrollPayheadSalarySetupDTO);
			}
		}
		return payrollPayheadSalarySetupDTOList;
	}*/
	
	@Override
	@Transactional
	public AssociateSalaryInd saveAssociateSalaryInd(AssociateSalaryInd associateSalaryInd) throws Exception {
		logger.debug("Adding AssociateSalaryInd");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalaryInd != null) {
				session.saveOrUpdate(associateSalaryInd);
			}
			logger.debug("associateSalaryInd Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_SALARY_IND"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associateSalaryInd;
	}
	
	
	@Override
	@Transactional
	public AssociateSalaryInd getAssociateSalaryIndByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateSalaryInd salary where salary.uuid = :uuid and salary.status = '1' ";  
		List<AssociateSalaryInd> associateSalaryIndList =  session.createQuery(query).setString("uuid", uuid).list();
		AssociateSalaryInd associateSalaryInd = null;
		if (associateSalaryIndList != null && associateSalaryIndList.size() > 0) {
			associateSalaryInd = (AssociateSalaryInd) associateSalaryIndList.iterator().next();
		}
		return associateSalaryInd;
	}
	
	@Override
	@Transactional
	public AssociateSalaryInd getAssociateSalaryIndByCandidateId(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateSalaryInd salary where salary.candidate.id = :id and salary.status = '1'";  
		List<AssociateSalaryInd> associateSalaryIndList =  session.createQuery(query).setLong("id", id).list();
		AssociateSalaryInd associateSalaryInd = null;
		if (associateSalaryIndList != null && associateSalaryIndList.size() > 0) {
			associateSalaryInd = (AssociateSalaryInd) associateSalaryIndList.iterator().next();
		}
		return associateSalaryInd;
	}
	
	
	@Override
	@Transactional
	public AssociateSalaryPayheadInd getAssociateSalaryPayheadIndById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateSalaryPayheadInd payhead where payhead.id = :id and payhead.status = '1' ";  
		List<AssociateSalaryPayheadInd> associateSalaryPayheadIndList =  session.createQuery(query).setLong("id", id).list();
		AssociateSalaryPayheadInd associateSalaryPayheadInd = null;
		if (associateSalaryPayheadIndList != null && associateSalaryPayheadIndList.size() > 0) {
			associateSalaryPayheadInd = (AssociateSalaryPayheadInd) associateSalaryPayheadIndList.iterator().next();
		}
		return associateSalaryPayheadInd;
	}

	@Override
	@Transactional
	public PayrollPayhead getPayrollPayheadByShortCodeAndOrglocation(String shortCode, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPayhead payhead where payhead.shortCode = :shortCode and payhead.orgLocationId.id = :orgLocationId and payhead.status = '1' ";  
		List<PayrollPayhead> payrollPayheadList =  session.createQuery(query).setString("shortCode", shortCode).setLong("orgLocationId", orgLocationId).list();
		PayrollPayhead payrollPayhead = null;
		if (payrollPayheadList != null && payrollPayheadList.size() > 0) {
			payrollPayhead = (PayrollPayhead) payrollPayheadList.iterator().next();
		}
		return payrollPayhead;
	}

	@Override
	@Transactional
	public AssociateSalaryHistoryInd saveAssociateSalaryHistoryInd(AssociateSalaryHistoryInd associateSalaryHistoryInd)
			throws Exception {
		logger.debug("Adding AssociateSalaryHistoryInd");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalaryHistoryInd != null) {
				session.saveOrUpdate(associateSalaryHistoryInd);
			}
			logger.debug("associateSalaryHistoryInd Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_SALARY_HISTORY_IND"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associateSalaryHistoryInd;
	}
	
	@Override
	@Transactional
	public List<AssociateSalaryInd> getAssociateSalaryIndListByOrgLocationsId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateSalaryInd salary where salary.orgLocations.id = :orgLocationId and salary.status = '1' ";  
		List<AssociateSalaryInd> associateSalaryIndList =  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return associateSalaryIndList;
	}
	
	@Override
	@Transactional
	public Response<String> payheadHandlerByOrg(PayrollPayheadDTO payrollPayheadDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Response<String> response = new Response<>();
		String update ="UPDATE PAYROLL_PAYHEAD p set p.ACTIVE = :active where p.UUID = :uuid and p.ORGLOCATION_ID = :orgLocationId and p.STATUS = '1' ";
		Query updatequeryString = session.createSQLQuery(update);
		updatequeryString.setString("active", payrollPayheadDTO.getActive()).setString("uuid", payrollPayheadDTO.getUuid()).setLong("orgLocationId", Long.parseLong(payrollPayheadDTO.getOrgLocationId()));
		response.setResponse(String.valueOf(updatequeryString.executeUpdate()));
		return response;
	}
}
