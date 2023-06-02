# MY DOCKER  FILE
## NODE
```
FROM node:14-alpine

RUN mkdir -p /home/node/app && chown -R node:node /home/node/app
WORKDIR /home/node/app
COPY ./ /home/node/app
USER node
RUN npm install
EXPOSE 9201

CMD [ "node", "app.js" ]
```
## REACT
```
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*
COPY ./build /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/nginx.conf

ENTRYPOINT ["nginx", "-g", "daemon off;"]
```
## SPRING
```
FROM openjdk:16-jdk-alpine

WORKDIR /home/RestCMS
COPY ./build/libs/rest-1.jar rest.jar

EXPOSE 8080

CMD java -jar rest.jar
```
## Mysql
'''
docker run --name some-mysql -v /my/custom:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
'''
## MongoDB
'''
'''
