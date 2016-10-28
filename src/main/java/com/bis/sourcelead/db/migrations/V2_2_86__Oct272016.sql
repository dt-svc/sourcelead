
#RequirementPosting_email

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
'link', '{{RequirementPosting.link}}');

UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='You are Assigned as a manager for new Requirement of {{RequirementPosting.RequirementTitle}}', `SIGNATURE`='Best Regards \n {{RequirementPosting.LoginUser}},\n {{RequirementPosting.Role}}', `BODY`='Dear {{RequirementPosting.Ownername}},\n\n{{RequirementPosting.JobInitiator}}  assigned you as a owner for the requirement.\n\n The brief requirement details are below \n\n Requirement Title :{{RequirementPosting.RequirementTitle}} \n\n Requirement Location :{{RequirementPosting.RequirementLocation}} \n\n Requirement Rate :{{RequirementPosting.RequirementRate}} \n\n Experience :{{RequirementPosting.Experience}} \n\n For more details login into the system  Login {{RequirementPosting.link}}' WHERE `ID`='42';




#releaseMechanism_email
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ReleaseMechanism' and `ENTITY_TYPE` = 'Email'),
'link', '{{ReleaseMechanism.link}}');

UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='New requirement released by {{ReleaseMechanism.FirstName}}', `SIGNATURE`='Best Regards \n {{ReleaseMechanism.LoginUser}},\n{{ReleaseMechanism.LoginOrgName}}', `BODY`='Dear {{ReleaseMechanism.contactname}},\n\n  New requirement released by {{ReleaseMechanism.FirstName}}.\n\n The brief requirement details are below \n\n Requirement Title :{{ReleaseMechanism.RequirementTitle}} \n\n Requirement Location :{{ReleaseMechanism.RequirementLocation}} \n\n Requirement Rate :{{ReleaseMechanism.RequirementRate}} \n\n Experience :{{ReleaseMechanism.Experience}} \n\n For more details login into the system Login. \n{{ReleaseMechanism.link}}' WHERE `ID`='40';



#assign to team members
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
'link', '{{AssignToTeamMembers.link}}');


UPDATE `sourcelead`.`email_scenario` SET `DESCRIPTION`='Used to Assign TeamMembers for requirement', `SUBJECT`='{{AssignToTeamMembers.RequirementTitle}} new requirement assigned to you', `SIGNATURE`='Best Regards \n {{AssignToTeamMembers.LoginUser}},\n{{AssignToTeamMembers.Role}}', `BODY`='Dear {{AssignToTeamMembers.Ownername}},\n\n new requirement assigned to you. The brief requirement details are below  \n\n Requirement Title :{{AssignToTeamMembers.RequirementTitle}} \n\n Requirement Location :{{AssignToTeamMembers.RequirementLocation}} \n\n Requirement Rate :{{AssignToTeamMembers.RequirementRate}} \n\n Experience :{{AssignToTeamMembers.Experience}} \n\n For more details login into the system Login \n {{AssignToTeamMembers.link}}' WHERE `ID`='44';


