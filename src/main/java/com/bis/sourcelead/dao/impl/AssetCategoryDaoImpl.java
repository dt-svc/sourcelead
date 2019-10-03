package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.hibernate.Query;
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
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Projects;
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
		String query = "from AssetCategory ac where ac.orgLocations.id = :orgLocationId and ac.status = '1' or (ac.defaultCategory = '1')"; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return assetCategoryList;
	}



	@Override
	@Transactional
	public AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.orgLocations.id = :orgLocationId and ac.status = '1' and ac.id = :categoryId"; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setLong("orgLocationId", catLocationId).setLong("categoryId", categoryId).list();
		return assetCategoryList.get(0);
	}
	
	@Override
	@Transactional
	public AssetCategory getAssetCategoryByCategoryId(Long categoryId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.status = '1' and ac.id = :categoryId"; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setLong("categoryId", categoryId).list();
		return assetCategoryList.get(0);
	}



	@Override
	@Transactional
	public Boolean validateCategoryName(String categoryName, Long orgLocationId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.orgLocations.id = :orgLocationId and ac.categoryName = :categoryName"; 
		List<AssetCategory> assetCategoryList = session.createQuery(query.toString()).setString("categoryName", categoryName).setLong("orgLocationId", orgLocationId).list();
         boolean isCategoryFound = false;
		if (assetCategoryList != null && !assetCategoryList.isEmpty()) {
         	isCategoryFound = true;
         }
         return isCategoryFound;
	}



	@Override
	@Transactional
	public AssetCategory getAssetCategoryListByLocationIdAndName(Long orgLocationId, String name)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<AssetCategory> assetCategoryList = null;
		name=	WordUtils.capitalizeFully(name);
		if(name.equals("Laptop") || name.equals("Desktop") || name.equals("Others") ){
		 query = "from AssetCategory ac where ac.status = '1' and ac.categoryName =:name";
		 assetCategoryList = session.createQuery(query.toString()).setString("name", name).list();
		}else{
		 query = "from AssetCategory ac where ac.orgLocations.id =:orgLocationId and ac.status = '1' and ac.categoryName =:name";
		 assetCategoryList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("name", name).list();
		}
		AssetCategory category = null;
		if (assetCategoryList != null && assetCategoryList.size() > 0) {
			category = (AssetCategory) assetCategoryList.iterator().next();
		}
		return category;
	}



	@Override
	@Transactional
	public AssetCategory getAssetCategoryByCategoryIdAndLocationId(String categoryName, Long categoryId,
			Long orglocationId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetCategory ac where ac.categoryName = :categoryName and ac.orgLocations.id = :orglocationId and ac.id = :categoryId and ac.status = '1'";
		List<AssetCategory> assetCategoryList =  session.createQuery(query) .setString("categoryName", categoryName).setLong("categoryId", categoryId).setLong("orglocationId", orglocationId).list();
		AssetCategory assetCategory = null;
		if(assetCategoryList != null && !assetCategoryList.isEmpty())
		{
			assetCategory = (AssetCategory) assetCategoryList.iterator().next();
		}
		return assetCategory;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getAssetCategoryStringtByLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" CAT.CATEGORY_NAME "
				+" FROM ASSET_CATEGORY CAT "
				+" WHERE "
				+" CAT.ORG_LOCATION_ID = :orgLocationId AND CAT.STATUS = '1' OR (CAT.DEFAULT_CATEGORY = '1') ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> categoryList = (List<String>)queryObj.list();
		return categoryList;
	}
}
