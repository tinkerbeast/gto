gto
===
A inter-programming-language performance benchmark based on the V8 benchmark suite

> This project is still in it's very early stages. Please bear with it until things stabilise.

> Also please feel free to contribute, suggest or give feedback about the project


### About
* * * * * * * * * * * * * * *
With programming languages becoming more and more 'high-level', estimating the performance of a block of code is no longer very straight-forward. **gto** is performance benchmark based on application specific tests rather than generic performance test. The tests are based on the [V8 benchmark suite](http://v8.googlecode.com/svn/data/benchmarks/v7/run.html).

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



