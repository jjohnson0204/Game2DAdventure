package ai;

public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost; // The distance between the current node and the start node.
    int hCost; // The distance from the current node to the goal node.
    int fCost; // the total cost(G + H) of the node.
    /*
    Every node has these 3 costs and the algorithm evaluates these and finds
    out which node is the most promising node to reach the goal. Among these
    3 costs, F Cost is the most important one, since it indicates the total cost.
     */
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.col = col;
        this.row = row;


    }
}
