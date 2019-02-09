package com.ndy.astar.node;

import com.ndy.astar.impl.IHeuristic;
import com.ndy.astar.path.util.PathFindTool;
import org.bukkit.Location;

import java.util.PriorityQueue;
import java.util.Queue;

public class Node implements IHeuristic, Comparable<IHeuristic> {

    private boolean isOpened;
    private Location location, end;
    private Queue<Node> nodes = new PriorityQueue<>();
    private int height, downHeight;
    private float g; // 휴리스틱 추정값중 G
    private int uid;

    public Node(Location location, Location end, float g, int uid) {
        this.end = end;
        this.g = g;
        this.uid = uid;

        Location defaultLocation = location.clone();
        this.height = PathFindTool.getInstance().getBlockHeight(defaultLocation);
        this.downHeight = PathFindTool.getInstance().getDownHeight(defaultLocation.clone());

        this.location = PathFindTool.getInstance().getHeightBlock(location, height);
        this.location = PathFindTool.getInstance().getDownBlock(location, downHeight);

        this.isOpened = Util.isAllowBlock(defaultLocation.clone().subtract(0, 1, 0).getBlock()) && !isWall() && PathFindTool.getInstance().isGround(downHeight);
    }

    /**
     * 주변에 탐색가능한 노드들을 모두 가져옵니다.
     * */
    public void initialize() {
        Node[] nodes = new Node[] {
                new Node(location.clone().add(1, 0, 0), end, 10.0F, 1),
                new Node(location.clone().add(1, 0, 1), end, 10.5F, 2),
                new Node(location.clone().add(0, 0, 1), end, 10.0F, 3),
                new Node(location.clone().add(1, 0, -1), end,10.5F, 4),

                new Node(location.clone().subtract(1, 0, 0), end, 10.0F, 5),
                new Node(location.clone().subtract(1, 0, 1), end, 10.5F, 6),
                new Node(location.clone().subtract(0, 0, 1), end, 10.0F, 7),
                new Node(location.clone().subtract(1, 0, -1), end, 10.5F, 8),
        };

        for(Node node : nodes) {
            if(node.isOpened()) this.nodes.add(node);
        }
    }

    public boolean isWall() { return height >= PathFindTool.getHeightGap(); } /** @return 해당 노드가 벽인지 확인합니다. */
    public boolean isOpened() { return isOpened; } /** @return 열린 노드인지 확인합니다. */

    public Location getLocation() { return location; }

    /**
     * @return F = G + H
     * */
    @Override
    public double getHeuristic() { return ((double) getG()) + getH(); }

    /**
     * @return H
     * */
    public double getH() { return PathFindTool.getInstance().getDistance(location, end) * 10.0D; }

    /*
    * @return G
    * */
    public float getG() { return g; }

    @Override
    public int compareTo(IHeuristic o) {
        if(getHeuristic() > o.getHeuristic()) return 1;
        else if(getHeuristic() < o.getHeuristic()) return -1;

        return 0;
    }

    /**
     * @return Getting Best Node
     * */
    public Node getNode() {
        Node node = nodes.poll();

        if(node == null) {
            return null;
        }

        return node;
    }

    @Override
    public String toString() { return "" + uid; }

    public void clearNodes() { this.nodes.clear(); }
}
