package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dao.PayrollLoanAndAdvancesDao;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PayrollLoanAndAdvancesDaoImpl implements PayrollLoanAndAdvancesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(PayrollLoanAndAdvancesDaoImpl.class);

	@Override
	@Transactional
	public PayrollLoanAndAdvances savePayrollLoanAndAdvances(PayrollLoanAndAdvances payrollLoanAndAdvances)
			throws Exception {
		
		logger.debug("Adding payrollLoanAndAdvances");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollLoanAndAdvances != null) {
				session.saveOrUpdate(payrollLoanAndAdvances);
			}
			logger.debug("payrollLoanAndAdvances Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_LOAN_AND_ADVANCES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollLoanAndAdvances;
	}

	@Override
	@Transactional
	public ResponseList<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvancesList(
			PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO, OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<PayrollLoanAndAdvancesDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList = null;
		
		
		StringBuilder query1 = new StringBuilder("");
		if(payrollLoanAndAdvancesDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollLoanAndAdvancesDTO.getSearchValue())) {
			 query1.append(" pld.minAmount like '" + payrollLoanAndAdvancesDTO.getSearchValue() + "%' OR pld.maxAmount like '%" + payrollLoanAndAdvancesDTO.getSearchValue() + "' ")
			    .append("OR pld.type like '%" + payrollLoanAndAdvancesDTO.getSearchValue() + "%' ")
			    .append("OR pld.name like '%" + payrollLoanAndAdvancesDTO.getSearchValue() + "%' ")
				.append("OR pld.interestRate like '%" + payrollLoanAndAdvancesDTO.getSearchValue() + "%' ")
				.append("OR pld.applicableFor like '%" + payrollLoanAndAdvancesDTO.getSearchValue() + "%' ");
		}
		
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getSortColumn())) {
				sortingQry = " order by pld."+ payrollLoanAndAdvancesDTO.getSortColumn() +" "+payrollLoanAndAdvancesDTO.getSortOrder();
		}
		
		query = "from PayrollLoanAndAdvances pld where pld.orgLocationId.id = :orgLocationsId and pld.status = '1' ";
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		
		queryObj.setFirstResult(payrollLoanAndAdvancesDTO.getStart());
		queryObj.setMaxResults(payrollLoanAndAdvancesDTO.getLength());
		queryObj.setLong("orgLocationsId", orgLocations.getId()).list();
		payrollLoanAndAdvancesList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		
	    int totalCount = 0;

		
		if (payrollLoanAndAdvancesDTO != null) {
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
		
		response.setData(getPayrollLoanAndAdvList(payrollLoanAndAdvancesList,orgLocations));
		return response;
	}
	
	private List<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvList(List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList,OrgLocations orgLocations) throws Exception {
		List<PayrollLoanAndAdvancesDTO> payrollLoanAndAdvancesDTOList = new ArrayList<>();
		if(payrollLoanAndAdvancesList != null && !payrollLoanAndAdvancesList.isEmpty()) {
			for(PayrollLoanAndAdvances pld : payrollLoanAndAdvancesList) {
				PayrollLoanAndAdvancesDTO pldDTO = new PayrollLoanAndAdvancesDTO();
				pldDTO.setName(pld.getName());
				pldDTO.setType(pld.getType());
				pldDTO.setUuid(pld.getUuid());
				if(pld.getMinAmount() != null) {
					pldDTO.setMinAmount(String.valueOf(pld.getMinAmount()));
				}
				
				if(pld.getMaxAmount() != null) {
					pldDTO.setMaxAmount(String.valueOf(pld.getMaxAmount()));
				}
				
				if(pld.getInterestRate() != null) {
					pldDTO.setInterestRate(String.valueOf(pld.getInterestRate()));
				}
			
				pldDTO.setApplicableFor(pld.getApplicableFor());
				pldDTO.setExperienceYears(String.valueOf(pld.getExperienceYears()));
				pldDTO.setExperienceMonths(String.valueOf(pld.getExperienceMonths()));
				pldDTO.setDescription(pld.getDescription());
				payrollLoanAndAdvancesDTOList.add(pldDTO);
			}
		}
		return payrollLoanAndAdvancesDTOList;
	}

	@Override
	@Transactional
	public PayrollLoanAndAdvances getPayrollLoanAndAdvancesByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollLoanAndAdvances pld where pld.uuid = :uuid and pld.status = '1' ";  
		List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList =  session.createQuery(query).setString("uuid", uuid).list();
		PayrollLoanAndAdvances payrollLoanAndAdvances = null;
		if (payrollLoanAndAdvancesList != null && payrollLoanAndAdvancesList.size() > 0) {
			payrollLoanAndAdvances = (PayrollLoanAndAdvances) payrollLoanAndAdvancesList.iterator().next();
		}
		return payrollLoanAndAdvances;
	}

	@Override
	@Transactional
	public List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByTypeAndOrgLocationId(String type, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from PayrollLoanAndAdvances loanAndAdv where loanAndAdv.type = :type  "
	    		+ " and loanAndAdv.orgLocationId.id = :orgLocationId and loanAndAdv.status = '1' ";

	    List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList = session.createQuery(query.toString()).setString("type", type).setLong( "orgLocationId", orgLocationId).list();
        return payrollLoanAndAdvancesList;
	}

	@Override
	@Transactional
	public PayrollLoanAndAdvances getPayrollLoanAndAdvancesById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollLoanAndAdvances pld where pld.id = :id ";  
		List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList =  session.createQuery(query).setLong("id", id).list();
		PayrollLoanAndAdvances payrollLoanAndAdvances = null;
		if (payrollLoanAndAdvancesList != null && payrollLoanAndAdvancesList.size() > 0) {
			payrollLoanAndAdvances = (PayrollLoanAndAdvances) payrollLoanAndAdvancesList.iterator().next();
		}
		return payrollLoanAndAdvances;
	}

	@Override
	@Transactional
	public List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByOrgLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from PayrollLoanAndAdvances loanAndAdv where loanAndAdv.orgLocationId.id = :orgLocationId and loanAndAdv.status = '1' ";

	    List<PayrollLoanAndAdvances> payrollLoanAndAdvancesList = session.createQuery(query.toString()).setLong( "orgLocationId", orgLocationId).list();
        return payrollLoanAndAdvancesList;
	}
}
