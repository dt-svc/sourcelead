ALTER TABLE `sourcelead`.`project_task_activity` 
DROP FOREIGN KEY `project_task_activity_ibfk_4`,
DROP FOREIGN KEY `project_task_activity_ibfk_5`;
ALTER TABLE `sourcelead`.`project_task_activity` 
CHANGE COLUMN `PRIORITY` `PRIORITY` BIGINT(20) NULL ,
CHANGE COLUMN `TASK_STATUS` `TASK_STATUS` BIGINT(20) NULL ;
ALTER TABLE `sourcelead`.`project_task_activity` 
ADD CONSTRAINT `project_task_activity_ibfk_4`
  FOREIGN KEY (`PRIORITY`)
  REFERENCES `sourcelead`.`lookup_data` (`ID`),
ADD CONSTRAINT `project_task_activity_ibfk_5`
  FOREIGN KEY (`TASK_STATUS`)
  REFERENCES `sourcelead`.`lookup_data` (`ID`);

  
ALTER TABLE `sourcelead`.`PROJECT_TASKS` ADD COLUMN `ESTIMATE_TIME` VARCHAR(100) NULL DEFAULT NULL AFTER `ESTIMATE_DATE`;
