package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements MutableGraph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. For example, a graph
     * with four vertices {v1, v2, v3, v4} and four edges {(v1,v1), (v1,v2), (v3,v1), (v3,v2)}
     * is represented by the map {v1 |-> {v1,v2}, v2 |-> {}, v3 -> {v1,v2}, v4 -> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return adjMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if(containsVertex(vertex)) return false;
        else {
            SortedSet<N> newSortedSet = new TreeSet<N>();
            adjMap.put(vertex, newSortedSet);
            return true;
        }
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if(containsVertex(vertex)){
            Set<N> sources = this.getSources(vertex);
            for(N source : sources){
                this.removeEdge(source, vertex);
            }
            adjMap.remove(vertex);
            return true;
        }
        else return false;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        SortedSet<N> targets = adjMap.get(source);
        if(targets == null) return false;
        for(N trg: targets){
            if(target == trg) return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(containsEdge(source, target)) return false;
        else {
            if(!containsVertex(source)) addVertex(source);
            if(!containsVertex(target)) addVertex(target);
            SortedSet<N> targets = adjMap.get(source);
            targets.add(target);
            adjMap.put(source, targets);
            return true;
        }
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(containsEdge(source, target)){
            SortedSet<N> targets = adjMap.get(source);
            targets.remove(target);
            adjMap.put(source, targets);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        Set<N> vertices = getVertices();
        Set<N> sources = new HashSet<N>();
        for(N vertex : vertices){
            Set<N> targets = getTargets(vertex);
            for(N trg : targets){
                if(trg == target){
                    sources.add(vertex);
                    break;
                }
            }
        }
        return sources;
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        return adjMap.get(source);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object.
     *
     * @return true if the representation of this graph is valid
     */
    boolean checkInv() {
        // TODO: implement this
        Set<N> vertices = getVertices();
        for(N vertex : vertices){
            Set<N> targets = getTargets(vertex);
            for(N trg : targets){
                if(!vertices.contains(trg)) return false;
            }
        }
        return true;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the graph
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
