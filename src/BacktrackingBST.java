import java.util.NoSuchElementException;

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    private BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        if (root == null) {
            throw new NoSuchElementException("empty tree has no root");
        }
        return root;
    }

    public Node search(int k) {
        if (root == null)
            return null;
        Node current = root;
        while (true) {
            if (current.key == k)
                return current;
            else if (current.key < k & current.right != null)
                current = current.right;
            else if (current.key > k & current.left != null)
                current = current.left;
            else
                return null;
        }
    }

    public void insert(Node node) {
        boolean changed = normalInsert(node);
        if (changed) {
            emptyRetrack();
        }
    }

    public void delete(Node node) {
        if (search(node.key) != null) {
            normalDelete(node);
            emptyRetrack();
        }
        else throw new RuntimeException("cannot delete node that is not in the tree");
    }

    // because when the user insert or delete i dont want to save the retrack stuck, but i want a functions to delete and insert
    public void normalDelete(Node node) {

        stack.push(node);
        choosesCorrectDelete(node);

    }

    private boolean normalInsert(Node node) {
        boolean changed = false;
        stack.push(node);
        stack.push(false);
        if (root == null) {
            root = node;
            node.setParent(null);
        } else {

            Node current = root;
            boolean stop = false;
            while (!stop) {
                if (current.key > node.key) {
                    if (current.left != null)
                        current = current.left;
                    else {
                        stop = true;
                    }
                } else if (current.key < node.key) {
                    if (current.right != null)
                        current = current.right;
                    else {
                        stop = true;
                    }
                } else
                    stop = true;

            }
            changed = current.getKey() != node.getKey();
            if (current.getKey() != node.getKey())
                current.putChild(node);
        }
        return changed;
    }

    private void choosesCorrectDelete(Node node) {
        if (node.hasTwoChildren()) {
            delete2ChildrenNode(node);
            stack.push(true);
        } else if (node.isLeaf()) {
            deleteLeaf(node);
            stack.push(false);

        } else {
            delete1ChildrenNode(node);
            stack.push(false);
        }
    }

    private void delete2ChildrenNode(Node node) {
        Node successor = successor(node);
        if (successor.isLeaf()) {
            deleteLeaf(successor);
        } else {
            delete1ChildrenNode(successor);
        }
        stack.push(successor.getParent());

        if (root != node) {
            Node parent = node.getParent();
            parent.putChild(successor);


        } else {
            root = successor;
            root.setParent(null);
        }
        if (node.right != null)
            successor.putChild(node.right);
        else
            successor.right =null;
        if (node.left != null)
            successor.putChild(node.left);
        else
            successor.left = null;
    }

    private void deleteLeaf(Node node) {
        if (node == root) {
            root = null;
        } else {
            Node parent = node.getParent();
            if (node.key < parent.key) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
    }

    private void delete1ChildrenNode(Node node) {
        if (node != root) {
            Node parent = node.getParent();
            if (node.right != null) {
                parent.putChild(node.right);
            } else {
                parent.putChild(node.left);
            }
        } else {
            if (node.right != null) {
                root = node.right;
            } else {
                root = node.left;
            }
            root.setParent(null);
        }
    }

    public Node minimum() {
        if (root == null)
            throw new RuntimeException("no minimum in empty tree");
        Node current = root;
        while (current.left != null)
            current = current.left;
        return current;
    }


    public Node maximum() {
        if (root == null)
            throw new RuntimeException("No maximum in empty tree");
        Node current = root;
        while (current.right != null)
            current = current.right;
        return current;
    }

    public Node successor(Node node) {
        if (search(node.key) == null) {
            throw new RuntimeException("This node does not exist in the tree");
        }

        if (node.right != null)
            return hasRightChildSuccessor(node);

        else
            return notHasRightChildSuccessor(node);
    }

    private Node hasRightChildSuccessor(Node node) {
        Node current = node.right;
        while (current.left != null)
            current = current.left;
        return current;
    }

    private Node notHasRightChildSuccessor(Node node) {
        Node output = node.getParent();
        while (output != null && output.key < node.key)
            output = output.getParent();

        if (output == null)
            throw new RuntimeException("this node has no successor");
        return output;
    }

    public Node predecessor(Node node) {
        if (search(node.key) == null) {
            throw new RuntimeException("This node does not exist in the tree");
        }
        Node output;

        if (node.left != null)
            output= hasLeftChildPredecessor(node);

        else
            output =notHasLeftChildPredecessor(node);

        if (output== null)
            throw new RuntimeException("This node dose not have a predecessor");

        return output;
    }

    private Node hasLeftChildPredecessor(Node node) {
        Node current = node.left;
        while (current.right != null)
            current = current.right;
        return current;
    }

    private Node notHasLeftChildPredecessor(Node node) {
        Node output = node.getParent();
        while (output != null && output.key > node.key)
            output = output.getParent();

        if (output == null)
            throw new RuntimeException("This node does not have successor");
        return output;
    }

    @Override
    public void backtrack() {
        Node redoNode;

        if (stack.isEmpty()) {
            throw new RuntimeException("Nothing to backtrack");
        }
        boolean deletedWith2Child = (boolean) stack.pop();
        if (deletedWith2Child) {
            redoNode = backtrackDelete2Child();
        } else {
            redoNode = (Node) stack.pop();
            Node parents =redoNode.getParent();
            if (parents == null) {
                if (root== null)
                    backTrackNormalDelete(redoNode);
                else
                    backtrackInsert(redoNode);

            }
            else {
                if (parents.right == redoNode | parents.left == redoNode)
                    backtrackInsert(redoNode);
                else
                    backTrackNormalDelete(redoNode);
            }

        }
        redoStack.push(redoNode);

    }


    private Node backtrackDelete2Child() {
        Node successorParent = (Node) stack.pop();
        Node toReturn = (Node) stack.pop();
        Node successor;
        if (toReturn.getParent() == null) {
            successor = root;
            root = toReturn;
        } else {
            Node toReturnParents = toReturn.parent;
            if (toReturnParents.getKey() > toReturn.getKey())
                successor = toReturnParents.left;
            else
                successor = toReturnParents.right;

            successor.getParent().putChild(toReturn);
        }


        if (successorParent == toReturn) {
            toReturn.putChild(successor);
            toReturn.putChild(successor.left);
        } else {
            toReturn.putChild(successor.right);
            toReturn.putChild(successor.left);
            Node successorRight = successorParent.left;
            if (successorRight != null)
                successor.putChild(successorRight);
            else {
                successor.right = null;
            }
            successorParent.putChild(successor);
        }

        successor.left = null;
        return toReturn;
    }

    private void backTrackNormalDelete(Node redoNode) {
        Node redoNodeChild;
        if (root == null)
            root = redoNode;
        else {
            Node redoNodeParent = redoNode.getParent();
            if (redoNode.getKey() < redoNodeParent.getKey()) {
                redoNodeChild = redoNodeParent.left;
            } else
                redoNodeChild = redoNodeParent.right;

            redoNodeParent.putChild(redoNode);
            if (redoNodeChild != null)
                redoNode.putChild(redoNodeChild);
        }
    }


    private void backtrackInsert(Node node) {
        deleteLeaf(node);
    }

    @Override
    public void retrack() {
        if (redoStack.isEmpty())
            throw new RuntimeException("No operation to undo");

        Node node = (Node) redoStack.pop();

        if (search(node.getKey()) == null)
            normalInsert(node);
        else
            normalDelete(node);
    }

    private void emptyRetrack() {
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    public void printPreOrder() {
        if (root == null) {
            System.out.println("");
        } else {
            root.printPreOrder(root);
        }
    }

    @Override
    public void print() {
        printPreOrder();
    }

    public static class Node {
        // These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }


        public Node getParent() {
            return parent;
        }

        public void putChild(Node node) {
            if (node.key > this.key)
                right = node;
            else
                left = node;
            node.setParent(this);
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }


        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public boolean hasTwoChildren() {
            return left != null & right != null;
        }

        public boolean isLeaf() {
            return left == null & right == null;
        }

        public void printPreOrder(Node root) {
            if (this == root)
                System.out.print(key);
            else {
                System.out.print(" " + key);

            }
            if (left != null)
                left.printPreOrder(root);
            if (right != null)
                right.printPreOrder(root);
        }
    }

}
