# Resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{ResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{ResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{ResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{ResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{ResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{ResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{ResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{ResourceRequest.loginLink}}');




INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Resource Request','Email', 'Resource Request', 'Resources Request for {{ResourceRequest.projectName}} Project', 'Resources Request for {{ResourceRequest.projectName}} Project','org', 'Best Regards,\n{{ResourceRequest.loginUserName}}\n{{ResourceRequest.loginOrgName}}', 'Dear {{ResourceRequest.approverName}},\n\n This is to notify we required resources for the {{ResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{ResourceRequest.resourceCount}}\n Total Estimated Hours : {{ResourceRequest.estimatedHours}}\n Total Estimated Budget : {{ResourceRequest.estimatedBudget}}\n\n {{ResourceRequest.resourceTable}}\n\n {{ResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
							

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ResourceRequestNotification', 'Notification', 'ResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Resources Request for {{ResourceRequest.projectName}} Project','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



# New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('NewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{NewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{NewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{NewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{NewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{NewResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{NewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{NewResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{NewResourceRequest.loginLink}}');



INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('New Resource Request','Email', 'New Resource Request', 'New Resources Request for {{ResourceRequest.projectName}} Project', 'New Resources Request for {{NewResourceRequest.projectName}} Project','org', 'Best Regards,\n{{NewResourceRequest.loginUserName}}\n{{NewResourceRequest.loginOrgName}}', 'Dear {{NewResourceRequest.approverName}},\n\n This is to notify we required resources for the {{NewResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{NewResourceRequest.resourceCount}}\n Total Estimated Hours : {{NewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{NewResourceRequest.estimatedBudget}}\n\n {{NewResourceRequest.resourceTable}}\n\n {{NewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
		
# New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('NewResourceRequestNotification', 'Notification', 'NewResourceRequestNotification', 'Send mail to NewResourceRequestNotification','org','Resources Request for {{NewResourceRequest.projectName}} Project','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
