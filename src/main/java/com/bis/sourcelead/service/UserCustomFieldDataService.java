package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.UserCustomFieldData;

public interface UserCustomFieldDataService {

	UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields);

	List<UserCustomFieldData> getUserCustomFieldDataByUserId(Long userId, List<CustomFormFieldDTO> formFieldList);

}
