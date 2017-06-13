
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','MAX_RATE','VARCHAR(50) NULL  AFTER `RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','TRAVEL_ALLOWANCE','VARCHAR(50) NULL  AFTER `MAX_RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','INDUSTRY_NAME','VARCHAR(50) NULL  AFTER `TRAVEL_ALLOWANCE`');


CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT_REFER','REFER_UUID','VARCHAR(20) NULL AFTER `REFER_STATUS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE_OFF_BOARDING','EMPLOYMENT_TYPE','VARCHAR(100) NULL AFTER `ROLE`');



