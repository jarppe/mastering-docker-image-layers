# Small example for Even smaller and safer Clojure containers

This repository has the examples for blog [Even smaller docker images](https://metosin.fi/blog/xxxxx)

## Step 1 - Good start

```bash
docker build --tag example/service:step-1 ./step-1--good-start
```

```bash
docker run --rm -p 127.0.0.1:8080:8080 example/service:step-1
```

```bash
docker run --rm --network=none example/service:step-1
```

## Step 2 - Pull dependencies to image

```bash
docker build --tag example/service:step-2 ./step-2--pull-dependencies-to-image
docker run --rm --network=none example/service:step-2
docker run --rm -p 127.0.0.1:8080:8080 example/service:step-2
```

Take a look at the layers:

```bash
bb explain-image example/service:step-1
bb explain-image example/service:step-2
```

```bash
$ docker image ls example/service
REPOSITORY        TAG       IMAGE ID       CREATED          SIZE
example/service   step-1    170bae194115   14 minutes ago   789MB
example/service   step-2    d332b6b45c12   5 minutes ago    795MB
```

## Step 3 - AOT and smaller distributable image

```bash
docker build --tag example/service:step-3 step-3--aot-and-smaller-distributable-image
docker run --rm -p 127.0.0.1:8080:8080 example/service:step-3
```

```bash
$ docker image ls example/service
REPOSITORY        TAG       IMAGE ID       CREATED          SIZE
example/service   step-1    170bae194115   28 minutes ago   789MB
example/service   step-2    d332b6b45c12   19 minutes ago   795MB
example/service   step-3    d0fdf579ecd6   2 minutes ago    715MB
```

Savings 80M

```bash
bb explain-image example/service:step-3
```

## Step 4 - Distroless

```bash
docker build --tag example/service:step-4 step-4--distroless
docker run --rm -p 127.0.0.1:8080:8080 example/service:step-4
```

```bash
$ docker image ls example/service
REPOSITORY        TAG       IMAGE ID       CREATED          SIZE
example/service   step-1    170bae194115   37 minutes ago   789MB
example/service   step-2    d332b6b45c12   29 minutes ago   795MB
example/service   step-3    d0fdf579ecd6   11 minutes ago   715MB
example/service   step-4    c95abeaf2d87   28 seconds ago   111MB
```

Savings 684M

```bash
bb explain-image example/service:step-4
```

## Step 5 - Separate layers for deps

```bash
docker build --tag example/service:step-5 step-5--separate-layers-for-deps
docker run --rm -p 127.0.0.1:8080:8080 example/service:step-5
```

```
--------------------------------------------------------------------------------
created: 2024/10/16 14:23:04
command: COPY /workspace/target/libs ./libs # buildkit
   size: 6 MB
 digest: sha256:acde5ca9454c1bd1ce810d8e1a1dee8edf9d418673cbfb316c8943111b073acb

 workspace/                                   0 B
 workspace/libs/                              0 B
 workspace/libs/clojure-1.12.0.jar            4 MB
 ...
--------------------------------------------------------------------------------
created: 2024/10/16 14:23:04
command: COPY /workspace/target/service.jar ./service.jar # buildkit
   size: 800 B
 digest: sha256:aa992bcadd34d6215c67ef9fe41fbcef400d1546f5c7c5458985a5973c297eb9

 workspace/                                   0 B
 workspace/service.jar                      869 B
--------------------------------------------------------------------------------
```

Make change to service.clj

```
--------------------------------------------------------------------------------
created: 2024/10/16 14:23:04
command: COPY /workspace/target/libs ./libs # buildkit
   size: 6 MB
 digest: sha256:acde5ca9454c1bd1ce810d8e1a1dee8edf9d418673cbfb316c8943111b073acb
 workspace/                                   0 B
 workspace/libs/                              0 B
 workspace/libs/clojure-1.12.0.jar            4 MB
 ...
--------------------------------------------------------------------------------
created: 2024/10/16 14:27:15
command: COPY /workspace/target/service.jar ./service.jar # buildkit
   size: 802 B
 digest: sha256:159f6170b71091dbbaf011176af0cd97970b7756419603b4537e69bafe64c22d

 workspace/                                   0 B
 workspace/service.jar                      873 B
--------------------------------------------------------------------------------
```

## Security check

```bash
for i in {1..5}; do
  docker scout quickview example/service:step-$i
done
```

For step-1:

```
  Target             │  example/service:step-1            │    0C     2H     0M    67L
    digest           │  170bae194115                      │
  Base image         │  clojure:temurin-21-bullseye-slim  │    0C     2H     0M    67L
  Updated base image │  clojure:temurin-23-bullseye-slim  │    0C     2H     0M    67L
```

For step-5:

```
  Target     │  example/service:step-5     │    0C     0H     0M     0L
    digest   │  d26638fc894e               │
  Base image │  distroless/static:nonroot  │    0C     0H     0M     0L
```
