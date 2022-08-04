FROM openjdk:11-jre-slim

ADD product-0.0.1-SNAPSHOT.jar app.jar

ENV MAVEN_HOST=https://repo1.maven.org/maven2 \
    ALPINE_HOST=dl-cdn.alpinelinux.org \
    MIRROR_MAVEN_HOST=https://maven.aliyun.com/repository/public \
    MIRROR_ALPINE_HOST=mirrors.aliyun.com

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
