package com.bis.sourcelead.util;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		File file = new File("C:\\Users\\BIS-2889\\Desktop\\kiran\\SarangMankar[13_0].docx");
    		System.out.println("********************"+file.getAbsolutePath());
        	MultiValueMap<String, Object> bodyMap =  new LinkedMultiValueMap();
            bodyMap.add("fileName",getUserFileResource(file));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(bodyMap, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> response = restTemplate.exchange("http://localhost:8081/parseResume", HttpMethod.POST, requestEntity, JSONObject.class);
            System.out.println(response.getBody());	
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    
    }
    
    public static Resource getUserFileResource(File file) throws IOException {
	      //to upload in-memory bytes use ByteArrayResource instead
	      return new FileSystemResource(file);
	  }
}
