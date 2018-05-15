
#Ajith

#UserRolechange_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserRoleChanged', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'FirstName', '{{UserRoleChanged.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'CurrentRole', '{{UserRoleChanged.CurrentRole}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ExistingRole', '{{UserRoleChanged.ExistingRole}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Loginlink', '{{UserRoleChanged.Loginlink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{UserRoleChanged.LoginUser}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`)  
										VALUES ('UserRoleChanged', 'Email', 'UserRoleChanged', 'User role changed mail', 'Role changed to {{UserRoleChanged.CurrentRole}}', 'sourcelead', 'Best Regards \n {{UserRoleChanged.LoginUser}}', 'Dear {{UserRoleChanged.FirstName}},\n Your Role has been updated from {{UserRoleChanged.ExistingRole}} to {{UserRoleChanged.CurrentRole}}.\n for  brief details login into system \n {{UserRoleChanged.Loginlink}}', 'N', 'SLADMIN', 'com', 'in.com');



#UserRolechange_Email_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UserRoleChanged', 'Notification');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`)  
										VALUES ('UserRoleChanged', 'Notification', 'UserRoleChanged', 'User role changed notification','org','Role changed to {{UserRoleChanged.CurrentRole}}','N', 'SLADMIN', 'com', 'in.com');

										
										
set SQL_SAFE_UPDATES=0;								
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{UserRoleChanged.FirstName}},\n Your Role has been updated from {{UserRoleChanged.ExistingRole}} to {{UserRoleChanged.CurrentRole}}.\n for  brief details login into system \n {{UserRoleChanged.Loginlink}}' WHERE `UNIQUE_SCENARIO_NAME`='UserRoleChanged' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;			
			
								

