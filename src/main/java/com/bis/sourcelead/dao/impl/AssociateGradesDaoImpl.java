package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
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

import com.bis.sourcelead.dao.AssociateGradesDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AssociateGradesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssociateGradesDaoImpl implements AssociateGradesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
    OrgLocationService orgLocationService;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(AssociateGradesDaoImpl.class);


	@Override
	@Transactional
	public AssociateGrades saveAssociateGrades(AssociateGrades associateGrades) throws Exception {
		
		logger.debug("Adding AssociateGrades");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateGrades != null) {
				session.saveOrUpdate(associateGrades);
			}
			logger.debug("AssociateGrades Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_GRADES"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public AssociateGrades getAssociateGradesByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateGrades grades where grades.uuid = :uuid and grades.status = '1' ";  
		List<AssociateGrades> associateGradesList =  session.createQuery(query).setString("uuid", uuid).list();
		AssociateGrades associateGrades = null;
		if (associateGradesList != null && associateGradesList.size() > 0) {
			associateGrades = (AssociateGrades) associateGradesList.iterator().next();
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public List<AssociateGrades> getAssociateGradesListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateGrades> associateGradesList = null;
		Query gradesList = session.createQuery("from AssociateGrades grades left join fetch grades.orgLocationId  where grades.orgLocationId = :orgLocationId and grades.status = '1' ");
		associateGradesList = gradesList.setLong("orgLocationId", orgLocationId).list();
		return associateGradesList;
	}


	@Override
	@Transactional
	public ResponseList<AssociateGradesDTO> getAssociateGradesDTOListByOrgLocationId(AssociateGradesDTO associateGradesDTO,
			Long orgLocationId) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<AssociateGradesDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<AssociateGrades> associateGradesList = null;
		List<AssociateGradesDTO> associateGradesDTOList = new ArrayList<AssociateGradesDTO>();
		StringBuilder query1 = new StringBuilder("");
		
		
		
		if(associateGradesDTO.getSearchValue() !=null && !"".equalsIgnoreCase(associateGradesDTO.getSearchValue())) {
			query1.append(" grades.gradeName like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.designation like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.minSalary like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.minSalaryRateType like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.minSalaryRatePer like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.midSalary like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.midSalaryRateType like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.midSalaryRatePer like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.maxSalary like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.maxSalaryRateType like '%" + associateGradesDTO.getSearchValue() + "%' ")
			.append("OR grades.maxSalaryRatePer like '%" + associateGradesDTO.getSearchValue() + "%' ");
						
			}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(associateGradesDTO.getSortColumn())) {
				sortingQry = " order by grades."+ associateGradesDTO.getSortColumn() +" "+associateGradesDTO.getSortOrder();
		}
		
		
		query = "from AssociateGrades grades where grades.orgLocationId = :orgLocationId and grades.status = '1'";
		if(query1.toString() != null && !query1.toString().isEmpty()){
			 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
		}else{
			 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
		}
		
		
		queryObj.setFirstResult(associateGradesDTO.getStart());
		queryObj.setMaxResults(associateGradesDTO.getLength());
		queryObj.setLong("orgLocationId", orgLocationId).list();
		associateGradesList = queryObj.list();
		if(query1.toString() != null && !query1.toString().isEmpty()){
			countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
		}else {
			countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
		}
		
		
		int totalCount = 0;

		
		if (associateGradesDTO != null) {
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
		
		if(associateGradesList != null && !associateGradesList.isEmpty()) {
			for(AssociateGrades grades : associateGradesList) {
				AssociateGradesDTO gradesDTO = new AssociateGradesDTO();
				gradesDTO.setGradeName(grades.getGradeName());
				gradesDTO.setDesignation(grades.getDesignation());
				
				if(grades.getMinSalary() != null) {
					gradesDTO.setMinSalary(String.valueOf(grades.getMinSalary()));
					gradesDTO.setMinSalaryRateType(grades.getMinSalaryRateType());
					gradesDTO.setMinSalaryRatePer(grades.getMinSalaryRatePer());
					
				}
				
				if(grades.getMidSalary() != null) {
					gradesDTO.setMidSalary(String.valueOf(grades.getMidSalary()));
					gradesDTO.setMidSalaryRateType(grades.getMidSalaryRateType());
					gradesDTO.setMidSalaryRatePer(grades.getMidSalaryRatePer());
				}
				
				if(grades.getMaxSalary() != null) {
					gradesDTO.setMaxSalary(String.valueOf(grades.getMaxSalary()));
					gradesDTO.setMaxSalaryRateType(grades.getMaxSalaryRateType());
					gradesDTO.setMaxSalaryRatePer(grades.getMaxSalaryRatePer());
				}
				
				gradesDTO.setUuid(grades.getUuid());
				associateGradesDTOList.add(gradesDTO);
			}
		}
		
		response.setData(associateGradesDTOList);
		return response;
	}


	@Override
	@Transactional
	public AssociateGrades getAssociateGradesByGradeNameAndOrgLocationId(String gradeName, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateGrades grades where grades.gradeName = :gradeName and grades.orgLocationId = :orgLocationId and grades.status = '1' ";  
		List<AssociateGrades> associateGradesList =  session.createQuery(query).setString("gradeName", gradeName).setLong("orgLocationId", orgLocationId).list();
		AssociateGrades associateGrades = null;
		if (associateGradesList != null && associateGradesList.size() > 0) {
			associateGrades = (AssociateGrades) associateGradesList.iterator().next();
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public AssociateGrades getAssociateGradeById(Long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateGrades grades where grades.id = :id";  
		List<AssociateGrades> associateGradesList =  session.createQuery(query).setLong("id", id).list();
		AssociateGrades associateGrades = null;
		if (associateGradesList != null && associateGradesList.size() > 0) {
			associateGrades = (AssociateGrades) associateGradesList.iterator().next();
		}
		return associateGrades;
	}


	@Override
	@Transactional
	public List<String> getGradesListByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" GRADE.GRADE_NAME "
				+" FROM ASSOCIATE_GRADES GRADE "
				+" WHERE "
				+" GRADE.ORGLOCATION_ID = :orgLocationId AND GRADE.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> gradesList = (List<String>)queryObj.list();
		
		return gradesList;
	
	}


	@Override
	@Transactional
	public boolean isGradeExistsByGradeNameAndOrgLocationId(String gradeName, Long orgLocationId) throws Exception {
		
		boolean flag = false;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" GRADE.ID "
				+" FROM ASSOCIATE_GRADES GRADE "
				+" WHERE "
				+" GRADE.ORGLOCATION_ID = :orgLocationId and GRADE.GRADE_NAME = :gradeName AND GRADE.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("gradeName", gradeName);	
		queryObj.setLong("orgLocationId", orgLocationId);	
		BigInteger id = (BigInteger) queryObj.uniqueResult();
		if(id != null) {
			flag = true;
		}
		
		return flag;
	}
}
