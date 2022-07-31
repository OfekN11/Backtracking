public class BinaryNode<T> {

    protected T data;
    protected BinaryNode<T> left;
    protected BinaryNode<T> right;

    public BinaryNode(T element) {
        if (element == null)
            throw new IllegalArgumentException();
        this.data = element;
        left = null;
        right = null;
    }

    public void insert(T element) {
        if (Math.random() < 0.5) {
            if (left == null)
                left = new BinaryNode<T>(element);
            else
                left.insert(element);
        }
        else {
            if (right == null)
                right = new BinaryNode<T>(element);
            else
                right.insert(element);
        }
    }
    public BinaryNode<T> remove(T toRemove) {
        BinaryNode<T> output = this;

        if (data.equals(toRemove)) {
            if (left != null) {
                data = left.data;
                left = left.remove(data);
            }
            else if (right != null) {
                data = right.data;
                right = right.remove(data);
            }
            else output = null;
        }
        else {
            if (left != null && left.contains(toRemove))
                left = left.remove(toRemove);
            else if (right != null)
                right = right.remove(toRemove);
        }
        return output;
    }
    public boolean contains(T element) {
        boolean found = false;
        if(data.equals(element))
            found = true;
        else if(left != null && left.contains(element))
            found = true;
        else if(right!= null && right.contains(element))
            found = true;
        return found;
    }
    public int height() {
        int leftH = -1, rightH = -1;
        if(left != null)
            leftH = left.height();
        if(right != null)
            rightH = right.height();
        return Math.max(leftH, rightH) + 1;
    }

    public int size() {
        int leftS = 0, rightS = 0;
        if(left != null)
            leftS = left.size();
        if(right != null)
            rightS = right.size();
        return leftS + rightS + 1;
    }

    public void printInOrder() {
        if(left != null)
            left.printInOrder();
        System.out.println(data.toString());
        if(right != null)
            right.printInOrder();
    }

    public void printPreOrder() {
        System.out.println(data.toString());
        if(left != null)
            left.printPreOrder();
        if(right != null)
            right.printPreOrder();
    }

    public void printPostOrder() {
        if (left != null)
            left.printPostOrder();
        if (right != null)
            right.printPostOrder();
        System.out.println(data.toString());
    }

    public boolean equals(Object other) {
        boolean isEqual = false;
        if (other instanceof BinaryNode<?>) {
            BinaryNode<?> otherNode = (BinaryNode<?>) other;
            isEqual = data.equals(otherNode.data)
                    &&
                    ((left == null & otherNode.left == null) |
                            (left != null && left.equals(otherNode.left)))
                    &&
                    ((right == null & otherNode.right == null) |
                            (right != null && right.equals(otherNode.right)));
        }
        return isEqual;
    }

}