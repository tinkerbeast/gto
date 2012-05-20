gto
===
An inter-programming-language performance benchmark based on the V8 benchmark suite

> This project is still in it's very early stages. Please bear with it until things stabilise.

> Also please feel free to contribute, suggest or give feedback about the project


### About
* * * * * * * * * * * * * * *
For developers who want to compare the relative performance of different 
languages / runtime environments **gto** is a benchmarking tool that is 
application oriented and written in multiple languages. Unlike most benchmarking suites
which focus on generic performance of the native platform, this tool compares the 
application specific performances on the native platform as well as different runtimes.

### License
* * * * * * * * * * * * * * *
This project is licensed under the GNU GPL version 3 or greater.

### Usage
* * * * * * * * * * * * * * *
To get the source

    $ git clone git://github.com/tinkerbeast/gto.git

To build the test suites

    $ cd gto
    gto$ make

To run the tests for a given language

    gto$ <shell> src/<language>/benchmark/Main

For example, to run the Java tests

    gto$ java src/java/benchmark/Main



