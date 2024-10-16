FROM clojure:tools-deps-bullseye-slim

WORKDIR /workspace

COPY ./deps.edn ./
COPY ./src/     ./src/

RUN clojure -P

CMD ["clojure", "-X", "example.service/main"]
