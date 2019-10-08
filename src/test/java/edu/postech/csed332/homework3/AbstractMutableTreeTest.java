package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * An abstract test class for MutableTree with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <T> type of Tree
 */
@Disabled
public abstract class AbstractMutableTreeTest<V extends Comparable<V>, T extends MutableTree<V>> {

    T tree;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of tree

    @Test
    void testGetDepthRoot() {
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getDepth(v1), 0);
    }

    @Test
    void testGetDepthTwo() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getDepth(v2), 1);
    }

    @Test
    void testGetDepthNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getDepth(v1));
    }

    @Test
    void testGetDepthNoVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.getDepth(v2));
    }

    // TODO: write black-box test casess for each method of MutableTree with respect to
    //  the specification, including the methods of Tree that MutableTree extends.

    @Test
    void testGetRoot() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertEquals(tree.getRoot().get(), v1);
    }

    @Test
    void testGetRootOptionalEmpty() {
        Assertions.assertEquals(tree.getRoot(), Optional.empty());
    }

    @Test
    void testGetHeightWithoutRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getHeight());
    }

    @Test
    void testGetHeight() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertEquals(tree.getHeight(), 0);
    }

    @Test
    void testContainsVertex() {
        Assertions.assertFalse(tree.containsVertex(v1));
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.containsVertex(v1));
    }

    @Test
    void testAddVertexFalse() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertFalse(tree.addVertex(v1));
    }

    @Test
    void testRemoveVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v1, v3));
        Assertions.assertTrue(tree.addEdge(v2, v4));
        Assertions.assertTrue(tree.removeVertex(v2));
    }

    @Test
    void testRemoveVertexFalse() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v1, v3));
        Assertions.assertTrue(tree.addEdge(v2, v4));
        Assertions.assertTrue(checkInv());
        Assertions.assertTrue(tree.removeVertex(v3));
        Assertions.assertFalse(tree.removeVertex(v3));
    }

    @Test
    void testAddEdgesFalse() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertFalse(tree.addEdge(v2, v3));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertFalse(tree.addEdge(v1, v2));
    }

    @Test
    void testRemoveEdge() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v1, v3));
        Assertions.assertTrue(tree.addEdge(v2, v4));
        Assertions.assertTrue(tree.removeEdge(v1, v2));
    }

    @Test
    void testRemoveEdgeFalse() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.addEdge(v1, v3));
        Assertions.assertTrue(tree.removeEdge(v1, v3));
        Assertions.assertFalse(tree.removeEdge(v1, v3));
    }

    @Test
    void testToString() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        System.out.println(tree.toString());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void containsEdge() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.containsEdge(v1, v2));
        Assertions.assertFalse(tree.containsEdge(v1, v3));
    }
}
