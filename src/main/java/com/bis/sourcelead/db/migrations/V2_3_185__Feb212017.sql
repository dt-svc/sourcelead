
		
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('Refer Requirment To Professional','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{ReferRequirement.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'referedProfessionalName', '{{ReferRequirement.referedProfessionalName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'professinalName', '{{ReferRequirement.professinalName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'registerLink', '{{ReferRequirement.registerLink}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Refer Requirment To Professional','Email', 'Refer Requirment To Professional', 'New requirement referral for {{ReferRequirement.requirementTitle}} ', 'New requirement referral for {{ReferRequirement.requirementTitle}}','org', 'Best Regards,\n{{ReferRequirement.referedProfessionalName}}.', 'Dear {{ReferRequirement.professinalName}}\n\n I am a user of Sourcelead which is a new recruitment platform to find requirements and I found a requirement I thought you might be interested in. Sourcelead is a referral driven platform which lets me find requirement for myself  or for my friends and connections.\n If you like the requirement, you can apply or refer it to your connections. Go through the link for viewing Requirement Details {{ReferRequirement.requirementTitle}} \n\n  Your Register Link : {{ReferRequirement.registerLink}}\n\n ','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
									
