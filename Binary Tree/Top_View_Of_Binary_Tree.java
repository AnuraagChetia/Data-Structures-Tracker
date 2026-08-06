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
// Given the root of a binary tree, return the top view of the binary tree.

// The top view of a binary tree consists of the set of nodes visible when the
// tree is observed from above.

// Return the values of these nodes ordered from the leftmost to the rightmost
// position.

// If multiple nodes share the same horizontal distance from the root, only the
// node that appears first when traversing from left to right (i.e., the
// leftmost node) should be included in the result.

// INTUITION:
// This is just a vertical order traversal problem with less twist
// return the first node encountered in each column

class Solution {
    class PosNode {
        TreeNode node;
        int col;

        PosNode(TreeNode node, int col) {
            this.node = node;
            this.col = col;
        }
    }

    public List<Integer> topView(TreeNode root) {
        // your code goes here
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        Queue<PosNode> q = new LinkedList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        q.offer(new PosNode(root, 0));

        while (!q.isEmpty()) {
            PosNode curr = q.poll();
            if (!map.containsKey(curr.col)) {
                map.put(curr.col, curr.node.val);
            }
            // add child to q
            if (curr.node.left != null)
                q.offer(new PosNode(curr.node.left, curr.col - 1));
            if (curr.node.right != null)
                q.offer(new PosNode(curr.node.right, curr.col + 1));
        }

        for (Integer node : map.values()) {
            result.add(node);
        }
        return result;
    }
}