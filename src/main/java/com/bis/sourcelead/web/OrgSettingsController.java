package com.bis.sourcelead.web;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
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
@PropertySource(value = { "classpath:config/application.properties" })
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
					
					
				    /*InputStream in = new ByteArrayInputStream(org.getDocFile());
			        final BufferedImage source = ImageIO.read(in);
			        final int color = source.getRGB(0, 0);
			        final Image imageWithTransparency = makeColorTransparent(source, new Color(color));
			        final BufferedImage transparentImage = imageToBufferedImage(imageWithTransparency);

			        OutputStream out = response.getOutputStream();
			        ImageIO.write(transparentImage, "PNG", out);*/
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
   }
   
   
   private static BufferedImage imageToBufferedImage(final Image image)
   {
      final BufferedImage bufferedImage =
      new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      final Graphics2D g2 = bufferedImage.createGraphics();
      g2.drawImage(image, 0, 0, null);
      g2.dispose();
      return bufferedImage;
    }
   
   
   public static Image makeColorTransparent(final BufferedImage im, final Color color)
   {
      final ImageFilter filter = new RGBImageFilter()
      {
         public int markerRGB = color.getRGB() | 0xFFFFFFFF;

         public final int filterRGB(final int x, final int y, final int rgb)
         {
            if ((rgb | 0xFF000000) == markerRGB)
            {
               return 0x00FFFFFF & rgb;
            }
            else
            {
               return rgb;
            }
         }
      };

      final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
      return Toolkit.getDefaultToolkit().createImage(ip);
   }
   
	   
}
