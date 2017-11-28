package com.bis.sendgrid;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class CreateList {
	private static final Logger logger = LoggerFactory.getLogger(CreateList.class);
	public static void main(String[] args) throws IOException {
	    try {
	      SendGrid sg = new SendGrid("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");
	      Request request = new Request();
	      request.setMethod(Method.POST);
	      request.setEndpoint("contactdb/lists");
	      request.setBody("{\"name\":\"rajashekar_test_team\"}");
	      Response response = sg.api(request);
	      logger.info(""+response.getStatusCode());
	      logger.info(""+response.getBody());
	      logger.info(""+response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }
}
