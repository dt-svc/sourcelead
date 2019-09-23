ALTER TABLE `HOLIDAY` 
CHANGE COLUMN `HALF_DAY` `FROM_HALF_DAY` VARCHAR(50) NULL DEFAULT NULL ,
CHANGE COLUMN `HALF_DAY_AM_PM` `FROM_HALF_DAY_AM_PM` VARCHAR(20) NULL DEFAULT NULL ,
ADD COLUMN `TO_HALF_DAY` VARCHAR(50) NULL DEFAULT NULL AFTER `FROM_HALF_DAY_AM_PM`,
ADD COLUMN `TO_HALF_DAY_AM_PM` VARCHAR(20) NULL DEFAULT NULL AFTER `TO_HALF_DAY`;

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('LeaveEmailToApprovers','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{LeaveEmailToApprovers.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverUser', '{{LeaveEmailToApprovers.approverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'noOfDays', '{{LeaveEmailToApprovers.noOfDays}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveType', '{{LeaveEmailToApprovers.leaveType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'fromDate', '{{LeaveEmailToApprovers.fromDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'toDate', '{{LeaveEmailToApprovers.toDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{LeaveEmailToApprovers.reason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('LeaveEmailToApprovers','Email', 'LeaveEmailToApprovers', 'Used for Submitting Leave Request to Approvers.', 'Request for Leave  to Approve','org', 'Best Regards,\n{{LeaveEmailToApprovers.employeeUser}}.', 'Dear {{LeaveEmailToApprovers.approverUser}},\nThis is a formal request for a leave of absence,from {{LeaveEmailToApprovers.fromDate}} to {{LeaveEmailToApprovers.toDate}} of {{LeaveEmailToApprovers.noOfDays}} days.\nLeave Type : {{LeaveEmailToApprovers.leaveType}}\nDuration : {{LeaveEmailToApprovers.fromDate}} to {{LeaveEmailToApprovers.toDate}}\nReason : {{LeaveEmailToApprovers.reason}}\nSo,please grant me leave of absence.','N', 'SLADMIN', 'slead', 'sleadIN');



#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('LeaveNotificationToApprovers', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('LeaveNotificationToApprovers', 'Notification', 'LeaveNotificationToApprovers', 'Used for Submitting Notification to Approvers.', 'New leave request by {{LeaveEmailToApprovers.employeeUser}}.', 'sourcelead', '', 'New leave request by {{LeaveEmailToApprovers.employeeUser}}.','N','SLADMIN', 'com', 'in.com');



INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('LeaveCancelEmailToUser','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{LeaveCancelEmailToUser.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverUser', '{{LeaveCancelEmailToUser.approverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'noOfDays', '{{LeaveCancelEmailToUser.noOfDays}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveType', '{{LeaveCancelEmailToUser.leaveType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'fromDate', '{{LeaveCancelEmailToUser.fromDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'toDate', '{{LeaveCancelEmailToUser.toDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{LeaveCancelEmailToUser.reason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('LeaveCancelEmailToUser','Email', 'LeaveCancelEmailToUser', 'Used for Canceling Leave after Approving.', 'Used for Canceling Leave after Approving.','org', 'Best Regards,\n{{LeaveCancelEmailToUser.employeeUser}}.', 'Dear {{LeaveCancelEmailToUser.approverUser}},\nWe are regret to inform you, your leave  got cancelled for the requested period.\nLeave Type : {{LeaveCancelEmailToUser.leaveType}}\nDuration : {{LeaveCancelEmailToUser.fromDate}} to {{LeaveCancelEmailToUser.toDate}}\n Cancelled Reason : {{LeaveCancelEmailToUser.reason}}\n','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('LeaveCancelNotificationToUser', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('LeaveCancelNotificationToUser', 'Notification', 'LeaveCancelNotificationToUser', 'Used for Canceling Leave after Approving.', 'Cancel leave request by {{LeaveCancelEmailToUser.approverUser}}.', 'sourcelead', '', 'Leave got cancelled for {{LeaveCancelEmailToUser.fromDate}} to {{LeaveCancelEmailToUser.toDate}}.','N','SLADMIN', 'com', 'in.com');