# config nginx
server {

        root /var/www/canh-labs.com/html;
        index index.html index.htm index.nginx-debian.html;
        listen 443 ssl;
        server_name *.canh-labs.com;
        ssl_certificate /etc/letsencrypt/live/canh-labs.com/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/canh-labs.com/privkey.pem;
        include /etc/letsencrypt/options-ssl-nginx.conf;
        ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;
        add_header Content-Security-Policy "upgrade-insecure-requests;";


        error_page 404 /error_404.html;
        location = /error_404.html {
                root /usr/share/nginx/html;

                # internal;


        }
        location /error/404 {
                root /var/www;
                index index.html;
                # try_files $uri $uri/
        }



        location /api/ {
             proxy_pass http://localhost:8081/;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
             proxy_set_header   X-Real-IP          $remote_addr;

             proxy_http_version 1.1;
             proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection 'upgrade';
             proxy_set_header Host $host;
             proxy_cache_bypass $http_upgrade;

        }
        location /r {
             proxy_pass http://localhost:8081/r;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
             proxy_set_header   X-Real-IP          $remote_addr;

             proxy_http_version 1.1;
             proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection 'upgrade';
             proxy_set_header Host $host;
             proxy_cache_bypass $http_upgrade;

        }
        location /api-test/ {
             proxy_pass http://localhost:8091/;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
             proxy_set_header   X-Real-IP          $remote_addr;

             proxy_http_version 1.1;
             proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection 'upgrade';
             proxy_set_header Host $host;
             proxy_cache_bypass $http_upgrade;

        }
        location /t/r {
             proxy_pass http://localhost:8091/r;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
             proxy_set_header   X-Real-IP          $remote_addr;

             proxy_http_version 1.1;
             proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection 'upgrade';
             proxy_set_header Host $host;
             proxy_cache_bypass $http_upgrade;

        }

}
server {
        listen 443 ssl;
        root /var/www/me.canh-labs.com;
        index index.html index.htm index.nginx-debian.html;

        server_name me.canh-labs.com www.me.canh-labs.com;

        location / {
                try_files $uri $uri/ =404;
        }

}

server {
        listen 443 ssl;
        root /var/www/me.canh-labs.com;
        index index.html index.htm index.nginx-debian.html;


        server_name monitor.canh-labs.com www.monitor.canh-labs.com;
        add_header Content-Security-Policy "upgrade-insecure-requests;";
        location / {
             proxy_pass http://localhost:8084/;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;

             proxy_http_version 1.1;
             proxy_set_header Upgrade $http_upgrade;
             proxy_set_header Connection 'upgrade';
             proxy_set_header Host $host;
             proxy_cache_bypass $http_upgrade;

        }
}
server {
  listen 80;
  listen [::]:80;
  server_name *.canh-labs.com www.canh-labs.com;
  return 301 https://$host$request_uri;

}
