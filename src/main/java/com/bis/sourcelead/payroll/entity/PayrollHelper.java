package com.bis.sourcelead.payroll.entity;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;

@Component
public class PayrollHelper {

	public int isValidTemplateForEmployeePayRoll(Iterator<Row> rowIterator)
	{
		 int isValid = 0;
			Row row = (Row) rowIterator.next();
			
			 if(row.getRowNum() == 0){
				 if(Constants.EMPLOYEE_PAYROLL_HEADERS[0].equalsIgnoreCase(row.getCell(0).getStringCellValue())){
		    			   if(Constants.EMPLOYEE_PAYROLL_HEADERS[1].equalsIgnoreCase(row.getCell(1).getStringCellValue())){
		    				   if(Constants.EMPLOYEE_PAYROLL_HEADERS[2].equalsIgnoreCase(row.getCell(2).getStringCellValue())){
		    						   if(Constants.EMPLOYEE_PAYROLL_HEADERS[3].equalsIgnoreCase(row.getCell(3).getStringCellValue())){
		    							   if(Constants.EMPLOYEE_PAYROLL_HEADERS[4].equalsIgnoreCase(row.getCell(4).getStringCellValue())){
		    								   if(Constants.EMPLOYEE_PAYROLL_HEADERS[5].equalsIgnoreCase(row.getCell(5).getStringCellValue())){
		    									   if(Constants.EMPLOYEE_PAYROLL_HEADERS[6].equalsIgnoreCase(row.getCell(6).getStringCellValue())){
		    										   if(Constants.EMPLOYEE_PAYROLL_HEADERS[7].equalsIgnoreCase(row.getCell(7).getStringCellValue())){
		    											   if(Constants.EMPLOYEE_PAYROLL_HEADERS[8].equalsIgnoreCase(row.getCell(8).getStringCellValue())){
		    												   if(Constants.EMPLOYEE_PAYROLL_HEADERS[9].equalsIgnoreCase(row.getCell(9).getStringCellValue())){
		    													   if(Constants.EMPLOYEE_PAYROLL_HEADERS[10].equalsIgnoreCase(row.getCell(10).getStringCellValue())){
		    														   if(Constants.EMPLOYEE_PAYROLL_HEADERS[11].equalsIgnoreCase(row.getCell(11).getStringCellValue())){
																		isValid = 1;
																}
															}
														}
													}
												}
											}
										}
								}
							}
						}
					}
				}
		}
		return isValid;
	}
	
	public int isValidTemplateForWagesTaxSummary(Iterator<Row> rowIterator)
	{
		 int isValid = 0;
			Row row = (Row) rowIterator.next();
			
			 if(row.getRowNum() == 0){
				 if(Constants.WAGES_TAX_HEADERS[0].equalsIgnoreCase(row.getCell(0).getStringCellValue())){
		    			   if(Constants.WAGES_TAX_HEADERS[1].equalsIgnoreCase(row.getCell(1).getStringCellValue())){
		    				   if(Constants.WAGES_TAX_HEADERS[2].equalsIgnoreCase(row.getCell(2).getStringCellValue())){
		    						   if(Constants.WAGES_TAX_HEADERS[3].equalsIgnoreCase(row.getCell(3).getStringCellValue())){
		    							   if(Constants.WAGES_TAX_HEADERS[4].equalsIgnoreCase(row.getCell(4).getStringCellValue())){
		    								   if(Constants.WAGES_TAX_HEADERS[5].equalsIgnoreCase(row.getCell(5).getStringCellValue())){
		    									   if(Constants.WAGES_TAX_HEADERS[6].equalsIgnoreCase(row.getCell(6).getStringCellValue())){
		    										   if(Constants.WAGES_TAX_HEADERS[7].equalsIgnoreCase(row.getCell(7).getStringCellValue())){
		    											   if(Constants.WAGES_TAX_HEADERS[8].equalsIgnoreCase(row.getCell(8).getStringCellValue())){
		    												   if(Constants.WAGES_TAX_HEADERS[9].equalsIgnoreCase(row.getCell(9).getStringCellValue())){
		    													   if(Constants.WAGES_TAX_HEADERS[10].equalsIgnoreCase(row.getCell(10).getStringCellValue())){
		    														   if(Constants.WAGES_TAX_HEADERS[11].equalsIgnoreCase(row.getCell(11).getStringCellValue())){
		    															   if(Constants.WAGES_TAX_HEADERS[12].equalsIgnoreCase(row.getCell(12).getStringCellValue())){
		    																   isValid = 1;
																}
															}
														}
													}
												}
											}
										}
								}
							}
						}
					}
				}
		}
	}
		return isValid;
	}
	
