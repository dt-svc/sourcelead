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
				 if(Constants.EMPLOYEE_PAYROLL_HEADERS[0].trim().equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())){
		    			   if(Constants.EMPLOYEE_PAYROLL_HEADERS[1].trim().equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())){
		    				   if(Constants.EMPLOYEE_PAYROLL_HEADERS[2].trim().equalsIgnoreCase(row.getCell(2).getStringCellValue().trim())){
		    						   if(Constants.EMPLOYEE_PAYROLL_HEADERS[3].trim().equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())){
		    							   if(Constants.EMPLOYEE_PAYROLL_HEADERS[4].trim().equalsIgnoreCase(row.getCell(4).getStringCellValue().trim())){
		    								   if(Constants.EMPLOYEE_PAYROLL_HEADERS[5].trim().equalsIgnoreCase(row.getCell(5).getStringCellValue().trim())){
		    									   if(Constants.EMPLOYEE_PAYROLL_HEADERS[6].trim().equalsIgnoreCase(row.getCell(6).getStringCellValue().trim())){
		    										   if(Constants.EMPLOYEE_PAYROLL_HEADERS[7].trim().equalsIgnoreCase(row.getCell(7).getStringCellValue().trim())){
		    											   if(Constants.EMPLOYEE_PAYROLL_HEADERS[8].trim().equalsIgnoreCase(row.getCell(8).getStringCellValue().trim())){
		    												   if(Constants.EMPLOYEE_PAYROLL_HEADERS[9].trim().equalsIgnoreCase(row.getCell(9).getStringCellValue().trim())){
		    													   if(Constants.EMPLOYEE_PAYROLL_HEADERS[10].trim().equalsIgnoreCase(row.getCell(10).getStringCellValue().trim())){
		    														   if(Constants.EMPLOYEE_PAYROLL_HEADERS[11].trim().equalsIgnoreCase(row.getCell(11).getStringCellValue().trim())){
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
				 if(Constants.WAGES_TAX_HEADERS[0].trim().equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())){
		    			   if(Constants.WAGES_TAX_HEADERS[1].trim().equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())){
		    				   if(Constants.WAGES_TAX_HEADERS[2].trim().equalsIgnoreCase(row.getCell(2).getStringCellValue().trim())){
		    						   if(Constants.WAGES_TAX_HEADERS[3].trim().equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())){
		    							   if(Constants.WAGES_TAX_HEADERS[4].trim().equalsIgnoreCase(row.getCell(4).getStringCellValue().trim())){
		    								   if(Constants.WAGES_TAX_HEADERS[5].trim().equalsIgnoreCase(row.getCell(5).getStringCellValue().trim())){
		    									   if(Constants.WAGES_TAX_HEADERS[6].trim().equalsIgnoreCase(row.getCell(6).getStringCellValue().trim())){
		    										   if(Constants.WAGES_TAX_HEADERS[7].trim().equalsIgnoreCase(row.getCell(7).getStringCellValue().trim())){
		    											   if(Constants.WAGES_TAX_HEADERS[8].trim().equalsIgnoreCase(row.getCell(8).getStringCellValue().trim())){
		    												   if(Constants.WAGES_TAX_HEADERS[9].trim().equalsIgnoreCase(row.getCell(9).getStringCellValue().trim())){
		    													   if(Constants.WAGES_TAX_HEADERS[10].trim().equalsIgnoreCase(row.getCell(10).getStringCellValue().trim())){
		    														   if(Constants.WAGES_TAX_HEADERS[11].trim().equalsIgnoreCase(row.getCell(11).getStringCellValue().trim())){
		    															   if(Constants.WAGES_TAX_HEADERS[12].trim().equalsIgnoreCase(row.getCell(12).getStringCellValue().trim())){
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
				 if(Constants.ACCOUNT_DEDUCTION_HEADERS[0].trim().equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())){
		    			   if(Constants.ACCOUNT_DEDUCTION_HEADERS[1].trim().equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())){
		    				   if(Constants.ACCOUNT_DEDUCTION_HEADERS[2].trim().equalsIgnoreCase(row.getCell(2).getStringCellValue().trim())){
		    						   if(Constants.ACCOUNT_DEDUCTION_HEADERS[3].trim().equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())){
		    							   if(Constants.ACCOUNT_DEDUCTION_HEADERS[4].trim().equalsIgnoreCase(row.getCell(4).getStringCellValue().trim())){
		    								   if(Constants.ACCOUNT_DEDUCTION_HEADERS[5].trim().equalsIgnoreCase(row.getCell(5).getStringCellValue().trim())){
		    									   if(Constants.ACCOUNT_DEDUCTION_HEADERS[6].trim().equalsIgnoreCase(row.getCell(6).getStringCellValue().trim())){
		    										   if(Constants.ACCOUNT_DEDUCTION_HEADERS[7].trim().equalsIgnoreCase(row.getCell(7).getStringCellValue().trim())){
		    											   if(Constants.ACCOUNT_DEDUCTION_HEADERS[8].trim().equalsIgnoreCase(row.getCell(8).getStringCellValue().trim())){
		    												   if(Constants.ACCOUNT_DEDUCTION_HEADERS[9].trim().equalsIgnoreCase(row.getCell(9).getStringCellValue().trim())){
		    													   if(Constants.ACCOUNT_DEDUCTION_HEADERS[10].trim().equalsIgnoreCase(row.getCell(10).getStringCellValue().trim())){
		    														   if(Constants.ACCOUNT_DEDUCTION_HEADERS[11].trim().equalsIgnoreCase(row.getCell(11).getStringCellValue().trim())){
		    															   if(Constants.ACCOUNT_DEDUCTION_HEADERS[12].trim().equalsIgnoreCase(row.getCell(12).getStringCellValue().trim())){
		    																   if(Constants.ACCOUNT_DEDUCTION_HEADERS[13].trim().equalsIgnoreCase(row.getCell(13).getStringCellValue().trim())){
		    																	   if(Constants.ACCOUNT_DEDUCTION_HEADERS[14].trim().equalsIgnoreCase(row.getCell(14).getStringCellValue().trim())){
		    																		   if(Constants.ACCOUNT_DEDUCTION_HEADERS[15].trim().equalsIgnoreCase(row.getCell(15).getStringCellValue().trim())){
		    																			   if(Constants.ACCOUNT_DEDUCTION_HEADERS[16].trim().equalsIgnoreCase(row.getCell(16).getStringCellValue().trim())){
		    																				   if(Constants.ACCOUNT_DEDUCTION_HEADERS[17].trim().equalsIgnoreCase(row.getCell(17).getStringCellValue().trim())){
		    																					   if(Constants.ACCOUNT_DEDUCTION_HEADERS[18].trim().equalsIgnoreCase(row.getCell(18).getStringCellValue().trim())){
		    																						   if(Constants.ACCOUNT_DEDUCTION_HEADERS[19].trim().equalsIgnoreCase(row.getCell(19).getStringCellValue().trim())){
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
				 if(Constants.HR_DEDUCTION_HEADERS[0].trim().equalsIgnoreCase(row.getCell(0).getStringCellValue().trim())){
		    			   if(Constants.HR_DEDUCTION_HEADERS[1].trim().equalsIgnoreCase(row.getCell(1).getStringCellValue().trim())){
		    				   if(Constants.HR_DEDUCTION_HEADERS[2].trim().equalsIgnoreCase(row.getCell(2).getStringCellValue().trim())){
		    						   if(Constants.HR_DEDUCTION_HEADERS[3].trim().equalsIgnoreCase(row.getCell(3).getStringCellValue().trim())){
		    							   if(Constants.HR_DEDUCTION_HEADERS[4].trim().equalsIgnoreCase(row.getCell(4).getStringCellValue().trim())){
		    								   if(Constants.HR_DEDUCTION_HEADERS[5].trim().equalsIgnoreCase(row.getCell(5).getStringCellValue().trim())){
		    									   if(Constants.HR_DEDUCTION_HEADERS[6].trim().equalsIgnoreCase(row.getCell(6).getStringCellValue().trim())){
		    										   if(Constants.HR_DEDUCTION_HEADERS[7].trim().equalsIgnoreCase(row.getCell(7).getStringCellValue().trim())){
		    											   if(Constants.HR_DEDUCTION_HEADERS[8].trim().equalsIgnoreCase(row.getCell(8).getStringCellValue().trim())){
		    												   if(Constants.HR_DEDUCTION_HEADERS[9].trim().equalsIgnoreCase(row.getCell(9).getStringCellValue().trim())){
		    													   if(Constants.HR_DEDUCTION_HEADERS[10].trim().equalsIgnoreCase(row.getCell(10).getStringCellValue().trim())){
		    														   if(Constants.HR_DEDUCTION_HEADERS[11].trim().equalsIgnoreCase(row.getCell(11).getStringCellValue().trim())){
		    															   if(Constants.HR_DEDUCTION_HEADERS[12].trim().equalsIgnoreCase(row.getCell(12).getStringCellValue().trim())){
		    																   if(Constants.HR_DEDUCTION_HEADERS[13].trim().equalsIgnoreCase(row.getCell(13).getStringCellValue().trim())){
		    																	   if(!"".equalsIgnoreCase(row.getCell(14).getStringCellValue())){
		    																		   isValid = 0;
		    																	   }else{
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
return isValid;
	}
}
