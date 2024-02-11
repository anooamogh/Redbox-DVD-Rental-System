// Amogh Anoo ASA210011

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinTree<T extends Comparable<T>> {
    public Node<T> root;

    // Constructor for BinTree that takes a file name as input and populates the tree with DVD objects.
    public BinTree(String i) {
        // Create a Scanner to read the specified file.
        Scanner scnr = null;
        try
        {
            scnr = new Scanner(new File(i));
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }


        // Iterate through the file, reading each line and creating DVD objects to insert into the tree.
        while (scnr.hasNext()) {
            String temp = scnr.nextLine();
            String[] parts = temp.split(",");
            String temp1 = parts[0];
            String title = temp1.replaceAll("\"", "");
            int available = Integer.parseInt(parts[1]);
            int rented = Integer.parseInt(parts[2]);
            Node<T> insertion = new Node<>(new DVD<T>(title, available, rented));
            insertHelper(insertion);
        }
    }

    // Private helper method to recursively find a node in the tree by title.
    private Node<T> _findNode(Node<T> title, Node<T> cur) {
        if (cur == null) {
            return null;
        }
        int comparisonResult = title.compareTo(cur);

        if (comparisonResult == 0) {
            return cur; // Found the Node with the matching title
        } else if (comparisonResult > 0) {
            return _findNode(title, cur.right); // Search the right subtree
        } else {
            return _findNode(title, cur.left); // Search the left subtree
        }
    }

    // Public method to find a DVD node by title in the tree.
    public DVD<T> findNode(String title) {
        // Create a temporary DVD object with the given title (used for comparison).
        DVD<T> tempDVD = new DVD<>(title, 0, 0);
        Node<T> titleNode = new Node<>(tempDVD);

        // Call the private _findNode method to search for the node with the given title.
        Node<T> temp = _findNode(titleNode, root);

        if (temp == null) {
            return null;
        }

        return temp.payload;
    }

    // Public method to insert a node into the tree.
    public void insertHelper(Node<T> n) {
        if (root == null) {
            root = n; // If the tree is empty, set the new node as the root.
            return;
        }

        insert(n, root); // Call the private insert method to recursively insert the node into the tree.
    }

    // Public method to insert a new DVD with a title and available count.
    public void insertHelper(String s, int a) {
        // Create a new node with a DVD object constructed from the given title and available count.
        Node<T> newTitle = new Node<T>(new DVD<>(s, a, 0));

        // Call the private insert method to recursively insert the new node into the tree.
        insert(newTitle, root);
    }

    // Private method for performing an inorder traversal of the tree.
    private void inorderTraversal(Node<T> root) {
        if (root != null) {
            // Visit the left subtree, print the current node, and visit the right subtree.
            inorderTraversal(root.left);
            System.out.print(root);
            inorderTraversal(root.right);
        }
    }

    // Override the toString method to perform an inorder traversal and return the results as a string.
    public String toString() {
        inorderTraversal(root); // Start the inorder traversal from the root.
        return "";
    }

    // Private method to recursively insert a node into the tree.
    private Node<T> insert(Node<T> n, Node<T> cur) {
        if (cur == null) {
            return n; // If the current node is null, the new node is inserted here.
        } else {
            if (cur.getPayload().compareTo((T) n.getPayload()) > 0) {
                cur.left = insert(n, cur.left); // Recursively insert into the left subtree.
            } else {
                cur.right = insert(n, cur.right); // Recursively insert into the right subtree.
            }
            return cur;
        }
    }

    // Public method to remove a node by title from the tree.
    public Node<T> BSTRemoveNode(String temp) {
        Node<T> target = new Node<T>(new DVD<>(temp, 0, 0));

        // Call the private removeRec method to remove the node with the specified title.
        root = removeRec(root, null, target);

        return root;
    }

    // Private method to recursively remove a node from the tree.
    private Node<T> removeRec(Node<T> current, Node<T> parent, Node<T> target) {
        if (current == null) {
            return null; // Node with the specified key was not found
        }

        int comparison = target.compareTo(current);
        if (comparison == 0) {
            // Case 1: Internal node with 2 children
            if (current.left != null && current.right != null) {
                // Find the minimum node in the right subtree and replace the current node with it.
                Node<T> successor = findMin(current.right);
                current.payload = successor.payload;
                current.right = removeRec(current.right, current, successor);
            } else if (current == root) {
                if (current.left != null)
                    root = current.left;
                else
                    root = current.right;
                // If there is a child on the left
            } else if (current.left != null) {
                if (parent.left == current) {
                    parent.left = current.left;
                } else {
                    parent.right = current.left;
                }
                //if the child on the right
            } else if (current.right != null) {
                if (parent.left == current) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
            } else {
                return null; // Current node is a leaf node, simply remove it.
            }
            return current;
        } else if (comparison < 0) {
            current.left = removeRec(current.left, current, target); // Recursively search the left subtree.
        } else {
            current.right = removeRec(current.right, current, target); // Recursively search the right subtree.
        }
        return current;
    }

    // Private method to find the minimum node in a subtree.
    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
