package com.ndy.worker.work.condition;

import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class MiningAreaCondition extends AbstractWorkAreaCondition {

    public MiningAreaCondition(Location location) { super(location); }

    @Override
    public boolean find(Material... findToMaterials) {
        super.propertyLocationPos(false);

        List<Location> locations = super.getCorrectLocations(Arrays.asList(findToMaterials));

        for(Location location : locations) {
            if(isMine(location)) {
                setResult(location);
                break;
            }
        }

        return getResult() != null;
    }

    /**
     * @return OreGen 형식의 광산인지 확인후 return
     */
    private boolean isMine(Location location) {
        Location clone = location.clone();
        Material fence = clone.getBlock().getType();
        Material mine = clone.add(0 ,1, 0).getBlock().getType();
        Material water = clone.add(0, 1, 0).getBlock().getType();

        return fence == Material.FENCE && isMineBlock(mine) || water == Material.STATIONARY_WATER;
    }

    public static boolean isMineBlock(Material material) {
        return material == Material.GOLD_ORE
                || material == Material.EMERALD_ORE || material == Material.COAL_ORE
                || material == Material.IRON_ORE  || material == Material.DIAMOND_ORE
                || material == Material.STONE || material == Material.LAPIS_ORE;
    }
}
