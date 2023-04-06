## NODE
```
version: '2'


services:
  web:
    image: "docker.pkg.github.com/dschek/crystal/crystal:latest"
    container_name: crystal
    ports:
      - "9201:3001"
    networks:
      - crystal
      - mongodb
networks:
  crystal: {}
  mongodb:
    external: true
```
## REACT
```
version: '3'

services:
  web:
    container_name: restf
    image: "docker.pkg.github.com/dschek/restfrontreact/restfrontreact:latest"
    ports:
      - "9001:80"
```
## SPRING
```
version: '3.8'

services:
  web:
    container_name: restb
    image: "docker.pkg.github.com/dschek/restcms/restcms:latest"
    ports:
      - "8080:8080"
    networks:
      - restb
      - mysql
networks:
  restb: {}
  mysql:
    external: true
```
