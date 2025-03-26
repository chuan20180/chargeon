#!/bin/bash

ps ax | grep java | grep charer-system | awk '{print $1}'  | xargs kill -9

