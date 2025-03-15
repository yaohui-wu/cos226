public final class Trie {
    private final Node root;

    public Trie() {
        root = null;
    }
    
    private static final class Node {
        private final char c;
        private final Node left;
        private final Node right;

        public Node(char c, Node left, Node right) {
            this.c = c;
            this.left = left;
            this.right = right;
        }
    }
}
