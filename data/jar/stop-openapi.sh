#!/bin/bash

ps ax | grep java | grep charer-openapi | awk '{print $1}'  | xargs kill -9

