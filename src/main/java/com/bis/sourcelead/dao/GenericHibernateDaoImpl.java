package com.bis.sourcelead.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

@Repository
public class GenericHibernateDaoImpl<T extends Serializable> extends AbstractGenericHibernateDao<T> {

	

}
