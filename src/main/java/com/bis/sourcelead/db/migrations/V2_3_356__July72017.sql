#REQUESTED_DOCUMENTS_SUBMITED_TO_USER_mail 
#email_scenario
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email'),
	'candidateName','{{UploadedRequestForDocumentsToCandidates.candidateName}}');
	
	
	INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email'),
	'userName','{{UploadedRequestForDocumentsToCandidates.userName}}');
	
	
#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Email', 'UploadedRequestForDocumentsToCandidates', 'Send mail to user for uploaded documents', 'Requested Documents are submitted','sourcelead','Best Regards,\n{{UploadedRequestForDocumentsToCandidates.candidateName}}','Dear {{UploadedRequestForDocumentsToCandidates.userName}},\n\nRequested Documents are submitted.Let me know if any documents required for further process.', 'N', 'SLADMIN', 'sourceLead.com','in.sourceLead.com');



#REQUESTED_DOCUMENTS_SUBMITED_TO_USER_Notification
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Notification');



#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Notification', 'UploadedRequestForDocumentsToCandidates', 'Send mail to user for uploaded documents','org','Requested Documents are submitted','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
