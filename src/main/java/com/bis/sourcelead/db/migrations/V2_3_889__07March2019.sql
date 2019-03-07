#DROP PROCEDURE associateBankDataProcedure;  

DELIMITER //
CREATE PROCEDURE associateBankDataProcedure 
(                                                       
    IN candidateId bigint,
    IN email varchar(100),
    IN accountType varchar(45),
    IN bankName varchar(50),
	IN accountNumber varchar(20),
	IN routingNumber varchar(20)	
) 

BEGIN
	   INSERT INTO associate_bank_data(CANDIDATE_ID,EMAIL, ACCOUNT_TYPE, BANK_NAME, ACCOUNT_NUMBER, ROUTING_NUMBER)
       values (candidateId,email, accountType, bankName, accountNumber, routingNumber); 

END;//

DELIMITER ;
