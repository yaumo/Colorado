# Denver

##Postgres Configuration
- Use postgres version 9.4.11. Download from here: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- set password for postgres user to "password"
- create database "denver" using pgAdmin III (comes with postgres installation)

## local nginx for testimg
- Download nginx for Windows: http://nginx.org/en/download.html
- follow steps: http://nginx.org/en/docs/windows.html
- Navigate to YourNginxFolder/conf/nginx.conf
- Add below the default server setting:

server {
  listen       8181;
  server_name  localhost;



    location / {
	 if ($request_method = OPTIONS ) {
        add_header Access-Control-Allow-Origin "http://localhost:8081";
        add_header Access-Control-Allow-Methods "GET, POST, PATCH, OPTIONS";
        add_header Access-Control-Allow-Headers "Authorization";
        add_header Access-Control-Allow-Credentials "true";
        add_header Content-Length 0;
        add_header Content-Type text/plain;
        return 200;
    }
	
	
	
  proxy_pass http://localhost:8080/;
        proxy_redirect     off;
      proxy_set_header   Host $host;
      proxy_set_header   X-Real-IP $remote_addr;
      proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
      proxy_set_header   X-Forwarded-Host $server_name;
}

}

     - nginx.exe -s reload

     - Every connection to localhost:8181/frontend is now mapped to the frontend and every connection to localhost:8181/backend to the backend

##Starting the Docker Application
- Open your Docker Quickstart Terminal
