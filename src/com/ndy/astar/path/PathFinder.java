package com.ndy.astar.path;

import com.ndy.astar.node.Node;
import com.ndy.astar.path.type.PathFindType;
import com.ndy.astar.path.util.PathFindTool;
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

    /**
     * @return Successfully path finding
     * */
    public PathFindType find() {
        Node currentNode = startNode;
        path.add(currentNode);

        while(!PathFindTool.getInstance().equals(currentNode.getLocation(), endNode.getLocation())) {
            Node node = currentNode.getNode();

            if(node == null) return PathFindType.Failed;

            if(closedNodes.contains(node.getLocation())) { continue; }

            currentNode = node;
            currentNode.initialize();

            closedNodes.add(currentNode.getLocation());
            path.add(currentNode);
        }

        //clearInitializePathNodes();
        //reverse();
        //refind(PathFindType.Success);

        return PathFindType.Success;
    }

    public PathFindType refind(PathFindType type) {
        if(type == PathFindType.Success) {
            this.closedNodes.clear();

            Node startNode = path.get(path.size() - 1);
            Node end = this.startNode;
            Node currentNode = startNode;

            List<Node> paths = new ArrayList<>();

            while(!PathFindTool.getInstance().equals(currentNode.getLocation(), end.getLocation())) {
                Node node = currentNode.getNode();

                if(node == null) return PathFindType.Failed;

                if(closedNodes.contains(node.getLocation())) { continue; }

                currentNode = node;
                currentNode.initialize();

                closedNodes.add(currentNode.getLocation());
                paths.add(currentNode);

                if(isNearbyGoal(currentNode.getLocation())) break;
            }

            type = PathFindType.Success;
            this.path = paths;
        }

        return type;
    }

    private Location[] getLocations(Location center) {
        return new Location[]{
                center.clone().add(1, 0, 0),
                center.clone().add(1, 0, 1),
                center.clone().add(0, 0, 1),
                center.clone().add(1, 0, -1),
                center.clone().subtract(1, 0, 0),
                center.clone().subtract(1, 0, 1),
                center.clone().subtract(0, 0, 1),
                center.clone().subtract(1, 0, -1),
        };
    }

    private boolean isNearbyGoal(Location center) {
        Location[] locations = getLocations(center);

        for(Location location : locations) {
            if(endNode.getLocation().equals(location)) return true;
        }

        return false;
    }

    /**
     * 경로 탐색이 된 노드들의 Openable Node 들을 모두 삭제
     * */
    private void clearInitializePathNodes() { path.forEach(i -> i.clearNodes()); }

    /**
     * @return Get shortest path
     * */
    public List<Node> getPath() { return path; }

    public Node getEndNode() { return endNode; }
    public Node getStartNode() { return startNode; }

    public Node getNode(int idx) { return path.get(idx); }
}
