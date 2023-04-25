FROM openjdk:11.0.11-jre-slim
MAINTAINER face-search
WORKDIR /app/face-search

#RUN sed -i 's/deb.debian.org/mirrors.aliyun.com/g' /etc/apt/sources.list && \
#apk update && apk add libssl1.0 libx11 libxext libxrender libstdc++ freetype fontconfig

COPY scripts/docker/entrypoint.sh /app/face-search
RUN chmod +x /app/face-search/entrypoint.sh

COPY face-search-core/src/main/resources/model /app/face-search/model

COPY face-search-server/target/face-search-server.jar /app/face-search/face-search-server.jar

EXPOSE 8080
ENTRYPOINT ["/app/face-search/entrypoint.sh"]
