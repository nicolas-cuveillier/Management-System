FROM openjdk:11

ENV JAVA_HOME /opt/jdk

ENV PATH ${PATH}:${JAVA_HOME}/bin  

RUN mkdir /app

COPY ./out/artifacts/ManagementSystem_jar/ManagementSystem.jar /app/ManagementSystem.jar

WORKDIR /app

CMD "java" "-jar" "ManagementSystem.jar"
