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
## INSTALL NGINX
- sudo apt update
- sudo apt install nginx
#### ADD DOMAIN
- sudo nano /etc/nginx/sites-available/your_domain
- sudo ln -s /etc/nginx/sites-available/your_domain /etc/nginx/sites-enabled/
- sudo systemctl reload nginx
#### CHANGE CONFIG FILE
- sudo nano /etc/nginx/nginx.conf
- sudo nginx -t
- sudo systemctl restart nginx
## FIREWALL COMAND
- sudo ufw app list
- sudo ufw allow 'Nginx HTTP'
- sudo ufw status
## HOW ADD SERT
- apt install certbot python3-certbot-nginx
- sudo certbot --nginx -d domain.online (http://domain.online/)  -d www.domain.online (http://www.domain.online/) --no-redirect
## Default file
{}

# Portainer

# Container (my pack)
## Mysql
- docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
## MongoDB
- docker run --name mongodb  -p 27017:27017 -d mongo
