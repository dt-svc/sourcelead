set SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Point of contact changed for {{clientOwnerUpdateMail.clientOrgName}}'  WHERE `UNIQUE_SCENARIO_NAME`='clientOwnerUpdateMail' and `ENTITY_TYPE`='Email';
set SQL_SAFE_UPDATES=1;