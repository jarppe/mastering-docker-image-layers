FROM clojure:tools-deps-bullseye-slim

WORKDIR /workspace

COPY ./deps.edn ./
RUN  clojure -P
COPY ./src/     ./src/

ENV HOST=0.0.0.0
ENV PORT=8080

CMD ["clojure", "-X", "example.service/-main"]
