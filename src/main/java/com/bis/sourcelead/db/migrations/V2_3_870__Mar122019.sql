#email should forward configured emailIds for  Employee on Leave . 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES (' EmployeeOnLeave', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('Employee on Leave','Email', 'EmployeeOnLeave', 'Mail should forward to configured emails to Employee on Leave.', '[No_Of_Employees_On_Leave] are leave on [Apply_Leave_Date].','org',
'Best Regards,\nSourcelead Team.', 'Dear [Manager],\nThis is notify that following applicants are going to Leave in [No_Of_Days].\n[Appliants_Info]\nFor brief details kindly login into the system.','N', 'SLADMIN', 'slead', 'sleadIN', 11,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NoOfEmployeesOnLeave', '[No_Of_Employees_On_Leave]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'ApplyLeaveDate', '[Apply_Leave_Date]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Manager', '[Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfDays', '[No_Of_Days]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'applicantsInfo', '[Appliants_Info]');




#email should forward configured emailIds for  Approve Pending Leaves. 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES (' ApprovePendingLeaves', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('Approve Pending Leaves','Email', 'ApprovePendingLeaves', 'Mail should forward to configured emails to Approve Pending Leaves.', '[No_Of_Approve_Pending_Leaves] are pending from [Apply_Leave_Date].','org',
'Best Regards,\nSourcelead Team.', 'Dear [Manager],\nThis is notify that following applicants Leaves are pending from [No_Of_Days].\n[Appliants_Info]\nFor brief details kindly login into the system. \n To approve leave please click here.\n[Approve_Leave_Link]','N', 'SLADMIN', 'slead', 'sleadIN', 11,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NoOfApprovePendingLeaves', '[No_Of_Approve Pending Leaves]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'ApplyLeaveDate', '[Apply_Leave_Date]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Manager', '[Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfDays', '[No_Of_Days]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'applicantsInfo', '[Appliants_Info]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approveLeaveLink', '[Approve_Leave_Link]');

set SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `DEFAULT_TEMPLATE`='Y' WHERE `UNIQUE_SCENARIO_NAME`='ReviseContractEmail';
set SQL_SAFE_UPDATES = 1;