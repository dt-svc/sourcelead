package com.bis.sourcelead.web;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RestController
@PropertySource("${propertyLocation:app.properties}")
public class OrgSettingsController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgService orgService;
	
	@RequestMapping(value = "/orgSettings")
 	public ModelAndView orgSettings() {
 			ModelAndView model = new ModelAndView();
 			model.setViewName("orgSettings");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
   @RequestMapping(value = "/saveOrgSettings" , method = RequestMethod.POST)
   public String saveOrgSettings(@RequestParam(value="json", required=false) String json,MultipartHttpServletRequest request) throws Exception {
		
    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
   
    json = "{"+json+"}";
	JSONObject jsonObj = new JSONObject(json);
	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	OrgSettings orgSettings =  gson.fromJson(json,OrgSettings.class);
	
	   try {
		org = orgService.saveOrgSettings(orgSettings, request, org);
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	   return "home";
   }
   
   
   
   
   
   
   @RequestMapping(value = "/displayOrgImage/{orgCode}" , method = RequestMethod.GET)
   public void displayUserImage(@PathVariable String orgCode,HttpServletRequest request,HttpServletResponse response) {
  	 
  	 Org org = null;
			try {
				org = orgService.getOrgByOrgCode(orgCode);
				
				if(org != null && org.getDocFile() != null)
				{
					org.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(org.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
					
				/*   try {
					    InputStream in = new ByteArrayInputStream(org.getDocFile());
			            BufferedImage img = colorImage(ImageIO.read(in));
			            OutputStream out = response.getOutputStream();
			            ImageIO.write(img, "png", out);
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }*/
					
					
				    
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
   }
   
   
   
   @RequestMapping(value = "/deleteOrgImage" , method = RequestMethod.GET)
   public String deleteOrgImage(HttpServletRequest request) throws Exception {
	   
	   Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	   org.setDocFile(null);
	   org.setFileContentType(null);
	   orgService.updateOrg(org);
	   SourceLeadSessionManager.setSessionAttribute(request, "org", org);
	   return "success";
   }
   
   
   private static BufferedImage colorImage(BufferedImage image) {
       int width = image.getWidth();
       int height = image.getHeight();
       WritableRaster raster = image.getRaster();

       for (int xx = 0; xx < width; xx++) {
           for (int yy = 0; yy < height; yy++) {
               int[] pixels = raster.getPixel(xx, yy, (int[]) null);
               pixels[0] = 0;
               pixels[1] = 0;
               pixels[2] = 255;
               raster.setPixel(xx, yy, pixels);
           }
       }
       return image;
   }
   
  
	   
}
