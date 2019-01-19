
#shivayogi

#ReleaseMechanism_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReleaseMechanism', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientname', '{{ReleaseMechanism.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'contactname', '{{ReleaseMechanism.contactname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{ReleaseMechanism.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{ReleaseMechanism.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementRate', '{{ReleaseMechanism.RequirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Experience', '{{ReleaseMechanism.Experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{ReleaseMechanism.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrgName', '{{ReleaseMechanism.LoginOrgName}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Release Mechanism', 'Email', 'Release Mechanism', 'Used for Release requirement', 'New requirement released by {{ReleaseMechanism.FirstName}}', 'sourcelead', 'Best Regards {{ReleaseMechanism.LoginUser}},{{ReleaseMechanism.LoginOrgName}}', 'Dear{{ReleaseMechanism.contactname}},\n New requirement released by {{ReleaseMechanism.FirstName}}. The brief requirement details are below Requirement Title :{{ReleaseMechanism.RequirementTitle}} \n Requirement Location :{{ReleaseMechanism.RequirementLocation}} \n Requirement Rate :{{ReleaseMechanism.RequirementRate}} \n Experience :{{ReleaseMechanism.Experience}} \n\n For more details login into the system Login', 'N', 'SLADMIN', 'com', 'in.com');



#ReleaseMechanism_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReleaseMechanism', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientname', '{{ReleaseMechanism.clientname}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Release Mechanism', 'Notification', 'Release Mechanism', 'Used to notify Release requirement','org','New requirement released by {{ReleaseMechanism.clientname}}','N', 'SLADMIN', 'com', 'in.com');






#RequirementPosting_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RequirementPosting', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'OwnerName', '{{RequirementPosting.OwnerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'JobInitiator', '{{RequirementPosting.JobInitiator}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{RequirementPosting.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{RequirementPosting.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementRate', '{{RequirementPosting.RequirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Experience', '{{RequirementPosting.Experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{RequirementPosting.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Role', '{{RequirementPosting.Role}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Requirement Posting', 'Email', 'Requirement Posting', 'Used for requirement posting', 'You are Assigned as a manager for new Requirement of {{RequirementPosting.RequirementTitle}}', 'sourcelead', 'Best Regards {{RequirementPosting.LoginUser}},{{RequirementPosting.Role}}', 'Dear{{RequirementPosting.Ownername}},\n{{RequirementPosting.JobInitiator}} assigned you as a owner for the requirement.The brief requirement details are below \n Requirement Title :{{RequirementPosting.RequirementTitle}} \n Requirement Location :{{RequirementPosting.RequirementLocation}} \n Requirement Rate :{{RequirementPosting.RequirementRate}} \n Experience :{{RequirementPosting.Experience}} \n\n For more details login into the system Login', 'N', 'SLADMIN', 'com', 'in.com');



#RequirementPosting_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RequirementPosting', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{RequirementPosting.RequirementTitle}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Requirement Posting', 'Notification', 'Requirement Posting', 'Used to notify Release Posting','org','You are Assigned as a manager for new Requirement of {{RequirementPosting.RequirementTitle}}','N', 'SLADMIN', 'com', 'in.com');








#AssignToTeamMembers_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AssignToTeamMembers', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'OwnerName', '{{AssignToTeamMembers.OwnerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementTitle', '{{AssignToTeamMembers.RequirementTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementLocation', '{{AssignToTeamMembers.RequirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'RequirementRate', '{{AssignToTeamMembers.RequirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Experience', '{{AssignToTeamMembers.Experience}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{AssignToTeamMembers.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Role', '{{AssignToTeamMembers.Role}}');
 
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AssignToTeamMembers', 'Email', 'AssignToTeamMembers', 'Used to Assign TeamMembers for requirement', '{{AssignToTeamMembers.RequirementTitle}} new requirement assigned to you', 'sourcelead', 'Best Regards {{AssignToTeamMembers.LoginUser}},{{AssignToTeamMembers.Role}}', 'Dear{{AssignToTeamMembers.Ownername}},\n new requirement assigned to you. The brief requirement details are below  \n Requirement Title :{{AssignToTeamMembers.RequirementTitle}} \n Requirement Location :{{AssignToTeamMembers.RequirementLocation}} \n Requirement Rate :{{AssignToTeamMembers.RequirementRate}} \n Experience :{{AssignToTeamMembers.Experience}} \n\n For more details login into the system Login', 'N', 'SLADMIN', 'com', 'in.com');



#AssignToTeamMembers_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AssignToTeamMembers', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'AssignToTeamMembers', '{{AssignToTeamMembers.RequirementTitle}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AssignToTeamMembers', 'Notification', 'AssignToTeamMembers', 'Used to Assign TeamMembers for requirement','org','{{AssignToTeamMembers.RequirementTitle}} new requirement assigned to you.','N', 'SLADMIN', 'com', 'in.com');
















#ajith matta

