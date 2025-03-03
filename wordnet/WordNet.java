import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

/**
 * The WordNet digraph.
 * 
 * @author Yaohui Wu
 */
public final class WordNet {
    private final List<String> synsetsList;
    // Map from noun to list of synsets.
    private final Map<String, List<Integer>> synsetsMap;
    private final SAP sap; // Shortest ancestral path (SAP).

    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArgs(synsets, hypernyms);
        synsetsList = new ArrayList<>();
        synsetsMap = new HashMap<>();
        readSynsets(synsets);
        // WordNet is a rooted, directed acyclic graph (DAG).
        Digraph g = readHypernyms(hypernyms);
        sap = new SAP(g);
    }

    private void readSynsets(String synsets) {
        In synsetsFile = new In(synsets);
        while (synsetsFile.hasNextLine()) {
            String line = synsetsFile.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            synsetsList.add(synset);
            String[] words = synset.split(" ");
            for (String word : words) {
                if (!synsetsMap.containsKey(word)) {
                    synsetsMap.put(word, new ArrayList<>());
                }
                synsetsMap.get(word).add(id);
            }
        }
        synsetsFile.close();
    }

    private Digraph readHypernyms(String hypernyms) {
        Digraph g = new Digraph(synsetsMap.size());
        In hypernymsFile = new In(hypernyms);
        while (hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i += 1) {
                int w = Integer.parseInt(fields[i]);
                g.addEdge(v, w);
            }
        }
        hypernymsFile.close();
        validateDAG(g);
        return g;
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        Set<String> nouns = new HashSet<>(synsetsMap.keySet());
        return nouns;
    }
 
    /**
     * Returns true if the word is a WordNet noun.
     */
    public boolean isNoun(String word) {
        validateArgs(word);
        boolean isNoun = synsetsMap.containsKey(word);
        return isNoun;
    }
 
    /**
     * Distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        validateArgs(nounA, nounB);
        validateNouns(nounA, nounB);
        List<Integer> v = synsetsMap.get(nounA);
        List<Integer> w = synsetsMap.get(nounB);
        int distance = sap.length(v, w);
        return distance;
    }
 
    /**
     * A synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB.
     */
    public String sap(String nounA, String nounB) {
        validateArgs(nounA, nounB);
        validateNouns(nounA, nounB);
        List<Integer> v = synsetsMap.get(nounA);
        List<Integer> w = synsetsMap.get(nounB);
        int ancestor = sap.ancestor(v, w);
        String sap = synsetsList.get(ancestor);
        return sap;
    }
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String nounA = args[2];
        String nounB = args[3];
        int distance = wordnet.distance(nounA, nounB);
        String sap = wordnet.sap(nounA, nounB);
        System.out.println("distance = " + distance + ", sap = " + sap);
    }

    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                String error = "Argument cannot be null";
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateDAG(Digraph graph) {
        if (!hasOneRoot(graph)) {
            String error = "Graph does not have only one root";
            throw new IllegalArgumentException(error);
        }
        if (hasCycle(graph)) {
            String error = "Graph is not a directed acyclic graph";
            throw new IllegalArgumentException(error);
        }
    }

    private boolean hasOneRoot(Digraph graph) {
        int count = 0;
        for (int v = 0; v < graph.V(); v += 1) {
            if (isRoot(graph, v)) {
                count += 1;
                if (count > 1) {
                    return false;
                }
            }
        }
        boolean hasOneRoot = (count == 1);
        return hasOneRoot;
    }

    private boolean isRoot(Digraph graph, int v) {
        int out = graph.outdegree(v);
        int in = graph.indegree(v);
        boolean isRoot = (out == 0 && in > 0);
        return isRoot;
    }

    private boolean hasCycle(Digraph graph) {
        int order = graph.V();
        boolean[] visited = new boolean[order];
        boolean[] onStack = new boolean[order];
        for (int v = 0; v < order; v += 1) {
            if (dfs(graph, v, visited, onStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Depth-first search (DFS).
     */
    private boolean dfs(Digraph graph, int s, boolean[] visited, boolean[] onStack) {
        visited[s] = true;
        onStack[s] = true;
        for (int v : graph.adj(s)) {
            if (!visited[v]) {
                if (dfs(graph, v, visited, onStack)) {
                    return true;
                }
            } else if (onStack[v]) {
                return true;
            }
        }
        onStack[s] = false;
        return false;
    }

    private void validateNouns(String... nouns) {
        for (String noun : nouns) {
            if (!isNoun(noun)) {
                String error  = "Noun is not a WordNet noun";
                throw new IllegalArgumentException(error);
            }
        }
    }
}
