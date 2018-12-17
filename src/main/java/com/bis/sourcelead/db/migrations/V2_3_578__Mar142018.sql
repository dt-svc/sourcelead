SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='New Resources Request for {{NewResourceRequest.projectName}} Project' WHERE `UNIQUE_SCENARIO_NAME`='NewResourceRequestNotification';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Budget Request for {{BudgetRequest.projectName}} Project' WHERE `UNIQUE_SCENARIO_NAME`='BudgetRequestNotification';
SET SQL_SAFE_UPDATES=1;


