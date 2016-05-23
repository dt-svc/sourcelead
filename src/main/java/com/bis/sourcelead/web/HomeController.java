package com.bis.sourcelead.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bis.sourcelead.model.LoginModel;
import com.bis.sourcelead.service.LoginService;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public LoginService loginService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		logger.info("in root =========================================>");
		LoginModel loginModel = new LoginModel();
		model.put("loginModel", loginModel);
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(ModelMap modelMap, @ModelAttribute("loginModel") @Valid LoginModel loginModel,
			BindingResult result) {

		logger.info("========> " + loginModel.getUserName() + " : " + loginModel.getPassword());
		if (loginService.checkAuthentication(loginModel.getUserName(), loginModel.getPassword())) {
			modelMap.put("loginModel", loginModel);
			return "success";
		} else {
			// result.rejectValue("userName","invaliduser");
			modelMap.put("error", "Invalid UserName / Password");
			return "login";
		}

	}
}
