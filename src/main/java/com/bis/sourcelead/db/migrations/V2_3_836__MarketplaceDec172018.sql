ALTER TABLE `sourcelead`.`candidate_market_place_sales_order` 
ADD COLUMN `PAYMENT_METHOD` VARCHAR(100) NULL AFTER `ORDER_DATE`;

ALTER TABLE `sourcelead`.`candidate_market_place_sales_order` 
ADD COLUMN `FIRST_NAME` VARCHAR(100) NULL AFTER `PAYMENT_METHOD`;

ALTER TABLE `sourcelead`.`candidate_market_place_sales_order` 
ADD COLUMN `LAST_NAME` VARCHAR(100) NULL AFTER `FIRST_NAME`;

ALTER TABLE `sourcelead`.`candidate_market_place_sales_order` 
ADD COLUMN `EMAIL` VARCHAR(150) NULL AFTER `LAST_NAME`;

ALTER TABLE `sourcelead`.`candidate_market_place_sales_order` 
ADD COLUMN `COUNTRY_CODE` VARCHAR(20) NULL AFTER `EMAIL`;