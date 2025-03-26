#!/bin/bash

cd /home/rocket/
nohup /usr/bin/java -Dspring.config.additional-location=/home/rocket/application-plugin-rocket-prod.yml -jar /home/rocket/charer-plugin-1.0.6.jar --springboot.profiles.active=dev> /home/rocket/log/plugin-start.log 2>&1 &