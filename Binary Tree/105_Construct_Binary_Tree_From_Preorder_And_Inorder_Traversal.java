/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
// INTUITION:
//
// We are given the Preorder and Inorder traversals of a binary tree
// and need to reconstruct the original tree.
//
// The key properties are:
//
// Preorder = ROOT -> LEFT -> RIGHT
// Inorder = LEFT -> ROOT -> RIGHT
//
// Therefore:
// 1. Preorder tells us WHO the current root is.
// 2. Inorder tells us which nodes belong to the LEFT and RIGHT of that root.
//
// Example:
//
// Preorder = [3, 9, 20, 15, 7]
// ↑
// root
//
// Inorder = [9, 3, 15, 20, 7]
// ↑
// root
//
// Once preorder tells us that 3 is the root,
// we find 3 in inorder and split it:
//
// Inorder:
// [9] | 3 | [15, 20, 7]
// LEFT ROOT RIGHT
//
// So now we know:
//
// 3
// / \
// [9] [15,20,7]
//
// We recursively repeat the exact same process for both subtrees.
//
// ------------------------------------------------------------
//
// HOW DO WE MANAGE THE ARRAYS?
//
// Instead of creating new arrays for every subtree, we pass boundaries:
//
// preStart, preEnd -> current portion of preorder
// inStart, inEnd -> current portion of inorder
//
// This avoids repeatedly copying arrays.
//
// ------------------------------------------------------------
//
// WHY DO WE NEED A HASHMAP?
//
// Every recursive call needs to find the root's position in inorder.
//
// Searching inorder every time would take O(N), making the overall
// worst-case complexity O(N^2).
//
// So we build:
//
// node value -> inorder index
//
// This allows us to find the root's inorder position in O(1) average time.
//
// ------------------------------------------------------------
//
// HOW DO WE FIND THE PREORDER BOUNDARIES?
//
// Suppose:
//
// Inorder:
// [ LEFT SUBTREE | ROOT | RIGHT SUBTREE ]
// ↑ ↑
// inStart inorderRootIndex
//
// Number of nodes in the left subtree:
//
// leftSize = inorderRootIndex - inStart
//
// Since preorder is:
//
// [ ROOT | LEFT SUBTREE | RIGHT SUBTREE ]
//
// LEFT preorder range:
// preStart + 1 -> preStart + leftSize
//
// RIGHT preorder range:
// preStart + leftSize + 1 -> preEnd
//
// ------------------------------------------------------------
//
// BASE CASE:
//
// If either range becomes invalid, there are no nodes in that subtree:
//
// preStart > preEnd
// OR
// inStart > inEnd
//
// return null.
class Solution {

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        // Store each value's position in inorder.
        // This avoids searching the inorder array during every recursive call.
        Map<Integer, Integer> inorderMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // Initially, the entire preorder and inorder arrays belong to the tree.
        return buildSubTree(
                preorder,
                inorder,
                inorderMap,
                0,
                preorder.length - 1,
                0,
                inorder.length - 1);
    }

    public static TreeNode buildSubTree(
            int[] preorder,
            int[] inorder,
            Map<Integer, Integer> inorderMap,
            int preStart,
            int preEnd,
            int inStart,
            int inEnd) {

        // BASE CASE:
        // An invalid range means there are no nodes for this subtree.
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // PREORDER = ROOT -> LEFT -> RIGHT
        //
        // Therefore, the first element of the current preorder range
        // is always the root of the current subtree.
        TreeNode root = new TreeNode(preorder[preStart]);

        // Find where this root occurs in inorder.
        //
        // INORDER = LEFT -> ROOT -> RIGHT
        //
        // Everything before this index belongs to the left subtree,
        // and everything after it belongs to the right subtree.
        int inorderRootIndex = inorderMap.get(root.val);

        // Number of nodes belonging to the left subtree.
        //
        // Example:
        //
        // [9, 5, 3]
        // ↑ ↑
        // start root
        //
        // rootIndex - inStart = 2 nodes on the left
        int leftSize = inorderRootIndex - inStart;

        // BUILD LEFT SUBTREE
        //
        // Preorder:
        // [ROOT | LEFT........ | RIGHT........]
        //
        // Left preorder:
        // preStart + 1 -> preStart + leftSize
        //
        // Left inorder:
        // inStart -> inorderRootIndex - 1
        root.left = buildSubTree(
                preorder,
                inorder,
                inorderMap,
                preStart + 1,
                preStart + leftSize,
                inStart,
                inorderRootIndex - 1);

        // BUILD RIGHT SUBTREE
        //
        // The right subtree begins immediately after all left-subtree
        // elements in preorder.
        //
        // Right preorder:
        // preStart + leftSize + 1 -> preEnd
        //
        // Right inorder:
        // inorderRootIndex + 1 -> inEnd
        root.right = buildSubTree(
                preorder,
                inorder,
                inorderMap,
                preStart + leftSize + 1,
                preEnd,
                inorderRootIndex + 1,
                inEnd);

        // Return the root of the subtree we just constructed.
        return root;
    }
}