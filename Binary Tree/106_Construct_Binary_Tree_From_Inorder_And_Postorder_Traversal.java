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
// We are given the Inorder and Postorder traversals of a binary tree
// and need to reconstruct the original tree.
//
// The key properties are:
//
// Postorder = LEFT -> RIGHT -> ROOT
// Inorder = LEFT -> ROOT -> RIGHT
//
// Therefore:
// 1. Postorder tells us WHO the current root is.
// Since ROOT comes last in postorder, the last element of the current
// postorder range is always the root.
//
// 2. Inorder tells us which nodes belong to the LEFT and RIGHT subtrees.
//
// Example:
//
// Inorder: [9, 3, 15, 20, 7]
// ↑
// root
//
// Postorder: [9, 15, 7, 20, 3]
// ↑
// root
//
// Postorder tells us that 3 is the root.
//
// Find 3 in inorder:
//
// [9] | 3 | [15, 20, 7]
// LEFT ROOT RIGHT
//
// So:
//
// 3
// / \
// [9] [15,20,7]
//
// We then recursively repeat the same process for the left and right subtrees.
//
// ------------------------------------------------------------
//
// HOW DO WE MANAGE THE ARRAYS?
//
// Instead of creating new arrays for every subtree, we pass boundaries:
//
// postStart, postEnd -> current portion of postorder
// inStart, inEnd -> current portion of inorder
//
// This avoids repeatedly copying arrays.
//
// ------------------------------------------------------------
//
// WHY DO WE NEED A HASHMAP?
//
// Every recursive call needs to find the current root's position in inorder.
//
// Searching the inorder array every time would take O(N) per call,
// making the worst-case overall time O(N^2).
//
// Instead, we create a map:
//
// node value -> inorder index
//
// This lets us find the root's inorder position in O(1) average time.
//
// ------------------------------------------------------------
//
// HOW DO WE FIND THE POSTORDER BOUNDARIES?
//
// Inorder:
//
// [ LEFT SUBTREE | ROOT | RIGHT SUBTREE ]
// ↑ ↑
// inStart inorderRootIndex
//
// Number of nodes in the left subtree:
//
// leftSize = inorderRootIndex - inStart
//
// Since Postorder is:
//
// [ LEFT SUBTREE | RIGHT SUBTREE | ROOT ]
//
// and we know exactly how many nodes belong to LEFT,
// we can determine the postorder ranges:
//
// LEFT postorder:
// postStart -> postStart + leftSize - 1
//
// RIGHT postorder:
// postStart + leftSize -> postEnd - 1
//
// We use postEnd - 1 because postEnd itself is the current ROOT.
//
// ------------------------------------------------------------
//
// BASE CASE:
//
// If either range becomes invalid, there are no nodes in that subtree:
//
// postStart > postEnd
// OR
// inStart > inEnd
//
// return null.

class Solution {

    public TreeNode buildTree(int[] inorder, int[] postorder) {

        // Store each node's position in inorder.
        //
        // Example:
        // inorder = [9, 3, 15, 20, 7]
        //
        // map:
        // 9 -> 0
        // 3 -> 1
        // 15 -> 2
        // 20 -> 3
        // 7 -> 4
        //
        // This allows O(1) average lookup of a root's inorder position.
        Map<Integer, Integer> inorderMap = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // Initially, the entire postorder and inorder arrays
        // belong to the tree.
        return buildSubTree(
                inorder,
                postorder,
                inorderMap,
                0,
                postorder.length - 1,
                0,
                inorder.length - 1);
    }

    public TreeNode buildSubTree(
            int[] inorder,
            int[] postorder,
            Map<Integer, Integer> inorderMap,
            int postStart,
            int postEnd,
            int inStart,
            int inEnd) {

        // BASE CASE:
        // An invalid range means there are no nodes in this subtree.
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }

        // POSTORDER = LEFT -> RIGHT -> ROOT
        //
        // Therefore, the LAST element of the current postorder range
        // is always the root of the current subtree.
        TreeNode root = new TreeNode(postorder[postEnd]);

        // Find the root's position in inorder.
        //
        // INORDER = LEFT -> ROOT -> RIGHT
        //
        // Everything before this position belongs to the left subtree.
        // Everything after this position belongs to the right subtree.
        int inorderRootIndex = inorderMap.get(root.val);

        // Number of nodes in the left subtree.
        //
        // Example:
        //
        // inorder:
        // [9, 5, 3, 10, 12]
        // ↑ ↑
        // start root
        //
        // inorderRootIndex - inStart = 2
        //
        // Therefore, there are 2 nodes in the left subtree.
        int leftSize = inorderRootIndex - inStart;

        // BUILD LEFT SUBTREE
        //
        // Postorder:
        //
        // [ LEFT...... | RIGHT...... | ROOT ]
        //
        // Since leftSize tells us exactly how many nodes belong
        // to the left subtree:
        //
        // Left postorder:
        // postStart -> postStart + leftSize - 1
        //
        // Left inorder:
        // inStart -> inorderRootIndex - 1
        root.left = buildSubTree(
                inorder,
                postorder,
                inorderMap,
                postStart,
                postStart + leftSize - 1,
                inStart,
                inorderRootIndex - 1);

        // BUILD RIGHT SUBTREE
        //
        // The right subtree starts immediately after the left subtree
        // in postorder.
        //
        // Right postorder:
        // postStart + leftSize -> postEnd - 1
        //
        // postEnd - 1 is used because postEnd itself contains
        // the current root.
        //
        // Right inorder:
        // inorderRootIndex + 1 -> inEnd
        root.right = buildSubTree(
                inorder,
                postorder,
                inorderMap,
                postStart + leftSize,
                postEnd - 1,
                inorderRootIndex + 1,
                inEnd);

        // Return the root of the subtree we just constructed.
        return root;
    }
}

// ------------------------------------------------------------
// QUICK REVISION:
//
// LC 105 (Preorder + Inorder):
//
// Preorder = ROOT | LEFT | RIGHT
// ↑
// preStart
//
// root = preorder[preStart]
//
//
// LC 106 (Inorder + Postorder):
//
// Postorder = LEFT | RIGHT | ROOT
// ↑
// postEnd
//
// root = postorder[postEnd]
//
//
// In BOTH problems:
//
// Inorder = LEFT | ROOT | RIGHT
// ↑
// inorderRootIndex
//
// leftSize = inorderRootIndex - inStart
//
// Inorder tells us how many nodes belong to the left subtree.
// We use that leftSize to calculate the traversal boundaries.
//
// HashMap:
// value -> inorder index
//
// This avoids repeatedly searching inorder.
//
// Time Complexity: O(N)
// Space Complexity: O(N)