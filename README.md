# MySQL Docker Image Creator

This project creates docker images, by backing up a database and seeding a MysqlDocker image with the backup.


## Runnig an image created with this project
To run the image the best way is to create a Docker compose file like:
```yaml
version: "3.9"
services:
  db:
    image: ghcr.io/bluesagesolutions/mysql-5.7-bluesage:latest
    command: ['mysqld', '--sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES']
    environment:
        MYSQL_DATABASE: 'bluesage_dev'
        MYSQL_ROOT_PASSWORD: 'secret-pw'
        MYSQL_ROOT_HOST: '%'
        MYSQL_USER: 'lp_user'
        MYSQL_PASSWORD: 'lp_user'
    ports:
      - '3306:3306'
    expose:
      - '3306'
    volumes:
      - '~/.mysql-data-bluesage/db:/var/lib/mysql'
```

This will get the image, blue sage for example, from our GitHub packages repository, with the database name bluesage_dev, with a root user
having the password secret-pw, an lp_user user with the password lp_user, on port 3606 exposed in the to:from format, and mounts that 
data on a volume, on your local file system. 

On first run the Docker image will initialize and load the database, which takes about 1.5 minutes. After it will not run the initialization
again, even if you bring the image down and bring it up again, unless you change the volume directory(~/.mysql-data-bluesage/db) or delete that folder.

If you have multiple db images you want to run then you should probably make multiple compose files, pointing to different directories
for their volumes.

To run these compose files either run the following to run:
```bash
docker-compose -f ~/dockerCompose/mysql-5.7.yml up -d
```

Then to stop run:
```bash
docker-compose -f ~/dockerCompose/mysql-5.7.yml down
```

Or create and use aliases in your .bashrc/.bash_profile:
```bash 
alias mysql='docker-compose -f ~/g5/dockerCompose/mysql-5.7.yml up -d'
alias kmysql='docker-compose -f ~/g5/dockerCompose/mysql-5.7.yml down'
```

## Fixing Column_Statistics error
If you have a newer version of `mysqldump` you may see this error:
```shell
mysqldump: Couldn't execute 'SELECT COLUMN_NAME,                       JSON_EXTRACT(HISTOGRAM, '$."number-of-buckets-specified"')                FROM information_schema.COLUMN_STATISTICS                WHERE SCHEMA_NAME = 'bluesage_dev' AND TABLE_NAME = 'address';': Unknown table 'COLUMN_STATISTICS' in information_schema (1109)
```
To fix that we have a variable for extraFlags set that to this:
```shell
-PextraFlags=--column-statistics=0
```