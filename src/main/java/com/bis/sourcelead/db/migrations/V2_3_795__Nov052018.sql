#Change task status mail To Request Assign members mail 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UpdatedTaskEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('UpdatedTaskEmail','Email', 'UpdatedTaskEmail', 'Forward the email to the project task assignee & owners about project details updated', 'Task details got updated task id - [TaskId]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Assign_Member_Name],\n [Login_User] updated the task details for the [Porject_Name] Project \n Task Id : [TaskId] \n Task Title : [Task_Title] \n\n [Activity] [Activity_Descrption]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assignMemberName', '[Assign_Member_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskId', '[TaskId]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Porject_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskTitle', '[Task_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'activity', '[Activity]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'activityDescrption', '[Activity_Descrption]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'login', '[Login]');

#EmailScenario Notification To Request User from approver
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('UpdatedTaskNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('UpdatedTaskNotification', 'Notification', 'UpdatedTaskNotification', 'Forward the email to the project task assignee & owners about project details updated', 'Task details got updated task id  [TaskId]', 'org', '', 'Task details got updated task id - [TaskId]','N','SLADMIN', 'com', 'in.com', 3,1,'Y');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskId', '[TaskId]');



#Add comment mail To Request Assign members mail 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AddTaskCommentEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('AddTaskCommentEmail','Email', 'AddTaskCommentEmail', 'Forward the email to the project task assignee & owners about project add Comment', '[Login_User] commented on  [TaskId]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Assign_Member_Name],\n [Login_User] commented on the task for the [Porject_Name] Project \n Task Id : [TaskId] \n Task Title : [Task_Title] \n Comment: [Task_Comment] \n\n Please log into Sourcelead to view the comment. \n [Login_Link]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assignMemberName', '[Assign_Member_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskId', '[TaskId]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Porject_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskTitle', '[Task_Title]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskComment', '[Task_Comment]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[Login_Link]');

#EmailScenario Notification To Request User from approver
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AddTaskCommentNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('AddTaskCommentNotification', 'Notification', 'AddTaskCommentNotification', 'Forward the email to the project task assignee & owners about project details updated', '[Login_User] commented on  [TaskId]', 'org', '', '[Login_User] commented on  [TaskId]','N','SLADMIN', 'com', 'in.com', 3,1,'Y');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'taskId', '[TaskId]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');



ALTER TABLE LOOKUP_DATA 
ADD COLUMN `ORG_LOCATIONID` BIGINT(5) NULL DEFAULT NULL AFTER `UPDATED_BY`,
ADD COLUMN `REFER_ID` BIGINT(5) NULL DEFAULT NULL AFTER `ORG_LOCATIONID`,
ADD COLUMN `COLOR_PIC` VARCHAR(45) NULL DEFAULT NULL AFTER `REFER_ID`;

SET SQL_SAFE_UPDATES = 0;
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FF0000' WHERE  NAME='P1' and CATEGORY = 'TaskPriority';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FFA500' WHERE  NAME='P2' and CATEGORY = 'TaskPriority';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FFFF00' WHERE  NAME='P3' and CATEGORY = 'TaskPriority';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#808080' WHERE  NAME='P4' and CATEGORY = 'TaskPriority';
        
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FF0000' WHERE  NAME='Critical' and CATEGORY = 'TaskSeverity';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FFA500' WHERE  NAME='Important' and CATEGORY = 'TaskSeverity';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#FFFF00' WHERE  NAME='Normal' and CATEGORY = 'TaskSeverity';
UPDATE  LOOKUP_DATA SET `COLOR_PIC`='#808080' WHERE  NAME='Minor' and CATEGORY = 'TaskSeverity';



#Change asset To Request Assign members mail 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AssigneAssetEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('AssigneAssetEmail','Email', 'AssigneAssetEmail', 'Forward the email to Assign asset member ', 'New Asset assigned to you','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Assign_Member_Name],\n This is to notify that new asset assigned to you,go with following details \n Asset Name : [Asset_Name] \n Category : [Category] \n Assigned on: [Assigned_On] \n Returned on : [Returned_On]','N', 'SLADMIN', 'slead', 'sleadIN', 14,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assignMemberName', '[Assign_Member_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assetName', '[Asset_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'category', '[Category]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'assignedOn', '[Assigned_On]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'returnedOn', '[Returned_On]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');


#EmailScenario Notification login usere To Assign member
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AssigneAssetNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('AssigneAssetNotification', 'Notification', 'AssigneAssetNotification', 'Forward the email to Assign asset member', 'New Asset assigned to you', 'org', '', 'New Asset assigned to you','N','SLADMIN', 'com', 'in.com', 14,1,'Y');
 


















