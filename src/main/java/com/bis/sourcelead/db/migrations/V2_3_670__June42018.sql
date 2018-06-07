CALL AlterTableAddColumn ('ASSOCIATE_BALANCE_LEAVES','ORG_LOCATION_ID','BIGINT(20) NULL AFTER `USER_ID`');


DROP PROCEDURE associateBalanceLeave;  

DELIMITER //

CREATE PROCEDURE associateBalanceLeave 
(
    IN userid bigint,
    IN locationId bigint,
    IN leaveTypeId bigint,
    IN totalLeaves varchar(50),
	IN assBalLeaveUUID varchar(50),
    IN status INT(10),
    IN createdBy bigint,
    IN createdDate date,
    IN updatedBy bigint,
    IN updatedDate date
) 
BEGIN
	   INSERT INTO ASSOCIATE_BALANCE_LEAVES(USER_ID,ORG_LOCATION_ID, LEAVE_TYPE_ID, TOTAL_LEAVES, ASSO_BAL_LEAVE_UUID, STATUS, CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE)
       values (userid,locationId, leaveTypeId, totalLeaves, assBalLeaveUUID, status, createdBy,createdDate,updatedBy,updatedDate); 


END; //

DELIMITER ;
