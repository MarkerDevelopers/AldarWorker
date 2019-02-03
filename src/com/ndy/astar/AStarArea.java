package com.ndy.astar;

import com.ndy.util.LocationStruct;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class AStarArea {

    private LocationStruct struct;

    private List<Location> searchingList = new ArrayList<>();

    public AStarArea(Location player, Location goal) {
        this.struct = new LocationStruct(player.subtract(3, 0, 3), goal.add(3, 0, 3));
        initialize();
    }

    public void initialize() {
        Location minLoc = struct.getMinLocation();
        Location maxLoc = struct.getMaxLocation();


        for(int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
            for(int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                for(int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++) {
                    searchingList.add(new Location(minLoc.getWorld(), x, y, z));
                }
            }
        }
    }

    public void test() {
        Location minLoc = struct.getMinLocation();
        Location maxLoc = struct.getMaxLocation();


        for(int x = minLoc.getBlockX(); x <= maxLoc.getBlockX(); x++) {
            for(int z = minLoc.getBlockZ(); z <= maxLoc.getBlockZ(); z++) {
                for(int y = minLoc.getBlockY(); y <= maxLoc.getBlockY(); y++) {
                    Location location = new Location(minLoc.getWorld(), x, y, z);
                    location.getBlock().setType(Material.STONE);
                }
            }
        }
    }

    private boolean isBiggerOrEqual(int a1, int a2) {
        return a1 >= a2;
    }

}
