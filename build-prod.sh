#!/bin/bash
CCCLOCK_ENV=prod npm i && npm run release && clj -T:build uberjar
