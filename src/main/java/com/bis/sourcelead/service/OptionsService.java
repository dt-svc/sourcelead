package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.entity.Option;

public interface OptionsService {

	Option saveLeaveOptions(OptionsDTO optionsDTO)throws Exception;

	List<OptionsDTO> getOptionsListByLocationId(Long orgLocationId)throws Exception;

	Option getOptionsById(String optionId) throws Exception;

	List<Long> getOptionRoleIds(Long orgLocationId) throws Exception;


}
