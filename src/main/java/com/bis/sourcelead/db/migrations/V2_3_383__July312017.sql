
DROP TABLE IF EXISTS `PROJECT_PLANNING_REQUREST`;
CREATE TABLE `PROJECT_PLANNING_REQUREST` (
ID BIGINT NOT NULL AUTO_INCREMENT,      
REQUEST_ID VARCHAR(250),
PROJECT_ID BIGINT DEFAULT NULL,
DESCRIPTION LONGTEXT DEFAULT NULL,
APPROVER_USER_ID BIGINT DEFAULT NULL,
APPROVER_NAME VARCHAR(500) DEFAULT NULL,
REQUESTED_RESOURCES INT DEFAULT NULL,
ALLOCATED_RESOURCES INT DEFAULT NULL,
ESTIMATED_HOURS DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET_CURRENCY VARCHAR(50) DEFAULT NULL,
REQUEST_STATUS VARCHAR(50) DEFAULT NULL,
REQUESTED_USER VARCHAR(500) DEFAULT NULL,
ORG_LOCATION_ID BIGINT DEFAULT NULL,
REQUEST_UUID VARCHAR(20) DEFAULT NULL,
REQUEST_TYPE VARCHAR(50) DEFAULT NULL,
REJECTED_REASON LONGTEXT DEFAULT NULL,
STATUS INT,
CREATED_DATE DATE,
CREATED_BY BIGINT DEFAULT NULL,
UPDATED_DATE DATE,
UPDATED_BY BIGINT DEFAULT NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
FOREIGN KEY(PROJECT_ID) REFERENCES `PROJECTS`(ID),
FOREIGN KEY(APPROVER_USER_ID) REFERENCES `USER`(ID),
FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
);

DROP TABLE IF EXISTS `PROJECT_PLANNING_NEW_RESOURCE_REQUEST`;
CREATE TABLE `PROJECT_PLANNING_NEW_RESOURCE_REQUEST` (
ID BIGINT NOT NULL AUTO_INCREMENT,      
PROJECT_PLANNING_REQUREST_ID BIGINT DEFAULT NULL,
ROLE_ID BIGINT DEFAULT NULL,
ROLE_NAME VARCHAR(250),
OTHER_ROLE VARCHAR(100),
NO_OF_RESOURCES INT DEFAULT NULL,
MIN_EXP INT DEFAULT NULL,
MAX_EXP INT DEFAULT NULL,
SKILLS TEXT DEFAULT NULL,
BILL_RATE VARCHAR(100) DEFAULT NULL,
RATE_CURRENCY VARCHAR(50) DEFAULT NULL,
RATE_TYPE VARCHAR(100) DEFAULT NULL,
PAY_RATE VARCHAR(100) DEFAULT NULL,
START_DATE DATE,
END_DATE DATE,
ESTIMATED_HOURS DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET_CURRENCY VARCHAR(50) DEFAULT NULL,
CONSULTING_TYPE VARCHAR(50) DEFAULT NULL,
ORG_LOCATION_ID BIGINT DEFAULT NULL,
REQUEST_UUID VARCHAR(20) DEFAULT NULL,
NEW_RESOURCE_STATUS VARCHAR(50) DEFAULT NULL,
REJECTED_REASON LONGTEXT DEFAULT NULL,
STATUS INT,
CREATED_DATE DATE,
CREATED_BY BIGINT DEFAULT NULL,
UPDATED_DATE DATETIME,
UPDATED_BY BIGINT DEFAULT NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
FOREIGN KEY(PROJECT_PLANNING_REQUREST_ID) REFERENCES `PROJECT_PLANNING_REQUREST`(ID),
FOREIGN KEY(ROLE_ID) REFERENCES `ROLES`(ID),
FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
);


