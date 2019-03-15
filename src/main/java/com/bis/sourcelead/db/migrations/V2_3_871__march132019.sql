CALL AlterTableAddColumn ('EXPENSES','SUBMITTED_DATE','DATETIME  DEFAULT NULL AFTER `BILLING_PAYABLE_STATUS`');

#DROP PROCEDURE associateBankingDetails;  

DELIMITER //
CREATE PROCEDURE associateBankingDetails (                                                       
    IN candidateId bigint,
    IN email varchar(100),
    IN accountType varchar(45),
    IN bankName varchar(50),
	IN accountNumber varchar(20),
	IN bsrNumber varchar(20),
	IN micrCode varchar(20),	
	IN routingNumber varchar(20),	
    IN firstName varchar(100),
    IN lastName varchar(100)
) 
BEGIN
	   INSERT INTO PAYMENT_ACCOUNT(CANDIDATE_ID,EMAIL, ACCOUNT_TYPE, BANK_NAME, ACCOUNT_NUMBER, BSR_NUMBER, MICR_CODE, ROUTING_NUMBER, FIRST_NAME, LAST_NAME)
       values (candidateId,email, accountType, bankName, accountNumber, bsrNumber, micrCode, routingNumber ,firstName, lastName); 

END;//
DELIMITER ;