FROM openjdk:11-jre-slim
ADD registry/target/registry.jar app.jar
EXPOSE 8761
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=production -jar /app.jar" ]