DROP TABLE IF EXISTS `PROJECT_PLANNING_RESOURCE_REQUEST`;
CREATE TABLE `PROJECT_PLANNING_RESOURCE_REQUEST` (
ID BIGINT NOT NULL AUTO_INCREMENT,      
PROJECT_PLANNING_REQUREST_ID BIGINT DEFAULT NULL,
CANDIDATE_ID BIGINT DEFAULT NULL,
CANDIDATE_NAME VARCHAR(500) DEFAULT NULL,
PAY_RATE DOUBLE DEFAULT NULL,
PAY_CURRENCY VARCHAR(100) DEFAULT NULL,
PAY_RATE_TYPE VARCHAR(100) DEFAULT NULL,
BILL_RATE DOUBLE DEFAULT NULL,
BILL_RATE_CURRENCY VARCHAR(100) DEFAULT NULL,
BILL_RATE_TYPE VARCHAR(100) DEFAULT NULL,
START_DATE DATE,
END_DATE DATE,
CONSULTING_TYPE VARCHAR(100) DEFAULT NULL,
ESTIMATED_HOURS DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET DOUBLE DEFAULT NULL,
ESTIMATED_BUDGET_CURRENCY VARCHAR(50) DEFAULT NULL,
REQUEST_UUID VARCHAR(20) DEFAULT NULL,
REJECTED_REASON LONGTEXT DEFAULT NULL,
ORG_LOCATION_ID BIGINT DEFAULT NULL,
STATUS INT,
RESOURCE_STATUS VARCHAR(50) DEFAULT NULL,
CREATED_DATE DATE,
CREATED_BY BIGINT DEFAULT NULL,
UPDATED_DATE DATE,
UPDATED_BY BIGINT DEFAULT NULL,
PRIMARY KEY (`ID`),
FOREIGN KEY(PROJECT_PLANNING_REQUREST_ID) REFERENCES `PROJECT_PLANNING_REQUREST`(ID),
FOREIGN KEY(CANDIDATE_ID) REFERENCES `CANDIDATE`(ID),
FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
FOREIGN KEY(CREATED_BY) REFERENCES `USER`(ID),
FOREIGN KEY(UPDATED_BY) REFERENCES `USER`(ID)
);


# Resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{ResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{ResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{ResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{ResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{ResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{ResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{ResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{ResourceRequest.loginLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Resource Request','Email', 'Resource Request', 'Resources Request for {{ResourceRequest.projectName}} Project', 'Resources Request for {{ResourceRequest.projectName}} Project','org', 'Best Regards,\n{{ResourceRequest.loginUserName}}\n{{ResourceRequest.loginOrgName}}', ' Dear {{ResourceRequest.approverName}},\n This is to notify we required resources for the {{ResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{ResourceRequest.resourceCount}}\n Total Estimated Hours : {{ResourceRequest.estimatedHours}}\n Total Estimated Budget : {{ResourceRequest.estimatedBudget}}\n\n {{ResourceRequest.resourceTable}}\n\n {{ResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ResourceRequestNotification', 'Notification', 'ResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Resources Request for {{ResourceRequest.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');



# New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('NewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{NewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{NewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{NewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{NewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{NewResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{NewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{NewResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{NewResourceRequest.loginLink}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('New Resource Request','Email', 'New Resource Request', 'New Resources Request for {{ResourceRequest.projectName}} Project', 'New Resources Request for {{NewResourceRequest.projectName}} Project','org', 'Best Regards,\n{{NewResourceRequest.loginUserName}}\n{{NewResourceRequest.loginOrgName}}', ' Dear {{NewResourceRequest.approverName}},\n This is to notify we required resources for the {{NewResourceRequest.projectName}} project,so go through the brief details of the resources.\n\n Number of Resources : {{NewResourceRequest.resourceCount}}\n Total Estimated Hours : {{NewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{NewResourceRequest.estimatedBudget}}\n\n {{NewResourceRequest.resourceTable}}\n\n {{NewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
		
# New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('NewResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('NewResourceRequestNotification', 'Notification', 'NewResourceRequestNotification', 'Send mail to NewResourceRequestNotification','org','Resources Request for {{NewResourceRequest.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');




