DemonstrablyBad
---------------

DemonstrablyBad is a project that attempts to translate text found in images.

By Nobel Gautam and James Wang.

#### Dependencies
On Ubuntu 14.04 or later, dependencies can be installed via:

`sudo apt-get install openjdk-7-jdk openjdk-7-jre junit4 tesseract-ocr`

#### Running the project
To compile:

`make all`

To run:

`make run img=path/to/image [to=<target>] [from=<source>]`

#### Tests
This project uses [JUnit](http://junit.org/) as a testing suite, which makes it very easy to
create and run tests to ensure consistent code results.

To run the tests, simply run:
```
$ make tests
$ make run_tests
```
