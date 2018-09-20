package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.UserCustomFieldData;

public interface UserCustomFieldDataService {

	UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields);

	Map<Long, String[]> getUserCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldList, Long userId);

	UserCustomFieldData getUserCustomFieldDataById(Long entityId);

}
