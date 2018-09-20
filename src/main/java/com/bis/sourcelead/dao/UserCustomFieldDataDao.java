package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.UserCustomFieldData;

public interface UserCustomFieldDataDao {

	UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields);

	Map<Long, String[]> getUserCustomFieldDataByUserId(Long orgId, List<CustomFormFieldDTO> formFieldId, Long userId);

	UserCustomFieldData getUserCustomFieldDataById(Long entityId);

}
