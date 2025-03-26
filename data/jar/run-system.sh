#!/bin/bash

cd /home/rocket/
nohup /usr/bin/java -Dspring.config.additional-location=/home/rocket/application-system-rocket-prod.yml -jar /home/rocket/charer-system-1.0.6.jar --springboot.profiles.active=dev> /home/rocket/log/system-start.log 2>&1 &