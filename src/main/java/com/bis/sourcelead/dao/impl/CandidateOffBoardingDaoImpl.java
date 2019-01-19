package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.dao.CandidateOffBoardingDao;
import com.bis.sourcelead.dto.CandidateOffBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class CandidateOffBoardingDaoImpl implements CandidateOffBoardingDao {

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
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateOffBoardingDaoImpl.class);


	@Override
	@Transactional
	public CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateOffBoarding != null) {
				session.saveOrUpdate(candidateOffBoarding);
			}
			logger.debug("candidateOffBoarding Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_OFF_BOARDING"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateOffBoarding;
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding left join fetch offboarding.userId "
				+ " where offboarding.id =:id" ).setLong( "id", Id ).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from CandidateOffBoarding offboarding left join fetch offboarding.orgLocationId where offboarding.orgLocationId.id =:orgLocationId ";
	    List<CandidateOffBoarding> candidateOffBoardingList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return candidateOffBoardingList;
	}


	@Override
	@Transactional
	public Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from CandidateOffBoarding offboarding where offboarding.orgLocationId.id =:orgLocationId ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding  "
				+ " where offboarding.offboardUUID =:offboardUUID" ).setString( "offboardUUID", offboardUUID ).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding left join fetch offboarding.userId left join fetch offboarding.orgLocationId   "
				+ " where offboarding.orgLocationId.id =:orgLocationId and offboarding.userId.id = :offboardingUser and offboarding.status = '1' and offboarding.offBoardStatus <> 'Draft' " ).setLong("offboardingUser", offboardingUser).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOffBoarding.size() > 0) {
			return candidateOffBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<CandidateOffBoarding> candidateOffboringDelayMail(){
		Session session = this.sessionFactory.getCurrentSession();	
		List<CandidateOffBoarding> candidateOffBoarding = session.createQuery("from CandidateOffBoarding offboarding").list();
		return candidateOffBoarding;
	}


	@Override
	@Transactional
	public ResponseList<CandidateOffBoardingDTO> getCandidateOffBoardingList(
			CandidateOffBoardingDTO candidateOffBoardingDTO, OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<CandidateOffBoardingDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<CandidateOffBoarding> offboardingList = null;
		 java.sql.Date sqlDate = null;
		if(candidateOffBoardingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateOffBoardingDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(candidateOffBoardingDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(candidateOffBoardingDTO.getSearchValue().contains("/")||(candidateOffBoardingDTO.getSearchValue().contains("-"))) {
							String replacedValue = candidateOffBoardingDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		
		
	    StringBuilder query1 = new StringBuilder("");
		if(candidateOffBoardingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateOffBoardingDTO.getSearchValue())) {
		 query1.append(" offboarding.firstName like '" + candidateOffBoardingDTO.getSearchValue() + "%' OR offboarding.lastName like '%" + candidateOffBoardingDTO.getSearchValue() + "' ")
				.append("OR (concat(offboarding.firstName,offboarding.lastName) like '%" + candidateOffBoardingDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%') ")
		        .append("OR offboarding.offBoardStatus like '%" + candidateOffBoardingDTO.getSearchValue() + "%' ");
		 
			 if(candidateOffBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Completed")) {
				 query1.append("OR offboarding.offboardSubmitted = 'Y' ");
			 }else if(candidateOffBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Pending")) {
				 query1.append("OR offboarding.offboardSubmitted = 'N' ");
			 }
			 
		     if(sqlDate != null) {
		    	query1.append("OR offboarding.endDate like '%" + sqlDate + "%' OR offboarding.effectiveDate like '%" + sqlDate + "%' ");
			 }
					
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getSortColumn())) {
				sortingQry = " order by offboarding."+ candidateOffBoardingDTO.getSortColumn() +" "+candidateOffBoardingDTO.getSortOrder();
		}
		
		query = "from CandidateOffBoarding offboarding where offboarding.orgLocationId.id = :orgLocationsId ";
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		queryObj.setFirstResult(candidateOffBoardingDTO.getStart());
		queryObj.setMaxResults(candidateOffBoardingDTO.getLength());
		queryObj.setLong("orgLocationsId", orgLocations.getId()).list();
		offboardingList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationsId", orgLocations.getId()).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		
		int totalCount = 0;

		
		if (candidateOffBoardingDTO != null) {
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
		
		response.setData(getOffboardingDetails(offboardingList,orgLocations));
		return response;
		
		
	}
	
	
	private List<CandidateOffBoardingDTO> getOffboardingDetails(List<CandidateOffBoarding> candidateOffBoardingList,OrgLocations orgLocations) throws Exception {
		
		List<CandidateOffBoardingDTO> offBoardingList = new ArrayList<CandidateOffBoardingDTO>();
		
		if(null != candidateOffBoardingList && candidateOffBoardingList.size() > 0){
			for(CandidateOffBoarding candidateOffBoarding : candidateOffBoardingList){
				
				CandidateOffBoardingDTO candidateOffBoardingDTO=new CandidateOffBoardingDTO();
				
				if(null != candidateOffBoarding.getDateOfJoin()){
					candidateOffBoardingDTO.setDateOfJoinStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOffBoarding.getDateOfJoin()));
				}
				
				if(null != candidateOffBoarding.getEffectiveDate()){
					candidateOffBoardingDTO.setEffectiveDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOffBoarding.getEffectiveDate()));
				}
				
				if(null != candidateOffBoarding.getEndDate()){
					candidateOffBoardingDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOffBoarding.getEndDate()));
				}
				
				if(null != candidateOffBoarding.getOffboardUUID()){
					candidateOffBoardingDTO.setOffboardUUID(candidateOffBoarding.getOffboardUUID());
				}
				
				if(null != candidateOffBoarding.getOffboardUUID()){
					candidateOffBoardingDTO.setOffboardUUID(candidateOffBoarding.getOffboardUUID());
				}
				
				if(null != candidateOffBoarding.getOffBoardStatus()){
					candidateOffBoardingDTO.setOffBoardStatus(candidateOffBoarding.getOffBoardStatus());
				}
				
				if(null != candidateOffBoarding.getOffboardSubmitted()){
					candidateOffBoardingDTO.setOffboardSubmitted(candidateOffBoarding.getOffboardSubmitted());
				}
				
				
				StringBuilder candidateName = new StringBuilder();
				
				if(null != candidateOffBoarding.getLastName()){
					candidateOffBoardingDTO.setCandidateName(candidateName.append(candidateOffBoarding.getFirstName()).append(" ").append(candidateOffBoarding.getLastName()).toString());
				}else{
					candidateOffBoardingDTO.setCandidateName(candidateName.append(candidateOffBoarding.getFirstName()).toString());
				}
				
				offBoardingList.add(candidateOffBoardingDTO);
				
			}
		}
		
		return offBoardingList;
	}
}
