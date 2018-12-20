package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementAssignToDao;
import com.bis.sourcelead.dto.RequirementAssignToDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

@Service
public class RequirementAssignToServiceImpl implements RequirementAssignToService{
	
	@Autowired
	SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	RequirementAssignToDao  requirementAssignToDao;
	
	@Autowired
	 private OrgLocationService orgLocationService;
	
	@Autowired
	 RequirementService requirementService;
	
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @Autowired
	 TeamService teamService;

	@Override
	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId) throws Exception {
		
		return requirementAssignToDao.getRequirementAssignToListByRequirementId(requirementId);
		
	}
	
	@Override
	public ResponseList<RequirementAssignTo> loadRequirementAssignToListByRequirementId(RequirementAssignToDTO requirementAssignToDTO) throws Exception
	{
		return requirementAssignToDao.loadRequirementAssignToListByRequirementId(requirementAssignToDTO);
	}

	@Override
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId, Long assignToId)
			throws Exception {
		return requirementAssignToDao.getRequirementAssignToByRequirementIdandAssignToId(requirementId, assignToId);
	}

	@Override
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId) throws Exception {
		return requirementAssignToDao.deleteRequirementAssignToByRequirementId(requirementId);
	}

	@Override
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId,String jobType) {
		return requirementAssignToDao.getAssignedTeamMembersCountByTeamMemberUserId(assignUserId, jobType);
	}

	@Override
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId) throws Exception {
		 return requirementAssignToDao.getRequirementAssignToByRequirementId(requirementId);
	}

	@Override
	public Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		 
		return requirementAssignToDao.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, userId);
	
	}

	@Override
	public List<RequirementAssignTo> getAssignedToMembersByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId) throws Exception {
		return requirementAssignToDao.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocationId);
	}

	@Override
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignedUserId(Long assignedUserId,Long requirementId) throws Exception {
		return requirementAssignToDao.getRequirementAssignToByRequirementIdandAssignedUserId(assignedUserId,requirementId);
	}

	@Override
	public List<AssignedRequirementReports> getAllAssignedRequirementByOrglocationId(Long orgLocationId,Timezone timezone) throws Exception {
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<RequirementAssignTo> reqAssignToList = requirementAssignToDao.getAllAssignedRequirementByOrglocationId(orgLocationId);
		List<AssignedRequirementReports> astoList = null;
		if(null != reqAssignToList){
			astoList = new ArrayList<>();
			for(RequirementAssignTo rato :reqAssignToList){
				StringBuilder sb = new StringBuilder();
				AssignedRequirementReports assignedRequirementReports = new AssignedRequirementReports();
				assignedRequirementReports.setJobTitle(rato.getRequirementId().getJobTitle());
				assignedRequirementReports.setPostedOn(getDateStr(rato.getRequirementId().getUpdatedDate(),timezone,orgLocations.getDateFormat()));
				if(rato.getRequirementId().getManagerUserId() != null){
					if(rato.getRequirementId().getManagerUserId().getFirstName() !=null && rato.getRequirementId().getManagerUserId().getLastName() !=null){
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName()).append(" ").append(rato.getRequirementId().getManagerUserId().getLastName());
					}else{
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName());
					}
				}
				assignedRequirementReports.setOwner(sb.toString());
				
				if(rato.getRequirementId().getClientId() != null){
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Client")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getLocationId().getOrgLocationName());
					}
				}
				StringBuilder assignUserName = new StringBuilder();
				if(null != rato.getAssignUserId()){
					if(null != rato.getAssignUserId().getFirstName() && null != rato.getAssignUserId().getLastName()){
						assignUserName.append(rato.getAssignUserId().getFirstName()).append(" ").append(rato.getAssignUserId().getLastName());
					}else{
						assignUserName.append(rato.getAssignUserId().getFirstName());
					}
				}
				assignedRequirementReports.setAssignedUser(assignUserName.toString());
				assignedRequirementReports.setAssignedOn(getDateStr(rato.getUpdatedDate(), timezone,orgLocations.getDateFormat()));
				assignedRequirementReports.setOffered(0);
				if(rato.getTeamId() !=null){
					assignedRequirementReports.setTeamName(rato.getTeamId().getName());
				}
				astoList.add(assignedRequirementReports);
			}	
		}
		return astoList;
	}

	@Override
	public List<RequirementAssignTo> getAllAssignedRequirementByDateRange(Long orgLocationId, String userId,Date startDateUTC, Date endDateUTC) throws Exception {
		return requirementAssignToDao.getAllAssignedRequirementByDateRange(orgLocationId,userId,startDateUTC,endDateUTC);
	}

	@Override
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationIdAndTeamId(Long orgLocationId,
			Long teamId) {
		return requirementAssignToDao.getAllAssignedRequirementByOrglocationIdAndTeamId(orgLocationId,teamId);
	}
	
	@Override
	public List<RequirementAssignTo> getAllAssignRequirementByUserIdAndOrglocationId(String userId, Long orgLocationId) throws Exception
	{
		return requirementAssignToDao.getAllAssignRequirementByUserIdAndOrglocationId(userId, orgLocationId);
	}
	
	
	/**
	 * Returns date format as string
	 * @param date
	 * @param timezone
	 * @return String
	 * @throws ParseException
	 */
