INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('InterviewerInvitationRedifined','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerFirstName', '{{InterviewerInvitationRedifined.InterviewerFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewerLastName', '{{InterviewerInvitationRedifined.InterviewerLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{InterviewerInvitationRedifined.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{InterviewerInvitationRedifined.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginLink', '{{InterviewerInvitationRedifined.LoginLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'interviewTableDetails', '{{InterviewerInvitationRedifined.interviewTableDetails}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('Interviewer InvitationRedifined','Email', 'Interviewer InvitationRedifined', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitationRedifined.RequirementTitle}} requirement.','org', 'Best Regards,\n{{InterviewerInvitationRedifined.LoginUser}}.', 'Dear {{InterviewerInvitation.InterviewerFirstName}} {{InterviewerInvitation.InterviewerLastName}},\n\n We invite you to kindly consent to be a Interviewer  for the Position {{InterviewerInvitationRedifined.RequirementTitle}} ,Following are the details for interview.\n {{InterviewerInvitationRedifined.interviewTableDetails}} \n\n For clear details login into the system.\n{{InterviewerInvitationRedifined.LoginLink}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InterviewerInvitation', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('Interviewer InvitationRedifined', 'Notification', 'Interviewer InvitationRedifined', 'Used for Inviting Interviewer to Interview', 'Interview scheduled for the {{InterviewerInvitationRedifined.RequirementTitle}} requirement', 'sourcelead', '', 'Interview scheduled for the {{InterviewerInvitationRedifined.RequirementTitle}} requirement','N','SLADMIN', 'com', 'in.com');



SET SQL_SAFE_UPDATES = 0;

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{RequirementPosting.Ownername}},\n\n{{RequirementPosting.JobInitiator}} assigned you as a manager for the requirement.\n\n The brief requirement details are below \n\n Requirement Title :{{RequirementPosting.RequirementTitle}} \n\n Requirement Location :{{RequirementPosting.RequirementLocation}} \n\n Requirement Rate :{{RequirementPosting.RequirementRate}} \n\n Experience :{{RequirementPosting.Experience}} \n\n For more details login into the system  Login {{RequirementPosting.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Requirement Posting' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='rejected the requirement {{RejectedMailAboutTheRequirement.approverName}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{AssignToTeamMembers.RequirementTitle}} requirement assigned to you' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='New requirement assigned to you.' WHERE `ID`='45';

#inviteuser Email
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{InviteUser.FirstName}} {{InviteUser.LastName}},\n You have been invited to SourceLead as a {{InviteUser.Role}}.\nYou can simply login into SourceLead by clicking below link with following credentials\n\n Username : {{InviteUser.UserEmailId}} \n Password : {{InviteUser.Password}}\n\n {{InviteUser.LoginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='Invite User' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;
