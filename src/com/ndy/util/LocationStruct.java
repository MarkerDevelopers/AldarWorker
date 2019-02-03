package com.ndy.util;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationStruct {

    private Location minLocation, maxLocation;

    public LocationStruct(Location point1, Location point2) {
        this.convert(point1, point2);
    }

    public Location getMinLocation() { return minLocation; }
    public Location getMaxLocation() { return maxLocation; }

    private void convert(Location point1, Location point2) {
        World world = point1.getWorld();
        int[] compareX = compareLocation(point1.getBlockX(), point2.getBlockX());
        int[] compareY = compareLocation(point1.getBlockY(), point2.getBlockY());
        int[] compareZ = compareLocation(point1.getBlockZ(), point2.getBlockZ());

        minLocation = new Location(world, compareX[0], compareY[0], compareZ[0]);
        maxLocation = new Location(world, compareX[1], compareY[1], compareZ[1]);
    }

    /**
     * @description 사이즈 2의 INT형 배열이 반환되는데, Index 0번에는 두 수중 작은 값이 넣어지고 1번에는 큰값이 넣어진다.
     * */
    private int[] compareLocation(int compare1, int compare2) {
        int[] compareResult = new int[2];
        compareResult[0] = compare1 >= compare2 ? compare2 : compare1;
        compareResult[1] = compare1 <= compare2 ? compare2 : compare1;

        return compareResult;
    }

}
