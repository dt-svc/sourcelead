
#ApprovedRequirement_Email


set SQL_SAFE_UPDATES=0;

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'requirementLocation','{{ApprovedRequirement.requirementLocation}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'requirementRate','{{ApprovedRequirement.requirementRate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'client','{{ApprovedRequirement.client}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ApprovedRequirement' and `ENTITY_TYPE` = 'Email'),
'link','{{ApprovedRequirement.link}}');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovedRequirement.jobInitiatorName}},\n\n {{ApprovedRequirement.approverName}}, \n approved the requirement {{ApprovedRequirement.requirementId-Name}} \n \n  The brief requirement details are below \n \n  Requirement Name : {{ApprovedRequirement.requirementId-Name}} \n \n  Requirement Location :{{ApprovedRequirement.requirementLocation}}\n \n  Requirement Rate  : {{ApprovedRequirement.requirementRate}}\n \n  Client :  {{ApprovedRequirement.client}}.\n \n   For clear details login into the system : \n  {{ApprovedRequirement.link}}\n ' WHERE `UNIQUE_SCENARIO_NAME`='Approved Requirement' and `ENTITY_TYPE`='Email';

#ApprovalMechanismSequential

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanismSequential.approverName}},\n \n  Approve the requirement {{ApprovalMechanismSequential.requirementName}}, \n \n  The brief requirement details are below \n \n  Requirement Name : {{ApprovalMechanismSequential.requirementName}} \n \n  Requirement Location :{{ApprovalMechanismSequential.requirementLocation}}\n \n  Requirement Rate  : {{ApprovalMechanismSequential.requirementRate}}\n \n  Client :  {{ApprovalMechanismSequential.client}},\n \n{{ApprovalMechanismSequential.jobInitiatorName}} approved the requirement .\n \n   For more details login into the system : \n  {{ApprovalMechanismSequential.link}}\n ' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';
