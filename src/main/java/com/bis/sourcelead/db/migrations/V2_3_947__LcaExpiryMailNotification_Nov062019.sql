 
 
 
 SET SQL_SAFE_UPDATES=0;
 
 UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi [Lca_Manager],\n This is to notify that [No_Of_Lcas] LCA/s expires today. \n Here are the details of expired LCA/s.\n [Lca_Table]\n  To view more details, please login [URL_Link]' 
 WHERE `UNIQUE_SCENARIO_NAME`='LCAManagerExpiredEmail';
 
 
  UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi [Lca_Manager],\n This is to notify that [No_Of_Lcas] LCA/s expires today.' 
 WHERE `UNIQUE_SCENARIO_NAME`='LCAManagerExpiredNotification';
 
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
	VALUES ((select ID from EMAIL_SCENARIO where UNIQUE_SCENARIO_NAME like 'LCAManagerExpiredNotification') , 'No_Of_Lcas', '[No_Of_Lcas]');
 INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
 VALUES ((select ID from EMAIL_SCENARIO where UNIQUE_SCENARIO_NAME like 'LCAManagerExpiredNotification'), 'Lca_Manager', '[Lca_Manager]');
 
 
   UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi [Lca_Manager],\n This is to notify that [No_Of_Lcas] LCA/s expiring in ([Expiring_Days] Days) .' 
 WHERE `UNIQUE_SCENARIO_NAME`='LCAManagerExpiresInDaysNotification';
  
 
 INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) 
 VALUES ((select ID from EMAIL_SCENARIO where UNIQUE_SCENARIO_NAME like 'LCAManagerExpiresInDaysNotification'), 'Lca_Manager', '[Lca_Manager]');
 
 SET SQL_SAFE_UPDATES=1;
 
 