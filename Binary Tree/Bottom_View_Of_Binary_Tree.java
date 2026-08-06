import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int val) { data = val; left = null, right = null }
 * }
 **/
// INTUITION
// This is same as Top View but the only difference is -
// For each column, keep overwriting the value as BFS visits nodes. The final
// value is the bottom-view node.
class Solution {
    class PosNode {
        TreeNode node;
        int col;

        PosNode(TreeNode node, int col) {
            this.node = node;
            this.col = col;
        }
    }

    public List<Integer> bottomView(TreeNode root) {
        // your code goes here
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<PosNode> q = new LinkedList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();

        q.offer(new PosNode(root, 0));

        while (!q.isEmpty()) {
            PosNode curr = q.poll();
            map.put(curr.col, curr.node.val);
            if (curr.node.left != null)
                q.add(new PosNode(curr.node.left, curr.col - 1));
            if (curr.node.right != null)
                q.add(new PosNode(curr.node.right, curr.col + 1));
        }

        for (Integer val : map.values()) {
            result.add(val);
        }
        return result;
    }
}