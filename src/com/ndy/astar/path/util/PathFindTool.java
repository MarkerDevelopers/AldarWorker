package com.ndy.astar.path.util;

import org.bukkit.Location;
import org.bukkit.Material;

public class PathFindTool {

    private static PathFindTool instance;

    private static final int UP_HEIGHT_GAP = 2;
    private static final int DOWN_HEIGHT_GAP = 3;

    private PathFindTool() {}

    public static synchronized PathFindTool getInstance() {
        if(instance == null) instance = new PathFindTool();

        return instance;
    }

    public boolean isGround(int height) {
        return height < DOWN_HEIGHT_GAP;
    }

    public int getDownHeight(Location location) {
        int y = 0;

        while(location.subtract(0, y, 0).getBlock().getType() == Material.AIR) {
            ++y;
        }

        return y;
    }

    public static int getHeightGap() { return UP_HEIGHT_GAP; }

    public int getBlockHeight(Location location) {
        int y = 0;

        while(location.add(0 , y, 0).getBlock().getTypeId() != 0) {
            if(y >= UP_HEIGHT_GAP) break;

            y++;
        }

        return y;
    }

    public Location getLocation(Location location, int height, int downHeight) {
        if(downHeight > 0) location.subtract(0, downHeight, 0);
        if(height > 0) location.add(0 , height, 0);

        return location;
    }

    public Location getHeightBlock(Location location, int height) {
        if(height >= UP_HEIGHT_GAP) return location;

        return location.add(0, height, 0);
    }

    public Location getDownBlock(Location location, int downHeight) {
        if(isGround(downHeight) && downHeight == 1) return location;

        return location.subtract(0, downHeight-1, 0);
    }

    public double getDistance(Location loc1, Location loc2) {
        int x2 = loc2.getBlockX(), x1 = loc1.getBlockX();
        int y2 = loc2.getBlockY(), y1 = loc1.getBlockY();
        int z2 = loc2.getBlockZ(), z1 = loc1.getBlockZ();

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }

    public boolean equals(Location loc1, Location loc2) {
        return (loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY()) && (loc1.getBlockZ() == loc2.getBlockZ());
    }

}
