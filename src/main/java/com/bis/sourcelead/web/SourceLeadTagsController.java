package com.bis.sourcelead.web;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class SourceLeadTagsController {
	

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	SourceleadTagsService sourceleadTagsService;
	
	
	@RequestMapping("/sourceLeadTags")
	public ModelAndView sourceLeadTags(HttpServletRequest request) throws Exception {

		ModelAndView modal = new ModelAndView();
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 List<SourceleadTags> tagsList = sourceleadTagsService.SourceleadTagsListByLocationId(loginOrgLocation.getId());
		 List<SourceleadTags> uniqueList = tagsList.stream().filter(distinctByKey(s->s.getTagName())).collect(Collectors.toList());
		 for(SourceleadTags st : uniqueList) {
			 st.setAssociateCount(sourceleadTagsService.individualTagsCount(TagEntites.ASSOCIATES.toString(), st.getTagName()));
			 st.setCandidateCount(sourceleadTagsService.individualTagsCount(TagEntites.CANDIDATES.toString(), st.getTagName()));
			 st.setJobCount(sourceleadTagsService.individualTagsCount(TagEntites.JOBS.toString(), st.getTagName()));
			 st.setClientCount(sourceleadTagsService.individualTagsCount(TagEntites.CLIENTS.toString(), st.getTagName()));
			 st.setVendorCount(sourceleadTagsService.individualTagsCount(TagEntites.VENDORS.toString(), st.getTagName()));
			 st.setProjectCount(sourceleadTagsService.individualTagsCount(TagEntites.PROJECTS.toString(), st.getTagName()));
		 }
	     modal.addObject("tagsList",uniqueList);
		modal.addObject("sourceleadUrl",sourceleadUrl);
		modal.setViewName("/sourceLeadTags");
		return modal;
	}
	
	

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	

}
