
SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{HolidayRemainder.UserName}},\n\nThis is to inform that there will be holiday on {{HolidayRemainder.date}} due to {{HolidayRemainder.holidayname}} as per {{HolidayRemainder.companyName}} Holidays list.' WHERE `UNIQUE_SCENARIO_NAME`='HolidayRemainder';
SET SQL_SAFE_UPDATES=1;

