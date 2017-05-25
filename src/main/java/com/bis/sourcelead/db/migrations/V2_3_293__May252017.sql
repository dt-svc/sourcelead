CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','EXCHANGE_RATE','VARCHAR(50) NULL  AFTER `CURRENCY_CODE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','CONVERT_AMOUNT','VARCHAR(50) NULL  AFTER `EXCHANGE_RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','CONVERT_CURRENCY_CODE','VARCHAR(50) NULL  AFTER `CONVERT_AMOUNT`');