

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','MAX_RATE','VARCHAR(50) NULL  AFTER `RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','TRAVEL_ALLOWANCE','VARCHAR(50) NULL  AFTER `MAX_RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','INDUSTRY_NAME','VARCHAR(50) NULL  AFTER `TRAVEL_ALLOWANCE`');



