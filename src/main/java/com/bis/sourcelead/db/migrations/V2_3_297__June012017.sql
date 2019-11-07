#CandidateInvitationEmail
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('CandidateInvitation', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{CandidateInvitation.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'candidate', '{{CandidateInvitation.candidate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{CandidateInvitation.loginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'registerURL', '{{CandidateInvitation.registerURL}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('CandidateInvitation', 'Email', 'CandidateInvitation', 'Send invitation mail to candidate', '{{CandidateInvitation.loginUser}} Invited to sourcelead as Professional', 'sourcelead', 'Best Regards,\n{{CandidateInvitation.loginUser}}\n{{CandidateInvitation.loginOrg}}','Dear {{CandidateInvitation.candidate}},\nOur Organization {{CandidateInvitation.loginOrg}} is using SourceLead Talent Acquisition Platform. We would like to invite you to \nSourceLead is a Software as a Service talent acquisition platform that helps you to find jobs in a large scale.\nTo register into the system, click on the register button. \n{{CandidateInvitation.registerURL}} ', 'N', 'SLADMIN', 'com','in.com');
