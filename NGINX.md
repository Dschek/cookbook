# Install Nginx
- sudo apt update
- sudo apt install nginx
# Add domain
- sudo nano /etc/nginx/sites-available/your_domain
- sudo ln -s /etc/nginx/sites-available/your_domain /etc/nginx/sites-enabled/
- sudo systemctl reload nginx
# Change config file (if need)
- sudo nano /etc/nginx/nginx.conf
- sudo nginx -t
- sudo systemctl restart nginx
# Firewall comaand
- sudo ufw app list
- sudo ufw allow 'Nginx HTTP'
- sudo ufw status
# How add sert
- apt install certbot python3-certbot-nginx
- sudo certbot --nginx -d domain.online (http://domain.online/)  -d www.domain.online (http://www.domain.online/) --no-redirect
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