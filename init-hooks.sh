#!/bin/bash

rm .git/hooks/* # clean old content of hooks
cp git-hooks/* .git/hooks # copy new hooks over to the folder
chmod +x .git/hooks/* # make the scripts executable
