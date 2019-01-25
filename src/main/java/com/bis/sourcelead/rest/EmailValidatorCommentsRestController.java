package com.bis.sourcelead.rest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.util.CommonUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")

public class EmailValidatorCommentsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	CommonUtil commonUtil;

	private static final Logger logger = LoggerFactory.getLogger(EmailValidatorCommentsRestController.class);

	/**
	 * @author Venkat Kesav
	 * 
	 * Handles to verify email is existed universally or not
	 * 
	 * @param email
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateEmailForExistenceUniversally", method = RequestMethod.GET)
	public @ResponseBody Response<String> validateEmail(@RequestParam(value = "email", required = false) String email)
	{
		Response<String> response = new Response<>();
		try {
			OkHttpClient client = new OkHttpClient();
			Request req = new Request.Builder().url(Constants.API_TRUMAIL_URL + email).get().build();
			com.squareup.okhttp.Response res = client.newCall(req).execute();
			JSONObject json = new JSONObject(res.body().string());
			if (json.has("hostExists") && json.getBoolean("hostExists")) {
				if (json.has("deliverable") && json.getBoolean("deliverable")) {
					response.setObject("true");
				} else {
					response.setObject("false");
				}
			}else {
				response.setObject("invalidDomain");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setObject("noResponse");
		}
		return response;
	}

}