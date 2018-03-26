package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.BlogSubscription;

public interface BlogSubscriptionDao {
	
	BlogSubscription addBlogSubscription(BlogSubscription blogSubscription);
	
	BlogSubscription getBlogSubscriptionByBlogId(Long blogSubscriptionId) throws Exception;

}
