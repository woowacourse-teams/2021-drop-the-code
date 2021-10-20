FROM openjdk:8-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN wget https://github.com/pinpoint-apm/pinpoint/releases/download/v2.2.2/pinpoint-agent-2.2.2.tar.gz
RUN tar -zxvf pinpoint-agent-2.2.2.tar.gz

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
