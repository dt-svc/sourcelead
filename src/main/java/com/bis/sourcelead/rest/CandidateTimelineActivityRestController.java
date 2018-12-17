package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateTimelineActivityService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@RequestMapping("/app")
public class CandidateTimelineActivityRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CandidateTimelineActivityService candidateTimelineActivityService;

	@RequestMapping(value = "/getCandidateActivitiesByCandidateIdAndLocationCode", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateTimelineActivity> getCandidateActivitiesByCandidateIdAndLocationCode(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
		ResponseList<CandidateTimelineActivity> response = new ResponseList<>();
		try {
			logger.debug("Calling getCandidateTimelineActivities mrthod to fetch Candidate Activities");
			List<CandidateTimelineActivity> activitiesList = candidateTimelineActivityService.getCandidateTimelineActivities(candidateId,
					locationId);
			for (CandidateTimelineActivity activity : activitiesList) {
				switch (activity.getActionIcon()) {
				case "JOBS":
					activity.setActionIconClass(Constants.JOBS_ACTION_ICON_CLASS);
					break;
				case "CANDIDATE":
					activity.setActionIconClass(Constants.CANDIDATE_ACTION_ICON_CLASS);
					break;
				case "PROJECTS":
					activity.setActionIconClass(Constants.PROJECTS_ACTION_ICON_CLASS);
					break;
				case "TIMESHEETS":
					activity.setActionIconClass(Constants.TIMESHEETS_ACTION_ICON_CLASS);
					break;
				case "EXPENSES":
					activity.setActionIconClass(Constants.EXPENSES_ACTION_ICON_CLASS);
					break;
				}

				long diff = commonUtil.currentDate().getTime() - activity.getCreatedDate().getTime();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				StringBuilder timeBuilder = new StringBuilder();
				if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
					activity.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
				} else if (diffDays == 0 && diffHours == 0) {
					activity.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
				} else if (diffDays == 0) {
					activity.setActionTimeStr(
							timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
				} else if (diffDays < 7) {
					activity.setActionTimeStr(
							timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
				} else {
					DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
					String commonDateFormat = standardFormat.format(activity.getCreatedDate());
					activity.setActionTimeStr(commonDateFormat.toString());
				}

				activity.setCreatedUserId(activity.getCreatedUser().getId());
			}
			response.setData(activitiesList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	public String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}
}
