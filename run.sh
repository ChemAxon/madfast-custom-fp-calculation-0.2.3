#!/bin/bash
#
# Tasks:
#  - Launch compilation 
#  - Use the custom fingerprint calculation from this project to calculate fingerprints
#  - Launch the modified MadFast WebUI to expose the calculated fingerprints

# Directory for generated binary files
mkdir -p tmp/

# Launch compilation and modified launcher script creation
./gradlew clean extendedBins

# Calculate fingerpints
#
# For description of the buildStorage script see https://disco.chemaxon.com/products/madfast/latest/doc/basic-search-workflow.html
# For the description of -contextjs <JS> hook see https://disco.chemaxon.com/products/madfast/latest/doc/concepts-overlap-analysis-context.html
build/bin/buildStorage.sh \
    -in data/a.smi \
    -out tmp/a-myfp.bin \
    -contextjs "ctx_from_desc(new Packages.myfp.MyFpGenerator())"

# Import molecule file/molecule IDs to binary storages to be used by the WebUI
# For description of the createMms script see https://disco.chemaxon.com/products/madfast/latest/doc/basic-search-workflow.html
build/bin/createMms.sh \
    -in data/a.smi \
    -out tmp/a-mms.bin \
    -name tmp/a-name.bin

# Read the fingerprint file and print the contents to the console
build/bin/dumpStorage.sh -in tmp/a-myfp.bin 

# Launch WebUI
# For description of the parametrization see https://disco.chemaxon.com/products/madfast/latest/doc/rest-api-example.html
build/bin/gui.sh \
    -mols -name:a:-mms:tmp/a-mms.bin:-mid:tmp/a-name.bin \
    -desc -desc:tmp/a-myfp.bin:-mols:a:-name:a-myfp 