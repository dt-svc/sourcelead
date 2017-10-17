
# email to vendor when manager approves the timesheet
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('TimesheetApprovedEmailToVendor','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeename', '{{TimesheetApprovedEmailToVendor.employeename}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'vendorcontactname', '{{TimesheetApprovedEmailToVendor.vendorcontactname}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectname', '{{TimesheetApprovedEmailToVendor.projectname}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'frequency', '{{TimesheetApprovedEmailToVendor.frequency}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientName', '{{TimesheetApprovedEmailToVendor.clientName}}');

#Email
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('TimesheetApprovedEmailToVendor','Email', 'TimesheetApprovedEmailToVendor', 'Mail should forward when the client(approver) approved the timesheets of vendor resource', '{{TimesheetApprovedEmailToVendor.employeename}} timesheet has been approved','org', 'Thanks & Regards\n Sourcelead team', 'Dear {{TimesheetApprovedEmailToVendor.vendorcontactname}},\n Timesheet of {{TimesheetApprovedEmailToVendor.employeename}} has been approved for {{TimesheetApprovedEmailToVendor.projectname}}project for the period{{TimesheetApprovedEmailToVendor.frequency}} by {{TimesheetApprovedEmailToVendor.clientName}}','N', 'SLADMIN', 'slead', 'sleadIN');

	
#Notification
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('TimesheetApprovedEmailToVendor', 'Notification', 'TimesheetApprovedEmailToVendor', ' Notification should forward when the client(approver) approved the timesheets of vendor resource','{{TimesheetApprovedEmailToVendor.employeename}} timesheet has been approved', 'sourcelead', '', '{{TimesheetApprovedEmailToVendor.employeename}} timesheet has been approved','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

ALTER TABLE `sourcelead`.`ACTION_HISTORY` ADD CONSTRAINT FK1_ORG_LOCATION_ID FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `sourcelead`.ORG_LOCATIONS(ID);