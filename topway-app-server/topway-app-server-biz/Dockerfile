FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /topway-app-server

WORKDIR /topway-app-server

EXPOSE 7060

ADD ./target/topway-app-server-biz.jar ./

CMD sleep 60;java $JAVA_OPTS -jar topway-app-server-biz.jar
