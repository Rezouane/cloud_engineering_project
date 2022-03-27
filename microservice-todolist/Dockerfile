FROM openjdk:11-jre-slim
ADD service/target/todolist.jar app.jar
EXPOSE 8282
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=production -jar /app.jar" ]