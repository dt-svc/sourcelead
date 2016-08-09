package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Prasad Salina
 * @author bis113
 *
 */

@Controller
@RestController
public class AutoCompleteController {

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);

	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CountriesService countriesService;
	
	@PostConstruct
	public void init() {
		System.out.println(" *** AutoCompleteController init with: " + applicationContext);
	}

    @RequestMapping(value = "/testAutoComplete")
  	public ModelAndView testAutoComplete() {
  		ModelAndView model = new ModelAndView();
  		model.setViewName("testAutoComplete");
  		model.addObject("sourceleadUrl", sourceleadUrl);
  		return model;

  	}
    
    @RequestMapping(value = "/clientAutoComplete")
    public String clientAutoComplete(HttpServletRequest request) throws Exception {
    	 
    	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	Collection<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
    	for(OrgRelations orgRelations : clientsList ){
    		orgRelations.setClientName(orgRelations.getClientLocationId().getOrg().getOrgName());
    	}
		
    	String resultJson = toJSON(clientsList);
    	return resultJson;
	}
    
    @RequestMapping(value = "/countryAutoComplete")
    public String countryAutoComplete(HttpServletRequest request) throws Exception {
    	
    	ArrayList<String> countriesList = new ArrayList<String>();
    	
    	List<Countries> countryList = countriesService.getAllCountries();
    	 
    	if(countryList != null && !countryList.isEmpty())
    	{
    		for(Countries countries: countryList){
    			countriesList.add(countries.getName());
    		}
    	}
		
		String[] temp = (String[]) countriesList.toArray(new String[] {});
		JSONArray mJSONArray = new JSONArray(Arrays.asList(temp));
		
		String result = mJSONArray.toString();
		
        return result;
	}
    
    /*@RequestMapping(value = "/userImage", method = RequestMethod.GET)
	public @ResponseBody  void addTimesheetEntries(HttpServletRequest request, HttpServletResponse response) throws Exception
			 
	{
    	User user = userService.findByUserName("SourceLeadAdmin");
		if(user.getDocFile() !=null)
		{
			response.setContentType("image/jpeg");
			
			try {
				
				InputStream in = new ByteArrayInputStream(user.getDocFile());
				BufferedImage bi = ImageIO.read(in);
				OutputStream out = response.getOutputStream();
				if(bi != null){
					ImageIO.write(bi, "jpg", out);
				}
				
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
    private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
    
	
}
