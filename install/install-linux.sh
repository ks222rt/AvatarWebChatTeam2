#! /bin/sh
# update & upgrade
printf "Avatar WebChat Installation Script v 1.0\n\n"


printf "Performing system update.\n"
which apt-get > /dev/null 2> /dev/null
if [ $? = 0 ]; then
	sudo apt-get -y update
	sudo apt-get -y upgrade
	clear
	sleep 2
	printf "Installing dialog program..."
	sudo apt-get -y install dialog > /dev/null 2> /dev/null
	printf "Done.\n"
	sleep 1
else
	which dnf > /dev/null 2> /dev/null
	if [ $? = 0 ]; then
		sudo dnf -y check-update
		sudo dnf -y update
		clear
		sleep 2
		printf "Installing dialog program..."
		sudo dnf -y install dialog > /dev/null 2> /dev/null
		printf "Done.\n"
		sleep 1
	else
		printf "This installation script uses apt-get (Debian, Ubuntu) or dnf (Fedora).\n"
		exit 1
	fi
fi


# install maven and Mysql
dialog --title "Confirmation Required" --yesno "This will install the following required packages (if not already installed):\n * maven\n * mysql-server (or mariadb)\n\nContinue?" 12 40
if [ $? != 0 ]; then
	dialog --title "Important" --infobox "\nInstallation aborted" 5 25
	sleep 3
	dialog --clear
	clear
	exit 0
fi

which apt-get > /dev/null 2> /dev/null
if [ $? = 0 ]; then
	dialog --title "Installing Packages" --infobox "\nPlease wait." 5 25
	sleep 1
	dialog --clear
	clear
	sudo apt-get -y install maven 
	sudo apt-get -y install mysql-server mysql-client 
	
	# start mysql server
	sudo service mysqld restart
	
	dialog --title "Confirmation Required" --yesno "Do you want to run mysql_secure_installation now?" 12 40
	if [ $? = 0 ]; then
		clear
		sudo mysql_secure_installation
	fi
else
	dialog --title "Installing Packages" --infobox "\nPlease wait." 5 25
	sleep 1
	dialog --clear
	clear
	sudo dnf -y install mariadb mariadb-server 
	sudo dnf -y install maven 
	
	# start mariadb server
	sudo systemctl start mariadb
	dialog --title "Confirmation Required" --yesno "Do you want to run mysql_secure_installation now?" 12 40
	if [ $? = 0 ]; then
		clear
		sudo mysql_secure_installation
	fi
fi

# log in to mysql db
clear
dialog --title "Username" --inputbox "Enter a user name for Avatar WebChat databse" 9 45 2>._ans.txt
AVATAR_NDBUSR=$(cat ._ans.txt)
rm -f ._ans.txt
dialog --title "Password" --inputbox "Enter a password for the database" 9 45 2>._ans.txt
AVATAR_NDBPWD=$(cat ._ans.txt)
rm -f ._ans.txt
clear

sed -i "s^GRANT ALL ON avatar_webchat\.\* to 'avatar_admin' identified by 'avatarTeam!2';^/GRANT ALL ON avatar_webchat.* to '${AVATAR_NDBUSR}' identified by '${AVATAR_NDBPWD}';^" avatar_webchat_db.sql
echo "jdbc.driverClassName=com.mysql.jdbc.Driver" > ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
echo "jdbc.url=jdbc:mysql://localhost:3306" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
echo "jdbc.username=$AVATAR_NDBUSR" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties
echo "jdbc.password=$AVATAR_NDBPWD" >> ../WebChat/src/main/webapp/WEB-INF/jdbc.properties

printf "Creating database tables. Enter your mysql server root password below.\n"
mysql -u root -p < avatar_webchat_db.sql

dialog --title "Building Package" --infobox "\nPlease wait." 5 25
cd ../WebChat
mvn package > /dev/null 2> ../build_errors.log

dialog --title "Installation Complete" --msgbox "Installation complete.\nSee build_error.log in the root directory for any errors that might have occured." 8 35
clear
