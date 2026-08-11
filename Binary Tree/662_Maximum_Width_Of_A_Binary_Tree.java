import java.util.LinkedList;
import java.util.Queue;

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
// INTUITION -
// Store the position of each node while performing a level-order traversal.
// For each level, the width is:
// rightmost node position - leftmost node position + 1
//
// The position of each node is determined using its parent's position:
// left child = parent.pos * 2 + 1
// right child = parent.pos * 2 + 2
class Solution {
    class PosNode {
        TreeNode node;
        int pos;

        PosNode(TreeNode node, int pos) {
            this.node = node;
            this.pos = pos;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        int width = 0;
        Queue<PosNode> q = new LinkedList<>();
        q.offer(new PosNode(root, 0));
        while (!q.isEmpty()) {
            int levelSize = q.size();
            PosNode left = q.peek();
            PosNode right = null;
            for (int i = 0; i < levelSize; i++) {
                PosNode curr = q.poll();
                if (i == levelSize - 1) {
                    right = curr;
                }
                if (curr.node.left != null)
                    q.offer(new PosNode(curr.node.left, curr.pos * 2 + 1));
                if (curr.node.right != null)
                    q.offer(new PosNode(curr.node.right, curr.pos * 2 + 2));
            }
            width = Math.max(right.pos - left.pos + 1, width);
        }
        return width;
    }
}

// ANOTHER SOLUTION THAT NORMALIZES POSITIONS TO AVOID INTEGER OVERFLOW IF THE
// TREE IS EXTREMELY DEEP
class Solution {
    class PosNode {
        TreeNode node;
        long pos;

        PosNode(TreeNode node, long pos) {
            this.node = node;
            this.pos = pos;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        int width = 0;
        Queue<PosNode> q = new LinkedList<>();
        q.offer(new PosNode(root, 0));
        while (!q.isEmpty()) {
            int levelSize = q.size();
            long firstPos = q.peek().pos;
            long lastPos = 0;
            for (int i = 0; i < levelSize; i++) {
                PosNode curr = q.poll();
                long normalizedPos = curr.pos - firstPos;

                if (i == levelSize - 1) {
                    lastPos = normalizedPos;
                }
                if (curr.node.left != null)
                    q.offer(new PosNode(curr.node.left, normalizedPos * 2 + 1));
                if (curr.node.right != null)
                    q.offer(new PosNode(curr.node.right, normalizedPos * 2 + 2));
            }
            width = (int) Math.max(lastPos + 1, width);
        }
        return width;
    }
}