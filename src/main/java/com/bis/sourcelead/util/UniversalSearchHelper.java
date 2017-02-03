package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

@Component
public class UniversalSearchHelper {

	/**
	 * MatchQueryBuilder preparation fro requirement
	 * @param keywd
	 * @return ArrayList<MatchQueryBuilder>
	 */
	public ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForRequirementSearch(String keywd) {

		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		matchQueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
		}

		return matchQueryBuilderList;
	}

	/**
	 * MatchQueryBuilder preparation fro user
	 * @param keywd
	 * @return ArrayList<MatchQueryBuilder>
	 */
	public ArrayList<MatchQueryBuilder> getUsersMatchQueryBuilderList(String keywd) {
		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		matchQueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("userType", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("ratePer", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("employeeId", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("userStatus", keywd));

		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);

		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			// matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("mobilePhone",
			// keywd));
			String mobileKeywd = matcher.group().replaceAll("[+91]", "");
			matchQueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
		}

		return matchQueryBuilderList;
	}

	/**
	 * MatchQueryBuilder preparation fro client and vendor
	 * @param keywd
	 * @return ArrayList<MatchQueryBuilder>
	 */
	public ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForClientVendor(String keywd) {
		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorOrgName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("responderOrgName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("initiatorLevel", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("createdByAdvanced", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		return matchQueryBuilderList;
	}
	/**
	 * MatchQueryBuilder preparation fro projects
	 * @param keywd
	 * @return ArrayList<MatchQueryBuilder>
	 */
	public ArrayList<MatchQueryBuilder> getProjectsMatchQueryBuilderList(String keywd) {
		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		// Project Search
		matchQueryBuilderList.add(QueryBuilders.matchQuery("projectName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("level1ManagerName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("level2ManagerName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("startDate", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("clientName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("projectStatus", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("projectMemberEmail", keywd));
		return matchQueryBuilderList;
	}
	/**
	 * MatchQueryBuilder preparation fro candidate
	 * @param keywd
	 * @return ArrayList<MatchQueryBuilder>
	 */
	public ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForCandidate(String keywd) {
		ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		matchQueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateEmail", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("designation", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("designationType", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("certifications", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("candidateType", keywd));

		return matchQueryBuilderList;
	}

}
