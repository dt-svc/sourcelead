package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrglocationFormSubFields;

public interface OrglocationFormSubFieldsDao {

	OrglocationFormSubFields saveOrglocationFormSubField(OrglocationFormSubFields subFields);

	List<OrglocationFormSubFields> getOrgCustomSufieldsBySectionId(Long customFieldId);
}
