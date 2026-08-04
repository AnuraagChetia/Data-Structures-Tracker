import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.tree.TreeNode;

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
// Intuition is that we just need to do a BFS (level-order traversal) and switch
// the order of adding
// elements to the result alternatively at each iteration
// Key points to note:
// Here I learned how to use LinkedList efficiently in cases like this
// I used linkedList to store elements in normal order and in reverse
// without using methods like Collections.reverse(list), whhich is huge
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        // Edge case
        if (root == null)
            return result;
        boolean leftToRight = true;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int levelSize = q.size();
            LinkedList<Integer> levelList = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = q.poll();
                if (leftToRight) {
                    levelList.addLast(curr.val);
                } else {
                    levelList.addFirst(curr.val);
                }
                if (curr.left != null)
                    q.offer(curr.left);
                if (curr.right != null)
                    q.offer(curr.right);
            }
            result.add(levelList);
            leftToRight = !leftToRight;
        }
        return result;
    }
}