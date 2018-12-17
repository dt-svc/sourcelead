#GuestApplicant Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('Guest Applied To Requirement', 'Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'guestUserName', '{{GuestAppliedRequirement.guestUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employerName', '{{GuestAppliedRequirement.employerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementTitle', '{{GuestAppliedRequirement.requirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'experience', '{{GuestAppliedRequirement.experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'location', '{{GuestAppliedRequirement.location}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'guestUserEmail', '{{GuestAppliedRequirement.guestUserEmail}}');




INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Guest Applied To Requirement','Email', 'Guest Applied To Requirement', 'Confirmation - Requirement Application ', 'Alert !! - This is to confirm that we received your application for the {{GuestAppliedRequirement.requirementTitle}} ','org', 'Best Regards,\n', 'Dear {{GuestAppliedRequirement.guestUserName}}\n\nThis is to confirm that we have received your application for the below mentioned Job.\nJob ID and Title : {{GuestAppliedRequirement.requirementTitle}}\nEmployer Name : {{GuestAppliedRequirement.employerName}}\nExperience : {{GuestAppliedRequirement.experience}}\nLocation : {{GuestAppliedRequirement.location}}\n\nIf your profile is suitable for the above position we will contact you.' , 'N', 'SLADMIN', 'sourcelead', 'sourceleadIN');