package com.bis.sendgrid;
import org.json.JSONException;
import org.json.JSONObject;

public class StringToJson {
	
	public static void main(String[] args) throws JSONException
	{
		String senderCreated = "{\"id\":191763,\"nickname\":\"duplicatejj23\",\"from\":{\"email\":\"chelpurirajashekar115@gmail.com\",\"name\":\"Sourcelead Admin\"},\"reply_to\":{\"email\":\"qa@sourcelead.com\",\"name\":\"\"},\"address\":\"Hyderabad\",\"address_2\":\"\",\"city\":\"Hyderabad\",\"state\":\"Telangana\",\"zip\":\"500081\",\"country\":\"India\",\"verified\":{\"status\":false,\"reason\":null},\"updated_at\":1511625774,\"created_at\":1511625774,\"locked\":false}";
		JSONObject jObject  = new JSONObject(senderCreated); // json
		JSONObject data = jObject.getJSONObject("verified"); // get data object
		String projectname = data.getString("status"); // g
		System.out.println(projectname);
	}
}