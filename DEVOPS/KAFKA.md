# INSTALL
## ADD USER KAFKA
ADD USER
```
sudo adduser kafka
```
ADD USER KAFKA TO GROUP ADMIN
```
sudo adduser kafka sudo
```
LOGIN TO KAFKA USER
```
su -l kafka
```
## INSTALL KAFKA
ADD DIR
```
mkdir ~/Downloads
```
DOWNLOAD KAFKA BINARIES
```
curl "https://downloads.apache.org/kafka/2.8.2/kafka_2.13-2.8.2.tgz" -o ~/Downloads/kafka.tgz
```
ADD DIR AND GO TO THIS DIR
```
mkdir ~/kafka && cd ~/kafka
``` 
EXTRAXT KAFKA ARCHIW BINARIES
```
tar -xvzf ~/Downloads/kafka.tgz --strip 1
```
# CONFIGURE
## CONFIGURE KAFKA 
OPEN FILE CONFIG
```
nano ~/kafka/config/server.properties
```
ADD RULE 
```
delete.topic.enable = true
```
OPEN 
```
sudo nano /etc/systemd/system/kafka.service
```
ADD 
```
[Unit]
Requires=zookeeper.service
After=zookeeper.service

[Service]
Type=simple
User=kafka
ExecStart=/bin/sh -c '/home/kafka/kafka/bin/kafka-server-start.sh /home/kafka/kafka/config/server.properties > /home/kafka/kafka/kafka.log 2>&1'
ExecStop=/home/kafka/kafka/bin/kafka-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
```
## CONFIGURE ZOOKEEPER
OPEN FILE CONFIG
```
sudo nano /etc/systemd/system/zookeeper.service
```
ADD
```
[Unit]
Requires=network.target remote-fs.target
After=network.target remote-fs.target

[Service]
Type=simple
User=kafka
ExecStart=/home/kafka/kafka/bin/zookeeper-server-start.sh /home/kafka/kafka/config/zookeeper.properties
ExecStop=/home/kafka/kafka/bin/zookeeper-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
```
# START 
START KAFKA
```
sudo systemctl start kafka
```
CHECK STATUS KAFKA
```
sudo systemctl status kafka
```
ADD TO AUTO START KAFKA
```
sudo systemctl enable kafka
```
START ZOOKEEPER
```
sudo systemctl start zookeeper
```
CHECK STATUS ZOOKEEPER
```
sudo systemctl status zookeeper
```
ADD TO AUTO START ZOOKEEPER
```
sudo systemctl enable zookeeper
```

# URL FOR COMAD AND DESCRIPTION FOR IT
```
https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-20-04
```