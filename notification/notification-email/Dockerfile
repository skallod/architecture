FROM maven:3.5.4-jdk-8
#
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
#
RUN git clone --single-branch --branch 02-notification-sidecar https://github.com/skallod/architecture \
&& cd architecture && mvn -DskipTests install \
&& cd notification/notification-email/target && mv *.jar app.jar

FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

##local jar
#ADD target/notification-sms-1.0.jar $PROJECT_DIR
#RUN mv *.jar app.jar && ls

COPY --from=0 $PROJECT_DIR/architecture/notification/notification-email/target/app.jar $PROJECT_DIR/

CMD ["java", "-Dspring.profiles.active=container", "-jar", "/opt/project/app.jar"]
