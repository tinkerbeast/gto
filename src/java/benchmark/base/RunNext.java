/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package benchmark.base;

import java.lang.reflect.*;

/**
 *
 * @author rishin.goswami
 */
public class RunNext<T> {
/*
  T[] results;
  Runner runner;
  int length;
  int index;
  BenchMarkSuite suite;
  Method data;
  Method method_RunNextSetup;
  Method method_RunNextBenchmark;
  Method method_RunNextTearDown;

    public RunNext(T[] results, Runner runner, int length, BenchMarkSuite suite) throws NoSuchMethodException {
        this.results = results;
        this.runner = runner;
        this.length = length;
        this.index = 0;
        this.suite = suite;
        this.method_RunNextSetup = benchmark.base.RunNext.class.getMethod("RunNextSetup", null);
        this.method_RunNextBenchmark = benchmark.base.RunNext.class.getMethod("RunNextBenchmark", null);
        this.method_RunNextBenchmark = benchmark.base.RunNext.class.getMethod("RunNextTearDown", null);
    }



  // Run the setup, the actual benchmark, and the tear down in three
  // separate steps to allow the framework to yield between any of the
  // steps.

  Method RunNextSetup() {
    if (index < length) {
      try {
        suite.benchmarks[index].Setup();
      } catch (Exception e) {
        suite.NotifyError(e);
        return null;
      }
     
      return method_RunNextBenchmark;
    }
    suite.NotifyResult();
    return null;
  }

  Method RunNextBenchmark() {
    try {
      data = suite.RunSingleBenchmark(suite.benchmarks[index], data);
    } catch (Exception e) {
      suite.NotifyError(e);
      return null;
    }
    // If data is null, we're done with this benchmark.
    return (data == null) ? method_RunNextTearDown : RunNextBenchmark();
  }

  Method RunNextTearDown() {
    try {
      suite.benchmarks[index++].TearDown();
    } catch (Exception e) {
      suite.NotifyError(e);
      return null;
    }
    return method_RunNextSetup;
  }
*/
}
