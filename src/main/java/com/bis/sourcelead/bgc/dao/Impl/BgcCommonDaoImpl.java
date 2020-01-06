package com.bis.sourcelead.bgc.dao.Impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.bgc.dao.BgcCommonDao;
import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationListView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Srinivas.Sabbu
 *
 */
@Repository
@Transactional(rollbackFor = { Exception.class })
public class BgcCommonDaoImpl implements BgcCommonDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BgcCommonDaoImpl.class);
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${jdbc.schema}")
	private String schema;
	

	@Override
	public BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetails BgcCandidateDetails) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(BgcCandidateDetails);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving Bgc Candidate Details in Database "+e.getMessage());
			return null;
		}
		return BgcCandidateDetails;
	}

	@Override
	public ResponseList<BgcCandidateDetailsDTO> getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal) {
		ResponseList<BgcCandidateDetailsDTO> bgcResponseList = new ResponseList<>();
		//orgLocations.setId(2846L);  // used for testing purpose
		try {
		
			Session session = this.sessionFactory.getCurrentSession();
			List<BgcCandidateDetails> bgcReturnList=null;
			System.out.println("orgLocations.getId( ) in dao >>>>>>>>>. "+orgLocations.getId());
			 System.out.println("orgLocations.getIsBackgroundCheck() :>>>>>> "+orgLocations.getIsBackgroundCheck());
			 
			 StringBuilder searchBuilder = new StringBuilder("");
			 if(searchValue != null && !searchValue.equalsIgnoreCase("")){
				 searchBuilder.append(" AND (bgc.candidate.firstName like '%"+searchValue+"%' OR bgc.candidate.lastName like '%"+searchValue+"%' OR bgc.candidate.middelName like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bgc.generatedId like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bgc.currentBgcStatus like '%"+searchValue+"%')");
			 } 
			 
			if(orgLocations.getIsBackgroundCheck() != null && orgLocations.getIsBackgroundCheck().equalsIgnoreCase("Y")) {
			 bgcReturnList= session.createQuery("from BgcCandidateDetails bgc where bgc.orgLocationId =:orgLocationId and bgc.status=1"+searchBuilder.toString()).
					                                   setLong("orgLocationId",orgLocations.getId()).list();
			}else {
				 bgcReturnList= session.createQuery("from BgcCandidateDetails bgc where bgc.sourceOrglocationId =:orgLocationId and bgc.status=1"+searchBuilder.toString()).
                         setLong("orgLocationId",orgLocations.getId()).list();
			}
			List<BgcCandidateDetailsDTO> bgcResponseDtoList = new ArrayList<BgcCandidateDetailsDTO>();	
			
			if(bgcReturnList != null && bgcReturnList.size()>0) {
				BgcCandidateDetailsDTO dto = null;
			  for(BgcCandidateDetails bgcCandidate : bgcReturnList) {
				  dto = new BgcCandidateDetailsDTO();
			//	BeanUtils.copyProperties(source, target);
				  dto.setId(bgcCandidate.getId());
				  dto.setUuid(bgcCandidate.getUuid());
				  dto.setGeneratedId(bgcCandidate.getGeneratedId());
				  dto.setFullName(bgcCandidate.getCandidate().getFirstName() +(bgcCandidate.getCandidate().getLastName() ==null?"":" "+bgcCandidate.getCandidate().getLastName()));
				  dto.setCurrentBgcStatus(bgcCandidate.getCurrentBgcStatus());
				  dto.setUpdatedPercentage(bgcCandidate.getupdatedPercentage());
				  dto.setPersonalInfoStatus(bgcCandidate.getPersonalInfoStatus());
				  dto.setEducationCheckStaus(bgcCandidate.getEducationCheckStaus());
				  dto.setPriorEmployersCheckStatus(bgcCandidate.getPriorEmployersCheckStatus());
				  dto.setHealthCheckStatus(bgcCandidate.getHealthCheckStatus());
				  dto.setCrimeCheckStatus(bgcCandidate.getCrimeCheckStatus());
				  dto.setOthersInfoStatus(bgcCandidate.getOthersInfoStatus());
				  if(orgLocations.getIsBackgroundCheck() != null && orgLocations.getIsBackgroundCheck().equalsIgnoreCase("Y")) {
				    dto.setClinetOrVendorName(bgcCandidate.getSourceOrglocationId().getOrg().getOrgName());
				  }else {
					  dto.setClinetOrVendorName(bgcCandidate.getOrgLocationId().getOrg().getOrgName());
				  }
				  System.out.println("ORG Name :>>>>>> "+bgcCandidate.getSourceOrglocationId().getOrg().getOrgName());
				  dto.setComments(bgcCandidate.getComments());
				  dto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), bgcCandidate.getUpdatedDate()));
				bgcResponseDtoList.add(dto);
			  }
			}
			bgcResponseList.setData(bgcResponseDtoList);
			
			// Below is for pagination in DATATABLE.
			String resultCountQuery = "select count(*) from BgcCandidateDetails bgc where bgc.orgLocationId =:orgLocationId and bgc.status=1";
			Query hqlQuery = session.createQuery(resultCountQuery);
			hqlQuery.setFirstResult(bgcCandidateDetailsDTO.getStart()).setMaxResults(bgcCandidateDetailsDTO.getLength())
			          .setLong("orgLocationId",orgLocations.getId()).list();
            List<Long> countList = hqlQuery.list();
			int totalCount = 0;
			int filterCount =0;
			
			if (bgcCandidateDetailsDTO != null) {
				if (countList != null && !countList.isEmpty()) {
					totalCount = countList.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			bgcResponseList.setRecordsTotal(totalCount);
			if (resultCountQuery.toString() != null && !resultCountQuery.toString().isEmpty()) {
				bgcResponseList.setRecordsFiltered(filterCount);
			} else {
				bgcResponseList.setRecordsFiltered(totalCount);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bgcResponseList;
	}

	@Override
	public String deleteBgcCandidateDetailsbyUuiId(String uuid) {
		try {
			   Session session = this.sessionFactory.getCurrentSession();
			   Query query = session.createQuery("delete from BgcCandidateDetails where uuid=:uuid").setString("uuid", uuid);
		        int count = query.executeUpdate();
		        if(count != 0) {
		        	return Constants.SUCCESS;
		        }
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete  by uuid-->" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			return Constants.SUCCESS;
	}
	@Override
	public Long getBgcIdByOrgLocationId(OrgLocations orgLocations, Integer generatedIdCount) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
			query = "SELECT MAX(CAST(SUBSTR(TRIM(GENERATED_ID),'" + generatedIdCount
					+ "') AS UNSIGNED)) FROM "+schema+".BGC_CANDIDATE_DETAILS WHERE ORG_LOCATION_ID = '" + orgLocations.getId()
					+ "'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;
	}
	@Override
	public BgcCandidateDetails getBgcCandidateDetailsByBgcUuid(String bgcUuid){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String queryString = "from BgcCandidateDetails bgc where bgc.uuid=:bgcUuid";
			Query query = session.createQuery(queryString).setString("bgcUuid", bgcUuid);
			BgcCandidateDetails bgcCandidateDetails = (BgcCandidateDetails)query.uniqueResult();
			return bgcCandidateDetails;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateDetailsByBgcUuid");
			return null;
		}
	}

	@Override
	public List<CandidateEducation> getBgcCandidateEducationSummaryListByCandidateId(Long candidateId){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<CandidateEducation> candidateEducationList = new ArrayList<CandidateEducation>();
			String queryString = "from CandidateEducation ce where ce.candidateId=:candidateId";
			Query query = session.createQuery(queryString).setLong("candidateId", candidateId);
			candidateEducationList = query.list();
			return candidateEducationList;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEducationSummaryListByCandidateId");
			return null;
		}
	}

	@Override
	public List<CandidateEmployers> getBgcCandidateEmploymentSummaryListByCandidateId(Long candidateId)
			throws Exception {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<CandidateEmployers> candidateEmploymentList = new ArrayList<CandidateEmployers>();
			String queryString = "from CandidateEmployers ce where ce.candidateId=:candidateId";
			Query query = session.createQuery(queryString).setLong("candidateId", candidateId);
			candidateEmploymentList = query.list();
			return candidateEmploymentList;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e+" in getBgcCandidateEmploymentSummaryListByCandidateId");
			return null;
		}
	}

}
