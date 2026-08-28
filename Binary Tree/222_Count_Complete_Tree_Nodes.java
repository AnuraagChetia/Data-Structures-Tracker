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
// In a perfect binary tree of height h, the total number of nodes is 2^h - 1.
//
// For each subtree, calculate its leftmost and rightmost heights.
// If both heights are equal, the subtree is perfect, so we can directly
// calculate its number of nodes using 2^h - 1.
//
// If the heights are different, the subtree is not perfect,
// so recursively count the nodes in the left and right subtrees.
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;

        int lh = leftHeight(root);
        int rh = rightHeight(root);

        if (lh == rh) {
            return (1 << lh) - 1; // 2^h - 1. Number of nodes in a Perfect binary tree
        }

        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    public static int leftHeight(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + leftHeight(root.left);
    }

    public static int rightHeight(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + rightHeight(root.right);
    }
}