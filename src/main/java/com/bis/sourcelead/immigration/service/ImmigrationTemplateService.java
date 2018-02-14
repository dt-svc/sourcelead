/**
 * 
 */
package com.bis.sourcelead.immigration.service;

import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationTemplateService {

	void savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, User user);

}
