#!/bin/bash

mkdir -p tmp/

./gradlew clean extendedBins

build/bin/buildStorage.sh \
    -in data/a.smi \
    -out tmp/a-myfp.bin \
    -contextjs "ctx_from_desc(new Packages.myfp.MyFpGenerator())"

build/bin/createMms.sh \
    -in data/a.smi \
    -out tmp/a-mms.bin \
    -name tmp/a-name.bin

build/bin/dumpStorage.sh -in tmp/a-myfp.bin 

build/bin/gui.sh \
    -mols -name:a:-mms:tmp/a-mms.bin:-mid:tmp/a-name.bin \
    -desc -desc:tmp/a-myfp.bin:-mols:a:-name:a-myfp 