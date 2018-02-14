package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

@Component
public class UniversalSearchHelper {

	/**
	 * QueryBuilder preparation fro requirement
	 * @param keywd
	 * @return ArrayList<QueryBuilder>
	 */
	public ArrayList<QueryBuilder> getQueryBuilderListForRequirementSearch(String keywd) {

		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		QueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
		}

		return QueryBuilderList;
	}

	/**
	 * QueryBuilder preparation fro user
	 * @param keywd
	 * @return ArrayList<QueryBuilder>
	 */
	public ArrayList<QueryBuilder> getUsersQueryBuilderList(String keywd) {
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		QueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("userType", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("ratePer", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("employeeId", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("userStatus", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", keywd));
		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);

		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			
			String mobileKeywd =matcher.group().replaceAll("[+91]", "");
			QueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
		}

		return QueryBuilderList;
	}

	/**
	 * QueryBuilder preparation fro client and vendor
	 * @param keywd
	 * @return ArrayList<QueryBuilder>
	 */
	public ArrayList<QueryBuilder> getQueryBuilderListForClientVendor(String keywd) {
		
		
//		  QueryBuilderList.add(QueryBuilders.matchQuery("initiatorOrgName", keywd));
//		    QueryBuilderList.add(QueryBuilders.matchQuery("responderOrgName", keywd));
//		    QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorLevel", keywd));
//		    QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderLevel", keywd));
//			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("createdBy", keywd));
//			
//			QueryBuilderList.add(QueryBuilders.matchQuery("responderCountry", keywd));
//			QueryBuilderList.add(QueryBuilders.matchQuery("responderState", keywd));
//			QueryBuilderList.add(QueryBuilders.matchQuery("responderCity", keywd));
//			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorCountry", keywd));
//			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorState", keywd));
//			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorCity", keywd));
//			
//			QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderWebSite", keywd));
//			QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorWebSite", keywd));
		
		
		
		
		
		
		
		
		
		
		
		
		
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		  QueryBuilderList.add(QueryBuilders.matchQuery("initiatorOrgName", keywd));
		    QueryBuilderList.add(QueryBuilders.matchQuery("responderOrgName", keywd));
		    QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorLevel", keywd));
		    QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderLevel", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("createdBy", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchQuery("responderCountry", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("responderState", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("responderCity", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorCountry", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorState", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("initiatorCity", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("responderWebSite", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhrasePrefixQuery("initiatorWebSite", keywd));
		return QueryBuilderList;
	}
	/**
	 * QueryBuilder preparation fro projects
	 * @param keywd
	 * @return ArrayList<QueryBuilder>
	 */
	public ArrayList<QueryBuilder> getProjectsQueryBuilderList(String keywd) {
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		// Project Search
		QueryBuilderList.add(QueryBuilders.matchQuery("projectSequeneNumber", keywd));
	    QueryBuilderList.add(QueryBuilders.matchQuery("projectName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("level1ManagerName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("level2ManagerName", keywd));
//		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("startDate", keywd));
//		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("invoiceFrequency", keywd));
		
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("clientName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectStatus", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("projectMembers", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("projectMemberEmail", keywd));
		return QueryBuilderList;
	}
	/**
	 * QueryBuilder preparation fro candidate
	 * @param keywd
	 * @return ArrayList<QueryBuilder>
	 */
	public ArrayList<QueryBuilder> getQueryBuilderListForCandidate(String keywd) {
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		QueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateEmail", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("designation", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("designationType", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("skillsName", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("visaType", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("primarySkill", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("certifications", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("candidateType", keywd));

		return QueryBuilderList;
	}
	 public ArrayList<String> getSearchValuesFromChosenSelectField(String searchValue) {
		 ArrayList<String> keyWordList = null;
		 if(searchValue !=null && !"".equalsIgnoreCase(searchValue)){
			 String searchKeywordString = searchValue.replaceAll("\\[", "").replaceAll("\\]","");
		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		 }
        return keyWordList;
	}
}
