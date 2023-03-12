
FROM maven:3.6.0-jdk-8-alpine AS jira-mm-bot

ADD pom.xml /
RUN mvn verify clean
ADD . /
RUN mvn package

FROM openjdk:17
WORKDIR /root/
EXPOSE 8081
ENV BOT_HOST http://localhost:8081
ENV BOT_PASSWORD wannacry1337
ENV BOT_USERNAME le30r
ENV MM_TOKEN t5kypzoumt8zxxturscdeq1nxo
COPY --from=0 /target/*-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar","./app.jar", "-port=8081"]