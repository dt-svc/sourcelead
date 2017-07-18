package com.bis.sourcelead.aspect;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.impl.ActionRolesDaoImpl;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.entity.ActionNames;
import com.bis.sourcelead.entity.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;


@Component
@Repository
@Transactional(rollbackFor = { Exception.class })
public class AuditRecordDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ActionRolesDaoImpl actionRolesDaoImpl;

	Connection conn = null ;



	public void save(AuditRecord ar,User user) throws Exception {

		ActionNames actionNames = actionRolesDaoImpl.getActionNameUsingMethodName(ar.getMethodName());
		if(null!=actionNames)
		{
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();

			String query4 = " insert into sourcelead.action_history (USER_ID, ACTION_NAME, ACTION_PERFORMED, ACTION_TIME "
					+ " values (?, ?, ?, ?)";


			PreparedStatement pstmt4 = conn.prepareStatement(query4);
			pstmt4.setLong(1, user.getId());
			pstmt4.setString(2, actionNames.getActionName());
			pstmt4.setString(3, "TEST");
			pstmt4.setDate(4,(java.sql.Date) new Date());
			pstmt4.executeUpdate();
			System.out.println("row inserted in acton_history");

		}
	}

	private DataSource comboPooledDataSource() {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		try {
			comboPooledDataSource.setDriverClass(dbconfig.getDriverClass());
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		comboPooledDataSource.setJdbcUrl(dbconfig.getJdbcUrl());
		comboPooledDataSource.setUser(dbconfig.getUser());
		comboPooledDataSource.setPassword(dbconfig.getPassword());
		return comboPooledDataSource;
	}
}
