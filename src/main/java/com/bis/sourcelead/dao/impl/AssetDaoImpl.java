package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.AssetServices;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.reportsDTO.AssetGrowthDTO;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class AssetDaoImpl implements AssetDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    OrgLocationService orgLocationService;
	
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
	 * @param Id
	 * @return
	 * @throws Exception
	 * To get the asset by Id.
	 * This method used in BackOfficeRestController
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Asset getAssetById(Long id) throws Exception {
		logger.debug("In AssetDaoImpl getAssetById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Asset) session.get(Asset.class, id);
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Asset getAssetByUUID(String  assetUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Asset asset  where asset.assetUUID =:assetUUID ";
		List<Asset> assetList =session.createQuery(query) .setString("assetUUID", assetUUID).list();
		Asset asset = null;
		if(assetList != null && !assetList.isEmpty())
		{
			asset = (Asset) assetList.iterator().next();
		}
		return asset;
	}
	
	/**
	 * 
	 * @param orgLocationId
	 * @param categoryIds
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	@Override
	@Transactional
	public List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds,String workingStatus) throws Exception {
		logger.debug("In AssetDaoImpl getAssetFilterListByLocationId method");
		Session session =  this.sessionFactory.getCurrentSession();
		String[] category = null;
		String subquery1 = "";
		
		String[] status = workingStatus.split(",");
		String subquery2 = "";
		
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
		
		if (Arrays.asList(status).contains("Working")) {
			subquery2 = subquery2 + "asset.workingStatus = 'Working' OR ";
		}
		if (Arrays.asList(status).contains("Out Of Service")) {
			subquery2 = subquery2 + "asset.workingStatus = 'Out Of Service' OR ";
		}
		if(!subquery2.equalsIgnoreCase("")){
			subquery2 = " (" + subquery2.substring(0, subquery2.length() - 4) + ")";
			subquery2 = " and " +subquery2;
		}
		
		String query = "from Asset asset left join fetch asset.shiftAEmployee.user left join fetch asset.shiftBEmployee.user  where asset.orgLocations.id = :orgLocationId and asset.status = '1' "+subquery1+" "+subquery2+" "; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return assetList;
	}

	@Override
	@Transactional
	public List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception {
		
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Asset asset   where asset.orgLocations.id = :orgLocationId and asset.status = '1'"; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
		return assetList;
	}

	@Override
	@Transactional
	public Asset getAssetByAssetSnoAndId(String assetSno, Long assetId, Long orgLocationsId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Asset asset where asset.slno = :assetSno and asset.orgLocations.id = :orgLocationsId and asset.id = :assetId and asset.status = '1'";
		List<Asset> assetList =  session.createQuery(query) .setString("assetSno", assetSno).setLong("assetId", assetId).setLong("orgLocationsId", orgLocationsId).list();
		Asset asset = null;
		if(assetList != null && !assetList.isEmpty())
		{
			asset = (Asset) assetList.iterator().next();
		}
		return asset;
	}

	@Override
	@Transactional
	public Asset validateAssetSno(String assetSno, Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
				String query = "from Asset asset where asset.orgLocations.id = :orgLocationsId and asset.slno = :assetSno ";
				List<Asset> assetList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setString("assetSno", assetSno).list();

				if (assetList.size() > 0) {
					return assetList.get(0);
				} else {
					return null;
				}
		 
	}
	
	@Override
	@Transactional
	public Boolean validateAssetId(String assetSno, Long orgLocationsId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
				String query = "from Asset asset where asset.orgLocations.id = :orgLocationsId and asset.slno = :assetSno and asset.status = '1' ";
				List<Asset> assetList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).setString("assetSno", assetSno).list();

				if (assetList.size() > 0) {
					return true;
				} else {
					return false;
				}
		 
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional
	public ResponseList<Asset> getDynamicAssetFilterListByLocationId(Long orgLocationId, AssetDTO assetDTO) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<Asset> response = new ResponseList<>();
		
		String[] category = null;
		String[] status = null;
		String subquery1 = "";
		String subquery2 = "";
		
		if(assetDTO.getWorkingStatus() != null) {
		 status = assetDTO.getWorkingStatus().split(",");
		 if (Arrays.asList(status).contains("Working")) {
				subquery2 = subquery2 + "asset.workingStatus = 'Working' OR ";
			}
			if (Arrays.asList(status).contains("Out Of Service")) {
				subquery2 = subquery2 + "asset.workingStatus = 'Out Of Service' OR ";
			}
			if (Arrays.asList(status).contains("Service")) {
				subquery2 = subquery2 + "asset.workingStatus = 'Service' OR ";
			}
		}
		
		
		
		if(assetDTO.getCategoryIds() != null){
			category = assetDTO.getCategoryIds().split(",");
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
		
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(assetDTO.getSortColumn())) {
				sortingQry = " order by asset."+ assetDTO.getSortColumn() +" "+assetDTO.getSortOrder();
		}
		
		if(!subquery2.equalsIgnoreCase("")){
			subquery2 = " (" + subquery2.substring(0, subquery2.length() - 4) + ")";
			subquery2 = " and " +subquery2;
		}
		
		
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
	   Matcher matcher = pattern.matcher(assetDTO.getSearchValue());
	   java.sql.Date sqlDate = null;
	    if(matcher.matches() == true) {
					if(assetDTO.getSearchValue().contains("/")||(assetDTO.getSearchValue().contains("-"))) {
						String replacedValue = assetDTO.getSearchValue().replaceAll("/", "-");
						    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
							SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
	    }
		StringBuilder query1 = new StringBuilder("");
		if(assetDTO.getSearchValue() !=null && !"".equalsIgnoreCase(assetDTO.getSearchValue())) {
		 query1.append("and (asset.slno like '%" + assetDTO.getSearchValue() + "%' ")
		       .append("OR asset.assetCategory.categoryName like '%" + assetDTO.getSearchValue() + "%' ")
		       .append("OR asset.model like '%" + assetDTO.getSearchValue() + "%' ")
		       .append("OR asset.name like '%" + assetDTO.getSearchValue() + "%' ")
		       .append("OR asset.macAddress like '%" + assetDTO.getSearchValue() + "%' ")
			   .append("OR asset.workingStatus like '%" + assetDTO.getSearchValue() + "%' )");
		    if(sqlDate != null) {
		    	query1.append("OR asset.issueDate like '%" + sqlDate + "%' ");
			 }
					
		}
		
		List<String> tagsList = new ArrayList<>();
		if(assetDTO.getTags() != null && !assetDTO.getTags().isEmpty()) {
			String[] tags = assetDTO.getTags().split(",");
			for(int i=0; i<tags.length; i++){
				tagsList.add(tags[i]);
			}
		}else {
			tagsList.add("");
		}
		
		String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and asset.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		
		String subQuery = "";
		if(StringUtils.isNotEmpty(assetDTO.getShiftIds())) {
			subQuery = " and asset.id NOT IN ( select aa.asset.id from AssetAssign aa where aa.shift.id IN("+assetDTO.getShiftIds()+"))";
		}
		String query = "from Asset asset  where asset.orgLocations.id = :orgLocationId and asset.status = '1' "+subQuery+subquery1+" "+subquery2+" "+query1+" "+tagsQuery+" "+sortingQry+""; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("varString", "").setParameterList("tagsList", tagsList).setFirstResult(assetDTO.getStart()).setMaxResults(assetDTO.getLength()).list();
		
		String queryStrForCount = "Select count (*) from Asset asset   where asset.orgLocations.id = :orgLocationId and asset.status = '1' "+subQuery+subquery1+" "+subquery2+" "+tagsQuery+" "+query1+"" ;
		countlist = session.createQuery(queryStrForCount.toString()).setLong("orgLocationId", orgLocationId).setString("varString", "").setParameterList("tagsList", tagsList).list();
		filterCount = countlist.iterator().next().intValue();
		int totalCount = 0;
		if (assetDTO != null) {
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
		
		response.setData(assetList);
		return response;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssetAssign> getUserBasedAssetManagementListandOrgLocation(Long orgLocationId, Long userId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign assetAssign left join fetch assetAssign.asset where assetAssign.asset.orgLocations.id = :orgLocationId and assetAssign.userRole.user.id =:userId ";
		List<AssetAssign> assetList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
      return assetList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AssetAssign> getAssignAssetListByAssetId(Long assetId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign assetAssign left join fetch assetAssign.shift left join fetch assetAssign.userRole where assetAssign.asset.id = :assetId and assetAssign.status = '1'";
		List<AssetAssign> assignAssetList = session.createQuery(query).setLong("assetId", assetId).list();
		return assignAssetList;
	}

	@Override
	@Transactional
	public AssetAssign getAssignAssetByAssetId(Long assetId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (AssetAssign) session.get(AssetAssign.class, assetId);
	}

	@Override
	@Transactional
	public AssetAssign saveAssetAssign(AssetAssign assetAssign) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assetAssign != null) {
				session.saveOrUpdate(assetAssign);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ASSET_ASSIGN"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return assetAssign;
	}

	@Override
	@Transactional
	public List<AssetAssign> getAssignAssetListByShiftId(Long shiftId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign assetAssign where assetAssign.shift.id = :shiftId ";
		List<AssetAssign> assignAssetList = session.createQuery(query).setLong("shiftId", shiftId).list();
		return assignAssetList;
	}

	@Override
	@Transactional
	public void deleteAssignAssetByAssignId(AssetAssign assetAssign) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		try {
			if(assetAssign != null) {
				session.delete(assetAssign);
			}
		}catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ASSET_ASSIGN"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}

	@Override
	@Transactional
	public AssetAssign getAssignAssetById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (AssetAssign) session.get(AssetAssign.class, id);
	}
	 
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AssetAssign> getReturnedOnAssignAssetListByAssetId(Long assetId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign assetAssign left join fetch assetAssign.shift left join fetch assetAssign.userRole where assetAssign.asset.id = :assetId and assetAssign.status = '0'";
		List<AssetAssign> assignAssetList = session.createQuery(query).setLong("assetId", assetId).list();
		return assignAssetList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AssetAssign> getAllAssignAssetListByAssetId(Long assetId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign assetAssign left join fetch assetAssign.shift left join fetch assetAssign.userRole where assetAssign.asset.id = :assetId";
		List<AssetAssign> assignAssetList = session.createQuery(query).setLong("assetId", assetId).list();
		return assignAssetList;
	}

	@Override
	@Transactional
	public AssetServices saveAssetService(AssetServices assetServices) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assetServices != null) {
				session.saveOrUpdate(assetServices);
			}
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ASSET_SERVICE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return assetServices;
	}

	@Override
	@Transactional
	public List<AssetServices> getAssetServicesListByAssetId(Long assetId) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from AssetServices assetServices where assetServices.asset.id = :assetId and assetServices.status ='1'";
		List<AssetServices> assetServicestList = session.createQuery(query).setLong("assetId", assetId).list();
		return assetServicestList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssetGrowthDTO> getAssetGrowthListByOrgloationIdAndAssetGrowthDTO(Long orgLocationId, AssetGrowthDTO assetGrowthDTO,Date[] searchDates) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		List<Long> categoryIdList = new ArrayList<>();
		if(StringUtils.isNotBlank(assetGrowthDTO.getCategoryIds())) {
			String[] catIds = assetGrowthDTO.getCategoryIds().split(",");
			for(int i=0; i<catIds.length; i++) {
				categoryIdList.add(Long.valueOf(catIds[i]));
			}
		}
		String catQury = " ";
		List<AssetGrowthDTO> assetGrowthList;
		if(categoryIdList.size() > 0) {
		 catQury = "and cat.ID IN (:categoryIdList)";
		}
		
		String dateQury;
		String  startDate =commonUtil.elkDateFormatter(searchDates[0]);
		String  endDate  = commonUtil.elkDateFormatter(searchDates[1]);
		
		dateQury = " and DATE_FORMAT(ass.PURCHASE_DATE,'%Y-%m-%d') BETWEEN  '"+startDate +"' and '"+endDate+"'";
		
		String query = "SELECT ass.ID as assestId, " + 
						"	ass.SL_NO as slNo, " + 
						"   ass.ASSET_CATEGORY_ID as categoryId, " + 
						"   ass.PURCHASE_DATE as purchaseDate, " + 
						"   ass.COST as cost," +
						"   cat.CATEGORY_NAME as catgoryName " + 
						" FROM ASSET ass,ASSET_CATEGORY cat " + 
						"where ass.ASSET_CATEGORY_ID = cat.ID and ass.STATUS = '1' and ass.ORG_LOCATION_ID = "+orgLocationId+" "+catQury+" "+dateQury+" order by ass.PURCHASE_DATE desc";
		if(categoryIdList.size() > 0) {
			assetGrowthList = session.createSQLQuery(query).setParameterList("categoryIdList", categoryIdList).setResultTransformer(Transformers.aliasToBean(AssetGrowthDTO.class)).list();
		}else {
			assetGrowthList = session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(AssetGrowthDTO.class)).list();
		}
		for(AssetGrowthDTO asset : assetGrowthList) {
			asset.setMonthNames(new SimpleDateFormat("MMMM").format(asset.getPurchaseDate()));
			asset.setMonthNumber(asset.getPurchaseDate().getMonth());
			if (asset.getCost() != null) 
			asset.setCostDoble(asset.getCost().doubleValue());
		}
		return assetGrowthList;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional
	public ResponseList<AssetAssign> getDynamicAssignAssetFilterListByLocationId(Long orgLocationId, AssetDTO assetDTO)
			throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<AssetAssign> response = new ResponseList<>();
		String[] category = null;
		String[] status = null;
		String subquery1 = "";
		String subquery2 = "";
		
		if(assetDTO.getWorkingStatus() != null) {
		 status = assetDTO.getWorkingStatus().split(",");
		 if (Arrays.asList(status).contains("Working")) {
				subquery2 = subquery2 + "assetAssign.asset.workingStatus = 'Working' OR ";
			}
			if (Arrays.asList(status).contains("Out Of Service")) {
				subquery2 = subquery2 + "assetAssign.asset.workingStatus = 'Out Of Service' OR ";
			}
			if (Arrays.asList(status).contains("Service")) {
				subquery2 = subquery2 + "assetAssign.asset.workingStatus = 'Service' OR ";
			}
		}
		
		 
		
		if(assetDTO.getCategoryIds() != null){
			category = assetDTO.getCategoryIds().split(",");
		}
		if (category != null && category[0] != null && category[0].length() > 0) {
			for (String cat : category) {
				  subquery1 = subquery1 + " assetAssign.asset.assetCategory.id = '"+cat+"' OR ";
			}
			if(!subquery1.equalsIgnoreCase("")){
				subquery1 = " (" + subquery1.substring(0, subquery1.length() - 4) + ")";
				subquery1 = " and " +subquery1;
			}
		}
		
		//usertList sorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(assetDTO.getSortColumn())) {
			if(assetDTO.getSortColumn().equalsIgnoreCase("name") || assetDTO.getSortColumn().equalsIgnoreCase("slno") || assetDTO.getSortColumn().equalsIgnoreCase("assetCategory")) {
				sortingQry = " order by assetAssign.asset."+ assetDTO.getSortColumn() +" "+assetDTO.getSortOrder();
			}else if(assetDTO.getSortColumn().equalsIgnoreCase("firstName")) {
				sortingQry = " order by assetAssign.userRole.user."+ assetDTO.getSortColumn() +" "+assetDTO.getSortOrder();
			}else {
				sortingQry = " order by assetAssign."+ assetDTO.getSortColumn() +" "+assetDTO.getSortOrder();
			}
				
		}
		
		if(!subquery2.equalsIgnoreCase("")){
			subquery2 = " (" + subquery2.substring(0, subquery2.length() - 4) + ")";
			subquery2 = " and " +subquery2;
		}
		
		
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
	   Matcher matcher = pattern.matcher(assetDTO.getSearchValue());
	   java.sql.Date sqlDate = null;
	    if(matcher.matches() == true) {
					if(assetDTO.getSearchValue().contains("/")||(assetDTO.getSearchValue().contains("-"))) {
						String replacedValue = assetDTO.getSearchValue().replaceAll("/", "-");
						    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocationId);
							SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
					        java.util.Date utilDate = format.parse(replacedValue);
					        sqlDate = new java.sql.Date(utilDate.getTime());
					}
	    }
		StringBuilder query1 = new StringBuilder("");
		if(assetDTO.getSearchValue() !=null && !"".equalsIgnoreCase(assetDTO.getSearchValue())) {
			 query1.append("and ( assetAssign.userRole.user.firstName like '%" + assetDTO.getSearchValue() + "%' ")
				   .append("OR assetAssign.userRole.user.lastName like '%" + assetDTO.getSearchValue() + "%' ")
				   .append("OR concat( assetAssign.userRole.user.firstName, assetAssign.userRole.user.lastName) like '%" + assetDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%' ")
				   .append("OR assetAssign.asset.slno like '%" + assetDTO.getSearchValue() + "%' ")
		           .append("OR assetAssign.asset.assetCategory.categoryName like '%" + assetDTO.getSearchValue() + "%' ")
		           .append("OR assetAssign.asset.name like '%" + assetDTO.getSearchValue() + "%' ")
		           .append("OR assetAssign.shift.shiftName like '%" + assetDTO.getSearchValue() + "%' )");
		    if(sqlDate != null) {
		    	query1.append("OR assetAssign.assignedOn like '%" + sqlDate + "%' OR assetAssign.returnedOn like '%" + sqlDate + "%'");
			 }
					
		}
		
		List<String> tagsList = new ArrayList<>();
		if(assetDTO.getTags() != null && !assetDTO.getTags().isEmpty()) {
			String[] tags = assetDTO.getTags().split(",");
			for(int i=0; i<tags.length; i++){
				tagsList.add(tags[i]);
			}
		}else {
			tagsList.add("");
		}
		
		String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and assetAssign.asset.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		
		String subQuery = "";
		if(StringUtils.isNotEmpty(assetDTO.getShiftIds())) {
			subQuery = " and  assetAssign.shift.id IN("+assetDTO.getShiftIds()+")";
		}
		String query = "from AssetAssign assetAssign  left join fetch assetAssign.asset asset where asset.orgLocations.id = :orgLocationId and assetAssign.status = '1' "+subQuery+subquery1+" "+subquery2+" "+query1+" "+tagsQuery+" "+sortingQry+""; 
		List<AssetAssign> assetList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setFirstResult(assetDTO.getStart()).setMaxResults(assetDTO.getLength()).setString("varString", "").setParameterList("tagsList", tagsList).list();
		
		String queryStrForCount = "Select count (*) from AssetAssign assetAssign   where assetAssign.asset.orgLocations.id = :orgLocationId and assetAssign.status = '1' "+subQuery+subquery1+" "+subquery2+" "+tagsQuery+" "+query1+"" ;
		countlist = session.createQuery(queryStrForCount.toString()).setLong("orgLocationId", orgLocationId).setString("varString", "").setParameterList("tagsList", tagsList).list();
		filterCount = countlist.iterator().next().intValue();
		int totalCount = 0;
		if (assetDTO != null) {
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
		
		response.setData(assetList);
		return response;
		
	}

	@Override
	@Transactional
	public Boolean validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(Long assetId, Long shiftId, Long userRoleId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssetAssign aa where aa.asset.id = :assetId and aa.shift.id =:shiftId and aa.userRole.id =:userRoleId ";
		List<Asset> assetList = session.createQuery(query).setLong("assetId", assetId).setLong("shiftId", shiftId).setLong("userRoleId", userRoleId).list();
		if (assetList.size() > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	@Transactional
	public List<Asset> getAllAssetByOrgId(Long orgLocationsId) {
		Session session =  this.sessionFactory.getCurrentSession();
		String query = "from Asset asset where asset.orgLocations.id = :orgLocationsId "; 
		List<Asset> assetList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		return assetList;
	}

	@Override
	@Transactional
	public List<AssetAssign> getAssetsReportList(AssetsReportDTO assetsReportDTO, Long orgLocationId) {
		try {
			java.sql.Timestamp fromDate1 = new java.sql.Timestamp(assetsReportDTO.getConvertedFromDate().getTime());
			java.sql.Timestamp toDate1 = new java.sql.Timestamp(assetsReportDTO.getConvertedToDate().getTime());
			java.sql.Date fromDate = new java.sql.Date(fromDate1.getTime());
			java.sql.Date toDate = new java.sql.Date(toDate1.getTime());

			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AssetAssign assetAssign ";
			/*if (assetsReportDTO.getCategories() != null && !assetsReportDTO.getCategories().isEmpty()) {
				query += " join asset.assetCategory assetCategory";
			}*/
			query += " where assetAssign.asset.orgLocations.id = :orgLocationId";
			if (assetsReportDTO.getDateType() == 1) {
				query += " and DATE_FORMAT(assetAssign.assignedOn,'%Y-%m-%d') between '" + fromDate + "' and '" + toDate
						+ "'";
			} else {
				query += " and DATE_FORMAT(assetAssign.returnedOn,'%Y-%m-%d') between '" + fromDate + "' and '" + toDate
						+ "'";
			}
			if (assetsReportDTO.getCategories() != null && !assetsReportDTO.getCategories().isEmpty()) {
				String catIdString = "";
				for (long catId : assetsReportDTO.getCategories()) {
					catIdString += (catIdString.isEmpty() ? "" : ",") + catId;
				}
				query += " and assetAssign.asset.assetCategory.id in (" + catIdString + ")";
			}
			if (assetsReportDTO.getShiftsIds() != null && !assetsReportDTO.getShiftsIds().isEmpty()) {
				String shiftIds = "";
				for (long shiftId : assetsReportDTO.getShiftsIds()) {
					shiftIds += (shiftIds.isEmpty() ? "" : ",") + shiftId;
				}
				query += " and assetAssign.shift.id in (" + shiftIds + ")";
			}
			if (assetsReportDTO.getAssetNames() != null && !assetsReportDTO.getAssetNames().isEmpty()) {
				String assetIds = "";
				for (long assetId : assetsReportDTO.getAssetNames()) {
					assetIds += (assetIds.isEmpty() ? "" : ",") + assetId;
				}
				query += " and assetAssign.asset.id in (" + assetIds + ")";
			}

			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);

			List<AssetAssign> assetAssignList = queryObj.list();

			if (assetAssignList != null && !assetAssignList.isEmpty()) {
				return assetAssignList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteAsset(Asset asset) throws Exception {
		Session session =  this.sessionFactory.getCurrentSession();
		try {
			if(asset != null) {
				session.delete(asset);
			}
		}catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ASSET"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
	}
	
	 

	
}
