package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.UserCustomFieldData;

public interface UserCustomFieldDataDao {

	UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields);

	List<UserCustomFieldData> getUserCustomFieldDataByUserId(Long userId, List<CustomFormFieldDTO> formFieldId);

}
