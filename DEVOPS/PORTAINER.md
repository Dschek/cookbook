# INSTALL PORTAINER
CREATE VOLUME
```
docker volume create portainer_data
```
INSTALL PORTAINER
```
docker run -d -p 8000:8000 -p 9443:9443 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ee:latest
```
SET LICENSE KEY
```
2-NgAXqIeNRJAi6LPFXfeMLiMBFV+EgWjKARTgGxseOaTZx508epmyYuVIUi/ebSCbd96epyetuF4V+fIX5Fg=
```
