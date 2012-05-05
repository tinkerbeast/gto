/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.base;

import java.util.*;
import java.util.ArrayList;
import java.lang.reflect.*;

/**
 *
 * @author rishin.goswami
 */
public class BenchMarkSuite {
/*
    public static final String version = "7";

    String name;

    int reference;

    BenchMark[] benchmarks;

    List<BenchMarkSuite> suites;

    List<Float> scores;

    T[] results;
  Runner runner;

    public BenchMarkSuite(String name, int reference, BenchMark[] benchmarks) {
        this.name = name;
        this.reference = reference;
        this.benchmarks = benchmarks;
        this.suites = new ArrayList<BenchMarkSuite>();
        //
        // error: should be safe since I'm doing it at the end
        this.suites.add(this);
    }

    //
     // Counts the total number of registered benchmarks. Useful for
     // showing progress as a percentage.
     //
    int CountBenchmarks() {
        int result = 0;
        for (BenchMarkSuite suite : suites) {
            result += suite.benchmarks.length;
        }
        return result;
    }

BenchmarkSuite.prototype.RunSingleBenchmark(Benchmark benchmark, Method data) {
  function Measure(data) {
    var elapsed = 0;
    var start = new Date();
    for (var n = 0; elapsed < 1000; n++) {
      benchmark.run();
      elapsed = new Date() - start;
    }
    if (data != null) {
      data.runs += n;
      data.elapsed += elapsed;
    }
  }

  if (data == null) {
    // Measure the benchmark once for warm up and throw the result
    // away. Return a fresh data object.
    Measure(null);
    return { runs: 0, elapsed: 0 };
  } else {
    Measure(data);
    // If we've run too few iterations, we continue for another second.
    if (data.runs < 32) return data;
    var usec = (data.elapsed * 1000) / data.runs;
    this.NotifyStep(new BenchmarkResult(benchmark, usec));
    return null;
  }
}

// This function starts running a suite, but stops between each
// individual benchmark in the suite and returns a continuation
// function which can be invoked to run the next benchmark. Once the
// last benchmark has been executed, null is returned.
 //
Method RunStep(Runner runner) {
  this.results = new T[];
  this.runner = runner;
  
  RunNext<T> runNext = null;
  try {
       runNext =   new RunNext(results, runner, this.benchmarks.length, this);
       runNext.RunNextSetup()
    } catch(NoSuchMethodException e) {
        this.NotifyError(e);
    }

  return null;
}



      void RunSuites(Runner runner) {
  boolean continuation = false;

  int length = suites.size();
  scores = new ArrayList<Integer>();
  int index = 0;

  function RunStep() {
    while (continuation || index < length) {
      if (continuation) {
        // ***********************************************************************
        // NOTE: Deviated from original, please look at the original function
        //continuation = continuation();
          throw new IllegalStateException("continuation = continuation();");
      } else {
        BenchMarkSuite suite = suites[index++];
        try {
            runner.NotifyStart(suite.name);
        } finally {
          continuation = suite.RunStep(runner);
        }
      }
      // ***********************************************************************
        // NOTE: Deviated from original, please look at the original function
      // NOTE: Commented because 'window' is never defined outside a browser envoironment      
      if (continuation && typeof window != 'undefined' && window.setTimeout) {
        window.setTimeout(RunStep, 25);
        return;
      }
    }
    if (runner.NotifyScore) {
      var score = BenchmarkSuite.GeometricMean(BenchmarkSuite.scores);
      var formatted = BenchmarkSuite.FormatScore(100 * score);
      runner.NotifyScore(formatted);
    }
  }
  RunStep();
}
*/
}
