package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.NewContractCustomFieldData;

public interface NewContractCustomFieldDataService {

	NewContractCustomFieldData saveNewContractCustomFieldData(NewContractCustomFieldData contractCustomFields);

	Map<Long, String[]> getNewContractCustomFieldDataByContractId(Long id, List<CustomFormFieldDTO> formFieldId,Long ContractId);

	NewContractCustomFieldData getNewContractCustomFieldDataById(Long entityId);

}
