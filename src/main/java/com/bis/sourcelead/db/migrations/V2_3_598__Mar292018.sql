DELIMITER //
CREATE PROCEDURE connectionRequestSent 
(
  IN fromUid int(50),
  IN toUid int(50),
  IN connectionStatus varchar(50),
	IN statusVal int(50),
    IN connUUID varchar(50),
   IN createdDate Date
) 
BEGIN
	SELECT COUNT(*) AS count into @myVar FROM CONNECTIONS c WHERE (c.FROM_UID = fromUid and c.TO_UID = toUid and c.CONNECTION_STATUS = connectionStatus) or (c.FROM_UID = toUid and c.TO_UID = fromUid and c.CONNECTION_STATUS = connectionStatus);
   IF(@myVar = '0') THEN
	   INSERT INTO CONNECTIONS(FROM_UID, TO_UID, CONNECTION_STATUS, STATUS, CONNECTION_UID, CREATED_DATE) values (fromUid, toUid, connectionStatus, statusVal, connUUID, createdDate);   
	END IF;
END

