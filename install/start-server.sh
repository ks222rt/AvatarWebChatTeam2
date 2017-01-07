#!/bin/sh

dialog --title "Select" --menu "Select action" 15 55 4 1 "Configure jdbc properties" 2 "Start Avatar WebChat" 3 "Start Avatar WebChat, run in the background" 2>._ans.txt
AVATAR_ACTION=$(cat ._ans.txt)
rm -f ._ans.txt

if [ "$AVATAR_ACTION" = "1" ]; then
	dialog --title "Warning" --yesno "This will erase your current configuration.\n\nContinue?" 10 35
	if [ $? != 0 ]; then
		dialog --clear
		clear
		exit 0
	fi
	
	dialog --title "URL" --inputbox "Enter database URL.\n\"mysql://\" is added automatically at the beginning. Do not add this yourself." 12 45 2>._ans.txt
	AVATAR_DBURL=$(cat ._ans.txt)
	rm -f ._ans.txt
	dialog --title "Username" --inputbox "Enter database username" 9 45 2>._ans.txt
	AVATAR_DBUSR=$(cat ._ans.txt)
	rm -f ._ans.txt
	dialog --title "Password" --inputbox "Enter database password" 9 45 2>._ans.txt
	AVATAR_DBPWD=$(cat ._ans.txt)
	rm -f ._ans.txt
	
	dialog --title "Warning" --yesno "This will write the following to your jdbc.properties:\n  URL: $AVATAR_DBURL\n  USERNAME: $AVATAR_DBUSR\n  PASSWORD: $AVATAR_DBPWD\n\nContinue?" 15 35
	if [ $? != 0 ]; then
		dialog --clear
		clear
		unset -v -n AVATAR_DBPWD AVATAR_DBUSR AVATAR_DBURL
		exit 0
	fi
	
	echo "jdbc.driverClassName=com.mysql.jdbc.Driver" > ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
	echo "jdbc.url=jdbc:mysql://$AVATAR_DBURL" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
	echo "jdbc.username=$AVATAR_DBUSR" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
	echo "jdbc.password=$AVATAR_DBPWD" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
	unset -v -n AVATAR_DBPWD AVATAR_DBUSR AVATAR_DBURL
	dialog --title "Finished" --msgbox "Your jdbc.properties has been updated." 9 35
	dialog --clear
	clear
fi
if [ "$AVATAR_ACTION" = "2" ]; then
	clear
	printf "Starting WebChat application...\n"
	sudo mvn package
	sudo java -jar WebChat/target/endorsed/webapp-runner.jar --port 8080 WebChat/target/*.war
fi
if [ "$AVATAR_ACTION" = "3" ]; then
	clear
	printf "Starting WebChat application...\n"
	sudo mvn package
	sudo java -jar WebChat/target/endorsed/webapp-runner.jar --port 8080 WebChat/target/*.war &
fi

