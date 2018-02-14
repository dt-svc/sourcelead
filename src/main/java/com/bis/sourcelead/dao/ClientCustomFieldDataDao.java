package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;

public interface ClientCustomFieldDataDao {

	ClientCustomFieldData saveUserCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception;

	List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocation,List<CustomFormFieldDTO> formFieldId);

}
