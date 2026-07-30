import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Node {
    public int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTreeTraversalGuide {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 1 3 7 -1 -1 11 -1 -1 5 17 -1 -1 -1
            // Node root = buildTree(sc);
            // 1 3 5 7 11 17 -1 -1 -1 -1 -1 -1 -1
            Node root = buildFromLevelOrder(sc);
            System.out.println("Printing the level order traversal");
            levelOrderTraversal(root);
            System.out.println("Printing the reverse level order traversal");
            reverseLevelOrderTraversal(root);
            System.out.println("Printing the inorder traversal");
            inorderTraversal(root);
            System.out.println();
            System.out.println("Printing the preorder traversal");
            preorderTraversal(root);
            System.out.println();
            System.out.println("Printing the postorder traversal");
            postorderTraversal(root);
        }

    }

    public static Node buildTree(Scanner sc) {
        System.out.println("Enter the data for node");
        int data = sc.nextInt();

        if (data == -1)
            return null;

        Node root = new Node(data);

        System.out.println("Enter the data for inserting in left of: " + data);
        root.left = buildTree(sc);
        System.out.println("Enter the data for inserting in right: " + data);
        root.right = buildTree(sc);
        return root;
    }

    public static Node buildFromLevelOrder(Scanner sc) {
        Queue<Node> q = new LinkedList<>();

        System.out.println("Enter the data for node");
        int data = sc.nextInt();

        Node root = new Node(data);
        q.offer(root);

        while (!q.isEmpty()) {
            Node temp = q.poll();
            System.out.println("Enter the data for inserting in left of: " + temp.data);
            int leftData = sc.nextInt();
            if (leftData != -1) {
                temp.left = new Node(leftData);
                q.offer(temp.left);
            }
            System.out.println("Enter the data for inserting in right of: " + temp.data);
            int rightData = sc.nextInt();
            if (rightData != -1) {
                temp.right = new Node(rightData);
                q.offer(temp.right);
            }
        }
        return root;
    }

    public static void levelOrderTraversal(Node root) {
        if (root == null)
            return;

        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                Node curNode = q.poll();
                System.out.print(curNode.data + " ");

                if (curNode.left != null) {
                    q.offer(curNode.left);
                }
                if (curNode.right != null) {
                    q.offer(curNode.right);
                }
            }
            System.out.println("");
        }

    }

    public static void reverseLevelOrderTraversal(Node root) {
        if (root == null)
            return;

        Queue<Node> q = new LinkedList<>();
        Stack<List<Node>> s = new Stack<>();

        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Node> level = new Stack<>();

            for (int i = 0; i < size; i++) {
                Node temp = q.poll();
                level.add(temp);

                if (temp.left != null)
                    q.add(temp.left);
                if (temp.right != null)
                    q.add(temp.right);
            }
            s.push(level);
        }

        while (!s.isEmpty()) {
            for (Node node : s.pop()) {
                System.out.print(node.data + " ");
            }
            System.out.println("");
        }
    }

    public static void inorderTraversal(Node root) {
        if (root == null)
            return;
        inorderTraversal(root.left);
        System.out.print(root.data + " ");
        inorderTraversal(root.right);
    }

    public static void preorderTraversal(Node root) {
        if (root == null)
            return;
        System.out.print(root.data + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public static void postorderTraversal(Node root) {
        if (root == null)
            return;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.data + " ");
    }

    // Iterative version of inorder, preorder and postorder

}
