#!/bin/bash
set -e

cd "$(dirname "$0")"

mvn compile

sudo docker run -it -u ${UID} \
  -v ${PWD}:/evosuite \
  evosuite/evosuite:1.2.0-java-11 \
  -target target/classes \
  -projectCP target/classes
