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
// Amak laage maxPathSum, juntu jikunu path t thakibo pare and not only through
// root node
// so ami sabo lagibo pathSum from every node, aru hetu ami amar ans logot check
// korim, if new pathSum2 dangor hoi then we will swap
// etia first t ami every node r pora pathSum2 uliyam, consider korim je hetu
// node through di amar maxPathSum2 pua jai ( curving point of our path)
// Note: negative path sum consider nokoru karon hetu path t ami eneu amr answer
// napao
// e.g if eta node ase 9 aru tar left and right t -ve value duta ase aru tehetor
// tolot nai ( -10 -> 9 <- -20 )
// so yaar karone maxPathSum okol 9 hbo hobo

class Solution {
    int maxPath = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        pathSum(root);
        return maxPath;
    }

    public int pathSum(TreeNode root) {
        if (root == null)
            return 0;

        int leftSum = Math.max(0, pathSum(root.left));
        int rightSum = Math.max(0, pathSum(root.right));

        maxPath = Math.max(maxPath, leftSum + rightSum + root.val);

        return root.val + Math.max(leftSum, rightSum);
    }
}