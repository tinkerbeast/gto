/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark;

import benchmark.base.*;
import benchmark.splay.SplayRunner;

/**
 *
 * @author rishin.goswami
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int iterations = Integer.parseInt(args[0]);
        for (int i = 0; i < iterations; i++) {
            BenchMark bm = new SplayRunner();
            bm.Setup();
            bm.Run();
            bm.TearDown();
        }
    }
}
