/**
 * 
 */
package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.ImmigrationTemplateView;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateService {
	Long savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user, String templateId);
	void savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, User user);
	void savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, User user);
	List<ImmigrationTemplateView> getAllTemplateDetails();
}
