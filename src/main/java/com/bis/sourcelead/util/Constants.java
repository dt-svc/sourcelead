package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
	
	public static String YES = "Y";
	public static String NO  = "N";
	public static String WAIT  = "W";
	public static String REJECT  = "R";
	public static String TEMP    = "T";
	public static transient DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	public static DateFormat DF_TM = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	public static int MAX_RESULTS_FIVE = 5;
	public static int MAX_RESULTS_TEN = 10;
	public static int MAX_RESULTS_TWENTY = 20;
	public static int MAX_RESULTS_THIRTY = 30;
	public static int MAX_RESULTS_FIFTY = 50;
	public static int PAGES_INCREMENT = 10;
	public static int PAGES_DECREMENT = -10;
	
	public static String SOURCELEAD_ORGCODE = "sourcelead.com";
	public static String SOURCELEAD_LOCATIONCODE_IN = "in.sourcelead.com";
	
	/**Constants values for unique emails and notification **/
	
	public static String ORG_REGISTRATION = "Org Registration";
	public static String FORGOT_PASSWORD = "Forgot Password";
	public static String UPDATE_PASSWORD = "Update Password";
	
	public static String ENTITY_TYPE_EMAIL = "Email";
	public static String ENTITY_TYPE_NOTIFICATION = "Notification";
	
	/**Constants values for unique emails and notification **/
	
	public static String NOTIFICATION_UNREAD = "unread";
	public static String NOTIFICATION_READ = "read";
	public static String  NOTIFICATION_DELETED= "0";
	public static String  NOTIFICATION_NOT_DELETED= "1";
	
	
	public static String  TIME_SHEET_SUBMISSION= "Time Sheet Submission";
	
	
}
