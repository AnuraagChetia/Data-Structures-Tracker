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

// Intuition -
// Look at every node and calculate the maxPathSum. A single leaf node could
// also be the maxPathSum
// for every node check pathSum for left subtree and rightSubtree
// if any subtree returns -ve value, dont consider that path because that path's
// sum will always be lesser than the parent node
class Solution {
    int maxPathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        pathSum(root);
        return maxPathSum;
    }

    public int pathSum(TreeNode root) {
        if (root == null)
            return 0;

        int leftPathSum = pathSum(root.left);
        int rightPathSum = pathSum(root.right);

        int currentMaxPathSum = root.val + Math.max(0, leftPathSum) + Math.max(0, rightPathSum);

        maxPathSum = Math.max(maxPathSum, currentMaxPathSum);

        return root.val + Math.max(0, Math.max(leftPathSum, rightPathSum));
    }
}
