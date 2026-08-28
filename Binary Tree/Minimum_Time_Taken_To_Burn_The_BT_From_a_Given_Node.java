/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int data;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int val) { data = val; left = null, right = null }
 * }
 **/
// INTUITION:
// This problem follows the same pattern as "All Nodes Distance K in Binary
// Tree".
//
// Since the fire spreads to the left child, right child, and parent,
// we first build a parent map so that we can traverse in all three directions.
//
// Start BFS from the target node.
// Each BFS level represents 1 second of the fire spreading.
// Continue until there are no more unvisited nodes to burn.
//
// KEY LEARNING:
// Avoid static/global variables when possible, as they create shared state
// that can persist between method calls.
// Prefer returning the required value from a helper method when possible.
class Solution {

    public int timeToBurnTree(TreeNode root, int start) {
        // your code goes here
        int ans = -1;
        if (root == null)
            return ans;

        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        TreeNode target = buildParentMap(root, parentMap, start);

        Queue<TreeNode> q = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();

        q.offer(target);
        visited.add(target);

        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = q.poll();
                TreeNode parent = parentMap.get(curr);
                if (parent != null && !visited.contains(parent)) {
                    q.offer(parent);
                    visited.add(parent);
                }
                if (curr.left != null && !visited.contains(curr.left)) {
                    q.offer(curr.left);
                    visited.add(curr.left);
                }
                if (curr.right != null && !visited.contains(curr.right)) {
                    q.offer(curr.right);
                    visited.add(curr.right);
                }
            }
            ans++;
        }
        return ans;
    }

    public static TreeNode buildParentMap(TreeNode root, Map<TreeNode, TreeNode> map, int start) {
        if (root == null)
            return null;

        TreeNode target = null;

        if (root.data == start)
            target = root;

        if (root.left != null) {
            map.put(root.left, root);
            TreeNode found = buildParentMap(root.left, map, start);
            // if it returns !null, that means it found the node with data = start
            if (found != null) {
                target = found;
            }
        }
        if (root.right != null) {
            // if it returns !null, that means it found the node with data = start
            map.put(root.right, root);
            TreeNode found = buildParentMap(root.right, map, start);
            if (found != null) {
                target = found;
            }
        }
        return target;
    }
}