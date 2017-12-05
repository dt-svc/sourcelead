package com.bis.sourcelead.rest;

import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.service.QuickBookEmployeeService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 
 * Handles requests for the Requirement service.
 * 
 * @author sarath kumar punnana
 * @date 14th Dec 2016
 *
 */

@RestController
@RequestMapping("/app")
public class QuickBooksEmployeeRestController {

	private static final Logger logger = LoggerFactory.getLogger(QuickBooksEmployeeRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	QuickBookEmployeeService quickBookEmployeeService;
	
	
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get invoice details by invoice id
	 * 
	 * @return JSON  : this method returns json object.
	 *  
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getEmoloyeesByEmoloyeesId", method = RequestMethod.POST)
	public @ResponseBody String getEmoloyeesByEmoloyeesId() throws Exception {
			
			Long employeeId = 1l;
			String outPut = quickBookEmployeeService.getEmoloyeesByEmoloyeesId(employeeId);
		    return outPut;
	    
	}
		
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method returns invoiceList
	 * 
	 * @return JSON  : this method returns json objects.
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllEmoloyees", method = RequestMethod.POST)
	public @ResponseBody String getAllEmoloyees() throws Exception {
			
			Long employeeId = 1l;
			String outPut = quickBookEmployeeService.getAllEmoloyees();
		    return outPut;
	    
	}
		
		
	 
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	 
}
