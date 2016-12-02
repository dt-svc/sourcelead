package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssetCategoryDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssetCategoryDaoImpl implements AssetCategoryDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(AssetCategoryDaoImpl.class);

	
	
	@Override
	@Transactional
	public AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception {
		logger.debug("Asset Category");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assetCategory != null) {
				session.saveOrUpdate(assetCategory);
			}
			logger.debug("Saving Asset Category");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Asset Category"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return assetCategory;
	}

	
	
	@Override
	@Transactional
	public List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.orgLocations.id = :orgLocationId and ac.status = '1' "; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return assetCategoryList;
	}



	@Override
	@Transactional
	public AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.orgLocations.id = :orgLocationId and ac.status = '1' and ac.id = :categoryId"; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setLong("orgLocationId", catLocationId).setLong("categoryId", categoryId).setCacheable(true).list();
		return assetCategoryList.get(0);
	}


}
