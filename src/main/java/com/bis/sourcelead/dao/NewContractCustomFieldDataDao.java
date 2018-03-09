package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.NewContractCustomFieldData;

public interface NewContractCustomFieldDataDao {

	NewContractCustomFieldData saveNewContractCustomFieldData(NewContractCustomFieldData contractCustomFields);

	Map<Long, String[]> getNewContractCustomFieldDataByContractId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long contractId);

	NewContractCustomFieldData getNewContractCustomFieldDataById(Long contractId);

}
