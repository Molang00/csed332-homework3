package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree that delegates to a given instance of Graph. This class
 * is a wrapper of a MutableGraph instance that enforces the Tree invariant.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class DelegateTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * The underlying graph of this tree
     */
    private final @NotNull MutableGraph<N> delegate;

    /**
     * A map assigning a depth to each vertex in this tree
     */
    private final @NotNull Map<N, Integer> depthMap;

    /**
     * Creates an empty tree that delegates to a given graph.
     *
     * @param emptyGraph an empty graph
     * @throws IllegalArgumentException if {@code emptyGraph} is not empty
     */
    public DelegateTree(@NotNull MutableGraph<N> emptyGraph) {
        if (!emptyGraph.getVertices().isEmpty())
            throw new IllegalArgumentException();

        delegate = emptyGraph;
        depthMap = new HashMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        // TODO: implement this
        if(delegate.getVertices().isEmpty()) return Optional.empty();
        else{
            @NotNull Optional<N> rst = Optional.of(root);
            return rst;
        }
    }

    @Override
    public int getDepth(@NotNull N vertex) throws IllegalStateException, IllegalArgumentException{
        // TODO: implement this

        if(root == null) throw new IllegalStateException();
        if(!delegate.containsVertex(vertex)) throw new IllegalArgumentException();
        return depthMap.get(vertex);
    }

    @Override
    public int getHeight() throws IllegalStateException {
        // TODO: implement this
        if(root == null) throw new IllegalStateException();
        return Collections.max(depthMap.values());
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return delegate.containsVertex(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if(delegate.getVertices().isEmpty()){
            delegate.addVertex(vertex);
            root = vertex;
            depthMap.put(root, 0);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        Set<N> children = delegate.getTargets(vertex);
        if(delegate.removeVertex(vertex)){
            for(N child : children){
                this.removeEdge(vertex, child);
            }
            depthMap.remove(vertex);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return delegate.containsEdge(source, target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!delegate.containsVertex(source)) return false;
        if(delegate.containsVertex(target)) return false;
        delegate.addEdge(source, target);
        depthMap.put(target, depthMap.get(source)+1);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        Set<N> children = delegate.getTargets(target);
        Set<N> sources = this.getSources(target);
        if(containsEdge(source, target) && delegate.removeEdge(source, target)){
            for(N child : children){
                this.removeEdge(target, child);
            }
            return this.removeVertex(target);
        }
        return false;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        return delegate.getSources(target);
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        return delegate.getTargets(source);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return delegate.getVertices();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        return delegate.getEdges();
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        // TODO: implement this
        List<N> vertices = new ArrayList<N>(getVertices());
        List<Boolean> visitCheckers = new ArrayList<Boolean>();
        for(Integer i = 0; i < vertices.size(); i++) visitCheckers.add(false);
        for(Integer i = 0; i < vertices.size(); i++){
            N vertex = vertices.get(i);
            List<N> targets = new ArrayList<N>(getTargets(vertex));
            for(N trg : targets){
                if(!vertices.contains(trg)) return false;
            }
            if(visitCheckers.get(i)) return false;
            else visitCheckers.set(i, true);
        }
        
        for(Integer i = 0; i < vertices.size(); i++) if(!visitCheckers.get(i)) return false;
        return true;
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
