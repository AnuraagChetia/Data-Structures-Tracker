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
// We need to find all nodes that are exactly K distance away from the target.
//
// From the target, we may need to move in three directions:
// left child, right child, and parent.
//
// Since each TreeNode already stores its left and right children,
// the only missing information is its parent.
// So, first build a map that stores each node's parent.
//
// Then, treat the tree like a graph and perform BFS starting from the target.
// After traversing K levels, the nodes remaining in the queue are exactly
// K distance away from the target.
//
// Use a visited set to prevent revisiting nodes while moving between
// children and parents.

class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        // if distance is 0, only target will be there
        if (k == 0) {
            ans.add(target.val);
            return ans;
        }
        // Map to store parents of each node
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        // Set to keep track of visited nodes
        Set<TreeNode> visited = new HashSet<>();

        buildParentMap(root, parentMap);
        // Queue will hold the neighbours, and at the very end will hold the K distant
        // neighbours
        Queue<TreeNode> q = new LinkedList<>();
        q.add(target);
        visited.add(target);
        // iterate distant-level wise from target
        for (int i = 0; i < k; i++) {
            int levelSize = q.size();
            for (int j = 0; j < levelSize; j++) {
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
        }

        // get the k-distant nodes and add to answer
        while (!q.isEmpty()) {
            ans.add(q.poll().val);
        }
        return ans;
    }

    public static void buildParentMap(TreeNode root, Map<TreeNode, TreeNode> parentMap) {
        if (root == null)
            return;

        if (root.left != null) {
            parentMap.put(root.left, root);
        }
        if (root.right != null) {
            parentMap.put(root.right, root);
        }

        buildParentMap(root.left, parentMap);
        buildParentMap(root.right, parentMap);
    }
}