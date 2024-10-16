#
# Build phase:
#

FROM clojure:tools-deps-bullseye-slim AS build

WORKDIR /workspace

COPY ./deps.edn ./
COPY ./src/     ./src/

RUN clojure -P
RUN clojure -T:build build-uber

#
# Distribution image:
#

FROM eclipse-temurin:23-jdk AS dist

COPY --from=build  /workspace/target/service.jar   ./service.jar

ENV HOST=0.0.0.0
ENV PORT=8080

CMD ["java", "-jar", "./service.jar"]
