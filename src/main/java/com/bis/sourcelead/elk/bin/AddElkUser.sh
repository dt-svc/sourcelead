
﻿cd /home/Sourcelead_Db_Scripts
PROPERTYLOCATION="-DpropertyLocation=file:/home/properties/app.properties"
<<<<<<< HEAD
export PROPERTYLOCATION
=======
export PROPERTYLOCATION 
>>>>>>> 5f2ae2e4948b9f5086806a68640a7b5d7eab74fa
java  $PROPERTYLOCATION -classpath /home/Sourcelead_Db_Scripts:/home/Sourcelead_Db_Scripts/lib/*  com.bis.sourcelead.elk.install.AddElkUser
