CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','OVERTIME_RATE','VARCHAR(15) NULL AFTER `TOTAL_HOURS`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','OVERTIME_RATE_TYPE','VARCHAR(15) NULL AFTER `OVERTIME_RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','CONTRACTS','STATE_JURISDICTION','VARCHAR(10) NULL AFTER `OVERTIME_RATE_TYPE`');