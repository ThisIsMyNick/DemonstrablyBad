DemonstrablyBad
---------------

DemonstrablyBad is a project that attempts to translate text found in images.

By Nobel Gautam and James Wang.

#### Dependencies
On Ubuntu 14.04 or later, required dependencies for the command-line program can be installed via:

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

#### Android App
We also have an Android implementation of this program. If you're cool and have an Android phone
(version 4.1+ please), then installing and running the app is as simple as installing the [apk](https://raw.githubusercontent.com/ThisIsMyNick/DemonstrablyBad/master/app.apk).
If you don't have an Android phone, then you can download an emulator and run the app on there.
The source for the Android app is in the `Translate` folder.
