package com.bis.sourcelead.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ClientImportsService {

	String  clientBulkSaving(MultipartFile multipartFile,User user,OrgLocations orgLocation,Org  org)throws IOException ,JSONException;
}
