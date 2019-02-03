package com.ndy.astar.node;

import com.ndy.astar.impl.IHeuristic;
import com.ndy.astar.path.PathFindTool;
import org.bukkit.Location;

import java.util.PriorityQueue;
import java.util.Queue;

public class Node implements IHeuristic, Comparable<IHeuristic>, Cloneable {

    private boolean isOpened;
    private Location location, end;
    private Queue<Node> nodes = new PriorityQueue<>();
    private int height;
    private float g; // 휴리스틱 추정값중 G
    private int uid;

    public Node(Location location, Location end, float g, int uid) {
        this.end = end;
        this.g = g;
        this.uid = uid;
        this.height = PathFindTool.getInstance().getBlockHeight(location.clone());

        this.location = PathFindTool.getInstance().getHeightBlock(location, height);


        this.isOpened = Util.isAllowBlock(location.clone().subtract(0, 1, 0).getBlock()) && !isWall() && !PathFindTool.getInstance().isGround(location);

    }

    public void initialize() {
        nodes.add(new Node(location.clone().add(1, 0, 0), end, 10.0F, 1));
        nodes.add(new Node(location.clone().add(1, 0, 1), end, 10.5F, 2));
        nodes.add(new Node(location.clone().add(0, 0, 1), end, 10.0F, 3));
        nodes.add(new Node(location.clone().add(1, 0, -1), end,10.5F, 4));

        nodes.add(new Node(location.clone().subtract(1, 0, 0), end, 10.0F, 5));
        nodes.add(new Node(location.clone().subtract(1, 0, 1), end, 10.5F, 6));
        nodes.add(new Node(location.clone().subtract(0, 0, 1), end, 10.0F, 7));
        nodes.add(new Node(location.clone().subtract(1, 0, -1), end, 10.5F, 8));
    }

    public boolean isWall() { return height >= PathFindTool.getHeightGap(); }
    public boolean isOpened() { return isOpened; }
    public Location getLocation() { return location; }

    public void setOpened(boolean opened) { isOpened = opened; }

    /**
     * @return F = G + H
     * */
    @Override
    public double getHeuristic() { return ((double) getG()) + getH(); }

    /**
     * @return H
     * */
    public double getH() {
        return PathFindTool.getInstance().getDistance(location, end) * 10.0D;
    }

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

        if(node == null) return null;

        if(!node.isOpened()) return getNode();

        return node;
    }

    @Override
    public String toString() { return "" + uid; }

    @Override
    public Node clone() {
        try {
            return (Node) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void print() {
        nodes.stream().forEach(i -> System.out.println(i.toString() + ": " + i.getHeuristic()));
    }
}
