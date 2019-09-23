package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.CampaignStatsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
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
	public boolean validateNickName(String nickName, OrgLocations orgLocation,String senderIdentity) throws Exception
	{
		Long senderId = 0l;
		if(StringUtils.isNotBlank(senderIdentity) && !"undefined".equalsIgnoreCase(senderIdentity)) {
			senderId = Long.parseLong(senderIdentity);
		}
			
		boolean isNickNameDuplicate = false;
		Session session = this.sessionFactory.getCurrentSession();
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.nickName = :nickName and cSender.id <> :senderId and cSender.orgLocations.id = :orgLocationId").setString("nickName", nickName).setLong("senderId", senderId).setLong("orgLocationId", orgLocation.getId()).list();
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
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.orgLocations.id = :orgLocationId and cSender.status = 1").setLong("orgLocationId", orgLocation.getId()).list();
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
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.id = :senderId").setLong("senderId", senderId).list();
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
		List<CreateSender> createSenderList = session.createQuery("from CreateSender cSender left join fetch cSender.orgLocations where cSender.createdBy.id = :userId and cSender.orgLocations.id = :orgLocationId and cSender.status = 1").setLong("userId", user.getId()).setLong("orgLocationId", orgLocations.getId()).list();
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
		String query = "from CampaignList cpnList left join fetch cpnList.orgLocation where cpnList.createdBy.id = :userId and cpnList.orgLocation.id = :orgLocationId and cpnList.campaignType = :campaignType and cpnList.status = 1";
		String countQuery = "from CampaignList cpnList  where cpnList.orgLocation.id = :orgLocationId and cpnList.createdBy.id = :userId and cpnList.campaignType = :campaignType and cpnList.status = 1";
		
		StringBuilder query1 = new StringBuilder("");
			if(campaignListDTO.getSearchValue() !=null && !"".equalsIgnoreCase(campaignListDTO.getSearchValue())) {
//				query1 = " and wts.employeeId like %%";
			query1.append(" and cpnList.listName like '%" + campaignListDTO.getSearchValue() + "%' ");
			}
			Query queryObj = session.createQuery(query + query1.toString());
			if (campaignListDTO != null || campaignListDTO.getLength() > 0) {
				queryObj.setFirstResult(campaignListDTO.getStart());
				queryObj.setMaxResults(campaignListDTO.getLength());
				queryObj.setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", Integer.parseInt(campaignListDTO.getTypeOfCampaign())).list();
			}
			cpnList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", Integer.parseInt(campaignListDTO.getTypeOfCampaign())).list();

			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (campaignListDTO != null) {

				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setInteger("campaignType", Integer.parseInt(campaignListDTO.getTypeOfCampaign())).list();

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
		List<CampaignList> campaignList = session.createQuery(query).setLong("id", id).list();
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
		List<CampaignList> cpnList = session.createQuery("from CampaignList cpnList left join fetch cpnList.orgLocation where cpnList.listId= :listId and cpnList.status = 1 and cpnList.campaignType = :campaignType").setString("listId", listId).setInteger("campaignType", campaignType).list();
		
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
		List<Long> found = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("name", name).list();
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
		List<CampaignRecipient> cpnList = session.createQuery("from CampaignRecipient cpnres where cpnres.campaignListId.id = :viewListId and cpnres.status = 1").setString("viewListId", viewListId).list();
		
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
		List<Campaign> cpnList = session.createQuery("from Campaign").list();
		return cpnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CampaignStats> getCampaignStatsListByOrgLocationId(Long orgLocationId, CampaignStatsDTO campaignStatsDTO) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CampaignStats> cpnStatsList = null;
		ResponseList<CampaignStats> response = new ResponseList<>();
		String query = "from CampaignStats cpnstats where cpnstats.orgLocation.id = :orgLocationId and cpnstats.status = 1";
//				.setLong("orgLocationId", orgLocationId).list();
//		String query = "from CampaignStats cpnstats where cpnstats.orgLocation.id = :orgLocationId";
		String countQuery = "from CampaignStats cpnstats where cpnstats.orgLocation.id = :orgLocationId and cpnstats.status = 1";
		
//			List<InvoiceData> invoiceDetailsList = null;
//			ResponseList<InvoiceData> response = new ResponseList<>();
//			String query = "from InvoiceData ind  left join fetch ind.orgLocationId where ind.orgLocationId.id =:orgLocationId";
//			String countQuery = "from InvoiceData ind  where ind.orgLocationId.id =:orgLocationId";
			
			StringBuilder query1 = new StringBuilder("");
				if(campaignStatsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(campaignStatsDTO.getSearchValue())) {
				query1.append(" and cpnstats.categoryName like '%" + campaignStatsDTO.getSearchValue() + "%' ")
						.append("OR cpnstats.opens like '%" + campaignStatsDTO.getSearchValue() + "%' ")
						.append("OR cpnstats.delivered like '%" + campaignStatsDTO.getSearchValue() + "%' ")
						.append("OR cpnstats.uniqueOpens like '%" + campaignStatsDTO.getSearchValue() + "%' ")
						.append("OR cpnstats.uniqueClicks like '%" + campaignStatsDTO.getSearchValue() + "%' ")
						.append("OR cpnstats.unsubscribes like '%" + campaignStatsDTO.getSearchValue() + "%' ");
//						.append("OR ind.lastmodified like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.dueDate like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.rate like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.qty like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.amount like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.arPaid like '%" + campaignStatsDTO.getSearchValue() + "%' ")
//						.append("OR ind.oldNew like '%" + campaignStatsDTO.getSearchValue() + "%' ");
				}
			Query queryObj = session.createQuery(query + query1.toString());
			if (campaignStatsDTO != null && campaignStatsDTO.getLength() > 0) {
				queryObj.setFirstResult(campaignStatsDTO.getStart());
				queryObj.setMaxResults(campaignStatsDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocationId).list();
			}
			cpnStatsList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (campaignStatsDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
//				response.setData(getInvoiceListSorting(invoiceDetailsList));
				response.setData(cpnStatsList);
			
			return response;
		}
			
		
		
		
	
	
	
	

    @Override
	@Transactional(rollbackFor = { Exception.class })
	public CampaignStats getCampaignStatsByCampaignId(String campaignName) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		CampaignStats campaignStats = null;
		List<CampaignStats> statsList = session.createQuery("from CampaignStats cmpStats where cmpStats.categoryName = :campaignName and cmpStats.status = 1").setString("campaignName", campaignName).list();
		if (statsList != null && statsList.size() > 0) {
			campaignStats = statsList.get(0);
		}
		return campaignStats;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Campaign validateCampaignSubject(String subjectName, Long orgLocationId,int campaignType) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Campaign campaign = null;
		List<Campaign> cpnList = session.createQuery("from Campaign cpn where cpn.campaignSubject = :subjectName and cpn.orgLocation.id = :orglocationId and cpn.campaignType = :campaignType").setString("subjectName", subjectName).setLong("orglocationId", orgLocationId).setInteger("campaignType", campaignType).list();
		if (cpnList != null && cpnList.size() > 0) {
			campaign = cpnList.get(0);
		}
		return campaign;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Map<String,Integer> getCampaignListName(String campaignId) throws Exception
	{
		Map<String,Integer> map = new HashMap();
		Session session = this.sessionFactory.getCurrentSession();
		List<Campaign> cpnList = session.createQuery("from Campaign cpn where cpn.campaignId = :campaignId").setString("campaignId", campaignId).list();
		if (cpnList != null && cpnList.size() > 0) {
			Campaign campaign = cpnList.get(0);
			StringBuilder sb = new StringBuilder();
			int numberOfRecipients = 0;
			if(campaign.getListIds() !=null) {
				String[] listIds = campaign.getListIds().split(",");
				for(String listId : listIds) {
					List<CampaignList> campaignList = session.createQuery("from CampaignList cpnlist where cpnlist.listId = :listId").setString("listId", listId).list();
				   if(campaignList !=null && campaignList.size() >0) {
					   CampaignList cpmList = campaignList.get(0);
					   sb.append(cpmList.getListName()).append(",");
					   numberOfRecipients = numberOfRecipients + cpmList.getCampaignRecipient().size();
				   }
				}
				
				
			}
		String listName = sb.toString();
		if(!"".equalsIgnoreCase(listName)) {
			listName = listName.substring(0, listName.length() - 1);
		}
//		System.out.println(numberOfRecipients);	
		map.put(listName, numberOfRecipients);
			
			
		}
		return map;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreateSender validateFromMail(String email, String toWhom,String id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		CreateSender createSender = null;
		Long senderId = 0l;
		if(StringUtils.isNotBlank(id) && !"undefined".equalsIgnoreCase(id)) {
			senderId = Long.parseLong(id);
		}
		String query = "";
		if(!"from".equalsIgnoreCase(toWhom)) {
			 query = "from CreateSender sender where sender.replyTo = :email and sender.status = 1 and sender.id <> :senderId ";
		}else {
			 query = "from CreateSender sender where sender.fromMail = :email and sender.status = 1 and sender.id <> :senderId ";
		}
		List<CreateSender> senderList = session.createQuery(query).setString("email", email).setLong("senderId", senderId).list();
		
		if(senderList !=null && senderList.size() >0) {
			createSender = senderList.get(0);
		}
		return createSender;
	}

}