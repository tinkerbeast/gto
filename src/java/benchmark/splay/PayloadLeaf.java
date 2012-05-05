/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.splay;

/**
 *
 * @author rishin.goswami
 */
public class PayloadLeaf implements Payload {

    private int[] arr;

    private String str;

    PayloadLeaf(String tag) {
        arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        str = "String for key " + tag + " in leaf node";
    }
}
