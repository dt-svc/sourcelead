#Job approval[parallel]_Email
SET SQL_SAFE_UPDATES = 0;			

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanism.approverName}},\n\n{{ApprovalMechanism.loginUserName}} has requested you to approve the following job\nPlease login to manage approval request.\nJob Name:{{ApprovalMechanism.requirementName}}\nJob Location:{{ApprovalMechanism.requirementLocation}}\nJob Rate:{{ApprovalMechanism.requirementRate}}\nClient:{{ApprovalMechanism.client}}\n\n{{ApprovalMechanism.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Approval request for {{ApprovalMechanism.requirementName}} from {{ApprovalMechanism.jobInitiatorName}} '  WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism' and `ENTITY_TYPE`='Email';

#Job approved_Email

UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{ApprovedRequirement.jobInitiatorName}},\n\n{{ApprovedRequirement.approverName}} Approved the Job {{ApprovedRequirement.requirementId-Name}}.' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';



UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{ApprovedRequirement.requirementId-Name}} has been approved '  WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';

#Job Rejected_Email
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
	VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'RejectedMailAboutTheRequirement' and `ENTITY_TYPE` = 'Email'),
	'link','{{RejectedMailAboutTheRequirement.link}}');


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='{{RejectedMailAboutTheRequirement.approverName}} rejected the Job', `BODY`=' Dear {{RejectedMailAboutTheRequirement.jobInitiatorName}},\n\n{{RejectedMailAboutTheRequirement.approverName}} rejected the Job {{RejectedMailAboutTheRequirement.requirementId-Name}}.\n<b>Reason:</b>\n{{RejectedMailAboutTheRequirement.reason}}\nPlease login to check more details\n\n{{RejectedMailAboutTheRequirement.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Rejected Mail About The Requirement' and `ENTITY_TYPE`='Email';


#Job Release[vendors]
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New Job Alert from {{ReleaseMechanism.FirstName}}',`SIGNATURE`='Best Regards \n{{ReleaseMechanism.LoginUser}},\n{{ReleaseMechanism.LoginOrgName}}',`BODY`=' Dear {{ReleaseMechanism.contactname}},\n\nNew job released by {{ReleaseMechanism.FirstName}}.\n\nThe brief job details are below\n\nJob Title :{{ReleaseMechanism.RequirementTitle}}\n\nJob Location :{{ReleaseMechanism.RequirementLocation}}\n\nJob Rate :{{ReleaseMechanism.RequirementRate}}\n\nExperience :{{ReleaseMechanism.Experience}}\n\nFor more details login into the system Login. \n{{ReleaseMechanism.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Release Mechanism' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES = 1;	