# Accepting single New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptSingleNewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{AcceptSingleNewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{AcceptSingleNewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{AcceptSingleNewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{AcceptSingleNewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'roleName', '{{AcceptSingleNewResourceRequest.roleName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{AcceptSingleNewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billRate', '{{AcceptSingleNewResourceRequest.billRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'payRate', '{{AcceptSingleNewResourceRequest.payRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{AcceptSingleNewResourceRequest.loginLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Single New Resource Request','Email', 'Accept Single New Resource Request', 'Accept Single New Resources Request for {{AcceptSingleNewResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptSingleNewResourceRequest.loginUserName}}\n{{AcceptSingleNewResourceRequest.loginOrgName}}', ' Dear {{AcceptSingleNewResourceRequest.approverName}},\n This is to notify that {{AcceptSingleNewResourceRequest.roleName}} got processed for the {{AcceptSingleNewResourceRequest.projectName}}. Go through the brief details
\n Role Name : {{AcceptSingleNewResourceRequest.roleName}} \n Pay Rate : {{AcceptSingleNewResourceRequest.payRate}}\n Bill Rate : {{AcceptSingleNewResourceRequest.billRate}}\n Esitmated Hours : {{AcceptSingleNewResourceRequest.estimatedHours}}\n Esitmated Budget : {{AcceptSingleNewResourceRequest.estimatedHours}} \n {{AcceptSingleNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification', 'AcceptSingleNewResourceRequestNotification', 'Send mail to AcceptSingleNewResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');


# Reject single New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectSingleNewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{RejectSingleNewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{RejectSingleNewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{RejectSingleNewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{RejectSingleNewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'roleName', '{{RejectSingleNewResourceRequest.roleName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{RejectSingleNewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{RejectSingleNewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billRate', '{{RejectSingleNewResourceRequest.billRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'payRate', '{{RejectSingleNewResourceRequest.payRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{RejectSingleNewResourceRequest.rejectReason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{RejectSingleNewResourceRequest.loginLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Single New Resource Request','Email', 'Reject Single New Resource Request', 'Reject Single New Resources Request for {{RejectSingleNewResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectSingleNewResourceRequest.loginUserName}}\n{{RejectSingleNewResourceRequest.loginOrgName}}', ' Dear {{RejectSingleNewResourceRequest.approverName}},\n I regret to inform you that the requested resource {{RejectSingleNewResourceRequest.roleName}} for {{RejectSingleNewResourceRequest.projectName}} Project rejected due to following reason
\n Reason : {{RejectSingleNewResourceRequest.rejectReason}} \n Role Name : {{RejectSingleNewResourceRequest.roleName}} \n Pay Rate : {{RejectSingleNewResourceRequest.payRate}}\n Bill Rate : {{RejectSingleNewResourceRequest.billRate}}\n Esitmated Hours : {{RejectSingleNewResourceRequest.estimatedHours}}\n Esitmated Budget : {{RejectSingleNewResourceRequest.estimatedHours}} \n {{RejectSingleNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Reject single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectSingleNewResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleNewResourceRequestNotification', 'Notification', 'AcceptSingleNewResourceRequestNotification', 'Send mail to AcceptSingleNewResourceRequestNotification','org',' Requested Resources approved for {{RejectSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');


# Accepting Bulk New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptBulkNewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{AcceptBulkNewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{AcceptBulkNewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{AcceptBulkNewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{AcceptBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{AcceptBulkNewResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{AcceptBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{AcceptBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{AcceptBulkNewResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{AcceptBulkNewResourceRequest.resourceTable}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Bulk New Resource Request','Email', 'Accept Bulk New Resource Request', 'Accept Bulk New Resources Request for {{AcceptSingleNewResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptBulkNewResourceRequest.loginUserName}}\n{{AcceptBulkNewResourceRequest.loginOrgName}}', ' Dear {{AcceptBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{AcceptBulkNewResourceRequest.projectName}} project got approved.\n Number of Resources : {{AcceptBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{AcceptBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{AcceptBulkNewResourceRequest.estimatedBudget}}\n {{AcceptBulkNewResourceRequest.resourceTable}}\n\n {{AcceptBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptBulkNewResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptBulkNewResourceRequestNotification', 'Notification', 'AcceptBulkNewResourceRequestNotification', 'Send mail to AcceptBulkNewResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleNewResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');


# Accepting Bulk New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectBulkNewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{RejectBulkNewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{RejectBulkNewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{RejectBulkNewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{RejectBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{RejectBulkNewResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{RejectBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{RejectBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{RejectBulkNewResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{RejectBulkNewResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{RejectBulkNewResourceRequest.rejectReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Bulk New Resource Request','Email', 'Reject Bulk New Resource Request', 'Reject Bulk New Resources Request for {{RejectBulkNewResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectBulkNewResourceRequest.loginUserName}}\n{{RejectBulkNewResourceRequest.loginOrgName}}', ' Dear {{RejectBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{RejectBulkNewResourceRequest.projectName}} project got Rejected.\n Reject Reason : {{RejectBulkNewResourceRequest.rejectReason}} \n Number of Resources : {{RejectBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{RejectBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{RejectBulkNewResourceRequest.estimatedBudget}}\n {{RejectBulkNewResourceRequest.resourceTable}}\n\n {{RejectBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');


# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectBulkNewResourceRequesttNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectBulkNewResourceRequesttNotification', 'Notification', 'RejectBulkNewResourceRequesttNotification', 'Send mail to RejectBulkNewResourceRequesttNotification','org',' Requested Resources Rejected for {{RejectBulkNewResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');



# OnHold Bulk New resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OnHoldBulkNewResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{OnHoldBulkNewResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{OnHoldBulkNewResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{OnHoldBulkNewResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{OnHoldBulkNewResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{OnHoldBulkNewResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{OnHoldBulkNewResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{OnHoldBulkNewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{OnHoldBulkNewResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{OnHoldBulkNewResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{OnHoldBulkNewResourceRequest.rejectReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('OnHold Bulk New Resource Request','Email', 'OnHold Bulk New Resource Request', 'OnHold Bulk New Resources Request for {{OnHoldBulkNewResourceRequest.projectName}} Project', 'Requested Resources On Hold','org', 'Best Regards,\n{{OnHoldBulkNewResourceRequest.loginUserName}}\n{{OnHoldBulkNewResourceRequest.loginOrgName}}', ' Dear {{OnHoldBulkNewResourceRequest.approverName}},\n This is to notify we required resources for the {{OnHoldBulkNewResourceRequest.projectName}} project are on-hold,due to following reason
.\n Reject Reason : {{OnHoldBulkNewResourceRequest.rejectReason}} \n Number of Resources : {{OnHoldBulkNewResourceRequest.resourceCount}}\n Total Estimated Hours : {{OnHoldBulkNewResourceRequest.estimatedHours}}\n Total Estimated Budget : {{OnHoldBulkNewResourceRequest.estimatedBudget}}\n {{OnHoldBulkNewResourceRequest.resourceTable}}\n\n {{OnHoldBulkNewResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OnHoldBulkNewResourceRequesttNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OnHoldBulkNewResourceRequesttNotification', 'Notification', 'OnHoldBulkNewResourceRequesttNotification', 'Send mail to OnHoldBulkNewResourceRequesttNotification','org',' Requested Resources on hold for {{OnHoldBulkNewResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');



#Accept Bulk Resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptBulkResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{AcceptBulkResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{AcceptBulkResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{AcceptBulkResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{AcceptBulkResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{AcceptBulkResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{AcceptBulkResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{AcceptBulkResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{AcceptBulkResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{AcceptBulkResourceRequest.loginLink}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Bulk Resource Request','Email', 'Accept Bulk Resource Request', 'Accept Bulk Resource Request', 'Requested Resources approved','org', 'Best Regards,\n{{AcceptBulkResourceRequest.loginUserName}}\n{{AcceptBulkResourceRequest.loginOrgName}}', ' Dear {{AcceptBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{AcceptBulkResourceRequest.projectName}} project got approved.\n Number of Resources : {{AcceptBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{AcceptBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{AcceptBulkResourceRequest.estimatedBudget}}\n {{AcceptBulkResourceRequest.resourceTable}}\n {{AcceptBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

			
#Accept Bulk Resource Request Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptBulkResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptBulkResourceRequestNotification', 'Notification', 'AcceptBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org',' Requested Resources approved for {{AcceptBulkResourceRequest.projectName}}','N', 'SLADMIN', 'com', 'in.com');


#Reject Bulk Resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectBulkResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{RejectBulkResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{RejectBulkResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{RejectBulkResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{RejectBulkResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{RejectBulkResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{RejectBulkResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{RejectBulkResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{RejectBulkResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{RejectBulkResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{RejectBulkResourceRequest.rejectReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Bulk Resource Request','Email', 'Reject Bulk Resource Request', 'Reject Bulk Resource Request', 'Requested Resources Reject','org', 'Best Regards,\n{{RejectBulkResourceRequest.loginUserName}}\n{{RejectBulkResourceRequest.loginOrgName}}', ' Dear {{RejectBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{RejectBulkResourceRequest.projectName}} project got rejected.\n Rejecte Reason: {{RejectBulkResourceRequest.rejectReason}}\n Number of Resources : {{RejectBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{RejectBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{RejectBulkResourceRequest.estimatedBudget}}\n {{RejectBulkResourceRequest.resourceTable}}\n {{RejectBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
			
#Accept Bulk Resource Request Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectBulkResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectBulkResourceRequestNotification', 'Notification', 'RejectBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Requested Resources Reject for {{RejectBulkResourceRequest.projectName}}','N', 'SLADMIN', 'com', 'in.com');


#Reject Bulk Resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('OnHoldBulkResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{OnHoldBulkResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{OnHoldBulkResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{OnHoldBulkResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{OnHoldBulkResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceCount', '{{OnHoldBulkResourceRequest.resourceCount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{OnHoldBulkResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{OnHoldBulkResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceTable', '{{OnHoldBulkResourceRequest.resourceTable}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{OnHoldBulkResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{OnHoldBulkResourceRequest.rejectReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('OnHold Bulk Resource Request','Email', 'OnHold Bulk Resource Request', 'OnHold Bulk Resource Request', 'Requested Resources OnHold','org', 'Best Regards,\n{{OnHoldBulkResourceRequest.loginUserName}}\n{{OnHoldBulkResourceRequest.loginOrgName}}', ' Dear {{OnHoldBulkResourceRequest.approverName}},\n This is to notify you that resources requests for {{OnHoldBulkResourceRequest.projectName}} project are on-hold,due to following reason.\n Reason: {{OnHoldBulkResourceRequest.rejectReason}}\n Number of Resources : {{OnHoldBulkResourceRequest.resourceCount}}\n Total Estimated Hours : {{OnHoldBulkResourceRequest.estimatedHours}}\n Total Estimated Budget : {{OnHoldBulkResourceRequest.estimatedBudget}}\n {{OnHoldBulkResourceRequest.resourceTable}}\n {{OnHoldBulkResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
			
#Accept Bulk Resource Request Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('OnHoldBulkResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('OnHoldBulkResourceRequestNotification', 'Notification', 'OnHoldBulkResourceRequestNotification', 'Send mail to ResourceRequestNotification','org','Requested resources are on hold for {{OnHoldBulkResourceRequest.projectName}}','N', 'SLADMIN', 'com', 'in.com');



# Accepting single resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptSingleResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{AcceptSingleResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{AcceptSingleResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{AcceptSingleResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{AcceptSingleResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceName', '{{AcceptSingleResourceRequest.resourceName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{AcceptSingleResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billRate', '{{AcceptSingleResourceRequest.billRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'payRate', '{{AcceptSingleResourceRequest.payRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{AcceptSingleResourceRequest.loginLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Accept Single Resource Request','Email', 'Accept Single Resource Request', 'Accept Single Resources Request for {{AcceptSingleResourceRequest.projectName}} Project', 'Requested Resources Accepted','org', 'Best Regards,\n{{AcceptSingleResourceRequest.loginUserName}}\n{{AcceptSingleResourceRequest.loginOrgName}}', ' Dear {{AcceptSingleResourceRequest.approverName}},\n This is to notify that {{AcceptSingleResourceRequest.resourceName}} got processed for the {{AcceptSingleResourceRequest.projectName}}. Go through the brief details
\n Resource Name : {{AcceptSingleResourceRequest.resourceName}} \n Pay Rate : {{AcceptSingleResourceRequest.payRate}}\n Bill Rate : {{AcceptSingleResourceRequest.billRate}}\n Esitmated Hours : {{AcceptSingleResourceRequest.estimatedHours}}\n Esitmated Budget : {{AcceptSingleResourceRequest.estimatedHours}} \n {{AcceptSingleResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptSingleResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptSingleResourceRequestNotification', 'Notification', 'AcceptSingleResourceRequestNotification', 'Send mail to AcceptSingleResourceRequestNotification','org',' Requested Resources approved for {{AcceptSingleResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');


# Reject single resource Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectSingleResourceRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{RejectSingleResourceRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{RejectSingleResourceRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{RejectSingleResourceRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{RejectSingleResourceRequest.loginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'resourceName', '{{RejectSingleResourceRequest.resourceName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{RejectSingleResourceRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{NewResourceRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'billRate', '{{RejectSingleResourceRequest.billRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'payRate', '{{RejectSingleResourceRequest.payRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{RejectSingleResourceRequest.loginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectReason', '{{RejectSingleResourceRequest.rejectReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Reject Single Resource Request','Email', 'Reject Single Resource Request', 'Reject Single Resources Request for {{RejectSingleResourceRequest.projectName}} Project', 'Requested Resources Rejected','org', 'Best Regards,\n{{RejectSingleResourceRequest.loginUserName}}\n{{RejectSingleResourceRequest.loginOrgName}}', ' Dear {{RejectSingleResourceRequest.approverName}},\n I regret to inform you that the requested resource {{RejectSingleResourceRequest.resourceName}} for {{RejectSingleResourceRequest.projectName}} Project rejected due to following reason
\n Rejected Reason : {{RejectSingleResourceRequest.rejectReason}} \n Resource Name : {{RejectSingleResourceRequest.resourceName}} \n Pay Rate : {{RejectSingleResourceRequest.payRate}}\n Bill Rate : {{RejectSingleResourceRequest.billRate}}\n Esitmated Hours : {{RejectSingleResourceRequest.estimatedHours}}\n Esitmated Budget : {{RejectSingleResourceRequest.estimatedHours}} \n {{RejectSingleResourceRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

# Accepting single New resource Request Notification			

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectSingleResourceRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectSingleResourceRequestNotification', 'Notification', 'RejectSingleResourceRequestNotification', 'Send mail to RejectSingleResourceRequestNotification','org',' Requested Resources rejectd for {{RejectSingleResourceRequest.projectName}} ','N', 'SLADMIN', 'com', 'in.com');


