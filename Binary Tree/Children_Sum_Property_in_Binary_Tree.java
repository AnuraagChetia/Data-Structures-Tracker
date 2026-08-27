// class TreeNode {
//     int val;
//     TreeNode left, right;
//     TreeNode(int x) { val = x; }
// }

// Given the root of a binary tree, return true if and only if every node’s value is equal to the sum of the values stored in its left and right children.

// For any missing ( null ) child, its value is treated as 0.
// A leaf node automatically satisfies the rule because both children are null.

// INTUITION:
//
// For every non-leaf node, check whether its value is equal to the sum
// of its existing children's values.
//
// - If a child is null, treat its value as 0.
// - A leaf node automatically satisfies the Children Sum Property.
//
// We need to make sure this condition is true for every node in the tree.

//BFS APPROACH
class Solution {
    boolean checkChildrenSum(TreeNode root) {
        // Your code goes here
        if (root == null)
            return true;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode curr = q.poll();

            TreeNode left = curr.left;
            TreeNode right = curr.right;

            if (left != null && right != null) {
                if (left.val + right.val != curr.val)
                    return false;
                // else add left and right to Queue
                q.offer(left);
                q.offer(right);
            } else if (left == null && right != null) {
                if (right.val != curr.val)
                    return false;
                // else add right to Queue
                q.offer(right);
            } else if (left != null && right == null) {
                if (left.val != curr.val)
                    return false;
                // ekse add left to Queue
                q.offer(left);
            }
        }
        return true;
    }
}

// Recursive approach
class Solution {
    boolean checkChildrenSum(TreeNode root) {
        if (root == null)
            return true;

        int childSum = 0;

        // if leaf node, then return true
        if (root.left == null && root.right == null)
            return true;

        if (root.left != null)
            childSum += root.left.val;

        if (root.right != null)
            childSum += root.right.val;

        if (childSum != root.val)
            return false;

        return checkChildrenSum(root.left) && checkChildrenSum(root.right);

    }
}
