
# email to vendor when manager reject the timesheet
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('TimesheetRejectEmailToVendor','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeename', '{{TimesheetRejectEmailToVendor.employeename}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'vendorcontactname', '{{TimesheetRejectEmailToVendor.vendorcontactname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectname', '{{TimesheetRejectEmailToVendor.projectname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'frequency', '{{TimesheetRejectEmailToVendor.frequency}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{TimesheetRejectEmailToVendor.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{TimesheetRejectEmailToVendor.reason}}');
#Email
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('TimesheetRejectEmailToVendor','Email', 'TimesheetRejectEmailToVendor', 'Mail should forward when the client(approver) reject the timesheets of vendor resource', '{{TimesheetRejectEmailToVendor.employeename}} timesheet has been rejected','org', 'Thanks & Regards\n Sourcelead team', 'Dear {{TimesheetRejectEmailToVendor.vendorcontactname}},\n Timesheet of {{TimesheetRejectEmailToVendor.employeename}} has been rejected for {{TimesheetRejectEmailToVendor.projectname}} project for the period {{TimesheetRejectEmailToVendor.frequency}} by {{TimesheetRejectEmailToVendor.clientName}} \n Reason : {{TimesheetRejectEmailToVendor.reason}}','N', 'SLADMIN', 'slead', 'sleadIN');

	
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('TimesheetRejectEmailToVendor', 'Notification', 'TimesheetRejectEmailToVendor', ' Notification should forward when the client(approver) reject the timesheets of vendor resource','{{TimesheetRejectEmailToVendor.employeename}} timesheet has been rejected', 'sourcelead', '', '{{TimesheetRejectEmailToVendor.employeename}} timesheet has been rejected','N','SLADMIN', 'com', 'in.com');

ALTER TABLE `EXPENSES_ENTRIES`
ADD COLUMN `PAID_BY` VARCHAR(45) NULL DEFAULT NULL AFTER `AMOUNT`;