#Approval Mechanism Email

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApprovalMechanism','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'jobInitiatorName', '{{ApprovalMechanism.jobInitiatorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{ApprovalMechanism.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementName', '{{ApprovalMechanism.requirementName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementLocation', '{{ApprovalMechanism.requirementLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementRate', '{{ApprovalMechanism.requirementRate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{ApprovalMechanism.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'link', '{{ApprovalMechanism.link}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'client ', '{{ApprovalMechanism.client}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'role ', '{{ApprovalMechanism.role}}');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Approval Mechanism','Email', 'Approval Mechanism', 'Used mail from Job Initiator to Approver  ', 'Approval request from the {{ApprovalMechanism.jobInitiatorName}}','org', 'Best Regards,\n{{ApprovalMechanism.loginUserName}},\n{{ApprovalMechanism.role}}', 'Dear {{ApprovalMechanism.approverName}},\n\n Approve the requirement {{ApprovalMechanism.requirementName}}, \n\n The brief requirement details are below \n\n Requirement Name : {{ApprovalMechanism.requirementName}} \n\n Requirement Location :{{ApprovalMechanism.requirementLocation}}\n\n Requirement Rate  : {{ApprovalMechanism.requirementRate}}\n\n Client :  {{ApprovalMechanism.client}} .\n\n  For clear details login into the system : \n {{ApprovalMechanism.link}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

#Approval Mechanism Notification

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApprovalMechanism', 'Notification');



INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('Approval Mechanism','Notification','Approval Mechanism', 'Used mail from Job Initiator to Approver', 'Approve the requirement {ApprovalMechanism.requirementName}.', 'sourcelead', '', 'Approve the requirement {{ApprovalMechanism.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');






                                          
#Approval Mechanism sequential Email                                        
                                          
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApprovalMechanismSequential','Email');


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverOne ', '{{ApprovalMechanismSequential.approverOne}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
										('Approval Mechanism Sequential','Email', 'Approval Mechanism Sequential', 'Used mail from Job Initiator to Approver  ', 'Approval request from the {{ApprovalMechanism.jobInitiatorName}}','org', 'Best Regards,\n{{ApprovalMechanism.loginUserName}},\n{{ApprovalMechanism.role}}', 'Dear {{ApprovalMechanism.approverName}},\n\n Approve the requirement {{ApprovalMechanism.requirementName}}, \n\n The brief requirement details are below \n\n Requirement Name : {{ApprovalMechanism.requirementName}} \n\n Requirement Location :{{ApprovalMechanism.requirementLocation}}\n\n Requirement Rate  : {{ApprovalMechanism.requirementRate}}\n\n Client :  {{ApprovalMechanism.client}},{{ApprovalMechanismSequential.approverOne}} approved the requirement .\n\n  For clear details login into the system : \n {{ApprovalMechanism.link}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

#Approval Mechanism sequential Notification 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApprovalMechanismSequential', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('Approval Mechanism Sequential','Notification','Approval Mechanism Sequential', 'Used mail from Job Initiator to Approver', 'Approve the requirement {ApprovalMechanism.requirementName}.', 'sourcelead', '', 'Approve the requirement {{ApprovalMechanism.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');



#Approved Requirement Email   

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApprovedRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'requirementId-Name', '{{ApprovedRequirement.requirementId-Name}}');
                                        
                                          
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
										('Approved Requirement','Email', 'Approved Requirement', 'Used mail from Job Initiator to Approver  ', ' {{ApprovalMechanism.approverName}} approved the requirement','org', 'Best Regards,\n{{ApprovalMechanism.loginUserName}},\n{{ApprovalMechanism.role}}', 'Dear {{ApprovalMechanism.jobInitiatorName}},\n {{ApprovalMechanism.approverName}}, \n\n }, approved the requirement {{ApprovedRequirement.requirementId-Name}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');
                                         
                                          
#Approved Requirement Notification 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApprovedRequirement', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
                                          ('Approved Requirement','Notification','Approved Requirement', 'Used mail from Job Initiator to Approver', 'Approved the requirement {ApprovalMechanism.requirementName}.', 'sourcelead', '', 'Approved the requirement {{ApprovalMechanism.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');
                                       

                                          
#Rakshith                                         
                                          
#RejectedMailAboutTheRequirement Email

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectedMailAboutTheRequirement','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{RejectedMailAboutTheRequirement.reason}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
							('Rejected Mail About The Requirement','Email', 'Rejected Mail About The Requirement', 'rejection of Email', 'rejected  the requirement{{ApprovalMechanism.approverName}}','org', 'Best Regards,\n{{ApprovalMechanism.loginUserName}},\n{{ApprovalMechanism.role}}', 'Dear {{ApprovalMechanism.jobInitiatorName}},{{ApprovalMechanism.approverName}}\n\n rejected the requirement {{ApprovedRequirement.requirementId-Name}}.\n\n The reason of rejecting requirement is following :{{RejectedMailAboutTheRequirement.reason}}\n','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

#RejectedMailAboutTheRequirement Notification


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectedMailAboutTheRequirement', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
				('Rejected Mail About The Requirement','Notification','Rejected Mail About The Requirement', 'rejection of Email', 'Rejected the requirement {{ApprovalMechanism.requirementName}', 'sourcelead', '', 'Rejected the requirement {{ApprovalMechanism.requirementName}}','N','SLADMIN', 'sourcelead', 'sourceleadIN');

 

