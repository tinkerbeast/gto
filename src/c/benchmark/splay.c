/////////////////////////////////////////////////
// Header
/////////////////////////////////////////////////

#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>

// DArray

struct DArray
{
    double MULT_FACTOR;
    size_t limSize;
    size_t curSize;
    double* arr;
};
typedef struct DArray DArray;
static DArray* darrayInstance = NULL;

// Payload

struct PayloadNode
{
    struct PayloadNode* left;
    struct PayloadNode* right;
};
typedef struct PayloadNode Payload;

struct PayloadLeaf
{
    Payload* left;
    Payload* right;
    int array[10];
    char string[64];
};

// SplayTree.Node

struct SplayTree_Node
{
    struct SplayTree_Node* left;
    struct SplayTree_Node* right;
    double key;
    Payload* value;
};
typedef struct SplayTree_Node SplayTree_Node;

// SplayTree

struct SplayTree
{
    struct SplayTree_Node* root_;
};
typedef struct SplayTree SplayTree;
void SplayTree_splay_(SplayTree*, double);




//////////////////////////////////////////////////////
// DArray
//////////////////////////////////////////////////////

DArray*
new_DArray(void)
{
    DArray* da = malloc(sizeof (DArray));
    if (da == NULL) {
        perror("Allocation error in new_DArray");
        exit(1);
    }
    da->MULT_FACTOR = 2.0;
    da->limSize = 128;
    da->curSize = 0;
    da->arr = malloc(da->limSize * sizeof (double));
    if (da->arr == NULL) {
        perror("Allocation error in new_DArray");
        exit(1);
    }
    return da;
}

void
DArray_push(double val)
{
    if (darrayInstance->curSize == darrayInstance->limSize) {
        darrayInstance->limSize = (size_t) (darrayInstance->MULT_FACTOR * darrayInstance->limSize);
        darrayInstance->arr = realloc(darrayInstance->arr, darrayInstance->limSize * sizeof (double));
        if (darrayInstance->arr == NULL) {
            perror("Re-Allocation error in DArray_push");
            exit(1);
        }
        //printf("{%d %d, %p}\n",
        //darrayInstance->limSize, darrayInstance->curSize, darrayInstance->arr);
    }
    darrayInstance->arr[darrayInstance->curSize++] = val; // WARN: error condition (curSize > limSize) ignored
}

////////////////////////////////////////
// Payload
////////////////////////////////////////

