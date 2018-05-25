
PROPERTYLOCATION="-DpropertyLocation=file:/home/$1/properties/app.properties"
export SCRIPTLOCATION="/home/$1/Sourcelead_Db_Scripts" export SCRIPTLIBLOCATION="/home/$1/Sourcelead_Db_Scripts/lib/*" 
java  $PROPERTYLOCATION -classpath $SCRIPTLOCATION:$SCRIPTLIBLOCATION  com.bis.sourcelead.elk.install.GenerateEmailScenarioUUID
