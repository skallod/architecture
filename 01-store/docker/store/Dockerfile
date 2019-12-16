#FROM maven:3.5.4-jdk-8
#
#ENV PROJECT_DIR=/opt/project
#RUN mkdir -p $PROJECT_DIR
#WORKDIR $PROJECT_DIR
#
#ADD pom.xml $PROJECT_DIR
#RUN mvn dependency:resolve
#
#ADD src/ $PROJECT_DIR/src
#RUN mvn install -DskipTests

FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

#local jar
ADD target/store-0.0.1-SNAPSHOT.jar $PROJECT_DIR

#COPY --from=0 $PROJECT_DIR/target/store-0.0.1-SNAPSHOT.jar $PROJECT_DIR/

#EXPOSE 18181

CMD ["java", "-Dspring.profiles.active=container", "-jar", "/opt/project/store-0.0.1-SNAPSHOT.jar"]

