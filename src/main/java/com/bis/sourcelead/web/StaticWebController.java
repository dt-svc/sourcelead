package com.bis.sourcelead.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWebController {
	
	@RequestMapping("/about-us.html")
	public String aboutUs() {
		return "static-web/about-us";
	}
	
	@RequestMapping("/ats.html")
	public String ats() {
		return "static-web/ats";
	}
	
	@RequestMapping("/blog.html")
	public String blog() {
		return "static-web/blog";
	}
	
	@RequestMapping("/blog1.html")
	public String blog1() {
		return "static-web/blog1";
	}
	
	@RequestMapping("/blog2.html")
	public String blog2() {
		return "static-web/blog2";
	}
	
	@RequestMapping("/blog3.html")
	public String blog3() {
		return "static-web/blog3";
	}
	
	@RequestMapping("/blog4.html")
	public String blog4() {
		return "static-web/blog4";
	}
	
	@RequestMapping("/blog5.html")
	public String blog5() {
		return "static-web/blog5";
	}
	
	@RequestMapping("/careers.html")
	public String careers() {
		return "static-web/careers";
	}
	
	@RequestMapping("/careers-details.html")
	public String careersDetails() {
		return "static-web/careers-details";
	}
	
	@RequestMapping("/contact.html")
	public String contact() {
		return "static-web/contact";
	}
	
	@RequestMapping("/crowdsourcing.html")
	public String crowdsourcing() {
		return "static-web/crowdsourcing";
	}
	
	
	@RequestMapping("/demo.html")
	public String demo() {
		return "static-web/demo";
	}
	
	@RequestMapping("/email-verification.html")
	public String emailVerification() {
		return "static-web/email-verification";
	}
	
	@RequestMapping("/faqs.html")
	public String faqs() {
		return "static-web/faqs";
	}
	
	@RequestMapping("/features.html")
	public String features() {
		return "static-web/features";
	}
	
	@RequestMapping("/help-empdata.html")
	public String helpEmpdata() {
		return "static-web/help-empdata";
	}
	
	@RequestMapping("/help-main.html")
	public String helpMain() {
		return "static-web/help-main";
	}
	
	@RequestMapping("/help-next.html")
	public String helpNext() {
		return "static-web/help-next";
	}
	
	@RequestMapping("/home.html")
	public String home() {
		return "static-web/home";
	}
	
	@RequestMapping("/index.html")
	public String index(HttpServletRequest request) {
	    Map<String,String> map =new HashMap();
	    map.put("SourceleadStaticUrl", request.getRequestURL().toString().split(request.getRequestURI())[0]);
		return "openJobs/sourceleadJobPage";
	}
	
	@RequestMapping("/job-details.html")
	public String jobDetails() {
		return "static-web/job-details";
	}
	
	@RequestMapping("/jobs.html")
	public String jobs() {
		return "static-web/jobs";
	}
	
	@RequestMapping("/privacy.html")
	public String privacy() {
		return "static-web/privacy";
	}
	
	@RequestMapping("/register.html")
	public String register() {
		return "static-web/register";
	}
	
	@RequestMapping("/team.html")
	public String team() {
		return "static-web/team";
	}
	
	@RequestMapping("/terms.html")
	public String terms() {
		return "static-web/terms";
	}
	
	@RequestMapping("/testimonial.html")
	public String testimonial() {
		return "static-web/testimonial";
	}

	@RequestMapping("/timesheet.html")
	public String timesheet() {
		return "static-web/timesheet";
	}
	
	@RequestMapping("/vms.html")
	public String vms() {
		return "static-web/vms";
	}
}
