
ALTER TABLE `ASSOCIATE_BALANCE_LEAVES` 
ADD COLUMN `LEAVETYPE_ROLES` VARCHAR(200)  NULL DEFAULT NULL AFTER `ASSO_BAL_LEAVE_UUID`,
ADD COLUMN `LEAVETYPE_DEPARTMENT` VARCHAR(200) NULL DEFAULT NULL AFTER `LEAVETYPE_ROLES`,
ADD COLUMN `LEAVETYPE_EMP_TYPE` VARCHAR(200) NULL DEFAULT NULL AFTER `LEAVETYPE_DEPARTMENT`;

ALTER TABLE `LEAVE_TYPE` 
ADD COLUMN `DEPARTMENT_IDS` VARCHAR(200) NULL DEFAULT NULL AFTER `ACCURAL_POLICY`;


DROP PROCEDURE associateBalanceLeave;  
DELIMITER //
CREATE PROCEDURE associateBalanceLeave 
(
    IN userid bigint,
    IN locationId bigint,
    IN leaveTypeId bigint,
    IN totalLeaves varchar(50),
	IN assBalLeaveUUID varchar(50),
	IN leavetypeRoles varchar(200),
	IN leavetypeDepartment varchar(200),
	IN leavetypeEmpType varchar(200),
    IN status INT(10),
    IN createdBy bigint,
    IN createdDate date,
    IN updatedBy bigint,
    IN updatedDate date
) 
BEGIN
	   INSERT INTO ASSOCIATE_BALANCE_LEAVES(USER_ID,ORG_LOCATION_ID, LEAVE_TYPE_ID, TOTAL_LEAVES, ASSO_BAL_LEAVE_UUID, LEAVETYPE_ROLES, LEAVETYPE_DEPARTMENT, LEAVETYPE_EMP_TYPE, STATUS, CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE)
       values (userid,locationId, leaveTypeId, totalLeaves, assBalLeaveUUID, leavetypeRoles, leavetypeDepartment, leavetypeEmpType, status, createdBy,createdDate,updatedBy,updatedDate); 

END; //

DELIMITER ;



DROP PROCEDURE IF EXISTS deleteAssociateBalLeave;
DELIMITER //
CREATE PROCEDURE deleteAssociateBalLeave(
 IN status int,
 IN totalLeaves varchar(20),
 IN locationID BIGINT,
 IN userID BIGINT,
 IN leaveTypeID BIGINT,
 IN leavetypeRoles varchar(200),
 IN leavetypeDepartment varchar(200),
 IN leavetypeEmpType varchar(200)
)
BEGIN
  UPDATE ASSOCIATE_BALANCE_LEAVES
  SET STATUS = status,
      TOTAL_LEAVES = totalLeaves,
      LEAVETYPE_ROLES = leavetypeRoles,
      LEAVETYPE_DEPARTMENT = leavetypeDepartment,
      LEAVETYPE_EMP_TYPE = leavetypeEmpType
  WHERE ORG_LOCATION_ID = locationID and USER_ID = userID and LEAVE_TYPE_ID = leaveTypeID;
END//
DELIMITER ;
