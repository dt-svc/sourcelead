package com.bis.sourcelead.si.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class slack {
	processor pr = new processor();

	/*
	 * GET AUTHENTICATION URL
	 */
	protected JSONObject getAuthenticationUrl(String service, String consumerkey, String callback) {
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();

		try {
			String authurl = "https://slack.com/oauth/authorize?client_id=" + consumerkey
					+ "&response_type=code&scope=chat:write:user users:read&redirect_uri=" + callback;
			data.put("token", "");
			data.put("tokensecret", "");
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", "");
			data.put("authurl", authurl);

			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		} catch (JSONException j) {

		} catch (Exception e) {
		}

		return jsonresponse;
	}

	/*
	 * GET ACCESS TOKEN
	 */
	protected JSONObject getAccessToken(String service, String code, String consumerkey, String consumersecret,
			String callback) {
		JSONObject jsonresponse = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			JSONObject msg = new JSONObject();
			String access_token_secret = "", user_id = "";
			String postdata = "grant_type=authorization_code&client_id=" + consumerkey + "&client_secret="
					+ consumersecret + "&code=" + code;

			// MAKE API CALL
			String response = pr.excutePost("POST", "https://slack.com/api/oauth.access", postdata, "");

			// PARSE DATA
			JSONObject resp = new JSONObject(response);
			data.put("token", resp.get("access_token"));
			data.put("tokensecret", access_token_secret);
			data.put("tokenverifier", "");
			data.put("service", service);
			data.put("userid", user_id);
			data.put("authurl", "");

			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			jsonresponse.put("responseStatus", msg);
		} catch (JSONException j) {

		} catch (Exception e) {

		}
		return jsonresponse;
	}

	/*
	 * GET FRIENDS
	 */
	protected JSONObject getFriends(String token, String service) {
		// DECLARATION
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		JSONArray friends = new JSONArray();
		try {
			String requrl = "https://slack.com/api/users.list?token=" + token;

			// MAKE API CALL
			String response = pr.executeGet(requrl);

			// PARSE DATA
			JSONObject jsonobject = new JSONObject(response);

			JSONArray friendsary = jsonobject.getJSONArray("members");
			int count = friendsary.length();
			for (int i = 0; i < count; i++) {
				JSONObject friend = new JSONObject();
				JSONObject name = new JSONObject();
				JSONObject thisObject = new JSONObject();
				thisObject = (JSONObject) friendsary.get(i);
				JSONObject profileObj = thisObject.getJSONObject("profile");
				name.put("first_name", profileObj.get("first_name").toString());
				name.put("last_name", profileObj.get("last_name").toString());
				friend.put("name", name);
				friend.put("imageurl", profileObj.get("image_72").toString());
				friend.put("id", thisObject.get("id").toString());
				friend.put("screenname", thisObject.get("name").toString());
				friend.put("location", "");
				if (!thisObject.get("name").toString().equals("slackbot")) {
					friends.put(friend);
				}
			}
			data.put("friends", friends);
			data.put("service", "slack");
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("data", data);
			msg.put("message", "");
			msg.put("type", "success");
			jsonresponse.put("responseStatus", msg);
		} catch (Exception e) {
			try {
				msg.put("message", "Error fetching friends");
				msg.put("type", "error");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			} catch (JSONException je) {

			}
		}
		return jsonresponse;
	}

	/*
	 * SEND MESSAGE
	 */
	protected JSONObject sendMessage(String friendsids, String message, String token) {
		JSONObject jsonresponse = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject msg = new JSONObject();
		String postdata = "text=" + message + "&channel=" + friendsids + "&token=" + token;
		String response = pr.excutePost("POST", "https://slack.com/api/chat.postMessage", postdata, "");
		if (!response.equals("error")) {
			try {
				msg.put("message", "");
				msg.put("type", "success");
				data.put("status", "success");
				data.put("service", "slack");
				jsonresponse.put("data", data);
				jsonresponse.put("responseStatus", msg);
			} catch (JSONException e) {}
		} else {

		}
		return jsonresponse;
	}
}
