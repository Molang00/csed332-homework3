package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree, where each vertex has a reference to its parent node but
 * no references to its children.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class ParentPointerTree<N extends Comparable<N>> implements MutableTree<N> {

    private static class Node<V> {
        final @Nullable V parent;
        final int depth;

        Node(@Nullable V parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }
    }

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * A map assigning to each vertex a pair of a parent reference and a depth. The parent
     * of the root is {@code null}. For example, a tree with four vertices {v1, v2, v3, v4}
     * and three edges {(v1,v2), (v1,v3), (v2,v4)}, where v1 is the root, is represented by
     * the map {v1 |-> (null,0), v2 |-> (v1,1), v3 |-> (v1,1), v4 |-> (v2,2)}.
     */
    private final @NotNull SortedMap<N, Node<N>> nodeMap;

    /**
     * Creates an empty parent pointer tree.
     */
    public ParentPointerTree() {
        this.root = null;
        this.nodeMap = new TreeMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        // TODO: implement this
        if(root == null) return Optional.empty();
        else{
            @NotNull Optional<N> rst = Optional.of(root);
            return rst;
        }
    }

    @Override
    public int getDepth(@NotNull N vertex) throws IllegalStateException, IllegalArgumentException{
        // TODO: implement this
        if(nodeMap.isEmpty()) throw new IllegalStateException();
        if(!nodeMap.containsKey(vertex)) throw new IllegalArgumentException();
        return nodeMap.get(vertex).depth;
    }

    @Override
    public int getHeight() throws IllegalStateException{
        // TODO: implement this
        int maxDepth = Integer.MIN_VALUE;
        if(nodeMap.isEmpty()) throw new IllegalStateException();
        for(N vertex : nodeMap.keySet()){
            if(maxDepth < nodeMap.get(vertex).depth) maxDepth = nodeMap.get(vertex).depth;
        }
        return maxDepth;
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return nodeMap.keySet().contains(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if(root == null){
            nodeMap.put(vertex, new Node<N>(null, 0));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        Set<N> children = new HashSet<N>();
        if(!this.containsVertex(vertex)) return false;
        for(N node : nodeMap.keySet()){
            Node<N> nodeValue = nodeMap.get(node);
            if(nodeValue.parent == vertex) children.add(node);
        }
        for(N child : children) this.removeEdge(vertex, child);
        nodeMap.remove(vertex);
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        N src = nodeMap.get(target).parent;
        if(source == src) return true;
        else return false;
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!nodeMap.containsKey(source)) return false;
        if(nodeMap.containsKey(target)) return false;
        nodeMap.put(target, new Node<N>(source, nodeMap.get(source).depth+1));
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!nodeMap.containsKey(source)) return false;
        if(!nodeMap.containsKey(target)) return false;
        if(this.containsEdge(source, target)){
            Set<N> targets = this.getTargets(target);
            for(N trg : targets){
                this.removeVertex(trg);
            }
            nodeMap.remove(target);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        Set<N> rst = new HashSet<N>();
        rst.add(nodeMap.get(target).parent);
        return rst;
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        Set<N> rst = new HashSet<N>();
        Set<Edge<N>> edges = this.getEdges();
        for(Edge<N> edge : edges){
            if(edge.getSource() == source) rst.add(edge.getTarget());
        }
        return rst;
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return nodeMap.keySet();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        Set<Edge<N>> rst = new HashSet<Edge<N>>();
        for(N node : nodeMap.keySet()){
            N parent = nodeMap.get(node).parent;
            Edge<N> edge = new Edge<N>(parent, node);
            rst.add(edge);
        }
        return rst;
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        // TODO: implement this
        return false;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the tree
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[root: %s, vertex: {%s}, edge: {%s}]",
                root,
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
