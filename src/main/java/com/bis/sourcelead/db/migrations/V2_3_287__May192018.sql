#UserNewActionAssigned_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserNewActionAssigned', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{UserNewActionAssigned.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'terminatedUser', '{{UserNewActionAssigned.terminatedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{UserNewActionAssigned.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqCount', '{{UserNewActionAssigned.reqCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{UserNewActionAssigned.loginURL}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserNewActionAssigned', 'Email', 'UserNewActionAssigned', 'Send mail to Assigned Task', 'New tasks Allocated To You', 'sourcelead', 'Best Regards,\n{{UserNewActionAssigned.loginUser}}','Dear {{UserNewActionAssigned.assignedUser}},\nTasks which are allocated to {{UserNewActionAssigned.terminatedUser}} are transfered to you. \n the allocated tasks are following \n Assigned as Requirment Manager-{{UserNewActionAssigned.reqCount}} \n For brief details login into the system.\n{{UserNewActionAssigned.loginURL}} ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');




#UserNewActionAssignedAsaApprover_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserNewActionAssignedAsaApprover', 'Email');
#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{UserNewActionAssignedAsaApprover.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'terminatedUser', '{{UserNewActionAssignedAsaApprover.terminatedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{UserNewActionAssignedAsaApprover.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqCount', '{{UserNewActionAssignedAsaApprover.reqCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{UserNewActionAssignedAsaApprover.loginURL}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserNewActionAssignedAsaApprover', 'Email', 'UserNewActionAssignedAsaApprover', 'Send mail to Assigned Task', 'New tasks Allocated To You', 'sourcelead', 'Best Regards,\n {{UserNewActionAssignedAsaApprover.loginUser}}','Dear {{UserNewActionAssignedAsaApprover.assignedUser}},\nTasks which are allocated to {{UserNewActionAssignedAsaApprover.terminatedUser}} are transfered to you. \n the allocated tasks are following \n Assigned as Requirment Approver Count: {{UserNewActionAssignedAsaApprover.reqCount}} \n For brief details login into the system.\n{{UserNewActionAssignedAsaApprover.loginURL}} ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');







#UserNewActionAssignedAsaReportingManager_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserNewActionAssignedAsaReportingManager', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{UserNewActionAssignedAsaReportingManager.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'terminatedUser', '{{UserNewActionAssignedAsaReportingManager.terminatedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{UserNewActionAssignedAsaReportingManager.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqCount', '{{UserNewActionAssignedAsaReportingManager.reqCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{UserNewActionAssignedAsaReportingManager.loginURL}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserNewActionAssignedAsaReportingManager', 'Email', 'UserNewActionAssignedAsaReportingManager', 'Send mail to Assigned Task', 'New tasks Allocated To You', 'sourcelead', 'Best Regards,\n {{UserNewActionAssignedAsaReportingManager.loginUser}}', 'Dear {{UserNewActionAssignedAsaReportingManager.assignedUser}},\nTasks which are allocated to {{UserNewActionAssignedAsaReportingManager.terminatedUser}} are transfered to you. \n the allocated tasks are following \n Assigned as ReportingManager Count: {{UserNewActionAssignedAsaReportingManager.reqCount}} \n For brief details login into the system.\n{{UserNewActionAssignedAsaReportingManager.loginURL}} ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');





#UserNewActionAssignedAsaTeamOwnerUser_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserNewActionAssignedAsaTeamOwnerUser', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{UserNewActionAssignedAsaTeamOwnerUser.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'terminatedUser', '{{UserNewActionAssignedAsaTeamOwnerUser.terminatedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{UserNewActionAssignedAsaTeamOwnerUser.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqCount', '{{UserNewActionAssignedAsaTeamOwnerUser.reqCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{UserNewActionAssignedAsaTeamOwnerUser.loginURL}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserNewActionAssignedAsaTeamOwnerUser', 'Email', 'UserNewActionAssignedAsaTeamOwnerUser', 'Send mail to Assigned Task', 'New tasks Allocated To You', 'sourcelead', 'Best Regards,\n {{UserNewActionAssignedAsaTeamOwnerUser.loginUser}}','Dear {{UserNewActionAssignedAsaTeamOwnerUser.assignedUser}},\nTasks which are allocated to {{UserNewActionAssignedAsaTeamOwnerUser.terminatedUser}} are transfered to you. \n the allocated tasks are following \n Assigned as TeamOwner Count: {{UserNewActionAssignedAsaTeamOwnerUser.reqCount}} \n For brief details login into the system.\n{{UserNewActionAssignedAsaTeamOwnerUser.loginURL}} ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');






#UserNewActionAssignedAsaProjectApproverUser_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserNewActionAssignedAsaProjectApproverUser', 'Email');

#CLASS_FIELD
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUser', '{{UserNewActionAssignedAsaProjectApproverUser.loginUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'terminatedUser', '{{UserNewActionAssignedAsaProjectApproverUser.terminatedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'assignedUser', '{{UserNewActionAssignedAsaProjectApproverUser.assignedUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reqCount', '{{UserNewActionAssignedAsaProjectApproverUser.reqCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginURL', '{{UserNewActionAssignedAsaProjectApproverUser.loginURL}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserNewActionAssignedAsaProjectApproverUser', 'Email', 'UserNewActionAssignedAsaProjectApproverUser', 'Send mail to Assigned Task', 'New tasks Allocated To You', 'sourcelead', 'Best Regards,\n {{UserNewActionAssignedAsaProjectApproverUser.loginUser}}','Dear {{UserNewActionAssignedAsaProjectApproverUser.assignedUser}},\nTasks which are allocated to {{UserNewActionAssignedAsaProjectApproverUser.terminatedUser}} are transfered to you. \n the allocated tasks are following \n Assigned as ProjectApproverUsers Count: {{UserNewActionAssignedAsaProjectApproverUser.reqCount}} \n For brief details login into the system.\n{{UserNewActionAssignedAsaProjectApproverUser.loginURL}} ', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');


