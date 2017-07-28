SET SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`email_scenario` SET `BODY`=' You need to update the timesheets of as soon as possible' WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Scheduler' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES=1;

DROP TABLE IF EXISTS `sourcelead`.`action_history`;
CREATE TABLE `sourcelead`.`action_history` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERID` bigint(20) DEFAULT NULL,
  `USER_LOGIN_LOGOUT_SESSION_ID` bigint(20) DEFAULT NULL,
  `ACTION_NAME` varchar(100) DEFAULT NULL,
  `ACTION_PERFORMED` varchar(500) DEFAULT NULL,
  `ACTION_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `USER_LOGIN_LOGOUT_SESSION_ID` (`USER_LOGIN_LOGOUT_SESSION_ID`),
  CONSTRAINT `action_history_ibfk_1` FOREIGN KEY (`USER_LOGIN_LOGOUT_SESSION_ID`) REFERENCES `user_login_logout_session` (`ID`)
);

DROP TABLE IF EXISTS `sourcelead`.`action_names`;
CREATE TABLE `sourcelead`.`action_names` (
  `ID` bigint(20) NOT NULL,
  `ACTION_NAME` varchar(100) DEFAULT NULL,
  `METHOD_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);