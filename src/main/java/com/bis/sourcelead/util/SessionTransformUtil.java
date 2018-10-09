package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.bis.sourcelead.entity.Tabs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * LEAST PREFERRED : ULTIMATELY GET RID OF JAVA OBJECTS/CLASSES/ENTITIES FRM THE SESSION
 * Utility using Google Gson to serialize java objects to and from JSON
 * @author Bis51
 *
 */
public class SessionTransformUtil {
	/**
	 * Session Key String (used in the app across all places) vs Java Object 
	 */
	static HashMap ObjectMappings = new HashMap();
	static Gson gson = new Gson();
	
	static {
		ObjectMappings.put("candidate", com.bis.sourcelead.entity.Candidate.class);
		ObjectMappings.put("tabsList", new TypeToken<ArrayList<Tabs>>(){}.getType());
		ObjectMappings.put("orgLocations", com.bis.sourcelead.entity.OrgLocations.class);
	}
	
	/**
	 * Transform the Java object to String.
	 * Call this before setting an object to the session store
	 * @param sessionKey
	 * @param sessionValue
	 */
	public static String keyValueToSession(String sessionKey, Object sessionValueObject){
		String jse = gson.toJson(sessionValueObject);
		return jse;
	}

	/**
	 * 
	 * @param sessionKey
	 * @param sessionValueObject
	 * @return
	 */
	public static Object objectValueFromSession(String sessionKey, String sessionValueString){
		Type t= (Type)ObjectMappings.get(sessionKey);
		if(t==null)
			return sessionValueString;
		
		Object ob   = gson.fromJson(sessionValueString, t);
		return ob;
	}
}
