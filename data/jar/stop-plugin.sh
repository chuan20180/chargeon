#!/bin/bash

ps ax | grep java | grep charer-plugin | awk '{print $1}'  | xargs kill -9

