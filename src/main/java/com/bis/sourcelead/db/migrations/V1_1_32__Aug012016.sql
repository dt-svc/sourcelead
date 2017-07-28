
#Notification to team members who are assigned to project
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToTeamMembers', 'Notification');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Team Members', 'Notification', 'Mail To Team Members', 'Used for Notification To Team Members', 'Assigned as a team member  for {{MailToTeamMembers.ProjectName}} project', 'sourcelead', '', 'Assigned as a team member  for {{MailToTeamMembers.ProjectName}} project','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


#Notification to project manager who is assigned to project
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToProjectManager', 'Notification');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Project Manager', 'Notification', 'Mail To Project Manager', 'Used for Notification To Project Manager', 'Assigned as a Manager for {{MailToProjectManager.ProjectName}} project', 'sourcelead', '', 'Assigned as a Manager for {{MailToProjectManager.ProjectName}} project','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
