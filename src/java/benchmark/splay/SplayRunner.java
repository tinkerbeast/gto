/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.splay;

/**
 *
 * @author rishin.goswami
 */
public class SplayRunner implements benchmark.base.BenchMark {

    /*

    function init(BenchmarkSuite, Benchmark) {

    var Splay = new BenchmarkSuite('Splay', 81491, [
    new Benchmark("Splay", SplayRun, SplaySetup, SplayTearDown)
    ]);
     */
    int kSplayTreeSize;

    int kSplayTreeModifications;

    int kSplayTreePayloadDepth;

    SplayTree splayTree = null;

    public SplayRunner() {
        kSplayTreeSize = 8000;
        kSplayTreeModifications = 80;
        kSplayTreePayloadDepth = 5;
    }

    Payload GeneratePayloadTree(int depth, String tag) {
        if (depth == 0) {
            return new PayloadLeaf(tag);
        }
        else {
            PayloadNode root = new PayloadNode();
            root.left = GeneratePayloadTree(depth - 1, tag);
            root.right = GeneratePayloadTree(depth - 1, tag);
            return root;
        }
    }

    double GenerateKey() {
        // The benchmark framework guarantees that myMath.random is
        // deterministic; see base.js.
        return benchmark.base.Math.random();
    }

    double InsertNewNode() {
        // Insert new node with a unique key.
        double key;
        do {
            key = GenerateKey();
        } while (splayTree.find(key) != null);
        Payload payload = GeneratePayloadTree(kSplayTreePayloadDepth, Double.toString(key));
        splayTree.insert(key, payload);
        return key;
    }

    public void Setup() {
        splayTree = new SplayTree();
        for (int i = 0; i < kSplayTreeSize; i++) {
            InsertNewNode();
        }
    }

    public void TearDown() throws Exception {
        // Allow the garbage collector to reclaim the memory
        // used by the splay tree no matter how we exit the
        // tear down function.
        Double[] keys = splayTree.exportKeys();
        splayTree = null;

        // Verify that the splay tree has the right size.
        int length = keys.length;
        if (length != kSplayTreeSize) {
            throw new Exception("Splay tree has wrong size");
        }

        // Verify that the splay tree has sorted, unique keys.
        for (int i = 0; i < length - 1; i++) {
            if (keys[i] >= keys[i + 1]) {
                throw new Exception("Splay tree not sorted");
            }
        }
    }

    public void Run() throws Exception {
        // Replace a few nodes in the splay tree.
        for (int i = 0; i < kSplayTreeModifications; i++) {
            double key = InsertNewNode();
            SplayTree.Node greatest = splayTree.findGreatestLessThan(key);
            if (greatest == null) {
                splayTree.remove(key);
            }
            else {
                splayTree.remove(greatest.key);
            }
        }
    }

    public String getName() {
        return "Splay";
    }
}
