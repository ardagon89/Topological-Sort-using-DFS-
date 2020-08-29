/**
 * Title: Short Project 5: DFS
 * @author Bhushan Vasisht
 * @author Shariq Ali
 */
package bsv180000;

import bsv180000.Graph.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//class DFS starts
public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    //class DFSVertex starts
    public static class DFSVertex implements Factory {
        int cno;

        // variable to check if a vertex is visited or not
        public boolean seen;

        // variable to check whether the parent has been seen or not; used to determine the cycles in a graph
        public boolean parentSeen;

        //constructor
        public DFSVertex(Vertex u) {

            // initially all vertices are set to false as they are unvisited
            seen = false;
            parentSeen = false;
        }
        //constructor ends

        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }
    //class DFSVertex ends

    //variable to store whether the graph is cyclic or not
    public boolean isCyclic;

    //variable to store the topologicalsort list
    private static List<Graph.Vertex> finishList;

    //constructor of DFS
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<Vertex>();
        isCyclic = false;
    }

    //method DFS_Visit is used to visit each vertex recursively
    public static void DFS_Visit(Vertex u, DFS d) {
        if (d.get(u).parentSeen) {
            //if parent has been seen before, it confirms the existence of cycles
            d.isCyclic = true;
            finishList = null;
            return;
        }
        if (!d.get(u).seen) {
            // Each vertex visited is marked true
            d.get(u).seen = true;
            d.get(u).parentSeen = true;
            // iterating through the edges connecting two vertices
            for (Edge e : d.g.incident(u)) {
                Vertex v = e.otherEnd(u);
                // visit the adjacent vertices of v
                DFS.DFS_Visit(v, d);
            }
            if (!d.isCyclic)
                // add each vertex visited to the starting of the list to get the topological ordering of the list
                ((LinkedList<Vertex>) finishList).addFirst(u);
            // making parentSeen of every vertex as false after checking for cycles
            d.get(u).parentSeen = false;
        }
    }

    public static DFS depthFirstSearch(Graph g) {

        // object of the dfs class
        DFS d = new DFS(g);
        for (Graph.Vertex u : g) {

            // call to DFS_Visit to start the dfs and mark the vertices as visited
            DFS_Visit(u, d);
        }
        return d;
    }

    // Function to find topological order
    public List<Vertex> topologicalOrder1() {
        // checking if the graph is directed or not
        if (!g.isDirected() || isCyclic) {
            // returns null if the graph is undirected
            return null;
        }
        depthFirstSearch(g);
        return finishList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "8 9   3 8 1   8 1 1   4 8 1   8 7 1   8 6 1   4 5 1   5 6 1   2 5 1   2 7 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(true);

        Timer t = new Timer();
        t.start();
        System.out.println(topologicalOrder1(g));
        t.end();
        System.out.println(t);
    }
}
//DFS class ends