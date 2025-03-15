public final class Trie {
    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String key, String value) {
        insert(root, key, value);
    }

    private void insert(Node node, String key, String value) {
        int length = key.length();
        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                node.children[index] = new Node();
            }
            node = node.children[index];
        }
        node.value = value;
    }

    public boolean contains(String value) {
        return search(value) != null;
    }

    public String search(String key) {
        return search(root, key);
    }

    private String search(Node node, String key) {
        int length = key.length();
        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node.value;
    }
    
    private static final class Node {
        private static final int ALPHABET_SIZE = 26;

        private final Node[] children;
        
        private String value;
        
        public Node() {
            children = new Node[ALPHABET_SIZE];
            value = null;
        }
    }
}
