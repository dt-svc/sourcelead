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
			('Resource Request','Email', 'Resource Request', 'Resources Request for {{ResourceRequest.projectName}} Project', 'Resources Request for {{ResourceRequest.projectName}} Project','org', 'Best Regards,\n{{ResourceRequest.loginUserName}}\n{{ResourceRequest.loginOrgName}}', ' Dear {{ResourceRequest.approverName}},\n This is to notify we required resources for the {{ResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{ResourceRequest.resourceCount}}\n Total Estimated Hours : {{ResourceRequest.estimatedHours}}\n Total Estimated Budget : {{ResourceRequest.estimatedBudget}}\n\n {{ResourceRequest.resourceTable}}\n\n {{ResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

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
			('New Resource Request','Email', 'New Resource Request', 'New Resources Request for {{ResourceRequest.projectName}} Project', 'New Resources Request for {{NewResourceRequest.projectName}} Project','org', 'Best Regards,\n{{NewResourceRequest.loginUserName}}\n{{NewResourceRequest.loginOrgName}}', ' Dear {{NewResourceRequest.approverName}},\n This is to notify we required resources for the {{NewResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{NewResourceRequest.resourceCount}}\n Total Estimated Hours : {{NewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{NewResourceRequest.estimatedBudget}}\n\n {{NewResourceRequest.resourceTable}}\n\n {{NewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
		
# New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('NewResourceRequestNotification', 'Notification', 'NewResourceRequestNotification', 'Send mail to NewResourceRequestNotification','org','Resources Request for {{NewResourceRequest.projectName}} Project','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');




# Accepting single New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptSingleNewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{AcceptSingleNewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{AcceptSingleNewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{AcceptSingleNewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{AcceptSingleNewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'roleName', '{{AcceptSingleNewResourceRequest.roleName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{AcceptSingleNewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'billRate', '{{AcceptSingleNewResourceRequest.billRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'payRate', '{{AcceptSingleNewResourceRequest.payRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{AcceptSingleNewResourceRequest.loginLink}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Single New Resource Request','Email', 'Accept Single New Resource Request', 'Accept Single New Resources Request for {{AcceptSingleNewResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptSingleNewResourceRequest.loginUserName}}\n{{AcceptSingleNewResourceRequest.loginOrgName}}', ' Dear {{AcceptSingleNewResourceRequest.approverName}},\n This is to notify that {{AcceptSingleNewResourceRequest.roleName}} got processed for the {{AcceptSingleNewResourceRequest.projectName}}. Go through the brief details
\n Role Name : {{AcceptSingleNewResourceRequest.roleName}} \n Pay Rate : {{AcceptSingleNewResourceRequest.payRate}}\n Bill Rate : {{AcceptSingleNewResourceRequest.billRate}}\n Esitmated Hours : {{AcceptSingleNewResourceRequest.estimatedHours}}\n Esitmated Budget : {{AcceptSingleNewResourceRequest.estimatedHours}} \n {{AcceptSingleNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification', 'AcceptSingleNewResourceRequestNotification', 'Send mail to AcceptSingleNewResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


# Reject single New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectSingleNewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{RejectSingleNewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{RejectSingleNewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{RejectSingleNewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{RejectSingleNewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'roleName', '{{RejectSingleNewResourceRequest.roleName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{RejectSingleNewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{RejectSingleNewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'billRate', '{{RejectSingleNewResourceRequest.billRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'payRate', '{{RejectSingleNewResourceRequest.payRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{RejectSingleNewResourceRequest.rejectReason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{RejectSingleNewResourceRequest.loginLink}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Single New Resource Request','Email', 'Reject Single New Resource Request', 'Reject Single New Resources Request for {{RejectSingleNewResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectSingleNewResourceRequest.loginUserName}}\n{{RejectSingleNewResourceRequest.loginOrgName}}', ' Dear {{RejectSingleNewResourceRequest.approverName}},\n I regret to inform you that the requested resource {{RejectSingleNewResourceRequest.roleName}} for {{RejectSingleNewResourceRequest.projectName}} Project rejected due to following reason
\n Reason : {{RejectSingleNewResourceRequest.rejectReason}} \n Role Name : {{RejectSingleNewResourceRequest.roleName}} \n Pay Rate : {{RejectSingleNewResourceRequest.payRate}}\n Bill Rate : {{RejectSingleNewResourceRequest.billRate}}\n Esitmated Hours : {{RejectSingleNewResourceRequest.estimatedHours}}\n Esitmated Budget : {{RejectSingleNewResourceRequest.estimatedHours}} \n {{RejectSingleNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Reject single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectSingleNewResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification', 'AcceptSingleNewResourceRequestNotification', 'Send mail to AcceptSingleNewResourceRequestNotification','org',' Requested Resources approved for {{RejectSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


# Accepting Bulk New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptBulkNewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{AcceptBulkNewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{AcceptBulkNewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{AcceptBulkNewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{AcceptBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{AcceptBulkNewResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{AcceptBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{AcceptBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{AcceptBulkNewResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{AcceptBulkNewResourceRequest.resourceTable}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Bulk New Resource Request','Email', 'Accept Bulk New Resource Request', 'Accept Bulk New Resources Request for {{AcceptSingleNewResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptBulkNewResourceRequest.loginUserName}}\n{{AcceptBulkNewResourceRequest.loginOrgName}}', ' Dear {{AcceptBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{AcceptBulkNewResourceRequest.projectName}} project got approved.\n Number of Resources : {{AcceptBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{AcceptBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{AcceptBulkNewResourceRequest.estimatedBudget}}\n {{AcceptBulkNewResourceRequest.resourceTable}}\n\n {{AcceptBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptBulkNewResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptBulkNewResourceRequestNotification', 'Notification', 'AcceptBulkNewResourceRequestNotification', 'Send mail to AcceptBulkNewResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


# Accepting Bulk New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectBulkNewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{RejectBulkNewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{RejectBulkNewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{RejectBulkNewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{RejectBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{RejectBulkNewResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{RejectBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{RejectBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{RejectBulkNewResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{RejectBulkNewResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{RejectBulkNewResourceRequest.rejectReason}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Bulk New Resource Request','Email', 'Reject Bulk New Resource Request', 'Reject Bulk New Resources Request for {{RejectBulkNewResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectBulkNewResourceRequest.loginUserName}}\n{{RejectBulkNewResourceRequest.loginOrgName}}', ' Dear {{RejectBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{RejectBulkNewResourceRequest.projectName}} project got Rejected.\n Reject Reason : {{RejectBulkNewResourceRequest.rejectReason}} \n Number of Resources : {{RejectBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{RejectBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{RejectBulkNewResourceRequest.estimatedBudget}}\n {{RejectBulkNewResourceRequest.resourceTable}}\n\n {{RejectBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');


# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectBulkNewResourceRequesttNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectBulkNewResourceRequesttNotification', 'Notification', 'RejectBulkNewResourceRequesttNotification', 'Send mail to RejectBulkNewResourceRequesttNotification','org',' Requested Resources Rejected for {{RejectBulkNewResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



# OnHold Bulk New resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OnHoldBulkNewResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{OnHoldBulkNewResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{OnHoldBulkNewResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{OnHoldBulkNewResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{OnHoldBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{OnHoldBulkNewResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{OnHoldBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{OnHoldBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{OnHoldBulkNewResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{OnHoldBulkNewResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{OnHoldBulkNewResourceRequest.rejectReason}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('OnHold Bulk New Resource Request','Email', 'OnHold Bulk New Resource Request', 'OnHold Bulk New Resources Request for {{OnHoldBulkNewResourceRequest.projectName}} Project', 'Requested Resources On Hold','org', 'Best Regards,\n{{OnHoldBulkNewResourceRequest.loginUserName}}\n{{OnHoldBulkNewResourceRequest.loginOrgName}}', ' Dear {{OnHoldBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{OnHoldBulkNewResourceRequest.projectName}} project are on-hold,due to following reason
.\n Reject Reason : {{OnHoldBulkNewResourceRequest.rejectReason}} \n Number of Resources : {{OnHoldBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{OnHoldBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{OnHoldBulkNewResourceRequest.estimatedBudget}}\n {{OnHoldBulkNewResourceRequest.resourceTable}}\n\n {{OnHoldBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OnHoldBulkNewResourceRequesttNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OnHoldBulkNewResourceRequesttNotification', 'Notification', 'OnHoldBulkNewResourceRequesttNotification', 'Send mail to OnHoldBulkNewResourceRequesttNotification','org',' Requested Resources on hold for {{OnHoldBulkNewResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



#Accept Bulk Resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptBulkResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{AcceptBulkResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{AcceptBulkResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{AcceptBulkResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{AcceptBulkResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{AcceptBulkResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{AcceptBulkResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{AcceptBulkResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{AcceptBulkResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{AcceptBulkResourceRequest.loginLink}}');


INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Bulk Resource Request','Email', 'Accept Bulk Resource Request', 'Accept Bulk Resource Request', 'Requested Resources approved','org', 'Best Regards,\n{{AcceptBulkResourceRequest.loginUserName}}\n{{AcceptBulkResourceRequest.loginOrgName}}', ' Dear {{AcceptBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{AcceptBulkResourceRequest.projectName}} project got approved.\n Number of Resources : {{AcceptBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{AcceptBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{AcceptBulkResourceRequest.estimatedBudget}}\n {{AcceptBulkResourceRequest.resourceTable}}\n {{AcceptBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

			
#Accept Bulk Resource Request Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptBulkResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptBulkResourceRequestNotification', 'Notification', 'AcceptBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org',' Requested Resources approved for {{AcceptBulkResourceRequest.projectName}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


#Reject Bulk Resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectBulkResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{RejectBulkResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{RejectBulkResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{RejectBulkResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{RejectBulkResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{RejectBulkResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{RejectBulkResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{RejectBulkResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{RejectBulkResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{RejectBulkResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{RejectBulkResourceRequest.rejectReason}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Bulk Resource Request','Email', 'Reject Bulk Resource Request', 'Reject Bulk Resource Request', 'Requested Resources Reject','org', 'Best Regards,\n{{RejectBulkResourceRequest.loginUserName}}\n{{RejectBulkResourceRequest.loginOrgName}}', ' Dear {{RejectBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{RejectBulkResourceRequest.projectName}} project got rejected.\n Rejecte Reason: {{RejectBulkResourceRequest.rejectReason}}\n Number of Resources : {{RejectBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{RejectBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{RejectBulkResourceRequest.estimatedBudget}}\n {{RejectBulkResourceRequest.resourceTable}}\n {{RejectBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
			
#Accept Bulk Resource Request Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectBulkResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectBulkResourceRequestNotification', 'Notification', 'RejectBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Requested Resources Reject for {{RejectBulkResourceRequest.projectName}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


#Reject Bulk Resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OnHoldBulkResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{OnHoldBulkResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{OnHoldBulkResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{OnHoldBulkResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{OnHoldBulkResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceCount', '{{OnHoldBulkResourceRequest.resourceCount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{OnHoldBulkResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{OnHoldBulkResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceTable', '{{OnHoldBulkResourceRequest.resourceTable}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{OnHoldBulkResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{OnHoldBulkResourceRequest.rejectReason}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('OnHold Bulk Resource Request','Email', 'OnHold Bulk Resource Request', 'OnHold Bulk Resource Request', 'Requested Resources OnHold','org', 'Best Regards,\n{{OnHoldBulkResourceRequest.loginUserName}}\n{{OnHoldBulkResourceRequest.loginOrgName}}', ' Dear {{OnHoldBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{OnHoldBulkResourceRequest.projectName}} project are on-hold,due to following reason.\n Reason: {{OnHoldBulkResourceRequest.rejectReason}}\n Number of Resources : {{OnHoldBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{OnHoldBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{OnHoldBulkResourceRequest.estimatedBudget}}\n {{OnHoldBulkResourceRequest.resourceTable}}\n {{OnHoldBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
			
#Accept Bulk Resource Request Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OnHoldBulkResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OnHoldBulkResourceRequestNotification', 'Notification', 'OnHoldBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Requested resources are on hold for {{OnHoldBulkResourceRequest.projectName}}','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



# Accepting single resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptSingleResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{AcceptSingleResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{AcceptSingleResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{AcceptSingleResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{AcceptSingleResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceName', '{{AcceptSingleResourceRequest.resourceName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{AcceptSingleResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'billRate', '{{AcceptSingleResourceRequest.billRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'payRate', '{{AcceptSingleResourceRequest.payRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{AcceptSingleResourceRequest.loginLink}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Single Resource Request','Email', 'Accept Single Resource Request', 'Accept Single Resources Request for {{AcceptSingleResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptSingleResourceRequest.loginUserName}}\n{{AcceptSingleResourceRequest.loginOrgName}}', ' Dear {{AcceptSingleResourceRequest.approverName}},\n This is to notify that {{AcceptSingleResourceRequest.resourceName}} got processed for the {{AcceptSingleResourceRequest.projectName}}. Go through the brief details
\n Resource Name : {{AcceptSingleResourceRequest.resourceName}} \n Pay Rate : {{AcceptSingleResourceRequest.payRate}}\n Bill Rate : {{AcceptSingleResourceRequest.billRate}}\n Esitmated Hours : {{AcceptSingleResourceRequest.estimatedHours}}\n Esitmated Budget : {{AcceptSingleResourceRequest.estimatedHours}} \n {{AcceptSingleResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptSingleResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleResourceRequestNotification', 'Notification', 'AcceptSingleResourceRequestNotification', 'Send mail to AcceptSingleResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


# Reject single resource Request Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectSingleResourceRequest','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{RejectSingleResourceRequest.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{RejectSingleResourceRequest.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserName', '{{RejectSingleResourceRequest.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrgName', '{{RejectSingleResourceRequest.loginOrgName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'resourceName', '{{RejectSingleResourceRequest.resourceName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedHours', '{{RejectSingleResourceRequest.estimatedHours}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'billRate', '{{RejectSingleResourceRequest.billRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'payRate', '{{RejectSingleResourceRequest.payRate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginLink', '{{RejectSingleResourceRequest.loginLink}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'rejectReason', '{{RejectSingleResourceRequest.rejectReason}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Single Resource Request','Email', 'Reject Single Resource Request', 'Reject Single Resources Request for {{RejectSingleResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectSingleResourceRequest.loginUserName}}\n{{RejectSingleResourceRequest.loginOrgName}}', ' Dear {{RejectSingleResourceRequest.approverName}},\n I regret to inform you that the requested resource {{RejectSingleResourceRequest.resourceName}} for {{RejectSingleResourceRequest.projectName}} Project rejected due to following reason
\n Rejected Reason : {{RejectSingleResourceRequest.rejectReason}} \n Resource Name : {{RejectSingleResourceRequest.resourceName}} \n Pay Rate : {{RejectSingleResourceRequest.payRate}}\n Bill Rate : {{RejectSingleResourceRequest.billRate}}\n Esitmated Hours : {{RejectSingleResourceRequest.estimatedHours}}\n Esitmated Budget : {{RejectSingleResourceRequest.estimatedHours}} \n {{RejectSingleResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectSingleResourceRequestNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectSingleResourceRequestNotification', 'Notification', 'RejectSingleResourceRequestNotification', 'Send mail to RejectSingleResourceRequestNotification','org',' Requested Resources rejectd for {{RejectSingleResourceRequest.projectName}} ','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


