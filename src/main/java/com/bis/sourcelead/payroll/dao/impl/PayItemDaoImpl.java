package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.PayItemDao;
import com.bis.sourcelead.payroll.dto.PayItemDTO;
import com.bis.sourcelead.payroll.entity.PayItem;

@Repository
public class PayItemDaoImpl implements PayItemDao{

	private static final Logger logger = LoggerFactory.getLogger(PayItemDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PayItem addPayItem(PayItem payItem)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payItem != null) {
					session.saveOrUpdate(payItem);
			}
		} catch (Exception exception) {
			logger.error("add pay item failed due to : "+exception.getMessage());
		}
		return payItem;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<PayItem> getPayItemByLocationId(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<PayItem> res = new ResponseList<>();
		List<PayItem>   payItemList = session.createQuery("from PayItem payItem where payItem.orgLocationId.id =:orgLocationId and payItem.status = '1'" ).setLong( "orgLocationId", orgLocation.getId()).list();
		res.setData(payItemList);
		return res;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PayItem getPayItemByUUID(String paymentUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<PayItem> res = new ResponseList<>();
		List<PayItem> payItemList = null;
		payItemList = session.createQuery("from PayItem payItem where payItem.payItemUuid =:paymentUUID" ).setString( "paymentUUID", paymentUUID).list();
		if(payItemList != null && payItemList.size() > 0 ){
			return payItemList.get(0);
		}else{
			return null;
		}
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public boolean validatePayItemName(PayItemDTO payItemDTO, Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<PayItem> itemList = null;
		Long id = 0l;
		if(payItemDTO.getId() != null){
			id = payItemDTO.getId();
		}
		String query = "from PayItem payItem where payItem.orgLocationId.id = :orgLocationId and payItem.payItemName = :groupId and payItem.id <> :id and payItem.status <> 0";
		itemList = session.createQuery(query.toString()).setLong("orgLocationId", locationId).setString("groupId", payItemDTO.getPayItemName()).setLong("id", id).list();
		if(itemList != null && itemList.size() > 0){
			return true;
		}else{
			return false;
		}
	}



}
