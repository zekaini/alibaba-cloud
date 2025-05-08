FROM mysql/mysql-server:8.0.32

MAINTAINER lengleng(wangiegie@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./1schema.sql /docker-entrypoint-initdb.d

COPY ./2topway.sql /docker-entrypoint-initdb.d

COPY ./3topway_ac.sql /docker-entrypoint-initdb.d

COPY ./4topway_job.sql /docker-entrypoint-initdb.d

COPY ./5topway_mp.sql /docker-entrypoint-initdb.d

COPY ./6topway_config.sql /docker-entrypoint-initdb.d

COPY ./7topway_pay.sql /docker-entrypoint-initdb.d

COPY ./8topway_codegen.sql /docker-entrypoint-initdb.d

COPY ./99topway_bi.sql /docker-entrypoint-initdb.d

COPY ./999topway_app.sql /docker-entrypoint-initdb.d
