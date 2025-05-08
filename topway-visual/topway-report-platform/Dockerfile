FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /topway-report-platform

WORKDIR /topway-report-platform

EXPOSE 9095

ADD ./target/topway-report-platform.jar ./

CMD sleep 180;java $JAVA_OPTS -jar topway-report-platform.jar
