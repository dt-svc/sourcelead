

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','DOC_FILE','MEDIUMBLOB NULL AFTER `UPDATED_BY`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','DOC_FILENAME','VARCHAR(500) NULL AFTER `DOC_FILE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','INVOICE','INVOICE_FREQUENCY','VARCHAR(100) NULL AFTER `IS_SEPARATE_INVOICE`');