 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LOCATION','VARCHAR(100) NULL AFTER `ADDRESS`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','ZIPCODE','VARCHAR(20) NULL AFTER `LOCATION`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LATITUDE','VARCHAR(50) NULL AFTER `ZIPCODE`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','LONGITUDE','VARCHAR(50) NULL AFTER `LATITUDE`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LOCATION','VARCHAR(100) NULL AFTER `ZIP_CODE`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LATITUDE','VARCHAR(50) NULL AFTER `LOCATION`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','LONGITUDE','VARCHAR(50) NULL AFTER `LATITUDE`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_SUBMISSION_TEMPLATE_FIELDS','LOCATION','VARCHAR(100) NULL AFTER `EMAIL_ID`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_SUBMISSION_TEMPLATE_FIELDS','ZIPCODE','VARCHAR(20) NULL AFTER `CITY`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','JOB_ALERTS','LOCATION','VARCHAR(100) NULL AFTER `SKILLS_NAME`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','JOB_ALERTS','ZIPCODE','VARCHAR(20) NULL AFTER `CITY`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','LOCATION','VARCHAR(100) NULL AFTER `RATE_TYPE`');
 CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','ZIPCODE','VARCHAR(20) NULL AFTER `CITY`');
 
 CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_ON_BOARDING','LOCATION','VARCHAR(100) NULL AFTER `ADDRESS_2`');
 
  CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_OFF_BOARDING','LOCATION','VARCHAR(100) NULL AFTER `ADDRESS_2`');