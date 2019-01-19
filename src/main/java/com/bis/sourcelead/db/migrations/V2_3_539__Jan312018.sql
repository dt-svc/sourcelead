set SQL_SAFE_UPDATES=0;

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'leaveUser', '{{LeaveEmailToApprovers.leaveUser}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('OnBehafLeaveEmail','Email', 'OnBehafLeaveEmail', 'Used for Submitting on behalf Leave Request to Approvers.', 'Request for on behalf Leave to Approve','org', 'Best Regards,\n{{LeaveEmailToApprovers.employeeUser}}.', 'Dear {{LeaveEmailToApprovers.approverUser}},\nThis is a formal request for a leave of absence on behalf of "{{LeaveEmailToApprovers.leaveUser}}",from {{LeaveEmailToApprovers.fromDate}} to {{LeaveEmailToApprovers.toDate}} of {{LeaveEmailToApprovers.noOfDays}} days.\nLeave Type : {{LeaveEmailToApprovers.leaveType}}\nDuration : {{LeaveEmailToApprovers.fromDate}} to {{LeaveEmailToApprovers.toDate}}\nReason : {{LeaveEmailToApprovers.reason}}\nSo,please grant me leave of absence.','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('OnBehafLeaveNotification', 'Notification', 'OnBehafLeaveNotification', 'Used for Submitting Notification to Approvers.', 'New leave request on behalf of {{LeaveEmailToApprovers.leaveUser}}.', 'sourcelead', '', 'New leave request on behalf of {{LeaveEmailToApprovers.leaveUser}}.','N','SLADMIN', 'com', 'in.com');

set SQL_SAFE_UPDATES=1;