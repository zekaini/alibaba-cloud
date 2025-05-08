FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /topway-xxl-job-admin

WORKDIR /topway-xxl-job-admin

EXPOSE 9080

ADD ./target/topway-xxl-job-admin.jar ./

CMD sleep 120;java $JAVA_OPTS -jar topway-xxl-job-admin.jar
