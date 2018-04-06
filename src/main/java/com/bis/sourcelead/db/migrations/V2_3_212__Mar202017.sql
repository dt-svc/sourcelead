set SQL_SAFE_UPDATES=0;

#Project edit EMAIL to managers
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('EditProjectMailtoManager', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{EditProjectMailtoManager.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectStartdate', '{{EditProjectMailtoManager.projectStartdate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectEnddate', '{{EditProjectMailtoManager.projectEnddate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{EditProjectMailtoManager.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{EditProjectMailtoManager.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{EditProjectMailtoManager.loginOrgName}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('Editprojecttomanager', 'Email', 'Editprojecttomanager', 'Project edit mail to manager', '{{EditProjectMailtoManager.projectName}} Project Details Updated', 'sourcelead', 'Best Regards,\n {{EditProjectMailtoManager.loginUser}},\n{{EditProjectMailtoManager.loginOrgName}}', 'Dear {{EditProjectMailtoManager.managerName}},\n{{EditProjectMailtoManager.projectName}} Project end date have been updated \n Project Start Date :{{EditProjectMailtoManager.projectStartdate}} \n Project End Date : {{EditProjectMailtoManager.projectEnddate}}', 'N', 'SLADMIN', 'com', 'in.com');



#ReleaseMechanism_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('EditProjectMailtoManager', 'Notification');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Editprojecttomanager', 'Notification', 'Editprojecttomanager', 'Project edit mail to manager','org','{{EditProjectMailtoManager.projectName}} Project Details Updated','N', 'SLADMIN', 'com', 'in.com');

											
											
									
											
											
#Project edit Email to team members
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('EditProjectMailtoTeamMember', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{EditProjectMailtoTeamMember.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectStartdate', '{{EditProjectMailtoTeamMember.projectStartdate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectEnddate', '{{EditProjectMailtoTeamMember.projectEnddate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeName', '{{EditProjectMailtoTeamMember.employeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUser', '{{EditProjectMailtoTeamMember.loginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrgName', '{{EditProjectMailtoTeamMember.loginOrgName}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
										('Editprojecttoteammember', 'Email', 'Editprojecttoteammember', 'Project edit mail to manager', '{{EditProjectMailtoTeamMember.projectName}} Project Details Updated', 'sourcelead', 'Best Regards \n{{EditProjectMailtoTeamMember.loginUser}},\n{{EditProjectMailtoTeamMember.loginOrgName}}', 'Dear {{EditProjectMailtoTeamMember.employeeName}},\n{{EditProjectMailtoTeamMember.projectName}} Project end date have been updated \n Project Start Date :{{EditProjectMailtoTeamMember.projectStartdate}} \n Project End Date : {{EditProjectMailtoTeamMember.projectEnddate}}', 'N', 'SLADMIN', 'com', 'in.com');



#ReleaseMechanism_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('EditProjectMailtoTeamMember', 'Notification');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
											('Editprojecttoteammember', 'Notification', 'Editprojecttoteammember', 'Project edit mail to manager','org','{{EditProjectMailtoTeamMember.projectName}} Project Details Updated','N', 'SLADMIN', 'com', 'in.com');
										
set SQL_SAFE_UPDATES=1;