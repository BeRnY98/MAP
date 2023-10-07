@echo off
cd knnServer
start "knnServer.jar" java -jar knnServer.jar
echo Avvio del client
timeout 3
cd ../knnClient 
start "knnClient.jar" java -jar knnClient.jar 