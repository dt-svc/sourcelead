SET SQL_SAFE_UPDATES=0;
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='View & Edit Offboard User';
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Modify My Timesheets';
DELETE FROM `ACTION_NAMES` WHERE `METHOD_NAME`='newTimesheetApproval';
DELETE FROM `ACTION_NAMES` WHERE `METHOD_NAME`='getUserMailsFromELK';
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Crowd Sourcing Profile';
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Crowd Sourcing Jobs';
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Fill My Timesheets';
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Export My Timesheets '; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Mailbox'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Hotlist Campaign'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Role Enrollment'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Org Enrollment'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='View & Edit Org Details'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='View Member Timesheet'; 
DELETE FROM `ACTION_NAMES` WHERE `ACTION_NAME`='Fill Member Timesheet'; 

UPDATE `ACTION_NAMES` SET `METHOD_NAME` = 'saveExpenseApprovalStatusByExpenseId' where `ACTION_NAME` = 'Approve/Reject Employee Expenses';

INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('558', 'Email To Users', 'sendEmailToUsers');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('559', 'User Signature Save', 'saveUserSignature');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('560', 'Mail as Favorite', 'saveMailAsFavourite');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('561', 'Mail To Trash', 'sendMailsToTrash');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('562', 'View Email', 'getEmailDetails');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('563', 'Mail Attachment Download', 'downloadMailAttachment');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('564', 'Signatures', 'getSignatures');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('565', 'Delete Read Mail', 'deleteReadMail');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('566', 'View Mail Box Details', 'getUserMailBoxDetailsByUUID');
INSERT INTO `ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('567', 'Add New Org', 'createOrg');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectEndDate', '{{MailToTeamMembers.projectEndDate}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{MailToTeamMembers.EmployeeFirstName}},
\n{{MailToTeamMembers.LoginUserFirstName}} assigned you as a team member for the Project\n\n The brief details of the project are following\n Project name : {{MailToTeamMembers.ProjectName}} \n Start Date :{{MailToTeamMembers.userStartDate}}\n End Date :{{MailToTeamMembers.projectEndDate}}\n Project manager :{{MailToTeamMembers.ManagerFirstName}}' WHERE `UNIQUE_SCENARIO_NAME`='Mail To Team Members' and `ENTITY_TYPE`='Email';
SET SQL_SAFE_UPDATES=1;