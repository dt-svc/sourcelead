SET SQL_SAFE_UPDATES = 0;
 UPDATE `EMAIL_SCENARIO` SET `SUBJECT` = '',`SIGNATURE` = '',`BODY` = 'Hi (Immigration Manager) \n This is to notify that (no.of LCAs) LCA/s expiring in (Expiring days).' WHERE UNIQUE_SCENARIO_NAME = 'LCAManagerExpiredNotification';
SET SQL_SAFE_UPDATES = 1;