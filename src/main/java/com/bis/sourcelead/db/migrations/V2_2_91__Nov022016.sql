CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','USER_STATE','VARCHAR(50) NULL AFTER `IS_PRIMARY`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','PREVIOUS_USER_STATE','VARCHAR(50) NULL AFTER `USER_STATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','USER_TYPE','VARCHAR(50) NULL AFTER `PREVIOUS_USER_STATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','TERMINATE_REASON','TEXT NULL AFTER `USER_TYPE`');

#updates for old production data 
SET SQL_SAFE_UPDATES = 0;
UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE` = 'ACTIVE' where USER_ID in
(select id from `sourcelead`.`USER` where USER_STATE = 'ACTIVE');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE` = 'NOT_LOGGEDIN' where USER_ID in
(select id from `sourcelead`.`USER` where USER_STATE = 'NOT_LOGGEDIN');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE` = 'INACTIVE' where USER_ID in
(select id from `sourcelead`.`USER` where USER_STATE = 'INACTIVE');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE` = 'BLOCKED' where USER_ID in
(select id from `sourcelead`.`USER` where USER_STATE = 'BLOCKED');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE` = 'Employee' where USER_ID in
(select id from `sourcelead`.`USER` where USER_TYPE = 'Employee');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE` = 'Admin' where USER_ID in
(select id from `sourcelead`.`USER` where USER_TYPE = 'Admin');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE` = 'Team Lead' where USER_ID in
(select id from `sourcelead`.`USER` where USER_TYPE = 'Team Lead');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE` = 'Project Manager' where USER_ID in
(select id from `sourcelead`.`USER` where USER_TYPE = 'Project Manager');
#updates for old production data 








