DemonstrablyBad
---------------

DemonstrablyBad is a project that attempts to translate text found in images.

By Nobel Gautam and James Wang.

#### Dependencies
On Ubuntu 14.04 or later, required dependencies can be installed via:

`sudo apt-get install tesseract-ocr`

#### Running the project
To compile:

`make all`

To run:

`./run.sh --img=path/to/image [--to=<target>] [--from=<source>]`

Or, to transcribe but not translate:

`./run.sh --img=path/to/image --tr`

Or, to translate interactively, without an image:

`./run.sh --shell [--to=<target>] [--from=<source>]`
