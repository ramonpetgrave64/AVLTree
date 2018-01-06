//package HW4;
/**
 * Created by ramon on 7/17/17.
 */
public class AVLTreeTester {

    public static int insertCount, balanceCount, updateHeightCount = 0;
    public static int leftRotateCount, doubleLeftRotateCount = 0;
    public static int rightRotateCount, doubleRightRotateCount = 0;


    public static void main(String[] args) {
        System.out.println("All valid AVLs: " + tester());
//        go();
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
        int iterations = 10;
        for(int j = 0; j < iterations; j++) {
            resetTestVariables();
            AVLTree tree = new AVLTree();
            int size = 1000;
            int range = 10000;
//            int[] keys = new int[(int)(Math.random()*size)];
            int[] keys = new int[size];
            for (int i : keys) {
                i = (int) (Math.random() * range);
                tree.avlInsert(tree.root, new AVLTree.Node(i));
            }
            if (!tree.checkAVL()) {
                works = false;
            }
            printTestVariables();
//            tree.printTreeView();
        }
        return works;
    }

    public static void resetTestVariables() {
        insertCount = balanceCount = updateHeightCount = 0;
        leftRotateCount = doubleLeftRotateCount = 0;
        rightRotateCount = doubleRightRotateCount = 0;
    }

    public static void printTestVariables() {
        System.out.printf("I: %d\n" +
                        "Bln: %d, Upd: %d\n" +
                        "LR: %d, DLR: %d\n" +
                        "RR: %d, DRR: %d\n" +
                        "_______________\n",
                insertCount, balanceCount, updateHeightCount,
                leftRotateCount, doubleLeftRotateCount,
                rightRotateCount, doubleRightRotateCount);
    }
}

class AVLTree {
    public static final Node nil = new Sentinel();

    public Node root = nil;

    public void avlInsert(Node z) {
        avlInsert(root, z);
    }

    public void avlInsert(Node x, Node z) {
        AVLTreeTester.insertCount++;
        if (root == nil)
            root = z;
        else {
            if (z.key < x.key)
                if (x.left == nil) {
                    x.left = z;
                    z.p = x;
                } else
                    avlInsert(x.left, z);
            else {
                if (x.right == nil) {
                    x.right = z;
                    z.p = x;
                } else
                    avlInsert(x.right, z);
            }
            balance(x);
        }
    }

    public void balance(Node x) {
        int diff = x.left.h - x.right.h;
        if (Math.abs(diff) > 1) {
            if (diff > 0)
                rightRotate(x);
            else
                leftRotate(x);
            balance(x.p);
//            updateHeight(x.p);
            AVLTreeTester.balanceCount++;
        }
        else
            updateHeight(x);
    }

    public void updateHeight(Node x) {
        if (x != nil) {
            AVLTreeTester.updateHeightCount++;
            int newH = 1 + Math.max(x.left.h, x.right.h);
            if (x.h != newH) {
                x.h = newH;
//                updateHeight(x.p);
            }
        }
    }

    public void leftRotate(Node x) {
        AVLTreeTester.leftRotateCount++;
        if (x.right.left.h > x.right.right.h) {  // do a double rotation.
            AVLTreeTester.doubleLeftRotateCount++;
            rightRotate(x.right);
        }
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
        AVLTreeTester.rightRotateCount++;
        if (x.left.right.h > x.left.left.h) {  // do a double rotation.
            AVLTreeTester.doubleRightRotateCount++;
            leftRotate(x.left);
        }
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
            super(0);
            h = -1;
            p = left = right = this;
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