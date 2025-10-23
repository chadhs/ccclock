#!/bin/bash
# Ensure vendor assets exist before starting dev server
echo "Checking vendor assets..."
if [ ! -d "resources/public/vendor/weather-icons" ]; then
    echo "Vendoring assets..."
    npm run vendor:weather-icons
fi
echo "Starting dev server..."
clj -M:run