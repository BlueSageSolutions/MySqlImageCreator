FROM  mysql/mysql-server:5.7

COPY 0_initial_users.sql /docker-entrypoint-initdb.d/0_initial_users.sql
COPY 1_initial_schema.sql /docker-entrypoint-initdb.d/1_initial_schema.sql
COPY 2_initial_data.sql /docker-entrypoint-initdb.d/2_initial_data.sql
