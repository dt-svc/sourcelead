#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('passwordResetMailToUser', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'userName', '{{passwordResetMailToUser.userName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('password reset mail to user','Email', 'passwordResetMailToUser', 'password reset mail to login User', 'Your SourceLead Password has been Reset','org', 'Best Regards,\nSourceLead Team.', 'Dear {{passwordResetMailToUser.userName}},\nThe Password of your Sourcelead  has been reset successfully.\nYou can now log in to your Sourcelead application with your new password.\nIf you do not request for new password please contact info@com.\n','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('passwordResetMailToUser', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES('password reset mail to user', 'Notification', 'passwordResetMailToUser', 'password reset mail to login User', 'Your SourceLead Password has been Reset', 'sourcelead', '', 'Your SourceLead Password has been Reset','N','SLADMIN', 'com', 'in.com');



