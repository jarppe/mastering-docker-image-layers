FROM clojure:tools-deps-bullseye-slim

WORKDIR /workspace

COPY ./deps.edn ./
COPY ./src/     ./src/

CMD ["clojure", "-X", "example.service/main"]
