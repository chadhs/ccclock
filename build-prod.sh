#!/bin/bash
CCCLOCK_ENV=prod npm i && npm run vendor:weather-icons && npm run release && clj -T:build uberjar
