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

import com.bis.sourcelead.dao.CandidateOnBoardingDao;
import com.bis.sourcelead.dao.OnBoardingTemplateDocumentsDao;
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class CandidateOnBoardingDaoImpl  implements CandidateOnBoardingDao{


	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OnBoardingTemplateDocumentsDao onBoardingTemplateDocumentsDao;
	
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(CandidateOnBoardingDaoImpl.class);


	@Override
	@Transactional
	public CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception {
		

		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateOnBoarding != null) {
				session.saveOrUpdate(candidateOnBoarding);
			}
			logger.debug("candidateOnBoarding Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"CANDIDATE_ON_BOARDING"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return candidateOnBoarding;
	}


	@Override
	@Transactional
	public List<CandidateOnBoarding> getCandidateOnBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId where onboarding.orgLocationId.id =:orgLocationId ";
	    List<CandidateOnBoarding> candidateOnBoardingList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
        return candidateOnBoardingList;
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByUUID(String onboardCandidateUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.candidateId left join fetch onboarding.offerId offer "
				+ " left join fetch offer.submissionId submission left join fetch submission.requirement left join fetch onboarding.departmentId left join fetch onboarding.roleId left join fetch onboarding.managerUserId left join fetch onboarding.teamId where onboarding.onboardCandidateUUID =:onboardCandidateUUID" ).setString( "onboardCandidateUUID", onboardCandidateUUID ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public Integer getCandidateOnBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from CandidateOnBoarding onboarding where onboarding.orgLocationId.id =:orgLocationId ";
		List<Long> onboardCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(onboardCount != null && !onboardCount.isEmpty()){
					count = onboardCount.get(0).intValue();
			}
			return count;
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingById(Long Id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.roleId left join fetch onboarding.managerUserId "
				+ " left join fetch onboarding.teamId where onboarding.id =:id" ).setLong( "id", Id ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByEmailAndLocationId(String email, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.companyEmail = :email" ).setString("email", email).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByUserIdAndLocationId(Long onboardingUser, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.onboardingUser left join fetch onboarding.orgLocationId left join fetch onboarding.teamId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.onboardingUser.id = :onboardingUser" ).setLong("onboardingUser", onboardingUser).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndOfferId(Long candidateId ,Long offerId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.candidateId left join fetch onboarding.offerId offer "
				+ "  where onboarding.candidateId.id = :candidateId and onboarding.offerId.id = :offerId" ).setLong( "candidateId", candidateId ).setLong("offerId", offerId).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByPersonalEmailAndLocationId(String personalEmail,
			Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId  "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.personalEmail = :personalEmail" ).setString("personalEmail", personalEmail).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public CandidateOnBoarding getCandidateOnBoardingByCandidateIdAndLocationId(Long candidateId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateOnBoarding> candidateOnBoarding = session.createQuery("from CandidateOnBoarding onboarding left join fetch onboarding.orgLocationId left join fetch onboarding.candidateId "
				+ " where onboarding.orgLocationId.id =:orgLocationId and onboarding.candidateId.id = :candidateId " ).setLong("candidateId", candidateId).setLong( "orgLocationId", orgLocationId ).list();
		if (candidateOnBoarding.size() > 0) {
			return candidateOnBoarding.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public ResponseList<CandidateOnBoardingDTO> getCandidateOnBoardingList(
			CandidateOnBoardingDTO candidateOnBoardingDTO, OrgLocations orgLocations) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<CandidateOnBoardingDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<CandidateOnBoarding> onboardingList = null;
		 java.sql.Date sqlDate = null;
		if(candidateOnBoardingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateOnBoardingDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(candidateOnBoardingDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(candidateOnBoardingDTO.getSearchValue().contains("/")||(candidateOnBoardingDTO.getSearchValue().contains("-"))) {
							String replacedValue = candidateOnBoardingDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(orgLocations.getDateFormat());
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		    
		    StringBuilder query1 = new StringBuilder("");
			if(candidateOnBoardingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(candidateOnBoardingDTO.getSearchValue())) {
			 query1.append(" onboarding.firstName like '" + candidateOnBoardingDTO.getSearchValue() + "%' OR onboarding.lastName like '%" + candidateOnBoardingDTO.getSearchValue() + "' ")
					.append("OR (concat(onboarding.firstName,onboarding.lastName) like '%" + candidateOnBoardingDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%') ");
			 
				 if(candidateOnBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Finished") || candidateOnBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Sent")) {
					 query1.append("OR onboarding.candidateOnBoardStatus like '%Finished%' ");
				 }else if(candidateOnBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Accepted") || candidateOnBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Completed")) {
					 query1.append("OR onboarding.candidateOnBoardStatus like '%Accepted%' ");
				 }else if(candidateOnBoardingDTO.getSearchValue().toLowerCase().equalsIgnoreCase("Rejected")) {
					 query1.append("OR onboarding.candidateOnBoardStatus like '%Rejected%' ");
				 }
				 
				 else {
					 query1.append("OR onboarding.candidateOnBoardStatus like '%" + Project_Status.getNameByValue(candidateOnBoardingDTO.getSearchValue()) + "%' ");
				 }
				 
			     if(sqlDate != null) {
			    	query1.append("OR onboarding.startDate like '%" + sqlDate + "%' ");
				 }
						
			}
			
			String sortingQry = "";
			if(StringUtils.isNotBlank(candidateOnBoardingDTO.getSortColumn())) {
					sortingQry = " order by onboarding."+ candidateOnBoardingDTO.getSortColumn() +" "+candidateOnBoardingDTO.getSortOrder();
			}
			
			query = "from CandidateOnBoarding onboarding where onboarding.orgLocationId.id = :orgLocationsId ";
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
			}else{
				 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
			}
			
			
			
			queryObj.setFirstResult(candidateOnBoardingDTO.getStart());
			queryObj.setMaxResults(candidateOnBoardingDTO.getLength());
			queryObj.setLong("orgLocationsId", orgLocations.getId()).list();
			onboardingList = queryObj.list();
			if(query1.toString() != null && !query1.toString().isEmpty()){
				countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationsId", orgLocations.getId()).list();
				filterCount = countlist.iterator().next().intValue();
			}else {
				countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationsId", orgLocations.getId()).list();
				filterCount = countlist.iterator().next().intValue();
			}
			
			
			int totalCount = 0;

			
			if (candidateOnBoardingDTO != null) {
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
			
			response.setData(getOnboardingDetails(onboardingList,orgLocations));
			return response;
	}
	
	private List<CandidateOnBoardingDTO> getOnboardingDetails(List<CandidateOnBoarding> candidateOnBoardingList,OrgLocations orgLocations) throws Exception {
		
     List<CandidateOnBoardingDTO> onBoardingList = new ArrayList<CandidateOnBoardingDTO>();
		
		if(null != candidateOnBoardingList && candidateOnBoardingList.size() > 0){
			for(CandidateOnBoarding candidateOnBoarding : candidateOnBoardingList){
				
				CandidateOnBoardingDTO candidateOnBoardingDTO=new CandidateOnBoardingDTO();
				
				if(null != candidateOnBoarding.getStartDate()){
					candidateOnBoardingDTO.setJoiningDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOnBoarding.getStartDate()));
				}
				
				StringBuilder candidateName = new StringBuilder();
				
				if(null != candidateOnBoarding.getLastName()){
					candidateOnBoardingDTO.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).append(" ").append(candidateOnBoarding.getLastName()).toString());
				}else{
					candidateOnBoardingDTO.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).toString());
				}
				if(null != candidateOnBoarding.getCandidateOnBoardStatus()){
					candidateOnBoardingDTO.setCandidateOnBoardStatus(candidateOnBoarding.getCandidateOnBoardStatus());
				}
				if(null != candidateOnBoarding.getOnboardCandidateUUID()){
					candidateOnBoardingDTO.setOnboardCandidateUUID(candidateOnBoarding.getOnboardCandidateUUID());
				}
				if(null != candidateOnBoarding.getOnboardSubmitted()){
					candidateOnBoardingDTO.setOnboardSubmitted(candidateOnBoarding.getOnboardSubmitted());
				}
				
				if(null != candidateOnBoarding.getOnboardingUser()){
					candidateOnBoardingDTO.setUserEmail(candidateOnBoarding.getOnboardingUser().getEmail());
				}
				
				if(null != candidateOnBoarding.getRejectedReason()){
					candidateOnBoardingDTO.setRejectedReason(candidateOnBoarding.getRejectedReason());
				}
				
				Integer documentsCount = onBoardingTemplateDocumentsDao.getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(candidateOnBoarding.getId());
				candidateOnBoardingDTO.setDocumentsCount(documentsCount);
				Integer acceptedDocumentsCount = onBoardingTemplateDocumentsDao.getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(candidateOnBoarding.getId());
				candidateOnBoardingDTO.setDocumentsAcceptedCount(acceptedDocumentsCount);
				
				onBoardingList.add(candidateOnBoardingDTO);
				
			}
		}
		
		return onBoardingList;
	}

}
