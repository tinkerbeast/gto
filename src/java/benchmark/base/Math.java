/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.base;

/**
 *
 * @author rishin.goswami
 */
public class Math {

    private static int seed = 49734321;

    public static double random() {
        // Robert Jenkins' 32 bit integer hash function.
        seed = ((seed + 0x7ed55d16) + (seed << 12)) & 0xffffffff;
        seed = ((seed ^ 0xc761c23c) ^ (seed >>> 19)) & 0xffffffff;
        seed = ((seed + 0x165667b1) + (seed << 5)) & 0xffffffff;
        seed = ((seed + 0xd3a2646c) ^ (seed << 9)) & 0xffffffff;
        seed = ((seed + 0xfd7046c5) + (seed << 3)) & 0xffffffff;
        seed = ((seed ^ 0xb55a4f09) ^ (seed >>> 16)) & 0xffffffff;
        return (double)(seed & 0xfffffff) / 0x10000000;
    }
}