	public int isValidTemplateForAccountDeductions(Iterator<Row> rowIterator)
	{
		 int isValid = 0;
			Row row = (Row) rowIterator.next();
			
			 if(row.getRowNum() == 0){
				 if(Constants.ACCOUNT_DEDUCTION_HEADERS[0].equalsIgnoreCase(row.getCell(0).getStringCellValue())){
		    			   if(Constants.ACCOUNT_DEDUCTION_HEADERS[1].equalsIgnoreCase(row.getCell(1).getStringCellValue())){
		    				   if(Constants.ACCOUNT_DEDUCTION_HEADERS[2].equalsIgnoreCase(row.getCell(2).getStringCellValue())){
		    						   if(Constants.ACCOUNT_DEDUCTION_HEADERS[3].equalsIgnoreCase(row.getCell(3).getStringCellValue())){
		    							   if(Constants.ACCOUNT_DEDUCTION_HEADERS[4].equalsIgnoreCase(row.getCell(4).getStringCellValue())){
		    								   if(Constants.ACCOUNT_DEDUCTION_HEADERS[5].equalsIgnoreCase(row.getCell(5).getStringCellValue())){
		    									   if(Constants.ACCOUNT_DEDUCTION_HEADERS[6].equalsIgnoreCase(row.getCell(6).getStringCellValue())){
		    										   if(Constants.ACCOUNT_DEDUCTION_HEADERS[7].equalsIgnoreCase(row.getCell(7).getStringCellValue())){
		    											   if(Constants.ACCOUNT_DEDUCTION_HEADERS[8].equalsIgnoreCase(row.getCell(8).getStringCellValue())){
		    												   if(Constants.ACCOUNT_DEDUCTION_HEADERS[9].equalsIgnoreCase(row.getCell(9).getStringCellValue())){
		    													   if(Constants.ACCOUNT_DEDUCTION_HEADERS[10].equalsIgnoreCase(row.getCell(10).getStringCellValue())){
		    														   if(Constants.ACCOUNT_DEDUCTION_HEADERS[11].equalsIgnoreCase(row.getCell(11).getStringCellValue())){
		    															   if(Constants.ACCOUNT_DEDUCTION_HEADERS[12].equalsIgnoreCase(row.getCell(12).getStringCellValue())){
		    																   if(Constants.ACCOUNT_DEDUCTION_HEADERS[13].equalsIgnoreCase(row.getCell(13).getStringCellValue())){
		    																	   if(Constants.ACCOUNT_DEDUCTION_HEADERS[14].equalsIgnoreCase(row.getCell(14).getStringCellValue())){
		    																		   if(Constants.ACCOUNT_DEDUCTION_HEADERS[15].equalsIgnoreCase(row.getCell(15).getStringCellValue())){
		    																			   if(Constants.ACCOUNT_DEDUCTION_HEADERS[16].equalsIgnoreCase(row.getCell(16).getStringCellValue())){
		    																				   if(Constants.ACCOUNT_DEDUCTION_HEADERS[17].equalsIgnoreCase(row.getCell(17).getStringCellValue())){
		    																					   if(Constants.ACCOUNT_DEDUCTION_HEADERS[18].equalsIgnoreCase(row.getCell(18).getStringCellValue())){
		    																						   if(Constants.ACCOUNT_DEDUCTION_HEADERS[19].equalsIgnoreCase(row.getCell(19).getStringCellValue())){
		    																							   	isValid = 1;
																}
															}
														}
													}
												}
											}
										}
								}
							}
						}
					}
				}
		}
	}
}
}
}
}
}
}
}
		return isValid;
}
	
	public int isValidTemplateForHrDeductions(Iterator<Row> rowIterator)
	{
		 int isValid = 0;
			Row row = (Row) rowIterator.next();
			
			 if(row.getRowNum() == 0){
				 if(Constants.HR_DEDUCTION_HEADERS[0].equalsIgnoreCase(row.getCell(0).getStringCellValue())){
		    			   if(Constants.HR_DEDUCTION_HEADERS[1].equalsIgnoreCase(row.getCell(1).getStringCellValue())){
		    				   if(Constants.HR_DEDUCTION_HEADERS[2].equalsIgnoreCase(row.getCell(2).getStringCellValue())){
		    						   if(Constants.HR_DEDUCTION_HEADERS[3].equalsIgnoreCase(row.getCell(3).getStringCellValue())){
		    							   if(Constants.HR_DEDUCTION_HEADERS[4].equalsIgnoreCase(row.getCell(4).getStringCellValue())){
		    								   if(Constants.HR_DEDUCTION_HEADERS[5].equalsIgnoreCase(row.getCell(5).getStringCellValue())){
		    									   if(Constants.HR_DEDUCTION_HEADERS[6].equalsIgnoreCase(row.getCell(6).getStringCellValue())){
		    										   if(Constants.HR_DEDUCTION_HEADERS[7].equalsIgnoreCase(row.getCell(7).getStringCellValue())){
		    											   if(Constants.HR_DEDUCTION_HEADERS[8].equalsIgnoreCase(row.getCell(8).getStringCellValue())){
		    												   if(Constants.HR_DEDUCTION_HEADERS[9].equalsIgnoreCase(row.getCell(9).getStringCellValue())){
		    													   if(Constants.HR_DEDUCTION_HEADERS[10].equalsIgnoreCase(row.getCell(10).getStringCellValue())){
		    														   if(Constants.HR_DEDUCTION_HEADERS[11].equalsIgnoreCase(row.getCell(11).getStringCellValue())){
		    															   if(Constants.HR_DEDUCTION_HEADERS[12].equalsIgnoreCase(row.getCell(12).getStringCellValue())){
																		isValid = 1;
																}
															}
														}
													}
												}
											}
										}
								}
							}
						}
					}
				}
		}
	}
		return isValid;
	}
}
