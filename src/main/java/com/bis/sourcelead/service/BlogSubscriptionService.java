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

}
