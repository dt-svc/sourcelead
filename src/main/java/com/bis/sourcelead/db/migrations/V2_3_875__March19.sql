DELIMITER //
CREATE PROCEDURE associateInfoDataProcedure 
(                                                       
    IN candidateId bigint,
    IN uanNumber varchar(50),
    IN pfNumber varchar(50),
    IN esinumber varchar(50),
    IN orgLocationId bigint,
	IN createdDate date,
	IN eligibleForEsi varchar(10),
	IN eligibleForPf varchar(10)
) 
BEGIN
	   INSERT INTO ASSOCIATE_PF_ESI_DETAILS(CANDIDATE_ID,UAN_NUMBER, PF_NUMBER, ESI_NUMBER, ORG_LOCATION_ID,CREATED_DATE,ELIGIBLE_FOR_ESI,ELIGIBLE_FOR_PF)
       values (candidateId,uanNumber, pfNumber, esinumber, orgLocationId, createdDate,eligibleForEsi,eligibleForPf); 

END//
DELIMITER ;
