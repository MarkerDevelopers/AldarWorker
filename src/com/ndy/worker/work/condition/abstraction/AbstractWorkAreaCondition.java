package com.ndy.worker.work.condition.abstraction;

import com.ndy.util.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractWorkAreaCondition implements IWorkAreaCondition {

    private Location location;

    private Location pos1, pos2;

    private int range;

    private boolean sub;

    public AbstractWorkAreaCondition(Location location) {
        this.location = location;
    }

    /**
     * @return find 함수에서 작업영역을 찾았으면 그 위치를 반환 그게 아니라면 생성자에서 초기화 한 위치 반환
     * */
    public Location getResult() { return this.location; }

    protected void setResult(Location location) { this.location = location; }

    public void setRangeProperty(int range) {
        this.range = range;
    }

    protected Location getLocation(List<Material> materials) {
        /* 설정된 pos1 과 pos2가 없으면 기존 로케이션 반환 */
        if(pos1 == null || pos2 == null) return location;

        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < (pos2.getBlockX() - pos1.getBlockX()); x++) {
                for (int z = 0; z < (pos2.getBlockZ() - pos1.getBlockZ()); z++) {
                    Location location = new Location(this.location.getWorld(), pos1.getBlockX() + x, pos1.getBlockY() + y, pos1.getBlockZ() + z);

                    if (isFind(location, materials)) return location.add(0, 1, 0);
                }
            }
        }

        return null;
    }

    protected List<Location> getCorrectLocations(List<Material> materials) {
        if(pos1 == null || pos2 == null) return Arrays.asList(location);

        List<Location> locations = new ArrayList<>();

        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < (pos2.getBlockX() - pos1.getBlockX()); x++) {
                for (int z = 0; z < (pos2.getBlockZ() - pos1.getBlockZ()); z++) {
                    Location location = new Location(this.location.getWorld(), pos1.getBlockX() + x, pos1.getBlockY() + y, pos1.getBlockZ() + z);

                    find(location, materials, locations);
                }
            }
        }

        return locations;
    }

    protected void propertyLocationPos(boolean subY) {
        this.sub = subY;

        this.pos1 = this.location.clone().subtract(range, 0 , range);
        this.pos2 = this.location.clone().add(range, 0, range);

        if(subY) this.pos1.subtract(0, 1, 0);
    }

    /**
     * @return center 기준으로 상하좌우대각선 블럭들을 검사하여 materials에 포함되는 블럭이 있으면 true 반환
     * */
    private boolean isFind(Location center, List<Material> materials) {
        Location[] locations = getLocations(center);

        for(Location location : locations) {
            if(materials.contains(location.getBlock().getType())) return true;
        }

        return false;
    }

    private void find(Location center, List<Material> materials, List<Location> addedLocations) {

        if(materials.contains(center.getBlock().getType()) && !addedLocations.contains(center)) {
            if(sub) center.add(0, 1, 0);

            addedLocations.add(center);
        }
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

}
