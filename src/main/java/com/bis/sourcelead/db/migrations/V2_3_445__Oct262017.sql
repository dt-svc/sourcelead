
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName','{{InviteUser.orgName}}');

#User Terminating Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserTerminatedEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{UserTerminatedEmail.applicantName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'admin', '{{UserTerminatedEmail.admin}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{UserTerminatedEmail.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'date', '{{UserTerminatedEmail.date}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RejectReason', '{{UserTerminatedEmail.RejectReason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginURL', '{{UserTerminatedEmail.loginURL}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserTerminatedEmail', 'Email', 'UserTerminatedEmail','This mails should forward to the user about his termination', '{{UserTerminatedEmail.applicantName}} relieved you from {{UserTerminatedEmail.companyName}}',
 
'sourcelead','Best Regards,\n{{UserTerminatedEmail.admin}}','Dear {{UserTerminatedEmail.applicantName}},\n\nThis email is being sent to you because relieved you from all duties from {{UserTerminatedEmail.companyName}} \neffective from {{UserTerminatedEmail.date}} due to {{UserTerminatedEmail.RejectReason}}.\n\nPlease Complete the Exit Interview in order to complete offboarding process.
                                      \nHow ever you can still use SourceLead to find your next Job, refer your friends and \nearn money using our referrral program please update your profile with your personal \nemail ID.Over 10000 remote users get many benifits from sourcelead by referring \nfinding jobs domestiv and international for more information please click here. \n\n{{UserTerminatedEmail.loginURL}}', 'N', 'SLADMIN', 'com','in.com');


#UserTerminatedNotificationEmail
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserTerminatedNotificationEmail', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('UserTerminatedNotificationEmail', 'Notification', 'UserTerminatedNotificationEmail', 'This mails should forward to the user about his termination','org','Notify the Manager that user completed the exit interview ','N', 'SLADMIN', 'com', 'in.com');


#user invitation 

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`,`DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName','{{InviteUser.orgName}}');