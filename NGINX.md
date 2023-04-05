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
{}
