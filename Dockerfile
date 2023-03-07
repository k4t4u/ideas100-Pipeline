FROM openjdk:19-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/ideas100.jar ideas100.jar
ENTRYPOINT ["java","-jar","ideas100.jar"]
EXPOSE 8080