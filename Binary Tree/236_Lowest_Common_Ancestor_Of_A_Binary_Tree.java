/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

// INTUITION:
//
// Initially, I solved this using a brute-force approach.
//
// 1. Find the path from root → p and store it in a list.
// 2. Find the path from root → q and store it in another list.
// 3. Compare the two paths to find the lowest common ancestor.
//
// Then I learned the optimal recursive approach.
//
// The main idea:
// Ask the left and right subtrees whether they contain p or q.
//
// Result conditions:
//
// 1. If the left subtree returns a node and the right subtree returns null,
// then p or q was found on the left.
// Propagate the node found on the left upward.
//
// 2. If the right subtree returns a node and the left subtree returns null,
// then p or q was found on the right.
// Propagate the node found on the right upward.
//
// 3. If both left and right return a node,
// then one of p/q was found on each side.
// Therefore, the current root is their lowest common ancestor.
//
// 4. If the current root itself is p or q,
// return the current root immediately.
// This allows the result to propagate back up the recursion.
//
// 5. If the current root is null,
// return null because neither p nor q was found in that subtree.
//
// Key idea:
// Each recursive call returns information about whether p or q was found
// in that subtree. The first node where both sides return a result is the LCA.

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return root;
        }

    }
}