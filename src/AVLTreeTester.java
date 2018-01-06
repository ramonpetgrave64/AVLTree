/**
 * Created by ramon on 1/6/18.
 */


public class AVLTreeTester {
    public static void main(String[] args) {
//        System.out.println(tester());
        go();
    }

    public static void go() {
        AVLTree tree = new AVLTree();
        int[] keys = new int[] {54, 52, 67, 87, 65, 43, 64, 36, 77, 98, 76, 53,
                45, 68, 41, 23, 56, 78, 79, 33, 59, 8, 55, 69,
                32, 34, 35, 42};
        for (int i : keys)
            tree.avlInsert(new AVLTree.Node(i));
        tree.inOrderTreeWalk(tree.root);
        System.out.println("_______________________________");
        tree.printTreeView();
        System.out.println("isAVL: " + tree.checkAVL());
        System.out.println("tree size: " + keys.length);
    }

    public static boolean tester() {
        boolean works = true;
        for(int j = 0; j < 1000; j++) {
            AVLTree tree = new AVLTree();
            int size = 3000;
            int range = 1000;
            int[] keys = new int[(int)(Math.random()*size)];
            for (int i : keys) {
                i = (int) (Math.random() * range);
                tree.avlInsert(tree.root, new AVLTree.Node(i));
            }
            if (!tree.checkAVL()) {
                works = false;
            }
        }
        return works;
    }
}

class AVLTree {
    public static final Node nil = new Sentinel();

    public Node root = nil;

    public void avlInsert(Node z) {
        avlInsert(root, z);
    }

    public void avlInsert(Node x, Node z) {
        if (root == nil)
            root = z;
        else {
            if (z.key < x.key)
                if (x.left == nil) {
                    x.left = z;
                    z.p = x;
                } else
                    avlInsert(x.left, z);
            else
            if (x.right == nil) {
                x.right = z;
                z.p = x;
            } else
                avlInsert(x.right, z);
            balance(x);
        }
    }

    public void balance(Node x) {
        int diff = x.left.h - x.right.h;
        if (Math.abs(diff) > 1)
            if (diff > 0)
                rightRotate(x);
            else
                leftRotate(x);
        else
            updateHeight(x);
    }

    public void updateHeight(Node x) {
        if (x != nil) {
            int newH = 1 + Math.max(x.left.h, x.right.h);
            if (x.h != newH) {
                x.h = newH;
                updateHeight(x.p);
            }
        }
    }

    public void leftRotate(Node x) {
        if (x.right.left.h > x.right.right.h)  // do a double rotation.
            rightRotate(x.right);
        Node y = x.right;
        x.right = y.left;
        if (y.left != nil)
            y.left.p = x;
        y.p = x.p;
        if (x.p == nil)
            root = y;
        else if (x == x.p.left)
            x.p.left = y;
        else
            x.p.right = y;
        y.left = x;
        x.p = y;

        updateHeight(x);
    }

    public void rightRotate(Node x) {
        if (x.left.right.h > x.left.left.h)  // do a double rotation.
            leftRotate(x.left);
        Node y = x.left;
        x.left = y.right;
        if (y.right != nil)
            y.right.p = x;
        y.p = x.p;
        if (x.p == nil)
            root = y;
        else if (x == x.p.right)
            x.p.right = y;
        else
            x.p.left = y;
        y.right = x;
        x.p = y;

        updateHeight(x);
    }

    public void inOrderTreeWalk(Node n) {
        if (n != nil) {
            inOrderTreeWalk(n.left);
            System.out.println(n);
            inOrderTreeWalk(n.right);
        }
    }

    public boolean checkAVL() {
        return checkAVL(root);
    }

    public boolean checkAVL(Node n) {
        if (n == nil)
            return true;
        else if(Math.abs(n.left.h - n.right.h) > 1)
            return false;
        else if(n.h != 1 + Math.max(n.left.h, n.right.h))
            return false;
        else
            return ((checkAVL(n.left) && checkAVL(n.right)));
    }

    public void printTreeView() {
        root.printTreeView(0);
    }

    protected static class Sentinel extends Node {
        public Sentinel() {
            super(Integer.MAX_VALUE);
            h = -1;
        }
    }

    static class Node {
        int key;
        int h;

        Node p;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
            h = 0;
            p = left = right = AVLTree.nil;
        }

        public void printTreeView(int level) {
            if (this != AVLTree.nil) {
                right.printTreeView(level + 1);

                String spaces = "";
                for (int i = 0; i < level; i++)
                    spaces += "      ";
                System.out.println(spaces + this);

                left.printTreeView(level + 1);
            }
        }

        @Override
        public String toString() {
            return "(" + key + "," + h + ")";
        }
    }
}