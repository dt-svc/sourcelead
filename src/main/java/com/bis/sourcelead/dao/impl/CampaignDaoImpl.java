package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
@Repository
public class CampaignDaoImpl implements CampaignDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreateSender createSender(CreateSender createSender) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (createSender != null) {
				session.saveOrUpdate(createSender);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return createSender;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean validateNickName(String nickName, OrgLocations orgLocation,Long senderId) throws Exception
	{
		boolean isNickNameDuplicate = false;
		Session session = this.sessionFactory.getCurrentSession();
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.nickName = :nickName and cSender.id <> :senderId and cSender.orgLocations.id = :orgLocationId").setString("nickName", nickName).setLong("senderId", senderId).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if (createSenderList != null && createSenderList.size() > 0) {
			isNickNameDuplicate = true;
		}
		return isNickNameDuplicate;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CreateSender> getSendersListByOrgLocationId(OrgLocations orgLocation) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CreateSender> senderList = new ArrayList<>();
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.orgLocations.id = :orgLocationId and cSender.status = 1").setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if (createSenderList != null && createSenderList.size() > 0) {
			senderList = createSenderList;
		}
		return senderList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreateSender findSenderIdentityById(Long senderId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		CreateSender createSender = null;
		List<CreateSender> senderList = new ArrayList<>();
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.id = :senderId").setLong("senderId", senderId).setCacheable(true).list();
		if (createSenderList != null && createSenderList.size() > 0) {
			createSender = createSenderList.get(0);
		}
		return createSender;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CampaignList saveRecipientList(CampaignList campaignList) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (campaignList != null) {
				session.saveOrUpdate(campaignList);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return campaignList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CreateSender> getSenderIdentityByUserChoice(User user, OrgLocations orgLocations) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.createdBy.id = :userId and cSender.orgLocations.id = :orgLocationId and cSender.status = 1").setLong("userId", user.getId()).setLong("orgLocationId", orgLocations.getId()).setCacheable(true).list();
		return createSenderList;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CampaignList> getRecipientListByUserId(User user, OrgLocations orgLocation,CampaignListDTO campaignListDTO) throws Exception
	{
		int campaignType = Integer.parseInt(campaignListDTO.getTypeOfCampaign());
		Session session = this.sessionFactory.getCurrentSession();
		List<CampaignList> cpnList = null;
		ResponseList<CampaignList> response = new ResponseList<>();
		String query = "from CampaignList cpnList left join fetch cpnList.orgLocation where cpnList.createdBy.id = :userId and cpnList.orgLocation.id = :orgLocationId and cpnList.status = 1 and cpnList.campaignType = :campaignType";
		String countQuery = "from CampaignList cpnList  where cpnList.orgLocation.id = :orgLocationId and cpnList.createdBy.id = :userId and cpnList.status = 1 and cpnList.campaignType = :campaignType";
		Query queryObj = session.createQuery(query);
		if (campaignListDTO != null || campaignListDTO.getLength() > 0) {
			queryObj.setFirstResult(campaignListDTO.getStart());
			queryObj.setMaxResults(campaignListDTO.getLength());
			queryObj.setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", campaignType).setCacheable(true).list();
		}
		cpnList = queryObj.list();
		int count = 0;
		int filteredCount = 0;

		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", campaignType).setCacheable(true).list();

		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (campaignListDTO != null) {

			List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery).setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", campaignType).list();

			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

		response.setData(cpnList);
		return response;
		
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CampaignList getCampaignListById(long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CampaignList campaignList where campaignList.id=:id";
		List<CampaignList> campaignList = session.createQuery(query).setLong("id", id).setCacheable(true).list();
		if(campaignList != null && campaignList.size()>0){
			return campaignList.get(0);
		}else{
			return null;
		}
	}	
    @Override
	@Transactional(rollbackFor = { Exception.class })
	public CampaignList getRecipientListByListId(String listId,int campaignType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		CampaignList campaignList = null;
		List<CampaignList> cpnList = session.createQuery("from CampaignList cpnList left join fetch cpnList.orgLocation where cpnList.listId= :listId and cpnList.status = 1 and cpnList.campaignType = :campaignType").setString("listId", listId).setInteger("campaignType", campaignType).setCacheable(true).list();
		
		if(cpnList !=null && cpnList.size() >0) {
			campaignList = cpnList.get(0);
		}
		return campaignList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Campaign saveCampaignScheduledHistroy(Campaign campaign) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (campaign != null) {
				session.saveOrUpdate(campaign);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return campaign;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from " + fromTable
				+ " frm where frm.orgLocation.id = :orgLocationId and frm.status = '1' and frm.listName = :name";
		List<Long> found = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("name", name).setCacheable(true).list();
		if (found.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public List<CampaignRecipient> getRecipientListByListIdAndOrgLocationId(String viewListId, Long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
//		CampaignRecipient campaignRecipient = null;
		List<CampaignRecipient> cpnList = session.createQuery("from CampaignRecipient cpnres where cpnres.campaignListId.id = :viewListId and cpnres.status = 1").setString("viewListId", viewListId).setCacheable(true).list();
		
//		if(cpnList !=null && cpnList.size() >0) {
//			campaignRecipient = cpnList.get();
//		}
		return cpnList;
	}


    @Override
	@Transactional(rollbackFor = { Exception.class })
	public int deleteRecipientListByListId(Long campaignListId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query qry = null;
			String query = "delete from CampaignRecipient creci where creci.campaignListId.id =:campaignListId ";
	    	qry = session.createQuery(query).setLong("campaignListId", campaignListId);
		int i = qry.executeUpdate();
		return i;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CampaignStats saveCampaignStats(CampaignStats campaignStats) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (campaignStats != null) {
				session.saveOrUpdate(campaignStats);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return campaignStats;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Campaign> getAllCampaigns() throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Campaign> cpnList = session.createQuery("from Campaign").setCacheable(true).list();
		return cpnList;
	}

	@Override
	@Transactional
	public List<CampaignStats> getCampaignStatsListByOrgLocationId(Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CampaignStats> cpnStatsList = session.createQuery("from CampaignStats cpnstats where cpnstats.orgLocation.id = :orgLocationId and cpnstats.status = 1").setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return cpnStatsList;
	}


}
