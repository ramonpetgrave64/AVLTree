# AVLTree
From Algorithms class, this is a demonstration of an AVL Tree, maintaining balance upon avlInsert().
It makes use of a Sentinel node (as seen in the "Cormen's Algoriths" textook for Red-Black Trees), called nil, where the tree's virtual root (parent of the actual root) is nil, and the default children of a leaf node is also nil.

My tree's nodes also have a field for their current height. A Sentinel node will have a hieght of -1, and a leaf node a height of 0.

Double rotations are handled recurrently (recursively?) between the leftRotate() and righRotate() methods: If a there needs to be a double-left rotation on a node x, then first do a single-right rotation on x.left, and then do a single-left roation on x.

remove() and contains() methods can be added, as well as a Node.data field.
