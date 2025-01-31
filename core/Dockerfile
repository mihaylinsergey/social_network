FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY build/libs/core.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar \
--spring.profiles.active=${SPRING_PROFILES_ACTIVE} \
--db.name=${DB_NAME} \
--db.m.server=${DB_M_SERVER} \
--db.m.port=${DB_M_PORT} \
--db.m.user=${DB_M_USER} \
--db.m.pass=${DB_M_PASS} \
--db.s.server=${DB_S_SERVER} \
--db.s.port=${DB_S_PORT} \
--db.s.user=${DB_S_USER} \
--db.s.pass=${DB_S_PASS} \
--server.port=${PORT} \
--spring.rabbitmq.host=${RABBIT_MQ} \
--spring.rabbitmq.port=${RABBIT_PORT} \
--spring.rabbitmq.username=${RABBIT_USER} \
--spring.rabbitmq.password=${RABBIT_PASSWORD}"]
#EXPOSE 8080