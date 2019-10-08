# Problem 1


Let $`\mathcal{N}`$ be the set of all elements of type $`\textsf{N}`$, and $`\mathsf{null} \notin \mathcal{N}`$ be an distinguished element to represent `null`. Write formal abstract specifications of the interfaces below with respect to following abstract values:

- A graph is a pair $`G = (V, E)`$, where $`V \subseteq \mathcal{N}`$ and $`E \subseteq V \times V`$.
- A tree is a triple $`T = (V, E, \hat{v})`$, where $`(V, E)`$ is a graph and $`\hat{v} \in \mathcal{N}`$ denotes the root.

Other data types, such as `boolean`, `int`, `Set<N>`, etc. have conventional abstract values, e.g., Boolean values, integers, and subsets of $`\mathcal{N}`$, etc.

## `Graph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### containsVertex

```java 
boolean containsVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if vertex is in $`V_{this}`$; and
    - returns false, otherwise.

##### containsEdge

```java
boolean containsEdge(N source, N target);
```

- requires: 
  - source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
  - returns true if (source, target) is in $`E_{this}`$; and
  - returns false, otherwise.

##### getSources

```java
Set<N> getSources(N target);
```

- requires: target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures: 
  - returns Set of source that satisfied $`\forall (source, target) \in E_{this}.\ source \in V_{this}`$

##### getTargets

```java
Set<N> getTargets(N source);
```

- requires: source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - returns Set of target that satisfied $`\forall (source, target) \in E_{this}.\ target \in V_{this}`$


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
\forall w \n (\hat{v}_{this}, w) has T_{new} = (V_{w}, E_{w}, \hat{v}_{w})
that \bigcap V_{w} = \empty, \bigcup V_{w} = V_{this} - \hat{v}_{this},
\bigcap E_{w} = \empty, \bigcup E_{w} = E_{this} - \forall(\hat{v}_{this}, w),
\hat{v}_{w} = w
```

##### getDepth

```java
int getDepth(N vertex);
```

- requires: 
  + vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
  + getRoot.isPresent()
- ensures:  
  + returns 0 if vertex is getRoot.get(); and
  + returns getDepth(getParent(vertex)) + 1, otherwise.

##### getHeight

```java
int getHeight();
```

- requires: getRoot.isPresent()
- ensures: 
  - returns maximum value of getDepth(vertex) that all vertex is in $`\mathcal{N}`$.

##### getRoot

```java
Optional<N> getRoot();
```

- requires:
- ensures: 
  - returns $`\hat{v}_{this}`$ if exist; and
  - returns Optional.empty()

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
- ensures: 
  - returns source that $`(source, vertex) \in E_{this}`$ if source exist; and
  - returns Optional.empty()


## `MutableGraph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object,
and $`G_{next} = (V_{next}, E_{next})`$ be an abstract value of the graph object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} \cup \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - $`V_{next} = V_{this} - \{\texttt{vertex}\}`$
  - $`E_{next} = E_{this} - \forall (vertex, v) - \forall (w, vertex)`$ (the edges are not modified)
  - If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`\texttt{vertex} \in V_{this}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires:
  - source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - $`V_{next} = V_{this} \cup \{\texttt{source}\} \cup \{\texttt{target}\}`$ 
  - $`E_{next} = E_{this} \cup (source, target)`$
  - If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`(source, target) \notin E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: 
  - source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - $`V_{next} = V_{this}`$ (the vertexes are not modified)
  - $`E_{next} = E_{this} - (source, target) `$
  - If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`(source, target) \in E_{this}`$.


## `MutableTree<N>`

##### Class invariant 

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
  - If $`V_{this} is not empty`$, just return false;
  - $`V_{next} = \{\texttt{vertex}\}`$
  - $`\hat{v}_{next} = \{\texttt{vertex}\}`$
  - $`E_{next} = E_{this}`$ (the edges are not modified)
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`V_{this} is empty`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - $`V_{next} = V_{this} - \{\texttt{vertex}\} - \{\texttt{descendants of vertex}\}`$
  - $`E_{next} = E_{this} - \{\texttt{all edges that connect with parent of vertex, vertex and descendants of vertex}}`$
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`\texttt{vertex} \in V_{this}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires:
  - source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - If $`source \notin V_{this} or target \in V_{This}`$ just return false;
  - $`V_{next} = V_{this} \cup target`$
  - $`E_{next} = E_{this} \cup (source, target)`$
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`(source, target) \notin E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: 
  - source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
  - target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:
  - $`V_{next} = V_{this} - \{\texttt{target}\} - \{\texttt{descendants of target}\} `$
  - $`E_{next} = E_{this} - \{\texttt{all edges that connect with parent of target, vertex and descendants of target}}`$
  - If $`T_{this}`$ satisfies the class invariant, $`T_{next}`$ also satisfies the class invariant; and
  - returns true if and only if $`(source, target) \in E_{this}`$.


# Problem 2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?
Yes, because all of the abstract method in `Graph<N>` such as containsVertex, containsEdge like these can use at `Tree<N>` similarly. For example, containsVertex is use at interface `Graph<N>`for check the parameter vertex is in the `Graph<N>`, and at interface `Tree<N>`it works similarly. Basically `Tree<N>`is an subset of `Graph<N>`, we can treat all of methods in the `Graph<N>`at `Tree<N>`. So it means `Tree<N>`is a subtype of `Graph<N>`is satisfy the Liskov substitution principle.

##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`
Yes, because all of the abstract method in `Graph<N>` such as getSources, getTargets like these can use at `MutableGraph<N>`similarly. For example, getSources is use at interface `Graph<N>`for getting sources that makes edge with the parameter target, and at interface `MutableGraph<N>` it works similarly. Basically `MutableGraph<N>`is an subset of `Graph<N>`, we can treat all of methods in the `Graph<N>`at `MutableGraph<N>`. So it means `MutableGraph<N>`is a subtype of `Graph<N>` is satisfy the Liskov substitution principle.

##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`
Yes, because all of the abstract method in `Tree<N>` such as getDepth, getHeight like these can use at `MutableTree<N>` similarly. For example, getDepth is use at interface `Tree<N>` for getting depth value of the parameter vertex, and at interface `MutableTree<N>`it works similarly. Basically `MutableTree<N>`is an subset of `Tree<N>`, we can treat all of methods in the `Tree<N>` at `MutableTree<N>`. So it means `MutableTree<N>`is a subtype of `Tree<N>`is satisfy the Liskov substitution principle.

##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`
No, because some method in `MutableTree<N>` such as addVertex, removeEdge like these can't use at `MutableTree<N>` similarly. For example, addVertex is use at interface `MutableGraph<N>`for adding the parameter vertex to graph, but same method is use at interface `MutableTree<N>`for adding the parameter vertex to graph and also root of tree, when only tree is empty. Other example, removeEdge is use at interface `MutableTree<N>`for just removing the edge made by the parameters source and target, but same method is use at interface `MutableTree<N>`for removing all of the edge that connected with target and descendants of them. So we can't expect real working when `MutableTree<N>`is subtype of `MutableGraph<N>`. It means `MutableTree<N>`is not a subtype of `MutableGraph<N>`.