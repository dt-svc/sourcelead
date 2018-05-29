package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.BlogSubscriptionDTO;
import com.bis.sourcelead.entity.BlogSubscription;

/**
 * 
 *
 */
public interface BlogSubscriptionService {

	void addBlogSubscription(BlogSubscriptionDTO blogSubscriptionDTO) throws Exception;
	
	BlogSubscription getBlogSubscriptionByBlogId(Long blogSubscriptionId) throws Exception;
	
	void sendEmail(BlogSubscriptionDTO blogSubscriptionDTO) throws Exception;
	
	public BlogSubscription validateBlogSubscriptionByEmailAndSubscriptionUUID(String email,String subscriptionUUID) throws Exception;
	
	Boolean validateBlogSubscriptionByEmail(String email) throws Exception;
	
	BlogSubscription getBlogSubscriptionBySubscriptionUUID(String subscriptionUUID) throws Exception;

}
