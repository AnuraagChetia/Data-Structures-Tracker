/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int data;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int val) { data = val; left = null, right = null }
 * }
 **/

class Solution {
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> allRootToLeaf(TreeNode root) {
        // your code goes here
        List<Integer> list = new ArrayList<>();
        rootToLeafPath(root, list);
        return result;
    }

    public void rootToLeafPath(TreeNode root, List<Integer> list) {
        if (root == null)
            return;

        list.add(root.data);

        if (root.left == null && root.right == null) {
            result.add(new ArrayList<>(list));
        }

        rootToLeafPath(root.left, list);
        rootToLeafPath(root.right, list);

        list.remove(list.size() - 1);
    }
}