FROM openjdk:8-alpine

COPY target/uberjar/crash10x.jar /crash10x/app.jar

EXPOSE 3001

CMD ["java", "-jar", "/crash10x/app.jar"]
