package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;

public interface ClientCustomFieldDataService {

	ClientCustomFieldData saveUserCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception;

	List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocationId,List<CustomFormFieldDTO> formFieldId);

}
