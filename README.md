# VPN
## Install OpenVPN

### On server 

install openvpn

- wget https://git.io/vpn -O openvpn-install.sh

create sert
- bash openvpn-install.sh

sert pwd 

-

### On client 

install openvpn
- openvpn --client --config /etc/openvpn/client/homeserver.ovpn 

start openvpn 
- systemctl enable openvpn-client@.service service openvpn@client start &

mount directory
- 

save sert
- 

# K8S

# Docker

# Nginx

# Portainer

# Container (my pack)
## Mysql
- docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
## MongoDB
- docker run --name mongodb  -p 27017:27017 -d mongo
