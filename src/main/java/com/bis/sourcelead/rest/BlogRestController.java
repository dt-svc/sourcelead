package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.BlogSubscriptionDTO;
import com.bis.sourcelead.entity.BlogSubscription;
import com.bis.sourcelead.service.BlogSubscriptionService;
import com.bis.sourcelead.util.CommonUtil;

@RestController
public class BlogRestController {
	
private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
    @Autowired
	CommonUtil commonUtil;
    
    @Autowired
    BlogSubscriptionService blogSubscriptionService;
    
    
    @RequestMapping(value = "/addBlogSubscription", method = RequestMethod.POST)
	public void addBlogSubscription(BlogSubscriptionDTO blogSubscriptionDTO, HttpServletRequest request) throws Exception {
		blogSubscriptionService.addBlogSubscription(blogSubscriptionDTO);
	}
		 
    /**
     * validate Blog Subscription Email 
     * @param email
     * @param subscriptionUUID
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/validateBlogSubscriptionEmail", method = RequestMethod.POST)
	public @ResponseBody Boolean validateBlogSubscriptionEmail(@RequestParam("email") String email) throws Exception
	{
		 BlogSubscription  blogSubscription = null;
		 Boolean flag = false;
		 if(null == blogSubscription) {
			 flag = blogSubscriptionService.validateBlogSubscriptionByEmail(email);
		 }
	    return flag;
	}	 
		

}
