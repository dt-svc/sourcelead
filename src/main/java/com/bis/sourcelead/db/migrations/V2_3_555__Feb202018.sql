set sql_safe_updates=0;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Dear {{AcceptReopenTimeSheet.managerName}},\n\n{{ReopenTimeSheet.employeeName}} Time cart has been re-opened for you to modify.\nPlease Login into the SourceLead to update\n\n{{AcceptReopenTimeSheet.loginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='AcceptReopenTimeSheet' and `ENTITY_TYPE`='Email';
set sql_safe_updates=1;