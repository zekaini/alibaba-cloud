FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /topway-auth

WORKDIR /topway-auth

EXPOSE 3000

ADD ./target/topway-auth.jar ./

CMD sleep 120;java $JAVA_OPTS -jar topway-auth.jar
