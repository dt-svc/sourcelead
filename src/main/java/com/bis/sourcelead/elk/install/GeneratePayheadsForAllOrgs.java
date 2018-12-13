package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GeneratePayheadsForAllOrgs {

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
/**
 * @author
 * this method is using for generate Payroll Payheads
 * @throws IOException
 * @throws SQLException
 */
public void generatePayheadsByOrg() throws IOException, SQLException {

	Connection conn = null ;
	PreparedStatement stmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			
			String sql11 = "DELETE from sourcelead.payroll_loan_and_advances where sourcelead.payroll_loan_and_advances.id > 0 and  sourcelead.payroll_loan_and_advances.PAY_HEAD_ID IS NOT NULL and sourcelead.payroll_loan_and_advances.ORGLOCATION_ID IN (SELECT sourcelead.org_locations.id FROM sourcelead.org_locations where sourcelead.org_locations.COUNTRY='India')";
			PreparedStatement stmt11 = conn.prepareStatement(sql11);
			stmt11.executeUpdate();
			
			String sql12 = "DELETE from sourcelead.payroll_payhead_salary_setup_history where sourcelead.payroll_payhead_salary_setup_history.id > 0 and  sourcelead.payroll_payhead_salary_setup_history.PAYHEAD IN (SELECT sourcelead.payroll_payhead.id  FROM sourcelead.payroll_payhead where ORGLOCATION_ID != 1)";
			PreparedStatement stmt12 = conn.prepareStatement(sql12);
			stmt12.executeUpdate();
			
			String sql13 = "DELETE from sourcelead.associate_salary_payhead_ind where sourcelead.associate_salary_payhead_ind.id > 0 and  sourcelead.associate_salary_payhead_ind.PAYROLL_PAYHEAD IN (SELECT sourcelead.payroll_payhead.id  FROM sourcelead.payroll_payhead where ORGLOCATION_ID != 1)";
			PreparedStatement stmt13 = conn.prepareStatement(sql13);
			stmt13.executeUpdate();
			
			String sql14 = "DELETE from sourcelead.associate_payroll_payhead_history where sourcelead.associate_payroll_payhead_history.id > 0 and  sourcelead.associate_payroll_payhead_history.PAYROLL_PAYHEAD IN (SELECT sourcelead.payroll_payhead.id  FROM sourcelead.payroll_payhead where ORGLOCATION_ID != 1)";
			PreparedStatement stmt14 = conn.prepareStatement(sql14);
			stmt14.executeUpdate();
			
			String sql15 = "DELETE from sourcelead.payroll_payhead where ORGLOCATION_ID != 1";
			PreparedStatement stmt15 = conn.prepareStatement(sql15);
			stmt15.executeUpdate();
			
			
			
				 String sql = "SELECT * FROM sourcelead.ORG_LOCATIONS";
				 stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery();
				 System.out.println("----Adding payheads for All Orgs starts-----");
				 while(rs.next()){
					 Long orgLocationId = rs.getLong("ID");
					 if(orgLocationId != null && orgLocationId != 1l) {
						 PreparedStatement stmt1 = null;
						 String sql1 = "SELECT * FROM sourcelead.PAYROLL_PAYHEAD WHERE ORGLOCATION_ID = 1";
						 stmt1 = conn.prepareStatement(sql1);
						 ResultSet rs1 = stmt1.executeQuery();
						 while(rs1.next()){
							 String name = rs1.getString("NAME");
							 String shortCode = rs1.getString("SHORT_CODE");
							 String type = rs1.getString("TYPE");
						     Long payType = rs1.getLong("PAY_TYPE");
							 String form16 = rs1.getString("FORM_16");
							 String tax = rs1.getString("TAX");
							 String applicableFor = rs1.getString("APPLICABLE_FOR");
							 String component = rs1.getString("COMPONENT");
							 String partOfGratitutiy = rs1.getString("PART_OF_GRATITUTIY");
							 String showInPayslip = rs1.getString("SHOW_IN_PAYSLIP");
							 String pfApplicable = rs1.getString("PF_APPLICABLE");
							 String esiApplicable = rs1.getString("ESI_APPLICABLE");
							 Integer status = 1;
							 String uuid = SourceLeadRandomCodeHelper.generateRandomUUID(); 
							 String query = "INSERT INTO `PAYROLL_PAYHEAD` (`NAME`, `SHORT_CODE`,`TYPE`,`PAY_TYPE`,`FORM_16`,`TAX`,`APPLICABLE_FOR`,`COMPONENT`,`SHOW_IN_PAYSLIP`,`PF_APPLICABLE`,`ESI_APPLICABLE`,`PART_OF_GRATITUTIY`,`ORGLOCATION_ID`,`UUID`,`STATUS`) "
							 				+ " VALUES( '"+name+"', '"+shortCode+"', '"+type+"', '"+payType+"', '"+form16+"', '"+tax+"', '"+applicableFor+"', '"+component+"', '"+showInPayslip+"', '"+pfApplicable+"', '"+esiApplicable+"', '"+partOfGratitutiy+"', "+orgLocationId+", '"+uuid+"', "+status+") ";
							 PreparedStatement pstmt = conn.prepareStatement(query);
							 pstmt.executeUpdate();
						 }
					 }
					 System.out.println("----Adding payheads for "+orgLocationId+" Org Done.");	 
				 }
				 System.out.println("----Adding payheads for All Orgs ends-----");
		}
		
	catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}




/**
 * getting connection
 * @return DataSource
 */ 
private DataSource comboPooledDataSource() {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

		this.comboPooledDataSource = new ComboPooledDataSource();
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
 
public static void main(String[] args) throws IOException, SQLException {
	GeneratePayheadsForAllOrgs dump = new GeneratePayheadsForAllOrgs();
	dump.generatePayheadsByOrg();
	
}


}
