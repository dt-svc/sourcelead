#REQUESTED_DOCUMENTS_SUBMITED_TO_USER_mail 
#email_scenario
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email'),
	'candidateName','{{UploadedRequestForDocumentsToCandidates.candidateName}}');
	
	
	INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'UploadedRequestForDocumentsToCandidates' and `ENTITY_TYPE` = 'Email'),
	'userName','{{UploadedRequestForDocumentsToCandidates.userName}}');
	
	
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Email', 'UploadedRequestForDocumentsToCandidates', 'Send mail to user for uploaded documents', 'Requested Documents are submitted','sourcelead','Best Regards,\n{{UploadedRequestForDocumentsToCandidates.candidateName}}','Dear {{UploadedRequestForDocumentsToCandidates.userName}},\n\nRequested Documents are submitted.Let me know if any documents required for further process.', 'N', 'SLADMIN', 'com','in.com');



#REQUESTED_DOCUMENTS_SUBMITED_TO_USER_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Notification');



#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UploadedRequestForDocumentsToCandidates', 'Notification', 'UploadedRequestForDocumentsToCandidates', 'Send mail to user for uploaded documents','org','Requested Documents are submitted','N', 'SLADMIN', 'com', 'in.com');



#35.Applicant Authorization [Manager acceptance]

SET SQL_SAFE_UPDATES = 0;	
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignedUser' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{AssignedUser.loginLink}}');
	
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'AssignedUser' and `ENTITY_TYPE` = 'Email'),
'loginOrg','{{AssignedUser.loginOrg}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`= ' Dear {{AssignedUser.ApplicantName}},\n\nThis is to inform you that your Application has been submitted for the following job\nJob ID/name : {{AssignedUser.RequirementName}}.\nJob Location : {{AssignedUser.RequirementLocation}}\nEmployment Type : {{AssignedUser.EmploymentType}}\n\nFor more details please login to SourceLead\n\n{{AssignedUser.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards \n{{AssignedUser.ManagerName}},\n{{AssignedUser.loginOrg}}' WHERE`UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';
 
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Application Submitted'  WHERE `UNIQUE_SCENARIO_NAME`='ApplicantWillReceiveMailAboutTheAcceptanceOfRequirementByManager' and `ENTITY_TYPE`='Email';

#37.Notify to vendor about clientshortlisted
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ShortlistMailFromClientToVendor' and `ENTITY_TYPE` = 'Email'),
'EmploymentType','{{ShortlistMailFromClientToVendor.EmploymentType}}');


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ShortlistMailFromClientToVendor' and `ENTITY_TYPE` = 'Email'),
'RequirementLocation','{{ShortlistMailFromClientToVendor.RequirementLocation}}');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ShortlistMailFromClientToVendor.vendorContactName}},\n\nThis is to inform you that your {{ShortlistMailFromClientToVendor.countOfApplicants}} application/s has been shortlisted for the following job\nJob ID/name : {{ShortlistMailFromClientToVendor.requirementTitle}}\nJob Location : {{ShortlistMailFromClientToVendor.RequirementLocation}}\nEmployment Type : {{ShortlistMailFromClientToVendor.EmploymentType}}\n\nFor more details login to SourceLead\n\n {{ShortlistMailFromClientToVendor.loginLink}} \n\n {{ShortlistMailFromClientToVendor.shortlistedTable}} ' WHERE `UNIQUE_SCENARIO_NAME`='ShortlistMailFromClient_To_Vendor' and `ENTITY_TYPE`='Email';



 
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Application/s Shortlisted'  WHERE `UNIQUE_SCENARIO_NAME`='ShortlistMailFromClient_To_Vendor' and `ENTITY_TYPE`='Email';