package com.bis.sourcelead.util;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.service.ReferContactsService;

public class SocialInviterUtil {

	 @Async
		public static void storeReferContacts(ArrayList<ReferContacts> refContactsList,HttpServletRequest request){
	    	try {
	    		HttpSession session = request.getSession();
	    		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	    		ReferContactsService referContactsService = (ReferContactsService) ctx
	    				.getBean("referContactsService");
				referContactsService.saveAllReferContacts(refContactsList);
			} catch (Exception e) {
			}
		}
}
