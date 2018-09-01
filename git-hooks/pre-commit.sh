#!/bin/bash

gradle build

if [[ $? != 0 ]]; then
  echo "\n\n Gradle build failed, operation denied!"
fi
