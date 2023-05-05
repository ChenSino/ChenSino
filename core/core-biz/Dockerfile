FROM openjdk:11.0.16-jre

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /core-biz

WORKDIR /core-biz

EXPOSE 8888

ADD ./target/core-biz-0.0.1-SNAPSHOT-exec.jar ./

CMD sleep 10;java $JAVA_OPTS -jar core-biz-0.0.1-SNAPSHOT-exec.jar
