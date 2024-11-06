#
# Build stage:
#

FROM clojure:tools-deps-bullseye-slim AS build

WORKDIR /workspace

COPY ./deps.edn ./
RUN  clojure -P
COPY ./src/     ./src/

RUN clojure -T:build build-uber

#
# Distribution image:
#

FROM eclipse-temurin:23-jre AS dist

COPY --from=build  /workspace/target/service.jar   ./service.jar

ENV CLASSPATH="./service.jar"

ENV HOST=0.0.0.0
ENV PORT=8080

ENTRYPOINT ["java"]
CMD ["clojure.main", "-m", "example.service"]
