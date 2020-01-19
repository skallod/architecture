FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

RUN git clone --single-branch --branch 02-notification-sidecar https://github.com/skallod/architecture
#WORKDIR architecture
#RUN mvn install

#WORKDIR target
#RUN mv *.jar app.jar

#ADD pom.xml $PROJECT_DIR
#RUN mvn dependency:resolve

#ADD src/ $PROJECT_DIR/src
#RUN mvn install -DskipTests
#FROM openjdk:8-jdk

#ENV PROJECT_DIR=/opt/project
#RUN mkdir -p $PROJECT_DIR
#WORKDIR $PROJECT_DIR

#local jar
#ADD target/notification-sidecar-1.0.jar $PROJECT_DIR

#COPY --from=0 $PROJECT_DIR/target/notification-sidecar-1.0.jar $PROJECT_DIR/

#CMD ["java", "-Dspring.profiles.active=container", "-jar", "/opt/project/notification-sidecar-1.0.jar"]
