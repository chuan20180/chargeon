#!/bin/bash

cd /home/rocket/

ps ax | grep java | grep charer-system | awk '{print $1}'  | xargs kill -9

nohup /usr/bin/java -Dspring.config.additional-location=/home/rocket/application-system-rocket-prod.yml -jar /home/rocket/charer-system-1.0.6.jar --springboot.profiles.active=dev> /home/rocket/log/system-start.log 2>&1 &


tail -f log/system-start.log 