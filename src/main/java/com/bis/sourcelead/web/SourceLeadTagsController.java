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
	
	
	@RequestMapping("/tagList")
	public ModelAndView sourceLeadTags(HttpServletRequest request) throws Exception {
		ModelAndView modal = new ModelAndView();
		modal.addObject("sourceleadUrl",sourceleadUrl);
		modal.setViewName("/tagsList");
		return modal;
	}
	
	

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	

}
