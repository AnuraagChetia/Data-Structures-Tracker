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

// INTUITION
// This is similar to LEETCODE 100 (Same Tree), but instead of checking
// whether two trees are exactly the same, we check whether they are mirrors.
//
// First check if root is null.
// If it is null, the tree is symmetric.
//
// For a symmetric tree, root.left and root.right must be mirror images.
//
// So create two branches:
// left branch -> root.left
// right branch -> root.right
//
// Compare the two branches recursively.
//
// IMPORTANT:
// Since we are checking for a MIRROR, we cannot compare left with left
// and right with right.
//
// We need to compare:
// p.left -> q.right
// p.right -> q.left
//
// At every step:
// 1. If both nodes are null -> they are mirrors.
// 2. If only one is null -> they are not mirrors.
// 3. If their values are different -> they are not mirrors.
// 4. Otherwise, recursively check the two mirror pairs.
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isMirror(root.left, root.right);

    }

    public boolean isMirror(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        if (p.val != q.val)
            return false;
        return isMirror(p.left, q.right) && isMirror(p.right, q.left);
    }
}