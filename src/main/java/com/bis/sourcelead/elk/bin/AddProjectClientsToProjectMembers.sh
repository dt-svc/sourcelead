
PROPERTYLOCATION="-DpropertyLocation=file:/home/properties/app.properties"
export SCRIPTLOCATION="/home/Sourcelead_Db_Scripts" export SCRIPTLIBLOCATION="/home/Sourcelead_Db_Scripts/lib/*" 
java  $PROPERTYLOCATION -classpath $SCRIPTLOCATION:$SCRIPTLIBLOCATION  com.bis.sourcelead.elk.install.AddProjectClientsToProjectMembers
