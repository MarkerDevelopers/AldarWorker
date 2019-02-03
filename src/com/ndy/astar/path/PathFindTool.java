package com.ndy.astar.path;

import com.ndy.astar.node.Node;
import org.bukkit.Location;

public class PathFindTool {

    private static PathFindTool instance;

    private static final int HEIGHT_GAP = 2;

    private PathFindTool() {}

    public static synchronized PathFindTool getInstance() {
        if(instance == null) instance = new PathFindTool();

        return instance;
    }

    public boolean isMoveableLocation(Node node, Node compareTarget) {
        if(!compareTarget.isOpened()) return false;

        int heightGap = getHeightGap(node, compareTarget);

        return heightGap >= HEIGHT_GAP;
    }

    private int getHeightGap(Node node1, Node node2) { return Math.abs(node1.getLocation().getBlockY() - node2.getLocation().getBlockY()); }
    public static int getHeightGap() { return HEIGHT_GAP; }

    public int getBlockHeight(Location location) {
        int y = 0;

        while(location.add(0, y, 0).getBlock().getTypeId() != 0) {
            if(y >= HEIGHT_GAP) break;

            y++;
        }

        return y;
    }

    public int getDistance(Location loc1, Location loc2) {
        int x2 = loc2.getBlockX(), x1 = loc1.getBlockX();
        int y2 = loc2.getBlockY(), y1 = loc1.getBlockY();
        int z2 = loc2.getBlockZ(), z1 = loc1.getBlockZ();

        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    public boolean equals(Location loc1, Location loc2) {
        return (loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY()) && (loc1.getBlockZ() == loc2.getBlockZ());
    }

}
