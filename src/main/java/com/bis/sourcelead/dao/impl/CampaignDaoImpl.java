package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CampaignDao;
import com.bis.sourcelead.entity.CampaignList;
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CampaignList> getRecipientListByUserId(User user, OrgLocations orgLocation) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<CampaignList> cpnList = session.createQuery("from CampaignList cpnList left join fetch cpnList.orgLocation where cpnList.createdBy.id = :userId and cpnList.orgLocation.id = :orgLocationId and cpnList.status = 1 and cpnList.campaignType = 1").setLong("userId", user.getId()).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		return cpnList;
	}
	
	
}
