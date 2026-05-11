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

// Intuition:
// We need to be sure that the difference between leftHeight and rightHeight of
// any subtree is <= 1
// That means we need to check this difference at all subtrees
// After checking, if we get unbalanced at any subtree we return false

class Solution {
    public boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    public int height(TreeNode root) {
        if (root == null)
            return 0;

        int leftHeight = height(root.left);
        // if we get unbalanced at any subtree, we know that the entire tree is
        // unbalanced
        if (leftHeight == -1)
            return -1;

        int rightHeight = height(root.right);
        if (rightHeight == -1)
            return -1;

        // Check if balanced ( Math.abs(leftHeight - rightHeight) > 1 )
        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
