// Given a pair of tree traversal, return true if a unique binary tree can be constructed otherwise false. Each traversal is represented with integer: 1 -> Preorder , 2 -> Inorder , 3 -> Postorder.
// Example 1
// Input : 1 2
// Output : true 
// Explanation : Answer is True.
// It is possible to construct a unique binary tree. This is because the preorder traversal provides the root of the tree, and the inorder traversal helps determine the left and right subtrees.
// Example 2
// Input : 2 2
// Output : false
// Explanation : Two inorder traversals are insufficient to uniquely determine a binary tree.

class Solution {
    public boolean uniqueBinaryTree(int a, int b) {
        //your code goes here
        if(a == 1 && b == 1) return false; // preorder + preorder
        if(a == 1 && b == 2) return true; // preorder + inorder
        if(a == 1 && b == 3) return false; //pre + post
        if(a == 2 && b == 1) return true; // in + pre
        if(a == 2 && b == 2) return false; // in + in
        if(a == 2 && b == 3) return true; // in + post
        if(a == 3 && b == 1) return false; //post + pre
        if(a == 3 && b == 2) return true; //post + in
        if(a == 3 && b == 3) return false; // post + post
        return false;
    }
}