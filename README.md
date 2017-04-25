# Denver

##Postgres Configuration
- Use postgres version 9.4.11. Download from here: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- set password for postgres user to "password"
- create database "Denver" using pgAdmin III (comes with postgres installation)


##Starting the Docker Application
- Open your Docker Quickstart Terminal
- Navigate to /Docker/Dockerjava
- execute: dos2unix entrypoint.sh (needed for windows systems)
- Naviagte to /Docker
- execute: docker-compose up --build
- type: https://192.168.99.100:8081 in browser
- Login: 
- Docent: username: "system"  password: "password"
- Student: username: "Peter Student C1"  password: "password"
