# quarkus-blog

```shell
docker run -d \
   --name db-for-quarkus-blog \
   -e POSTGRES_USER=blog \
   -e POSTGRES_PASSWORD=123456 \
   -e POSTGRES_DB=blog \
   -e PGDATA=/var/lib/postgresql/data/pgdata \
   -e TZ=Asia/Shanghai \
   -e PGTZ=Asia/Shanghai \
   -e LANG=en_US.UTF-8 \
   -p 54321:5432 \
   postgres:16
```
