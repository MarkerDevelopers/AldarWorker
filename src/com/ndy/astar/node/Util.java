package com.ndy.astar.node;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class Util {

    private static List<Material> blockedBlock = Arrays.asList(Material.STATIONARY_WATER, Material.STATIONARY_LAVA, Material.SOIL);

    public static boolean isAllowBlock(Block block) {
        return !blockedBlock.contains(block.getType());
    }

}
