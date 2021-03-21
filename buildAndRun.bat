@echo off
call mvn clean package
call docker build -t org.example/dmit2015-assignment03-BraydonTol .
call docker rm -f dmit2015-assignment03-BraydonTol
call docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-assignment03-BraydonTol org.example/dmit2015-assignment03-BraydonTol