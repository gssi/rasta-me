FROM maven:3.9.9-eclipse-temurin-21
RUN apt-get update
RUN apt-get install -y locales locales-all
ENV LC_ALL it_IT.UTF-8
ENV LANG it_IT.UTF-8
ENV LANGUAGE it_IT.UTF-8

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 9090
ENTRYPOINT ["java","-jar","/home/app/target/rasta-me.jar", "--spring.profiles.active=prod"]

