package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Option;

public interface OptionsDao {

	Option saveLeaveOption(Option options)throws Exception;

	List<Option> getOptionsListByLocationId(Long orgLocationId)throws Exception;

	Option getOptionsById(String optionId)throws Exception;

	Option getOptionsByOptionId(String optionID) throws Exception;

}
