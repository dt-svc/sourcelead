#onBehalf leave Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('onBehalfEmail', 'Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'leaveTakenEmployee', '{{onBehalfEmail.leaveTakenEmployee}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{onBehalfEmail.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'leaveType', '{{onBehalfEmail.leaveType}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'leaveDuration', '{{onBehalfEmail.leaveDuration}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'leaveReason', '{{onBehalfEmail.leaveReason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{onBehalfEmail.loginLink}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('on behalf Email','Email', 'onBehalfEmail', 'apply leave on behalf of employee', 'Leave applied for you','org', 'Best Regards,\nSourceLead Team.', 'Dear {{onBehalfEmail.leaveTakenEmployee}},\n\This is to notify that leave applied for you behalf of {{onBehalfEmail.loginUser}}. Please find the details below.\nLeave Type : {{onBehalfEmail.leaveType}}\nDuration  : {{onBehalfEmail.leaveDuration}}\nReason  : {{onBehalfEmail.leaveReason}}\n\n{{onBehalfEmail.loginLink}}' , 'N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
											