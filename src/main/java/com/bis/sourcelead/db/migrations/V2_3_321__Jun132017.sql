SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanism.approverName}},\n\nApprove the requirement {{ApprovalMechanism.requirementName}},\nThe brief requirement details are below\nRequirement Name : {{ApprovalMechanism.requirementName}}\nRequirement Location :{{ApprovalMechanism.requirementLocation}}\nRequirement Rate  : {{ApprovalMechanism.requirementRate}}\nClient :  {{ApprovalMechanism.client}} .\n\nFor clear details login into the system :\n {{ApprovalMechanism.link}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ApprovalMechanism.approverName}},\n\nApprove the requirement {{ApprovalMechanism.requirementName}},\nThe brief requirement details are below\nRequirement Name : {{ApprovalMechanism.requirementName}}\nRequirement Location :{{ApprovalMechanism.requirementLocation}}\nRequirement Rate  : {{ApprovalMechanism.requirementRate}}\nClient :  {{ApprovalMechanism.client}},{{ApprovalMechanismSequential.approverOne}} \napproved the requirement .\n\nFor clear details login into the system :\n{{ApprovalMechanism.link}}'WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best Regards,\n{{ApprovalMechanism.loginUserName}},\n{{ApprovalMechanism.role}}' WHERE `UNIQUE_SCENARIO_NAME`='Approval Mechanism Sequential' and `ENTITY_TYPE`='Email';


CALL AlterTableAddColumn ('ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE','MEDIUMBLOB NULL AFTER `FILE_NAME`');

CALL AlterTableAddColumn ('ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE_CONTENT_TYPE','VARCHAR(500) NULL  AFTER `UPLOADED_DOC_FILE`');

CALL AlterTableAddColumn ('ON_BOARDING_TEMPLATE_DOCUMENTS','UPLOADED_DOC_FILE_NAME','TEXT NULL AFTER `UPLOADED_DOC_FILE_CONTENT_TYPE`');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear	{{TaskMechanism.assignedUser}},\n\nPriority:{{TaskMechanism.priority}}\nDate:{{TaskMechanism.date}}\nTime:{{TaskMechanism.time}}\nDescription:{{TaskMechanism.description}}\n' WHERE `ID`='93';
