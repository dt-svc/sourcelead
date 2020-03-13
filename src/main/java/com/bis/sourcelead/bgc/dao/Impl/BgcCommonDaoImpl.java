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
import com.bis.sourcelead.bgc.dto.BgcComponentDTO;
import com.bis.sourcelead.bgc.dto.BgcStatusDTO;
import com.bis.sourcelead.bgc.dto.BgcTemplateDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.entity.BgcComponent;
import com.bis.sourcelead.bgc.entity.BgcComponentJsonMap;
import com.bis.sourcelead.bgc.entity.BgcStatus;
import com.bis.sourcelead.bgc.entity.BgcTemplate;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssetCustomFieldData;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationListView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
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
			 bgcReturnList= session.createQuery("from BgcCandidateDetails bgc where bgc.orgLocationId =:orgLocationId and bgc.status=1 "+searchBuilder.toString() +" order by bgc.updatedDate desc, id desc ").
					                                   setLong("orgLocationId",orgLocations.getId()).list();
			}else {
				 bgcReturnList= session.createQuery("from BgcCandidateDetails bgc where bgc.sourceOrglocationId =:orgLocationId and bgc.status=1 "+searchBuilder.toString()+" order by bgc.updatedDate desc , id desc ").
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
					// below code is identifying  vendor if background check =Y.
				    dto.setClinetOrVendorName(bgcCandidate.getSourceOrglocationId().getOrg().getOrgName());
				    dto.setOrgType(Constants.VENDOR);
				  }else {
					  dto.setClinetOrVendorName(bgcCandidate.getOrgLocationId().getOrg().getOrgName());
					 // below code is for identifying Client.
					  dto.setOrgType(Constants.CLIENT);
				  }
				 // System.out.println("ORG Name :>>>>>> "+bgcCandidate.getSourceOrglocationId().getOrg().getOrgName());
				  dto.setComments(bgcCandidate.getComments());
				  dto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), bgcCandidate.getUpdatedDate()));
				bgcResponseDtoList.add(dto);
			  }
			}
			bgcResponseList.setData(bgcResponseDtoList);
			
			// Below is for pagination in DATATABLE.
			String resultCountQuery = "";
			if(orgLocations.getIsBackgroundCheck() != null && orgLocations.getIsBackgroundCheck().equalsIgnoreCase("Y")) {
				resultCountQuery= "select count(*) from BgcCandidateDetails bgc where bgc.orgLocationId =:orgLocationId and bgc.status=1"+searchBuilder.toString();
				}else {
					resultCountQuery= "select count(*) from BgcCandidateDetails bgc where bgc.sourceOrglocationId =:orgLocationId and bgc.status=1"+searchBuilder.toString();
				}
			
			
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

	@Override
	public List<BgcStatus> getBgcStatusListByOrgLocationId(Long orgLocationId) {
		return null;
	}

	@Override
	public ResponseList<BgcComponentDTO> getBgcComponentListByOrgLocationId(BgcComponentDTO bgcComponentDTO, String searchValue, OrgLocations orgLocations) {
		
		ResponseList<BgcComponentDTO> bgcComponentResponseList = new ResponseList<>();
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			List<BgcComponent> bgcReturnList=null;
			System.out.println("orgLocations.getId( ) in dao >>>>>>>>>. "+orgLocations.getId());
			 StringBuilder searchBuilder = new StringBuilder("");
			 if(searchValue != null && !searchValue.equalsIgnoreCase("")){
				 searchBuilder.append(" AND (bc.name like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bc.description like '%"+searchValue+"%')");
			 } 
			 
			 bgcReturnList= session.createQuery("from BgcComponent bc where bc.orgLocationId =:orgLocationId "+searchBuilder.toString()+" order by bc.updatedDate desc, bc.id desc ").
					                                   setLong("orgLocationId",orgLocations.getId()).list();
			List<BgcComponentDTO> bgcComponentDtoList = new ArrayList<BgcComponentDTO>();	
			
			if(bgcReturnList != null && bgcReturnList.size()>0) {
				BgcComponentDTO target = null;
			  for(BgcComponent bgcComponent : bgcReturnList) {
				  target = new BgcComponentDTO();
				BeanUtils.copyProperties(bgcComponent, target);
				 
				bgcComponentDtoList.add(target);
			  }
			}
			bgcComponentResponseList.setData(bgcComponentDtoList);
			
			// Below is for pagination in DATATABLE.
			String resultCountQuery = "";
				
				resultCountQuery= "select count(*) from BgcComponent bc where bc.orgLocationId =:orgLocationId "+searchBuilder.toString();
			
			Query hqlQuery = session.createQuery(resultCountQuery);
			hqlQuery.setFirstResult(bgcComponentDTO.getStart()).setMaxResults(bgcComponentDTO.getLength())
			          .setLong("orgLocationId",orgLocations.getId()).list();
            List<Long> countList = hqlQuery.list();
			int totalCount = 0;
			int filterCount =0;
			
			if (bgcComponentDTO != null) {
				if (countList != null && !countList.isEmpty()) {
					totalCount = countList.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			bgcComponentResponseList.setRecordsTotal(totalCount);
			if (resultCountQuery.toString() != null && !resultCountQuery.toString().isEmpty()) {
				bgcComponentResponseList.setRecordsFiltered(filterCount);
			} else {
				bgcComponentResponseList.setRecordsFiltered(totalCount);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bgcComponentResponseList;
	}

	@Override
	public ResponseList<BgcTemplateDTO> getBgcOrgcTemplateListByOrgLocationId(BgcTemplateDTO bgcTemplateDTO,
			String searchValue, OrgLocations orgLocations) {
		ResponseList<BgcTemplateDTO> bgcTemplateResponseList = new ResponseList<>();
		try {
			
			Session session = this.sessionFactory.getCurrentSession();
			List<BgcTemplate> bgcReturnList=null;
			System.out.println("orgLocations.getId( ) in dao >>>>>>>>>. "+orgLocations.getId());
			 StringBuilder searchBuilder = new StringBuilder("");
			 if(searchValue != null && !searchValue.equalsIgnoreCase("")){
				 searchBuilder.append(" AND (bc.name like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bc.description like '%"+searchValue+"%')");
			 } 
			 
			 bgcReturnList= session.createQuery("from BgcTemplate bc where bc.orgLocationId =:orgLocationId "+searchBuilder.toString()+" order by bc.updatedDate desc, bc.id desc ").
					                                   setLong("orgLocationId",orgLocations.getId()).list();
			List<BgcTemplateDTO> bgcTemplateDtoList = new ArrayList<BgcTemplateDTO>();	
			
			if(bgcReturnList != null && bgcReturnList.size()>0) {
				BgcTemplateDTO target = null;
			  for(BgcTemplate bgcTemplate : bgcReturnList) {
				  target = new BgcTemplateDTO();
				BeanUtils.copyProperties(bgcTemplate, target);
				 
				bgcTemplateDtoList.add(target);
			  }
			}
			bgcTemplateResponseList.setData(bgcTemplateDtoList);
			
			// Below is for pagination in DATATABLE.
			String resultCountQuery = "";
				
				resultCountQuery= "select count(*) from BgcTemplate bc where bc.orgLocationId =:orgLocationId "+searchBuilder.toString();
			
			Query hqlQuery = session.createQuery(resultCountQuery);
			hqlQuery.setFirstResult(bgcTemplateDTO.getStart()).setMaxResults(bgcTemplateDTO.getLength())
			          .setLong("orgLocationId",orgLocations.getId()).list();
            List<Long> countList = hqlQuery.list();
			int totalCount = 0;
			int filterCount =0;
			
			if (bgcTemplateDTO != null) {
				if (countList != null && !countList.isEmpty()) {
					totalCount = countList.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			bgcTemplateResponseList.setRecordsTotal(totalCount);
			if (resultCountQuery.toString() != null && !resultCountQuery.toString().isEmpty()) {
				bgcTemplateResponseList.setRecordsFiltered(filterCount);
			} else {
				bgcTemplateResponseList.setRecordsFiltered(totalCount);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bgcTemplateResponseList;
	}

	@Override
	public String saveOrUpdateBgcStatusListByOrgLocationId(BgcStatus bgcStatus) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(bgcStatus);
			return Constants.SUCCESS;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving Bgc status Details in Database "+e.getMessage());
			return null;
		}
	}

	@Override
	public String deleteBgcStatusListByOrgLocationId(Long statusId) {
		try {
			   Session session = this.sessionFactory.getCurrentSession();
			   Query query = session.createQuery("delete from BgcStatus where id=:statusId").setLong("statusId", statusId);
		        int count = query.executeUpdate();
		        if(count != 0) {
		        	return Constants.SUCCESS;
		        }
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete  bgcStatus by id-->" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			return Constants.FAILED;
	}
	
	@Override
	public ResponseList<BgcStatusDTO> getBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO,
			String searchValue, OrgLocations orgLocations) {
		ResponseList<BgcStatusDTO> bgcStatusResponseList = new ResponseList<>();
		
		try {
		
			Session session = this.sessionFactory.getCurrentSession();
			List<BgcStatus> bgcReturnList=null;
			System.out.println("orgLocations.getId( ) in dao >>>>>>>>>. "+orgLocations.getId());
			 StringBuilder searchBuilder = new StringBuilder("");
			 if(searchValue != null && !searchValue.equalsIgnoreCase("")){
				 searchBuilder.append(" AND (bgcStatus.statusName like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bgcStatus.color like '%"+searchValue+"%')");
				 searchBuilder.append(" OR (bgcStatus.description like '%"+searchValue+"%')");
			 } 
			 
			 bgcReturnList= session.createQuery("from BgcStatus bgcStatus where bgcStatus.orgLocationId =:orgLocationId "+searchBuilder.toString()+" order by bgcStatus.updatedDate desc, bgcStatus.id desc ").
					                                   setLong("orgLocationId",orgLocations.getId()).list();
			List<BgcStatusDTO> bgcResponseDtoList = new ArrayList<BgcStatusDTO>();	
			
			if(bgcReturnList != null && bgcReturnList.size()>0) {
				BgcStatusDTO target = null;
			  for(BgcStatus bgcStatus : bgcReturnList) {
				  target = new BgcStatusDTO();
				BeanUtils.copyProperties(bgcStatus, target);
				 
				bgcResponseDtoList.add(target);
			  }
			}
			bgcStatusResponseList.setData(bgcResponseDtoList);
			
			// Below is for pagination in DATATABLE.
			String resultCountQuery = "";
				resultCountQuery= "select count(*) from BgcStatus bgcStatus where bgcStatus.orgLocationId =:orgLocationId "+searchBuilder.toString();
			
			Query hqlQuery = session.createQuery(resultCountQuery);
			hqlQuery.setFirstResult(bgcStatusDTO.getStart()).setMaxResults(bgcStatusDTO.getLength())
			          .setLong("orgLocationId",orgLocations.getId()).list();
            List<Long> countList = hqlQuery.list();
			int totalCount = 0;
			int filterCount =0;
			
			if (bgcStatusDTO != null) {
				if (countList != null && !countList.isEmpty()) {
					totalCount = countList.iterator().next().intValue();
					filterCount = totalCount;
				}
			}
			bgcStatusResponseList.setRecordsTotal(totalCount);
			if (resultCountQuery.toString() != null && !resultCountQuery.toString().isEmpty()) {
				bgcStatusResponseList.setRecordsFiltered(filterCount);
			} else {
				bgcStatusResponseList.setRecordsFiltered(totalCount);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bgcStatusResponseList;
	}

	@Override
	public BgcStatusDTO getBgcStatusDetailsById(Long statusId) {
		 BgcStatusDTO BgcStatusDto = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			BgcStatus bgcStatus = (BgcStatus) session.get(BgcStatus.class, statusId);
			if(bgcStatus != null) {
				BgcStatusDto = new BgcStatusDTO();
				BeanUtils.copyProperties(bgcStatus, BgcStatusDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return BgcStatusDto;
	}

	@Override
	public String saveOrUpdateBgcComponentListByOrgLocationId(BgcComponent bgcComponent) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(bgcComponent);
			return Constants.SUCCESS;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving bgcComponent Details in Database "+e.getMessage());
			return null;
		}
    }

	@Override
	public BgcComponentDTO getBgcComponentDetailsById(Long componentId) {
		BgcComponentDTO BgcComponentDto = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				BgcComponent bgcComponent = (BgcComponent) session.get(BgcComponent.class, componentId);
				if(bgcComponent != null) {
					BgcComponentDto = new BgcComponentDTO();
					BeanUtils.copyProperties(bgcComponent, BgcComponentDto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return BgcComponentDto;
	}

	@Override
	public String deleteBgcComponentById(Long componentId) {
		try {
			   Session session = this.sessionFactory.getCurrentSession();
			   Query query = session.createQuery("delete from BgcComponent where id=:componentId").setLong("componentId", componentId);
		        int count = query.executeUpdate();
		        if(count != 0) {
		        	return Constants.SUCCESS;
		        }
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete  BgcComponent by id-->" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			return Constants.FAILED;
	}

	@Override
	public List<BgcStatus> getBgcUniqueStatusListByOrgLocationId(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from  BgcStatus status where status.orgLocationId = :orgLocationId order by status.updatedDate desc, status.id desc")
					       .setLong("orgLocationId", orgLocationId);
			List<BgcStatus> statusList = query.list();
			return statusList;
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while getting status list by org location id-->" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BgcComponent> getBgcUniqueComponentListByOrgLocationId(Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from  BgcComponent comp where comp.orgLocationId = :orgLocationId order by comp.updatedDate desc, comp.id desc")
					       .setLong("orgLocationId", orgLocationId);
			List<BgcComponent> componentList = query.list();
			return componentList;
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while getting component list by org location id-->" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BgcTemplate saveOrUpdateBgcTemplateByOrgLocationId(BgcTemplate bgcTemplate, OrgLocations orgLocations) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(bgcTemplate);
			return bgcTemplate;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving BGC Template Details in Database in Database "+e.getMessage());
			return null;
		}
	}

	@Override
	public BgcTemplateDTO getBgcTemplateDetailsByUuid(String uuid) {
		BgcTemplateDTO bgcTemplateDTO = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				
				BgcTemplate bgcTemplate= (BgcTemplate) session.createQuery("from BgcTemplate template where template.uuid =:uuid").setString("uuid", uuid).uniqueResult();
				if(bgcTemplate != null) {
					bgcTemplateDTO = new BgcTemplateDTO();
					BeanUtils.copyProperties(bgcTemplate, bgcTemplateDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return bgcTemplateDTO;
	}
	
	@Override
	public BgcTemplate getBgcTemplateDetailsById(Long id) {
		BgcTemplate template = null;
			try {
				Session session = sessionFactory.getCurrentSession();
				template = (BgcTemplate)session.load(BgcTemplate.class, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return template;
	}

	@Override
	public String deleteBgcTemplateByUuid(String templateUuid) {
		try {
			   Session session = this.sessionFactory.getCurrentSession();
			   Query query = session.createQuery("delete from BgcTemplate where uuid=:templateUuid").setString("templateUuid", templateUuid);
		        int count = query.executeUpdate();
		        if(count != 0) {
		        	return Constants.SUCCESS;
		        }
			} catch (HibernateException e) {
				logger.error("SQL Exception occured while doing delete  BgcComponent by id-->" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			return Constants.FAILED;
	}

	@Override
	public BgcComponentJsonMap saveTemplateComponentJsonMap(BgcComponentJsonMap bgcComponentJsonMap) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(bgcComponentJsonMap);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception caught while saving bgcComponent Details in Database "+e.getMessage());
		}
		return bgcComponentJsonMap;
	}

	@Override
	public BgcComponentJsonMap getBgcComponentJsonMapByTempalteIdAndFromId(Long templateId, String formId) {
		BgcComponentJsonMap bgcComponentJsonMap = null;
		try {
			Session session = sessionFactory.getCurrentSession();

			bgcComponentJsonMap = (BgcComponentJsonMap) session
					.createQuery(
							"from BgcComponentJsonMap comp where comp.bgcTemplate.id =:templateId and comp.formId =:formId")
					.setLong("templateId", templateId).setString("formId", formId).uniqueResult();
			System.out.println("query executed .....>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bgcComponentJsonMap;
	}
}
