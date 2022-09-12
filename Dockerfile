FROM  mysql/mysql-server:5.7

COPY *.sql /docker-entrypoint-initdb.d/