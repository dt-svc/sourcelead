package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class LetterHeadDaoImpl implements LetterHeadDao{
	
	private static final Logger logger = LoggerFactory.getLogger(LetterHeadDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public LetterHead storeLetterHeadData(LetterHead letterHead)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (letterHead != null) {
				session.saveOrUpdate(letterHead);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return letterHead;
	}

	@Override
	@Transactional
	public ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LetterHead ltr where ltr.orgLocationId.id =:orgLocationId and ltr.status ='1'";
		
		List<LetterHeadDTO> letterHeadList = null;
		ResponseList<LetterHeadDTO> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(letterHeadDTO.getSortColumn())) {
			qry = " order by ltr."+ letterHeadDTO.getSortColumn() +" "+letterHeadDTO.getSortOrder();
		}
		
		StringBuilder query1 = new StringBuilder("");
			if(letterHeadDTO.getSearchValue() !=null && !"".equalsIgnoreCase(letterHeadDTO.getSearchValue())) {
					query1.append(" and (ltr.letterHeadName like '%" + letterHeadDTO.getSearchValue() + "%') ");
//					.append("OR ltr.letterHeadDescription.lastName like '%" + letterHeadDTO.getSearchValue() + "%' )");
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (letterHeadDTO != null && letterHeadDTO.getLength() > 0) {
			queryObj.setFirstResult(letterHeadDTO.getStart());
			queryObj.setMaxResults(letterHeadDTO.getLength());
		}
		letterHeadList = queryObj.setLong("orgLocationId", orgLocationId).list();
		int count = 0;
		int filteredCount = 0;

		List<Long> countlist = session.createQuery("select count(*) " + query).setLong("orgLocationId", orgLocationId).list();

		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (letterHeadDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + query + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 
			response.setData(letterHeadList);
			return response;
	}

	@Override
	@Transactional
	public List<LetterHead> getLetterheadBylocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from LetterHead ltr where ltr.orgLocationId.id =:orgLocationId and ltr.status ='1'";
		List<LetterHead> list = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return list;
	}

	@Override
	@Transactional
	public LetterHead getLetterheadByUUID(String UUID) {
		Session session = this.sessionFactory.getCurrentSession();
		LetterHead  letter =  null;
		String query ="from LetterHead ltr where ltr.letterHeadUUID =:UUID and ltr.status ='1'";
		List<LetterHead> list = session.createQuery(query).setString("UUID", UUID).list();
		if(null != list && list.size() >0){
			letter = list.get(0);
		}
		return letter;
	}

	@Override
	@Transactional
	public LetterHead getLetterheadById(Long letterHeadId) {
		Session session = this.sessionFactory.getCurrentSession();
		LetterHead  letter =  null;
		String query ="from LetterHead ltr where ltr.id =:letterHeadId and ltr.status ='1'";
		List<LetterHead> list = session.createQuery(query).setLong("letterHeadId", letterHeadId).list();
		if(null != list && list.size() >0){
			letter = list.get(0);
		}
		return letter;
	}

	@Override
	@Transactional
	public LetterHead updateLetterhead(LetterHead letter) {
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(letter);
	    logger.info("Successfully updated..!");
		return letter;
	}

	@Override
	@Transactional
	public Boolean validateLetterHeadName(String letterHeadName, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from LetterHead ltr where ltr.letterHeadName =:letterHeadName and ltr.orgLocationId.id =:orgLocationId and ltr.status = '1'";
		LetterHead list = (LetterHead)session.createQuery(query).setString("letterHeadName", letterHeadName).setLong("orgLocationId", orgLocation.getId()).uniqueResult();
		if(null != list ){
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public LetterHead validateNameInEdit(String letterHeadName,Long letterHeadId,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from LetterHead ltr where ltr.letterHeadName =:letterHeadName and ltr.id =:letterHeadId and ltr.orgLocationId.id =:orgLocationId and ltr.status = '1'";
		LetterHead list = (LetterHead)session.createQuery(query).setString("letterHeadName", letterHeadName).setLong("orgLocationId", orgLocationId).setLong("letterHeadId", letterHeadId).uniqueResult();
		/*if(null != list ){
			return true;
		}else {
			return false;
		}*/
		return list;
	}

	
}
