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
```
docker run --name mysql \
  -v /my/custom:/etc/mysql/conf.d \
  -e MYSQL_ROOT_PASSWORD=my-secret-pw \
  -e MYSQL_DATABASE=mrcod \
  -p 3306:3306 \
  -d mysql
```
## MongoDB
```
docker run -d -p 27017:27017 --name mongodb -v mongo-data:/data/db -e MONGODB_INITDB_ROOT_USERNAME=MyMongoUser -e MONGODB_INITDB_ROOT_PASSWORD=My1MongoPass -e MONGO_INITDB_DATABASE=crystal --network mongodb mongo:4.4.6
```
## Postgres
```
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=12345678 --network postgres -d postgres
```
