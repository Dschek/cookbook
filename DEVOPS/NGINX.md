# Install NGINX
UPDATE PACKAGE
```
sudo apt update
```
INSTALL NGINX
```
sudo apt install nginx
```
# Add domain
ADD FILE 
```
sudo nano /etc/nginx/sites-available/your_domain
```
- SET YOUR CONFIG

LINK FILE IN DIRECTORIES 
```
sudo ln -s /etc/nginx/sites-available/your_domain /etc/nginx/sites-enabled/
```
RELOAD NGINX
```
sudo systemctl reload nginx
```
Change config file (if need)
```
sudo nano /etc/nginx/nginx.conf
```
CHECK NGINX CONFIG
```
sudo nginx -t
```
RESTART NGINX
```
sudo systemctl restart nginx
```
# Firewall CONFIG
GET LIST FIREWALL RULE
```
sudo ufw app list
```
ADD NGINX HTTP IN RULE
```
sudo ufw allow 'Nginx HTTP'
```
CHECK FIREWALL STATUS
```
sudo ufw status
```
ADD FIREWALL IP RULES
```
sudo ufw allow from 46.29.239.40 to any
```
# INSTALL CERTBOT
INSTALL CERTBOT
```
apt install certbot python3-certbot-nginx
```
ADD SERT FOR DOMAIN
```
sudo certbot --nginx -d domain.online -d www.domain.online  -no-redirect
```
# Default file
## REACT APP
```
# Запускать в качестве менее привилегированного пользователя по соображениям безопасности..
user nginx;

# Значение auto устанавливает число максимально доступных ядер CPU,
# чтобы обеспечить лучшую производительность.
worker_processes    auto;

events { worker_connections 1024; }

http {
    server {
        
        # Hide nginx version information.
        server_tokens off;

        listen  80;
        root    /usr/share/nginx/html;
        include /etc/nginx/mime.types;


        location / {
            try_files $uri /index.html;
        }

        gzip            on;
        gzip_vary       on;
        gzip_http_version  1.0;
        gzip_comp_level 5;
        gzip_types
                        application/atom+xml
                        application/javascript
                        application/json
                        application/rss+xml
                        application/vnd.ms-fontobject
                        application/x-font-ttf
                        application/x-web-app-manifest+json
                        application/xhtml+xml
                        application/xml
                        font/opentype
                        image/svg+xml
                        image/x-icon
                        text/css
                        text/plain
                        text/x-component;
        gzip_proxied    no-cache no-store private expired auth;
        gzip_min_length 256;
        gunzip          on;
    }
}
```
## DEFAULT APP
```
```