#ApprovedRequirement_Email

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'approverName','{{ApprovedRequirement.approverName}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'loginUserName','{{ApprovedRequirement.loginUserName}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'role','{{ApprovedRequirement.role}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'jobInitiatorName','{{ApprovedRequirement.jobInitiatorName}}');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'requirementName','{{ApprovedRequirement.requirementName}}');
        
#Email
UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='{{ApprovedRequirement.approverName}} approved the requirement', `SIGNATURE`='Best Regards \n{{ApprovedRequirement.loginUserName}},\n{{ApprovedRequirement.role}}', `BODY`='Dear {{ApprovedRequirement.jobInitiatorName}},\n\n {{ApprovedRequirement.approverName}}, \n approved the requirement {{ApprovedRequirement.requirementId-Name}}' WHERE `ID`='50';

#Notification
UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='Approved the requirement {{ApprovedRequirement.requirementName}}.', `BODY`='Approved the requirement {{ApprovedRequirement.requirementName}}' WHERE `ID`='51';


#approvalMechanism

 UPDATE `sourcelead`.`class_field` SET `FIELD_NAME`='role' WHERE `ID`='183';
 UPDATE `sourcelead`.`class_field` SET `FIELD_NAME`='client' WHERE `ID`='182';
 
#ApprovalMechanismSequential
 
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'jobInitiatorName', '{{ApprovalMechanismSequential.jobInitiatorName}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'approverName', '{{ApprovalMechanismSequential.approverName}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'requirementName', '{{ApprovalMechanismSequential.requirementName}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'requirementLocation', '{{ApprovalMechanismSequential.requirementLocation}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'requirementRate', '{{ApprovalMechanismSequential.requirementRate}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'loginUserName', '{{ApprovalMechanismSequential.loginUserName}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'link', '{{ApprovalMechanismSequential.link}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'client', '{{ApprovalMechanismSequential.client}}');
 INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovalMechanismSequential' and `ENTITY_TYPE` = 'Email'), 'role', '{{ApprovalMechanismSequential.role}}');

 #approvalMechanismSequential_email
UPDATE `sourcelead`.`email_scenario` SET `SIGNATURE`='Best Regards,\n {{ApprovalMechanismSequential.loginUserName}},\n {{ApprovalMechanismSequential.role}}' WHERE `ID`='48';
UPDATE `sourcelead`.`email_scenario` SET `BODY`='Dear {{ApprovalMechanismSequential.approverName}},\n \n  Approve the requirement {{ApprovalMechanismSequential.requirementName}}, \n \n  The brief requirement details are below \n \n  Requirement Name : {{ApprovalMechanismSequential.requirementName}} \n \n  Requirement Location :{{ApprovalMechanismSequential.requirementLocation}}\n \n  Requirement Rate  : {{ApprovalMechanismSequential.requirementRate}}\n \n  Client :  {{ApprovalMechanismSequential.client}},{{ApprovalMechanismSequential.approverOne}} approved the requirement .\n \n   For clear details login into the system : \n  {{ApprovalMechanismSequential.link}}\n ' WHERE `ID`='48';
UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='Approval request from the {{ApprovalMechanismSequential.jobInitiatorName}}' WHERE `ID`='48';


#approvalMechanismSequential Notification
UPDATE `sourcelead`.`email_scenario` SET `SUBJECT`='Approve the requirement {ApprovalMechanismSequential.requirementName}.', `BODY`='Approve the requirement {{ApprovalMechanismSequential.requirementName}}' WHERE `ID`='49';



#rejected_mail
#CLASS_FIELD`


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'approverName', '{{RejectedMailAboutTheRequirement.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'loginUserName', '{{RejectedMailAboutTheRequirement.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'role', '{{RejectedMailAboutTheRequirement.role}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'requirementId-Name', '{{RejectedMailAboutTheRequirement.requirementId-Name}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'jobInitiatorName', '{{RejectedMailAboutTheRequirement.jobInitiatorName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'requirementName', '{{RejectedMailAboutTheRequirement.requirementName}}');

#EMAIL_SCENARIO

UPDATE `sourcelead`.`email_scenario` SET `DESCRIPTION`='rejection of Email', `SUBJECT`='rejected the requirement{{RejectedMailAboutTheRequirement.approverName}}', `SIGNATURE`='Best Regards,\n{{RejectedMailAboutTheRequirement.loginUserName}},\n{{RejectedMailAboutTheRequirement.role}}', `BODY`='Dear {{RejectedMailAboutTheRequirement.jobInitiatorName}},\n\n{{RejectedMailAboutTheRequirement.approverName}} rejected the requirement {{RejectedMailAboutTheRequirement.requirementId-Name}}.\n\n The reason of rejecting requirement is following :\n\n{{RejectedMailAboutTheRequirement.reason}}' WHERE `ID`='52';

#NOTIFICATION

UPDATE `sourcelead`.`email_scenario` SET `DESCRIPTION`='rejection of Email', `SUBJECT`='Rejected the requirement {{RejectedMailAboutTheRequirement.requirementName}}', `SIGNATURE`='', `BODY`='Rejected the requirement {{RejectedMailAboutTheRequirement.requirementName}}' WHERE `ID`='53'; 