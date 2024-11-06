#!/usr/bin/env bash

docker run --rm --init -v /var/run/docker.sock:/var/run/docker.sock example/explain-layers $1
