#
# Build stage:
#

FROM clojure:tools-deps-bullseye-slim AS build

WORKDIR /workspace

COPY ./deps.edn ./
RUN \
  clojure -P            && \
  clojure -A:build -P

COPY ./src/build ./src/build
RUN \
  clojure -T:build copy-deps

COPY ./src/main ./src/main
RUN \
  clojure -T:build build-jar

#
# Minimal JRE:
#

FROM eclipse-temurin:23-jdk AS jre

WORKDIR /workspace

RUN \
  jlink --add-modules java.base          \
        --strip-debug                    \
        --strip-java-debug-attributes    \
        --no-man-pages                   \
        --no-header-files                \
        --vm=server                      \
        --compress=zip-6                 \
        --output /workspace/jre

#
# Distribution image:
#

FROM gcr.io/distroless/base-nossl:nonroot AS dist

WORKDIR /workspace

COPY --from=jre    /workspace/jre                  ./jre
COPY --from=build  /workspace/target/libs          ./libs
COPY --from=build  /workspace/target/service.jar   ./service.jar


ENV JAVA_HOME="/workspace/jre"
ENV CLASSPATH="./service.jar:./libs/*"

ENV HOST=0.0.0.0
ENV PORT=8080

ENTRYPOINT ["/workspace/jre/bin/java"]
CMD ["clojure.main", "-m", "example.service"]