private String getDateStr(Date date, Timezone timezone,String dateFormat) throws ParseException {
	 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	String currentTimeStr = Constants.TZ_DF.format(date);  
	String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
	Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
	return currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(dateFormat,converteDate);
	
}
	

	@Override
	public Integer getRequirementAssignToListCountByRequirementId(Long requirementId) throws Exception {
		return requirementAssignToDao.getRequirementAssignToListCountByRequirementId(requirementId);
	}


	/**
	 * @author VenkatKesav
	 * Returns boolean value whether success or fail
	 * @param assignedIdVal
	 * @param requirementId
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	public List<RequirementAssignTo> updateRequirementAssignToListByRequirementId(String assignedIdVal, String requirementId, OrgLocations orgLocations, User user) throws Exception
	{
		String assignedIdValCopy = assignedIdVal;
		List<RequirementAssignTo> requirementAssignToList = null;
		List<RequirementAssignTo> newlyUpdatedRequirementAssignToList = null;
		List<RequirementAssignTo> requirementAssignToListCopy = new ArrayList<RequirementAssignTo>();
		if (null != requirementId && "" != requirementId && "undefined" != requirementId) {
			
			List<String> assignedIdValList = new ArrayList<String>();
			for (Iterable<String> iterable : Iterables.partition(Splitter.on(",").split(assignedIdVal), 2)) {
				assignedIdValList.add(Joiner.on(",").join(iterable));
			}
			
			requirementAssignToList = getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));
			for (RequirementAssignTo requirementAssignTo : requirementAssignToList) {
				String existedUser = "";
				if (requirementAssignTo.getTeamId() != null) {
					existedUser = requirementAssignTo.getAssignUserId().getId().toString() + ","
							+ requirementAssignTo.getTeamId().getId().toString();
				}
				else {
					existedUser = requirementAssignTo.getAssignUserId().getId().toString()
							+ ",null";
				}
				if(assignedIdValList.contains(existedUser)) {
					assignedIdValList.remove(existedUser);
				}else{
					requirementAssignTo.setUpdatedBy(user.getUserCode());
					requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
					requirementAssignTo.setStatus(Constants.MEMBER_IN_ACTIVE);
					requirementAssignToListCopy.add(requirementAssignTo);
				}
			}
			for (String assigned : assignedIdValList) {
				if(!assigned.equalsIgnoreCase("")){
					String userId = assigned.split(",")[0];
					String teamId = assigned.split(",")[1];
					RequirementAssignTo requirementAssignTo = new RequirementAssignTo();
					User assignedUser = new User();
					assignedUser.setId(Long.parseLong(userId));
					if (StringUtils.isNotEmpty(teamId) && !teamId.equalsIgnoreCase("undefined") && !teamId.equalsIgnoreCase("null")) {
						Team assignedTeam = new Team();
						assignedTeam.setId(Long.parseLong(teamId));
						requirementAssignTo.setTeamId(assignedTeam);
					}
					Requirement requirement = new Requirement();
					requirement.setId(Long.parseLong(requirementId));
					requirementAssignTo.setAssignUserId(assignedUser);
					requirementAssignTo.setRequirementId(requirement);
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(assignedUser.getId(), orgLocations.getId());
					requirementAssignTo.setRoleId(userRole.getRoles());
					requirementAssignTo.setStatus(Constants.MEMBER_ACTIVE);
					requirementAssignTo.setCreatedDate(commonUtil.currentDate());
					requirementAssignTo.setCreatedBy(user.getUserCode());
					requirementAssignTo.setUpdatedBy(user.getUserCode());
					requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
					requirementAssignToListCopy.add(requirementAssignTo);
				}
			}
			
			/*save updated assignTo list in ELK*/
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.must(QueryBuilders.matchQuery("requirementId", requirementId));
			SearchHits searchHits = sourceleadELKBaseIndexer.searchDocument(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE, boolQueryBuilder);
			ObjectMapper mapper = new ObjectMapper();
	    	for (SearchHit hit : searchHits) {
	    		String json = hit.getSourceAsString();
	    		RequirementIndex requirementIndex = mapper.readValue(json, RequirementIndex.class);
	    		
	    		HashSet<String> teamOwnerUserId = new HashSet<>();
				HashSet<String> teamAssignUserId = new HashSet<>();
	    		for (Iterable<String> iterable : Iterables.partition(Splitter.on(",").split(assignedIdValCopy), 2)) {
					if(!iterable.toString().equalsIgnoreCase("[]")){
						String userId = Joiner.on(",").join(iterable).split(",")[0];
						teamAssignUserId.add(userId);
						String teamId = Joiner.on(",").join(iterable).split(",")[1];
						if(null != requirementId && "" != requirementId && StringUtils.isNotEmpty(teamId) && !teamId.equalsIgnoreCase("undefined") && !teamId.equalsIgnoreCase("null")){
							Team team = teamService.getTeamByTeamId(Long.parseLong(teamId));
							if(team.getOwnerUserId() != null && team.getOwnerUserId().getId() != null)
							teamOwnerUserId.add(team.getOwnerUserId().getId().toString());
						}
					}
	    		}
	    		requirementIndex.setTeamOwnerUserId(teamOwnerUserId.toArray(new String[teamOwnerUserId.size()]));
				requirementIndex.setTeamAssignUserId(teamAssignUserId.toArray(new String[teamAssignUserId.size()]));
				sourceleadELKBaseIndexer.saveOrUpdateElkDocument(requirementIndex, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE, requirementId);
	    	}
		}
		if(requirementAssignToListCopy.size() > 0){
			newlyUpdatedRequirementAssignToList = requirementAssignToDao.updateRequirementAssignToListByRequirementId(requirementAssignToListCopy);
			if (newlyUpdatedRequirementAssignToList != null) {
				Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
				StringBuilder location = new StringBuilder();
				if(req.getCountry() != null){
					location.append(req.getCountry());
				}
				if(req.getState() != null){
					location.append(",").append(req.getState());
				}
				if(req.getCity() != null){
					location.append(",").append(req.getCity());
				}
				// for URL redirections from mail
				String redirectURL = "/requirementSummary-params-requirementUUID="+req.getRequirementUUID();
				Long redirectOrgLocationId = orgLocations.getId();
				for(RequirementAssignTo newlyAddReqAssiTo : newlyUpdatedRequirementAssignToList){
					if(newlyAddReqAssiTo.getStatus() != null && newlyAddReqAssiTo.getStatus() != "0"){
						requirementService.AssignToTeamMembersEmail(user, newlyAddReqAssiTo.getAssignUserId(), req, location, orgLocations,redirectURL,redirectOrgLocationId);
					}
				}
			}
		}
		return newlyUpdatedRequirementAssignToList;
	}
	
}
