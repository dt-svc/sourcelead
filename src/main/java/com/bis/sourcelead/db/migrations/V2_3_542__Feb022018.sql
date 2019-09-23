#onBehalf leave Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('onBehalfEmail', 'Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveTakenEmployee', '{{onBehalfEmail.leaveTakenEmployee}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{onBehalfEmail.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveType', '{{onBehalfEmail.leaveType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveDuration', '{{onBehalfEmail.leaveDuration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveReason', '{{onBehalfEmail.leaveReason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{onBehalfEmail.loginLink}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('on behalf Email','Email', 'onBehalfEmail', 'apply leave on behalf of employee', 'Leave applied for you','org', 'Best Regards,\nSourceLead Team.', 'Dear {{onBehalfEmail.leaveTakenEmployee}},\n\This is to notify that leave applied for you behalf of {{onBehalfEmail.loginUser}}. Please find the details below.\nLeave Type : {{onBehalfEmail.leaveType}}\nDuration  : {{onBehalfEmail.leaveDuration}}\nReason  : {{onBehalfEmail.leaveReason}}\n\n{{onBehalfEmail.loginLink}}' , 'N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
											