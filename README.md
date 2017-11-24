Custom fingerprint calculation in MadFast CLI release 0.2.3
===========================================================

This projects contains a code/build example for a custom fingerprint calculation integration into MadFast version 0.2.3. Please note
that in future release(s) the method of such integration might be changed. Please note that these example codes use beta or 
non-public APIs.

Feel free to contact us at 
[disco-support@chemaxon.com](mailto:disco-support@chemaxon.com?subject=Question%20regarding%20https://github.com/ChemAxon/madfast-custom-fp-calculation-0.2.3)
with any further questions.



Prerequisites
-------------

 - madfast-cli-0.2.3 should be unpacked and available in next to this project (in `../madfast-cli-0.2.3`)
 - MadFast prerequisites (Java, ChemAxon licenses, etc) must be satisfied 
   (for details see [MadFast Getting started guide](https://disco.chemaxon.com/products/madfast/latest/doc/getting-started-guide.html)).
 - For building internet access is needed for the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) to
   download the used Gradle distribution.


Try it out
----------

Launch `run.sh` from the project root. After successfull execution the embedded server of the WebUI will listen on port 8081 and
a browser will be opened to location <http://localhost:8081>.


Licensing
---------

**This project** is distributed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
Dependencies of this project are **ChemAxon proprietary products which are not covered by this license**. Please
note that redistribution of ChemAxon proprietary products is not allowed.
