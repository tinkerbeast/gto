package benchmark.splay;

import java.util.*;

public class SplayTree {

    class Node {

        public double key;

        public Payload value;

        SplayTree.Node left = null;

        SplayTree.Node right = null;

        /**
         * Constructs a Splay tree node.
         *
         * @param {number} key Key.
         * @param {*} value Value.
         */
        Node(double key, Payload value) {
            this.key = key;
            this.value = value;
        }

        // ***********************************************************************
        // NOTE: Deviated from original, please look at the original function
        void traverseKeys(List<Double> list) {
            SplayTree.Node current = this;
            while (current != null) {
                SplayTree.Node left = current.left;
                if (left != null) {
                    left.traverseKeys(list);
                }
                list.add(current.key);
                current = current.right;
            }
        }
    };
    /**
     * Pointer to the root node of the tree.
     *
     * @type {SplayTree.Node}
     * @private
     */
    SplayTree.Node root_ = null;

    /**
     * Constructs a Splay tree.  A splay tree is a self-balancing binary
     * search tree with the additional property that recently accessed
     * elements are quick to access again. It performs basic operations
     * such as insertion, look-up and removal in O(log(n)) amortized time.
     *
     * @constructor
     */
    SplayTree() {
    }

    /**
     * @return {boolean} Whether the tree is empty.
     */
    boolean isEmpty() {
        return (this.root_ == null);
    }

    /**
     * Inserts a node into the tree with the specified key and value if
     * the tree does not already contain a node with the specified key. If
     * the value is inserted, it becomes the root of the tree.
     *
     * @param {number} key Key to insert into the tree.
     * @param {*} value Value to insert into the tree.
     */
    void insert(double key, Payload value) {
        if (this.isEmpty()) {
            this.root_ = new SplayTree.Node(key, value);
            return;
        }
        // Splay on the key to move the last node on the search path for
        // the key to the root of the tree.
        this.splay_(key);
        if (this.root_.key == key) {
            return;
        }
        SplayTree.Node node = new SplayTree.Node(key, value);
        if (key > this.root_.key) {
            node.left = this.root_;
            node.right = this.root_.right;
            this.root_.right = null;
        }
        else {
            node.right = this.root_;
            node.left = this.root_.left;
            this.root_.left = null;
        }
        this.root_ = node;
    }

    /**
     * Removes a node with the specified key from the tree if the tree
     * contains a node with this key. The removed node is returned. If the
     * key is not found, an exception is thrown.
     *
     * @param {number} key Key to find and remove from the tree.
     * @return {SplayTree.Node} The removed node.
     */
    SplayTree.Node remove(double key) throws Exception {
        if (this.isEmpty()) {
            throw new Exception("Key not found: " + key);
        }
        this.splay_(key);
        if (this.root_.key != key) {
            throw new Exception("Key not found: " + key);
        }
        SplayTree.Node removed = this.root_;
        if (this.root_.left == null) {
            this.root_ = this.root_.right;
        }
        else {
            SplayTree.Node right = this.root_.right;
            this.root_ = this.root_.left;
            // Splay to make sure that the new root has an empty right child.
            this.splay_(key);
            // Insert the original right child as the right child of the new
            // root.
            this.root_.right = right;
        }
        return removed;
    }

    /**
     * Returns the node having the specified key or null if the tree doesn't contain
     * a node with the specified key.
     *
     * @param {number} key Key to find in the tree.
     * @return {SplayTree.Node} Node having the specified key.
     */
    SplayTree.Node find(double key) {
        if (this.isEmpty()) {
            return null;
        }
        this.splay_(key);
        return this.root_.key == key ? this.root_ : null;
    }

    /**
     * @return {SplayTree.Node} Node having the maximum key value.
     */
    SplayTree.Node findMax(SplayTree.Node opt_startNode) {
        if (this.isEmpty()) {
            return null;
        }
        SplayTree.Node current = opt_startNode == null ? this.root_ : opt_startNode;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * @return {SplayTree.Node} Node having the maximum key value that
     *     is less than the specified key value.
     */
    SplayTree.Node findGreatestLessThan(double key) {
        if (this.isEmpty()) {
            return null;
        }
        // Splay on the key to move the node with the given key or the last
        // node on the search path to the top of the tree.
        this.splay_(key);
        // Now the result is either the root node or the greatest node in
        // the left subtree.
        if (this.root_.key < key) {
            return this.root_;
        }
        else if (this.root_.left != null) {
            return this.findMax(this.root_.left);
        }
        else {
            return null;
        }
    }

    /**
     * @return {Array<*>} An array containing all the keys of tree's nodes.
     */
    Double[] exportKeys() {
        List<Double> result = new ArrayList<Double>();
        if (!this.isEmpty()) {
            // ***********************************************************************
            // NOTE: Deviated from original, please look at the original function
            root_.traverseKeys(result);
        }

        return result.toArray(new Double[0]);
    }

    /**
     * Perform the splay operation for the given key. Moves the node with
     * the given key to the top of the tree.  If no node has the given
     * key, the last node on the search path is moved to the top of the
     * tree. This is the simplified top-down splaying algorithm from:
     * "Self-adjusting Binary Search Trees" by Sleator and Tarjan
     *
     * @param {number} key Key to splay the tree on.
     * @private
     */
    void splay_(double key) {
        if (this.isEmpty()) {
            return;
        }
        // Create a dummy node.  The use of the dummy node is a bit
        // counter-intuitive: The right child of the dummy node will hold
        // the L tree of the algorithm.  The left child of the dummy node
        // will hold the R tree of the algorithm.  Using a dummy node, left
        // and right will always be nodes and we avoid special cases.
        SplayTree.Node dummy = new SplayTree.Node(0, null);
        SplayTree.Node left = dummy;
        SplayTree.Node right = dummy;

        SplayTree.Node current = this.root_;
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    break;
                }
                if (key < current.left.key) {
                    // Rotate right.
                    SplayTree.Node tmp = current.left;
                    current.left = tmp.right;
                    tmp.right = current;
                    current = tmp;
                    if (current.left == null) {
                        break;
                    }
                }
                // Link right.
                right.left = current;
                right = current;
                current = current.left;
            }
            else if (key > current.key) {
                if (current.right == null) {
                    break;
                }
                if (key > current.right.key) {
                    // Rotate left.
                    SplayTree.Node tmp = current.right;
                    current.right = tmp.left;
                    tmp.left = current;
                    current = tmp;
                    if (current.right == null) {
                        break;
                    }
                }
                // Link left.
                left.right = current;
                left = current;
                current = current.right;
            }
            else {
                break;
            }
        }
        // Assemble.
        left.right = current.left;
        right.left = current.right;
        current.left = dummy.right;
        current.right = dummy.left;
        this.root_ = current;
    }
};
