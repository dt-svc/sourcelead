SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='New task Assigned in {{MailtoAssignEmployeeinProjecttask.ProjectName}}' WHERE `UNIQUE_SCENARIO_NAME`='MailtoAssignEmployeeinProjecttask' AND `ENTITY_TYPE`='Email';

UPDATE `ACTION_NAMES` SET `METHOD_NAME`='saveChangedStatus' WHERE `ACTION_NAME`='Close Filled Job';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='saveChangedStatus' WHERE `ACTION_NAME`='Close Unfilled Job';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='saveChangedStatus' WHERE `ACTION_NAME`='Cancel Job';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='saveChangedStatus' WHERE `ACTION_NAME`='Onhold Job';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='proposeRate' WHERE `ACTION_NAME`='Rate Negotiation';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='getCandidatesExportList' WHERE `ACTION_NAME`='Export Candidates';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='customErrorPage' WHERE `ACTION_NAME`='Approve Contract';
UPDATE `ACTION_NAMES` SET `METHOD_NAME`='getExportUsersData' WHERE `ACTION_NAME`='Export Associate';

SET SQL_SAFE_UPDATES=1;