package com.ndy.astar.path;

import com.ndy.astar.node.Node;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private List<Node> path = new ArrayList<>();
    private List<Location> closedNodes = new ArrayList<>();

    private Node startNode, endNode;

    public PathFinder(Location start, Location end) {
        initialize(start, end);
    }

    /**
     * 시작점 초기화 메소드
     * */
    public void initialize(Location start, Location end) {
        this.startNode = new Node(start, end, 0, -999);
        this.endNode = new Node(end, end, 0, -999);
        startNode.initialize();
    }

    public PathFindType find() {
        Node currentNode = startNode;

        while(!PathFindTool.getInstance().equals(currentNode.getLocation(), endNode.getLocation())) {
            currentNode = currentNode.getNode();

            if(currentNode == null) return PathFindType.Failed;

            if(closedNodes.contains(currentNode.getLocation()))
                continue;

            if(!currentNode.isOpened()) return PathFindType.Failed;

            currentNode.initialize();

            closedNodes.add(currentNode.getLocation());
            path.add(currentNode);
        }

        return PathFindType.Success;
    }

    public List<Node> getPath() { return path; }
    public Node getEndNode() { return endNode; }
}
