#!/bin/bash

cd /home/rocket/

ps ax | grep java | grep charer-openapi | awk '{print $1}'  | xargs kill -9

nohup /usr/bin/java -Dspring.config.additional-location=/home/rocket/application-openapi-rocket-prod.yml -jar /home/rocket/charer-openapi-1.0.6.jar --springboot.profiles.active=dev> /home/rocket/log/openapi-start.log 2>&1 &

tail -f log/openapi-start.log 