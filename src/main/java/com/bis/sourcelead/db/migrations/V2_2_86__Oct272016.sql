SET SQL_SAFE_UPDATES = 0;
#RequirementPosting_email

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'RequirementPosting' and `ENTITY_TYPE` = 'Email'),
'link', '{{RequirementPosting.link}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='You are Assigned as a manager for new Requirement of {{RequirementPosting.RequirementTitle}}', `SIGNATURE`='Best Regards \n {{RequirementPosting.LoginUser}},\n {{RequirementPosting.Role}}', `BODY`='Dear {{RequirementPosting.Ownername}},\n\n{{RequirementPosting.JobInitiator}}  assigned you as a owner for the requirement.\n\n The brief requirement details are below \n\n Requirement Title :{{RequirementPosting.RequirementTitle}} \n\n Requirement Location :{{RequirementPosting.RequirementLocation}} \n\n Requirement Rate :{{RequirementPosting.RequirementRate}} \n\n Experience :{{RequirementPosting.Experience}} \n\n For more details login into the system  Login {{RequirementPosting.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Requirement Posting' and `ENTITY_TYPE`='Email';




#releaseMechanism_email
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'ReleaseMechanism' and `ENTITY_TYPE` = 'Email'),
'link', '{{ReleaseMechanism.link}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='New requirement released by {{ReleaseMechanism.FirstName}}', `SIGNATURE`='Best Regards \n {{ReleaseMechanism.LoginUser}},\n{{ReleaseMechanism.LoginOrgName}}', `BODY`='Dear {{ReleaseMechanism.contactname}},\n\n  New requirement released by {{ReleaseMechanism.FirstName}}.\n\n The brief requirement details are below \n\n Requirement Title :{{ReleaseMechanism.RequirementTitle}} \n\n Requirement Location :{{ReleaseMechanism.RequirementLocation}} \n\n Requirement Rate :{{ReleaseMechanism.RequirementRate}} \n\n Experience :{{ReleaseMechanism.Experience}} \n\n For more details login into the system Login. \n{{ReleaseMechanism.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Release Mechanism' and `ENTITY_TYPE`='Email';



#assign to team members
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME` = 'AssignToTeamMembers' and `ENTITY_TYPE` = 'Email'),
'link', '{{AssignToTeamMembers.link}}');


UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `DESCRIPTION`='Used to Assign TeamMembers for requirement', `SUBJECT`='{{AssignToTeamMembers.RequirementTitle}} new requirement assigned to you', `SIGNATURE`='Best Regards \n {{AssignToTeamMembers.LoginUser}},\n{{AssignToTeamMembers.Role}}', `BODY`='Dear {{AssignToTeamMembers.Ownername}},\n\n new requirement assigned to you. The brief requirement details are below  \n\n Requirement Title :{{AssignToTeamMembers.RequirementTitle}} \n\n Requirement Location :{{AssignToTeamMembers.RequirementLocation}} \n\n Requirement Rate :{{AssignToTeamMembers.RequirementRate}} \n\n Experience :{{AssignToTeamMembers.Experience}} \n\n For more details login into the system Login \n {{AssignToTeamMembers.link}}' WHERE `UNIQUE_SCENARIO_NAME`='AssignToTeamMembers' and `ENTITY_TYPE`='Email';


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
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='{{ApprovedRequirement.approverName}} approved the requirement', `SIGNATURE`='Best Regards \n{{ApprovedRequirement.loginUserName}},\n{{ApprovedRequirement.role}}', `BODY`='Dear {{ApprovedRequirement.jobInitiatorName}},\n\n {{ApprovedRequirement.approverName}}, \n approved the requirement {{ApprovedRequirement.requirementId-Name}}' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';

#Notification
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='Approved the requirement {{ApprovedRequirement.requirementName}}.', `BODY`='Approved the requirement {{ApprovedRequirement.requirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Notification';


#approvalMechanism

 UPDATE `sourcelead`.`CLASS_FIELD` SET `FIELD_NAME`='role' WHERE `DELIM_STR`= '{{ApprovalMechanism.role}}' and id = (select  id  from `entity_type` where  `ENTITY_NAME`='ApprovalMechanism' and `ENTITY_TYPE`='Email');
 UPDATE `sourcelead`.`CLASS_FIELD` SET `FIELD_NAME`='client' WHERE `DELIM_STR`= '{{ApprovalMechanism.client}}' and id = (select  id  from `entity_type` where  `ENTITY_NAME`='ApprovalMechanism' and `ENTITY_TYPE`='Email');
 
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
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n {{ApprovalMechanismSequential.loginUserName}},\n {{ApprovalMechanismSequential.role}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanismSequential.approverName}},\n \n  Approve the requirement {{ApprovalMechanismSequential.requirementName}}, \n \n  The brief requirement details are below \n \n  Requirement Name : {{ApprovalMechanismSequential.requirementName}} \n \n  Requirement Location :{{ApprovalMechanismSequential.requirementLocation}}\n \n  Requirement Rate  : {{ApprovalMechanismSequential.requirementRate}}\n \n  Client :  {{ApprovalMechanismSequential.client}},{{ApprovalMechanismSequential.approverOne}} approved the requirement .\n \n   For clear details login into the system : \n  {{ApprovalMechanismSequential.link}}\n ' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='Approval request from the {{ApprovalMechanismSequential.jobInitiatorName}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';


#approvalMechanismSequential Notification
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `SUBJECT`='Approve the requirement {ApprovalMechanismSequential.requirementName}.', `BODY`='Approve the requirement {{ApprovalMechanismSequential.requirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Notification';



#rejected_mail
#CLASS_FIELD`


INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'approverName', '{{RejectedMailAboutTheRequirement.approverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'loginUserName', '{{RejectedMailAboutTheRequirement.loginUserName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'role', '{{RejectedMailAboutTheRequirement.role}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'requirementId-Name', '{{RejectedMailAboutTheRequirement.requirementId-Name}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'jobInitiatorName', '{{RejectedMailAboutTheRequirement.jobInitiatorName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT ID FROM `sourcelead`.`ENTITY_TYPE` where `ENTITY_NAME`='RejectedMailAboutTheRequirement' and `ENTITY_TYPE`='Email'), 'requirementName', '{{RejectedMailAboutTheRequirement.requirementName}}');

#EMAIL_SCENARIO

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `DESCRIPTION`='rejection of Email', `SUBJECT`='rejected the requirement{{RejectedMailAboutTheRequirement.approverName}}', `SIGNATURE`='Best Regards,\n{{RejectedMailAboutTheRequirement.loginUserName}},\n{{RejectedMailAboutTheRequirement.role}}', `BODY`='Dear {{RejectedMailAboutTheRequirement.jobInitiatorName}},\n\n{{RejectedMailAboutTheRequirement.approverName}} rejected the requirement {{RejectedMailAboutTheRequirement.requirementId-Name}}.\n\n The reason of rejecting requirement is following :\n\n{{RejectedMailAboutTheRequirement.reason}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Email';

#NOTIFICATION

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `DESCRIPTION`='rejection of Email', `SUBJECT`='Rejected the requirement {{RejectedMailAboutTheRequirement.requirementName}}', `SIGNATURE`='', `BODY`='Rejected the requirement {{RejectedMailAboutTheRequirement.requirementName}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;