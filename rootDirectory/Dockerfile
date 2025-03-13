FROM amazoncorretto:11-alpine3.18 AS build

WORKDIR /app

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build
RUN rm -rf build/libs/*-plain.jar

FROM amazoncorretto:11-alpine3.18 AS production

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/server.jar

ENTRYPOINT java -jar server.jar