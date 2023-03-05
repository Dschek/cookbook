# VPN
## Install OpenVPN

### On server 

Install openvpn
- wget https://git.io/vpn -O openvpn-install.sh

Create sert
- bash openvpn-install.sh

Sert pwd 
-

### On client 

Install openvpn
- sudo  apt intall openvpn

Start openvpn 
- systemctl enable openvpn-client@.service service openvpn@client start &

Download  sert
- scp root@185.185.70.255:/root/phone.ovpn C:\pr\vpn

Mount directory
> found disk
- lsblk
> create dir
- mkdir sdb1
> mount dir to disk
- sudo mount /dev/sdb1 sdb1

Save sert
- cp sert.ovpn /etc/openvpn/client.conf

Start openvpn with sert 
- openvpn --client --config /etc/openvpn/client.conf & 

# K8S

# DOCKER

## Container
### Mysql
- docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
### MongoDB
- docker run --name mongodb  -p 27017:27017 -d mongo

# NGINX
## Install Nginx
- sudo apt update
- sudo apt install nginx
## Add domain
- sudo nano /etc/nginx/sites-available/your_domain
- sudo ln -s /etc/nginx/sites-available/your_domain /etc/nginx/sites-enabled/
- sudo systemctl reload nginx
## Change config file (if need)
- sudo nano /etc/nginx/nginx.conf
- sudo nginx -t
- sudo systemctl restart nginx
## Firewall comaand
- sudo ufw app list
- sudo ufw allow 'Nginx HTTP'
- sudo ufw status
## How add sert
- apt install certbot python3-certbot-nginx
- sudo certbot --nginx -d domain.online (http://domain.online/)  -d www.domain.online (http://www.domain.online/) --no-redirect
## Default file
{}

# PORTAINER
