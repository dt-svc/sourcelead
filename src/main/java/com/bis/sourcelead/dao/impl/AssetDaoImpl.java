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

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssetDaoImpl implements AssetDao{
	
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(AssetDaoImpl.class);


	@Override
	@Transactional
	public Asset saveAsset(Asset asset) throws Exception {
		logger.debug("Asset");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (asset != null) {
				session.saveOrUpdate(asset);
			}
			logger.debug("Saving Asset");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Asset"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return asset;
	}
	
	/**
	 * @author Prasad salina
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	@Override
	@Transactional
	public List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String[] category = null;
		String subquery1 = "";
		if(categoryIds != null){
			category = categoryIds.split(",");
		}
		if (category != null && category[0] != null && category[0].length() > 0) {
			for (String cat : category) {
				  subquery1 = subquery1 + " asset.assetCategory.id = '"+cat+"' OR ";
			}
			if(!subquery1.equalsIgnoreCase("")){
				subquery1 = " (" + subquery1.substring(0, subquery1.length() - 4) + ")";
				subquery1 = " and " +subquery1;
			}
		}
		String query = "from Asset asset left join fetch asset.shiftAEmployee.user left join fetch asset.shiftBEmployee.user  where asset.orgLocations.id = :orgLocationId and asset.status = '1' "+subquery1+" "; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return assetList;
	}

	@Override
	@Transactional
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Asset asset left join fetch asset.shiftAEmployee.user left join fetch asset.shiftBEmployee.user  where asset.orgLocations.id = :orgLocationId and asset.status = '1' "; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return assetList;
	}

}
