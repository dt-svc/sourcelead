package com.bis.sourcelead.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class TaxController {

	private static final Logger logger = LoggerFactory.getLogger(TaxController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private StatesService statesService;

	@Autowired
	private TaxService taxService;

	// state tab
	@RequestMapping(value = "/stateTax/list")
	public ModelAndView stateTaxList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			// List<String> yearList = new LinkedList<>();
			Map<String, Boolean> yearList = new LinkedHashMap<>();
			Calendar c = Calendar.getInstance();
			c.roll(Calendar.YEAR, -2);
			for (int i = 0; i < 4; i++) {
				Calendar c1 = Calendar.getInstance();
				boolean isCurrent = c1.get(Calendar.YEAR) == c.get(Calendar.YEAR);

				String fromYear = c.get(Calendar.YEAR) + "";
				c.roll(Calendar.YEAR, 1);
				String toYear = c.get(Calendar.YEAR) + "";
				// yearList.add(fromYear + "-" + toYear);
				yearList.put(fromYear + "-" + toYear, isCurrent);
			}
			modelAndView.addObject("yearList", yearList);

			List<States> stateList = statesService.getAllStatesByCountry("India");
			modelAndView.addObject("stateList", stateList);
			modelAndView.addObject("activeTaxTab", "state");

			modelAndView.setViewName("tax/stateTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/stateTax/create")
	public ModelAndView addStateTax(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			List<SourceleadTaxType> taxTypeList = taxService.getAllTaxesByType(1);
			modelAndView.addObject("taxTypes", taxTypeList);
			modelAndView.addObject("activeTaxTab", "state");
			modelAndView.setViewName("tax/addStateTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/stateTax/taxSummary")
	public ModelAndView stateTaxSummary(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName("tax/stateTaxSummary");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/getStateTaxes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<StateTaxDTO> getStateTaxes(HttpServletRequest request,
			@RequestParam(defaultValue = "") String yearFrequency, @RequestParam(defaultValue = "0") long stateId) {
		ResponseList<StateTaxDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<StateTaxDTO> stateTaxList = taxService.getStateTaxList(yearFrequency, stateId);
				response.setData(stateTaxList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	// central tab
	@RequestMapping(value = "/centralTax/list")
	public ModelAndView centralTaxList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Map<String, Boolean> yearList = new LinkedHashMap<>();
			Calendar c = Calendar.getInstance();
			c.roll(Calendar.YEAR, -2);
			for (int i = 0; i < 4; i++) {
				Calendar c1 = Calendar.getInstance();
				boolean isCurrent = c1.get(Calendar.YEAR) == c.get(Calendar.YEAR);
				String fromYear = c.get(Calendar.YEAR) + "";
				c.roll(Calendar.YEAR, 1);
				String toYear = c.get(Calendar.YEAR) + "";
				yearList.put(fromYear + "-" + toYear, isCurrent);
			}
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("activeTaxTab", "central");
			modelAndView.setViewName("tax/centralTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/centralTax/create")
	public ModelAndView addCentralTax(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			List<SourceleadTaxType> taxTypeList = taxService.getAllTaxesByType(2);
			modelAndView.addObject("taxTypes", taxTypeList);
			modelAndView.addObject("activeTaxTab", "central");
			modelAndView.setViewName("tax/addCentralTax");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/centralTax/taxSummary")
	public ModelAndView centralTaxSummary(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName("tax/centralTaxSummary");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

}