static int PayLoadArray[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
// *NOTE : deviation from original code, since double to string is cumbersobe in c

struct PayloadLeaf *
new_PayloadLeaf(double tag)
{

    struct PayloadLeaf * node = malloc(sizeof (struct PayloadLeaf));
    if (node == NULL) {
        perror("Allocation error in PayloadLeaf");
        exit(1);
    }

    node->left = NULL;
    node->right = NULL;
    memcpy(node->array, PayLoadArray, sizeof (PayLoadArray));
    snprintf(node->string, 64, "String for key %f in leaf node", tag);

    return node;
}

// *NOTE : deviation from original code, since double to string is cumbersobe in c

Payload*
GeneratePayloadTree(int depth, double tag)
{
    if (depth == 0) {
        return (Payload*) new_PayloadLeaf(tag);
    } else {
        Payload* node = malloc(sizeof (Payload)); // ERROR : null return ignored
        node->left = GeneratePayloadTree(depth - 1, tag);
        node->right = GeneratePayloadTree(depth - 1, tag);
        return node;
    }
}


////////////////////////////////////////
// SplayTree.Node
////////////////////////////////////////

/**
 * Constructs a Splay tree node.
 *
 * @param {number} key Key.
 * @param {*} value Value.
 */
SplayTree_Node*
new_SplayTree_Node(double key, Payload* value)
{
    SplayTree_Node* this = malloc(sizeof (SplayTree_Node)); // ERROR : null return ignored
    this->left = NULL;
    this->right = NULL;
    this->key = key;
    this->value = value;
    return this;
}


/**
 * Performs an ordered traversal of the subtree starting at
 * this SplayTree.Node.
 *
 * @param {function(SplayTree.Node)} f Visitor function.
 * @private
 */
// NOTE: inorder traversal

void
SplayTree_Node_traverse_(SplayTree_Node* this, void (*f)(SplayTree_Node*))
{
    SplayTree_Node* current = this;
    while (current) {
        SplayTree_Node* left = current->left;
        if (left) SplayTree_Node_traverse_(left, f);
        f(current);
        current = current->right;
    }
}


////////////////////////////////////////
// SplayTree
////////////////////////////////////////

/**
 * Constructs a Splay tree.  A splay tree is a self-balancing binary
 * search tree with the additional property that recently accessed
 * elements are quick to access again. It performs basic operations
 * such as insertion, look-up and removal in O(log(n)) amortized time.
 *
 * @constructor
 */
SplayTree *
new_SplayTree(void)
{
    SplayTree* tree = malloc(sizeof (SplayTree)); // ERROR : null return ignored
    tree->root_ = NULL;
    return tree;
}

/**
 * @return {boolean} Whether the tree is empty.
 */
bool
SplayTree_isEmpty(SplayTree* this)
{
    return !this->root_;
}

/**
 * Inserts a node into the tree with the specified key and value if
 * the tree does not already contain a node with the specified key. If
 * the value is inserted, it becomes the root of the tree.
 *
 * @param {number} key Key to insert into the tree.
 * @param {*} value Value to insert into the tree.
 */
void
SplayTree_insert(SplayTree* this, double key, Payload* value)
{
    if (SplayTree_isEmpty(this)) {
        this->root_ = new_SplayTree_Node(key, value);
        return;
    }
    // Splay on the key to move the last node on the search path for
    // the key to the root of the tree.
    SplayTree_splay_(this, key);
    if (this->root_->key == key) {
        return;
    }
    SplayTree_Node* node = new_SplayTree_Node(key, value);
    if (key > this->root_->key) {
        node->left = this->root_;
        node->right = this->root_->right;
        this->root_->right = NULL;
    } else {
        node->right = this->root_;
        node->left = this->root_->left;
        this->root_->left = NULL;
    }
    this->root_ = node;
}

/**
 * Removes a node with the specified key from the tree if the tree
 * contains a node with this key. The removed node is returned. If the
 * key is not found, an exception is thrown.
 *
 * @param {number} key Key to find and remove from the tree.
 * @return {SplayTree.Node} The removed node.
 */
SplayTree_Node*
SplayTree_remove(SplayTree* this, double key)
{
    if (SplayTree_isEmpty(this)) {
        char buf[32];
        snprintf(buf, 32, "Key not found: %f", key);
        perror(buf);
        exit(1);
    }
    SplayTree_splay_(this, key);
    if (this->root_->key != key) {
        char buf[32];
        snprintf(buf, 32, "Key not found: %f", key);
        perror(buf);
        exit(1);
    }
    SplayTree_Node* removed = this->root_;
    if (!this->root_->left) {
        this->root_ = this->root_->right;
    } else {
        SplayTree_Node* right = this->root_->right;
        this->root_ = this->root_->left;
        // Splay to make sure that the new root has an empty right child.
        SplayTree_splay_(this, key);
        // Insert the original right child as the right child of the new
        // root.
        this->root_->right = right;
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
SplayTree_Node*
SplayTree_find(SplayTree* this, double key)
{
    if (SplayTree_isEmpty(this)) {
        return NULL;
    }
    SplayTree_splay_(this, key);
    return this->root_->key == key ? this->root_ : NULL;
}

/**
 * @return {SplayTree.Node} Node having the maximum key value.
 */
SplayTree_Node*
SplayTree_findMax(SplayTree* this, SplayTree_Node* opt_startNode)
{
    if (SplayTree_isEmpty(this)) {
        return NULL;
    }
    SplayTree_Node* current = opt_startNode ? opt_startNode : this->root_;
    while (current->right) {
        current = current->right;
    }
    return current;
}

/**
 * @return {SplayTree.Node} Node having the maximum key value that
 *     is less than the specified key value.
 */
SplayTree_Node*
SplayTree_findGreatestLessThan(SplayTree* this, double key)
{
    if (SplayTree_isEmpty(this)) {
        return NULL;
    }
    // Splay on the key to move the node with the given key or the last
    // node on the search path to the top of the tree.
    SplayTree_splay_(this, key);
    // Now the result is either the root node or the greatest node in
    // the left subtree.
    if (this->root_->key < key) {
        return this->root_;
    } else if (this->root_->left) {
        return SplayTree_findMax(this, this->root_->left);
    } else {
        return NULL;
    }
}

/**
 * @return {Array<*>} An array containing all the keys of tree's nodes.
 */
static void
exAcc(SplayTree_Node* node)
{
    DArray_push(node->key);
}
// *NOTE : deviation from original code, since we need the array length

DArray*
SplayTree_exportKeys(SplayTree* this)
{
    DArray* result = new_DArray();
    darrayInstance = result;
    if (!SplayTree_isEmpty(this)) {
        SplayTree_Node_traverse_(this->root_, exAcc);
    }
    darrayInstance = NULL;

    return result;
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
void
SplayTree_splay_(SplayTree* this, double key)
{
    if (SplayTree_isEmpty(this)) {
        return;
    }
    // Create a dummy node.  The use of the dummy node is a bit
    // counter-intuitive: The right child of the dummy node will hold
    // the L tree of the algorithm.  The left child of the dummy node
    // will hold the R tree of the algorithm.  Using a dummy node, left
    // and right will always be nodes and we avoid special cases.
    SplayTree_Node* dummy, *left, *right;
    dummy = left = right = new_SplayTree_Node(0, NULL);
    SplayTree_Node* current = this->root_;
    while (true) {
        if (key < current->key) {
            if (!current->left) {
                break;
            }
            if (key < current->left->key) {
                // Rotate right.
                SplayTree_Node* tmp = current->left;
                current->left = tmp->right;
                tmp->right = current;
                current = tmp;
                if (!current->left) {
                    break;
                }
            }
            // Link right.
            right->left = current;
            right = current;
            current = current->left;
        } else if (key > current->key) {
            if (!current->right) {
                break;
            }
            if (key > current->right->key) {
                // Rotate left.
                SplayTree_Node* tmp = current->right;
                current->right = tmp->left;
                tmp->left = current;
                current = tmp;
                if (!current->right) {
                    break;
                }
            }
            // Link left.
            left->right = current;
            left = current;
            current = current->right;
        } else {
            break;
        }
    }
    // Assemble.
    left->right = current->left;
    right->left = current->right;
    current->left = dummy->right;
    current->right = dummy->left;
    this->root_ = current;
}


////////////////////////////////////////
// SplayTree Runner
////////////////////////////////////////

// Configuration.
int kSplayTreeSize = 8000;
int kSplayTreeModifications = 80;
int kSplayTreePayloadDepth = 5;

SplayTree* splayTree = NULL;



static uint32_t seed = UINT32_C(49734321);

static double
Math_random(void)
{
    // Robert Jenkins' 32 bit integer hash function.
    seed = (seed + 0x7ed55d16) + (seed << 12);
    seed = (seed^0xc761c23c) ^ (seed >> 19);
    seed = (seed + 0x165667b1) + (seed << 5);
    seed = (seed + 0xd3a2646c) ^ (seed << 9);
    seed = (seed + 0xfd7046c5) + (seed << 3);
    seed = (seed^0xb55a4f09) ^ (seed >> 16);
    return (double) (seed & 0xfffffff) / 0x10000000;
}

double
GenerateKey(void)
{

    return Math_random();
}

double
InsertNewNode(void)
{
    // Insert new node with a unique key.
    double key;
    do {
        key = GenerateKey();
    } while (SplayTree_find(splayTree, key) != NULL);
    // *NOTE : deviation from original code, since double to string is cumbersobe in c
    Payload* payload = GeneratePayloadTree(kSplayTreePayloadDepth, key);
    SplayTree_insert(splayTree, key, payload);
    return key;
}

void
SplaySetup(void)
{
    splayTree = new_SplayTree();
    for (int i = 0; i < kSplayTreeSize; i++) InsertNewNode();
}

static void
freeTree(SplayTree_Node* node)
{
    if (NULL == node) return;

    freeTree(node->left);
    freeTree(node->right);
    free(node->value);
    free(node);
}

void
SplayTearDown(void)
{
    // Allow the garbage collector to reclaim the memory
    // used by the splay tree no matter how we exit the
    // tear down function.

    DArray* keys = SplayTree_exportKeys(splayTree);

    // *NOTE : deviation from original code, since no garbage collection in c
    freeTree(splayTree->root_);

    // Verify that the splay tree has the right size.
    int length = keys->curSize;
    if (length != kSplayTreeSize) {
        perror("Splay tree has wrong size");
        exit(1);
    }

    double* keysArr = keys->arr;
    // Verify that the splay tree has sorted, unique keys.
    for (int i = 0; i < length - 1; i++) {
        if (keysArr[i] >= keysArr[i + 1]) {
            perror("Splay tree not sorted");
            exit(1);
        }
    }

    // *NOTE : deviation from original code, since no garbage collection in c
    free(keys->arr);
    free(keys);
}

void
SplayRun(void)
{
    // Replace a few nodes in the splay tree.
    for (int i = 0; i < kSplayTreeModifications; i++) {
        double key = InsertNewNode();
        SplayTree_Node* greatest = SplayTree_findGreatestLessThan(splayTree, key);
        if (greatest == NULL) SplayTree_remove(splayTree, key);
        else SplayTree_remove(splayTree, greatest->key);
    }
}

int
main()
{

    int iterations = 1;
    for (int i = 0; i < iterations; i++) {
        SplaySetup();
        SplayRun();
        SplayTearDown();
    }

    return EXIT_SUCCESS;
}
