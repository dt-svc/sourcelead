INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
			('ApplyLeaveRemainder','Email', 'ApplyLeaveRemainder', 'Used to Remind Approver to Approve Leave', '[No_of_Leave_Requests] leaves are in pending for approve.','org', 'Best Regards\n[Org_Name].', 'Dear [Approver_Name],\nYou have [No_of_Leave_Requests] pending leave requests to approve from past 3 days.\nFor Complete details login into the system.\n[Login_URL]','N', 'SLADMIN', 'slead', 'sleadIN', 31,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'orgName', '[Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approverName', '[Approver_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfLeaveRequests', '[No_of_Leave_Requests]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUrl', '[Login_URL]');



DROP PROCEDURE IF EXISTS deleteAssociateBalLeave;
DELIMITER //
CREATE PROCEDURE deleteAssociateBalLeave(
  status int,
  totalLeaves varchar(20),
  locationID BIGINT,
  userID BIGINT,
  leaveTypeID BIGINT
)
BEGIN
  UPDATE ASSOCIATE_BALANCE_LEAVES
  SET STATUS = status,
      TOTAL_LEAVES = totalLeaves
  WHERE ORG_LOCATION_ID = locationID and USER_ID = userID and LEAVE_TYPE_ID = leaveTypeID;
END//
DELIMITER ;
