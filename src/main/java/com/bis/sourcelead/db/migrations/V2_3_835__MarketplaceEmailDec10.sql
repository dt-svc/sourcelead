
UPDATE `EMAIL_SCENARIO` SET `SCENARIO_NAME`='ProfilesAddedToCartMail', `UNIQUE_SCENARIO_NAME`='ProfilesAddedToCartMail', `DESCRIPTION`='Profiles Added To Cart Mail To Users' WHERE `ID`='512';
UPDATE `ENTITY_TYPE` SET `ENTITY_NAME`='ProfilesAddedToCartMail' WHERE `ID`='337';

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ProfilesAddedToCartNotification','Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) 
VALUES('ProfilesAddedToCartNotification',
			'Notification', 
			'ProfilesAddedToCartNotification', 
			'Profiles Added To Cart Notification To Users', 
			'NULL',
			'org', 
			'NULL', 
			'New [Resume_Count] added to the cart by [Current_User].',
			'N', 'SLADMIN', 'slead', 'sleadIN', 34,1,'Y');
			
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'currentUser', '[Current_User]');		
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'resumeCount', '[Resume_Count]');			
			




