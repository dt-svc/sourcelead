SET SQL_SAFE_UPDATES=0;
UPDATE `SOURCELEAD_FORM_FIELDS` sff SET sff.LABEL_NAME = "Min Experience" WHERE sff.SECTION_ID = 3 AND sff.FIELD_NAME = "minExp" AND sff.FIELD_TYPE = "TextBox";
UPDATE `SOURCELEAD_FORM_FIELDS` sff SET sff.LABEL_NAME = "Max Experience" WHERE sff.SECTION_ID = 3 AND sff.FIELD_NAME = "maxExp" AND sff.FIELD_TYPE = "TextBox";
UPDATE `SOURCELEAD_FORM_FIELDS` sff SET sff.LABEL_NAME = "Location" WHERE sff.SECTION_ID = 3 AND sff.FIELD_NAME = "location" AND sff.FIELD_TYPE = "TextBox(Auto Fill)";

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Assignedmember],\n \n This is to notify that you have been assigned the following task(s) in [Project_Name]:\n \n Task Id : [Task_Id]\n Task Title : [Task_Title]\n Priority :[Priority]\n Severity : [Severity]\n Due Date : [Due_Date]\n Owner :[Owner] \n \n For more information, login to the system:\n \n [Link]' WHERE ENTITY_TYPE = 'Email' and UNIQUE_SCENARIO_NAME = 'MailtoAssignEmployeeinProjecttask';
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES   ((SELECT `ID` FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailtoAssignEmployeeinProjecttask' AND `ENTITY_TYPE` = 'Email'), 'taskId', '[Task_Id]');

SET SQL_SAFE_UPDATES=1;

