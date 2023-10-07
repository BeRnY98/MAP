@echo off
cd server
start "server.jar" java -jar server.jar
echo Avvio del client
timeout 3
cd ../client
start "client.jar" java -jar client.jar 