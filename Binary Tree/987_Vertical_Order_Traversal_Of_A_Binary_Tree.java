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

// "The traversal isn't the difficult part. I need to figure out what
// information each node needs and how I should organize that information
// afterward."

// INTUITION:
//
// This is basically a BFS problem with additional logic for organizing
// the nodes by their column.
//
// We need to store more information in the Queue, so we created the
// PosNode object. It stores:
// - the node
// - its row
// - its column
//
// Every node has a position:
// left child -> column - 1
// right child -> column + 1
// child -> row + 1
//
// We want to store all elements belonging to the same column together
// and later sort them based on their row and value.
//
// For example, column 0 could contain multiple nodes such as:
// (row=0, value=1)
// (row=2, value=4)
// (row=2, value=6)
//
// So we use a TreeMap where:
// key = column
// value = List of (row, value)
//
// TreeMap keeps its keys sorted.
// Therefore, when we iterate through map.values(), we automatically
// process columns from left to right.
//
// After adding every node to the TreeMap, we process each column.
//
// Within each column:
// 1. Sort by row.
// 2. If two nodes have the same row, sort by value.
//
// Finally, extract the values from each column and add them to the result.
//
// NEW LEARNING:
//
// Learned TreeMap.
// TreeMap stores its keys in sorted order.
// In this problem:
// key = column
// value = list of (row, value)
//
// This means the columns automatically come out in:
//
// -2, -1, 0, 1, 2 ...
class PosNode {
    TreeNode node;
    int row;
    int column;

    PosNode(TreeNode node, int row, int column) {
        this.node = node;
        this.row = row;
        this.column = column;
    }
}

class Pos {
    int row;
    int val;

    Pos(int row, int val) {
        this.row = row;
        this.val = val;
    }
}

class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;

        Queue<PosNode> q = new LinkedList<>();
        TreeMap<Integer, List<Pos>> map = new TreeMap<>();

        q.offer(new PosNode(root, 0, 0));

        while (!q.isEmpty()) {
            PosNode curr = q.poll();
            // Add curr to map at index = column
            List<Pos> positions = map.getOrDefault(curr.column, new ArrayList<>());
            positions.add(new Pos(curr.row, curr.node.val));
            map.put(curr.column, positions);

            if (curr.node.left != null)
                q.offer(new PosNode(curr.node.left, curr.row + 1, curr.column - 1));
            if (curr.node.right != null)
                q.offer(new PosNode(curr.node.right, curr.row + 1, curr.column + 1));

        }

        for (List<Pos> positions : map.values()) {
            positions.sort((a, b) -> {
                if (a.row != b.row) {
                    return Integer.compare(a.row, b.row);
                }
                return Integer.compare(a.val, b.val);
            });

            List<Integer> column = new ArrayList<>();

            for (Pos pos : positions) {
                column.add(pos.val);
            }

            result.add(column);
        }

        return result;
    }
}