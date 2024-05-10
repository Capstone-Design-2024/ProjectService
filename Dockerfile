FROM openjdk:17

ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} projectService.jar

EXPOSE 9002

ENTRYPOINT ["java","-jar","memberService.jar"]