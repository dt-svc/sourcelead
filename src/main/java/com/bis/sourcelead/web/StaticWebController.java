package com.bis.sourcelead.web;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class StaticWebController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;
	
	public ModelAndView setMetaProperties(String viewPage) throws MissingResourceException {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("seo");
			if (bundle != null && bundle.getString(viewPage + ".title") != null) {
					model.addObject("title", bundle.getString(viewPage + ".title"));
				model.addObject("keywords", bundle.getString(viewPage + ".keywords"));
				model.addObject("description", bundle.getString(viewPage + ".description"));
			} else {
				model.addObject("title", ".::Welcome to SourceLead Website::.");
				model.addObject("keywords",
						"Application tracking system, ats software, top applicant tracking systems, payroll software, vendor management, client management, onboard candidate, offboard candidate, top hr tracking software");
				model.addObject("description",
						"Sourcelead is an integrated human resource solution which provides an automated process for the talent search. Be it a niche profile or a generic role, you have access to marketplace.");
			}
		} catch (java.util.MissingResourceException e) {
			e.printStackTrace();
		}
		if (viewPage.equalsIgnoreCase("search-jobs"))
			model.setViewName("openJobs/" + viewPage);
		else
			model.setViewName("static-web/" + viewPage);
		
		return model;
	}

	@RequestMapping("/about-us.html")
	public ModelAndView aboutUs() {
		return setMetaProperties("about-us");
	}

	@RequestMapping("/terms-of-use.html")
	public ModelAndView termsofuse() {
		return setMetaProperties("terms-of-use");
	}

	@RequestMapping("/privacy-policy.html")
	public ModelAndView privacypolicy() {
		return setMetaProperties("privacy-policy");
	}

	@RequestMapping("/crms.html")
	public ModelAndView crms() {
		return setMetaProperties("crms");
	}

	@RequestMapping("/disclaimer.html")
	public ModelAndView disclaimer() {
		return setMetaProperties("disclaimer");
	}

	@RequestMapping("/help-manuals.html")
	public ModelAndView helpManuals() {
		return setMetaProperties("help-manuals");
	}

	@RequestMapping("/hrms.html")
	public ModelAndView hrms() {
		return setMetaProperties("hrms");
	}

	@RequestMapping("/ims.html")
	public ModelAndView ims() {
		return setMetaProperties("ims");
	}

	@RequestMapping("/market-place.html")
	public ModelAndView marketplace() {
		return setMetaProperties("market-place");
	}

	@RequestMapping("/pms.html")
	public ModelAndView pms() {
		return setMetaProperties("pms");
	}

	@RequestMapping("/search-profile.html")
	public ModelAndView searchprofile() {
		return setMetaProperties("search-profile");
	}

	@RequestMapping("/terms-and-conditions.html")
	public ModelAndView termsnconditions() {
		return setMetaProperties("terms-and-conditions");
	}

	@RequestMapping("/ats.html")
	public ModelAndView ats() {
		return setMetaProperties("ats");
	}

	@RequestMapping("/immigration.html")
	public ModelAndView immigration() {
		return setMetaProperties("immigration");
	}

	@RequestMapping("/mergers-acquisition.html")
	public ModelAndView mergersAcquisition() {
		return setMetaProperties("mergers-acquisition");
	}

	@RequestMapping("/blog.html")
	public ModelAndView blog() {
		return setMetaProperties("blog");
	}

	@RequestMapping("/blog1.html")
	public ModelAndView blog1() {
		return setMetaProperties("blog1");
	}

	@RequestMapping("/blog2.html")
	public ModelAndView blog2() {
		return setMetaProperties("blog2");
	}

	@RequestMapping("/blog3.html")
	public ModelAndView blog3() {
		return setMetaProperties("blog3");
	}

	@RequestMapping("/blog4.html")
	public ModelAndView blog4() {
		return setMetaProperties("blog4");
	}

	@RequestMapping("/blog5.html")
	public ModelAndView blog5() {
		return setMetaProperties("blog5");
	}

	/*@RequestMapping("/careers.html")
	public ModelAndView careers() {
		return setMetaProperties("careers");
	}*/

	@RequestMapping("/careers-details.html")
	public ModelAndView careersDetails() {
		return setMetaProperties("careers-details");
	}

	@RequestMapping("/contact.html")
	public ModelAndView contact() {
		return setMetaProperties("contact");
	}

	@RequestMapping("/crowdsourcing.html")
	public ModelAndView crowdsourcing() {
		return setMetaProperties("crowdsourcing");
	}

	@RequestMapping("/demo.html")
	public ModelAndView demo() {
		return setMetaProperties("demo");
	}

	@RequestMapping("/email-verification.html")
	public ModelAndView emailVerification() {
		return setMetaProperties("email-verification");
	}

	@RequestMapping("/faqs.html")
	public ModelAndView faqs() {
		return setMetaProperties("faqs");
	}

	@RequestMapping("/features.html")
	public ModelAndView features() {
		return setMetaProperties("features");
	}

	@RequestMapping("/help-empdata.html")
	public ModelAndView helpEmpdata() {
		return setMetaProperties("help-empdata");
	}

	@RequestMapping("/help-main.html")
	public ModelAndView helpMain() {
		return setMetaProperties("help-next");
	}

	@RequestMapping("/help-next.html")
	public ModelAndView helpNext() {
		return setMetaProperties("terms-of-use");
	}

	@RequestMapping("/home.html")
	public ModelAndView home() {
		return setMetaProperties("home");
	}

	@RequestMapping("/search-jobs.html")
	public ModelAndView sourceleadJobPage() {
		return setMetaProperties("search-jobs");
	}

	@RequestMapping(value = { "/", "/index.html" })
	public ModelAndView index(HttpServletRequest request) {
		SourceLeadSessionManager.setSessionAttribute(request, "sourceleadUrl", sourceleadUrl);
		return setMetaProperties("index");
	}

	@RequestMapping("/job-details.html")
	public ModelAndView jobDetails() {
		return setMetaProperties("job-details");
	}

	@RequestMapping("/jobs.html")
	public ModelAndView jobs() {
		return setMetaProperties("jobs");
	}

	@RequestMapping("/privacy.html")
	public ModelAndView privacy() {
		return setMetaProperties("privacy");
	}

	@RequestMapping("/register.html")
	public ModelAndView register() {
		return setMetaProperties("register");
	}

	@RequestMapping("/team.html")
	public ModelAndView team() {
		return setMetaProperties("team");
	}

	@RequestMapping("/terms.html")
	public ModelAndView terms() {
		return setMetaProperties("terms");
	}

	@RequestMapping("/testimonial.html")
	public ModelAndView testimonial() {
		return setMetaProperties("testimonial");
	}

	@RequestMapping("/timesheet.html")
	public ModelAndView timesheet() {
		return setMetaProperties("timesheet");
	}

	@RequestMapping("/vms.html")
	public ModelAndView vms() {
		return setMetaProperties("vms");
	}

	@RequestMapping("/help.html")
	public ModelAndView sourceleadHelp() {
		return setMetaProperties("help");
	}

}
