DROP TABLE IF EXISTS `ASSOCIATE_BALANCE_LEAVE_HISTORY`;
CREATE TABLE `ASSOCIATE_BALANCE_LEAVE_HISTORY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  USER_ID BIGINT,
  ORG_LOCATION_ID BIGINT,
  START_DATE DATE,
  END_DATE DATE,
  YEAR INTEGER,
  MONTH varchar(100),
  TOTAL_BALANCE_LEAVES INTEGER,
  LEAVE_TYPE_ID BIGINT,
  CREATED_DATE DATE,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  
  PRIMARY KEY (`ID`),
  FOREIGN KEY(ORG_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
  FOREIGN KEY(USER_ID) REFERENCES `USER`(ID),
  FOREIGN KEY(LEAVE_TYPE_ID) REFERENCES `LEAVE_TYPE`(ID)
) ;





DROP PROCEDURE if exists associateBalanceLeaveHistoryProcedure;  

DELIMITER //
CREATE PROCEDURE associateBalanceLeaveHistoryProcedure 
(                                                       
    IN userId bigint,
    IN orgLocationId bigint,
    IN startDate date,
	IN endDate date,
	IN year integer,
	IN month varchar(100),
	IN totalBalanceLeaves integer,
    IN leaveTypeId bigint
) 

BEGIN
	   INSERT INTO ASSOCIATE_BALANCE_LEAVE_HISTORY(USER_ID, ORG_LOCATION_ID, START_DATE, END_DATE, YEAR, MONTH,TOTAL_BALANCE_LEAVES,LEAVE_TYPE_ID)
       values (userId, orgLocationId, startDate, endDate, year, month, totalBalanceLeaves, leaveTypeId); 

END;//

DELIMITER ;