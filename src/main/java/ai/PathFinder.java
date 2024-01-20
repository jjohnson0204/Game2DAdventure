package ai;
import main.GamePanel;
import java.util.ArrayList;
public class PathFinder {
    GamePanel gp;
    //Node
    Node[][] node;
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    //Others
    boolean goalReached = false;
    int step = 0;
    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        //Place Nodes
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void resetNodes(){
        //Place Nodes
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        //Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();
        //Set Start and Goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            //Set Solid Node
            //Check Tiles
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];

            if(gp.tileM.tile[tileNum].collision){
                node[col][row].solid = true;
            }
            //Check Interactive Tiles
            for(int i = 0; i < gp.iTile[1].length; i++){


                    if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible){
                        int itCol = (int) (gp.iTile[gp.currentMap][i].worldX / gp.tileSize);
                        int itRow = (int) (gp.iTile[gp.currentMap][i].worldY / gp.tileSize);
                        node[itCol][itRow].solid = true;
                    }
            }
                //Set Cost
            getCost(node[col][row]);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node){
        // Get G Cost (The distance from the start node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // Get H Cost (The distance from the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // Get F Cost (The total cost)
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search(){
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            //Open the UP node
            if(row - 1 >= 0){
                openNode(node[col][row - 1]);
            }
            //Open the LEFT node
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            //Oen the DOWN node
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row + 1]);
            }
            //Open the RIGHT node
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col + 1][row]);
            }
            //Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            for(int i = 0; i < openList.size(); i++){
                //Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //If there is no node in the openList, end the loop
            if(openList.size() == 0){
                break;
            }
            //After the loop, we get the best node which is our next step
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    private void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            //If the node is not opened yet, add it to the open list
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    private void trackThePath(){
        //Backtrack and draw the best path
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0, current);
            current = current.parent;
        }
    }
}