public class Main {
    public static BacktrackingBST tree = new BacktrackingBST(new Stack(), new Stack());

    public static void main(String[] args) {
        BacktrackingBST.Node checkNode = node(50);
        insert(checkNode);
        insert(node(60));
        insert(node(55));
        insert(node(56));
        insert(node(51));
        insert(node(100));
        insert(node(40));
        insert(node(30));
        delete(checkNode);
        backtrack();
    }

    public static BacktrackingBST.Node node(int k) {
        return new BacktrackingBST.Node(k, 9);
    }

    public static void insert(BacktrackingBST.Node node) {
        tree.insert(node);
        tree.print();
        System.out.println();
    }

    public static void delete(BacktrackingBST.Node node) {
        tree.delete(node);
        tree.print();
        System.out.println();
    }

    public static void backtrack() {
        tree.backtrack();
        tree.print();
        System.out.println();
    }

    public static void retrack() {
        tree.retrack();
        tree.print();
        System.out.println();
    }

}
