package com.ndy.worker.work.condition;

import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Arrays;

public class FishingAreaCondition extends AbstractWorkAreaCondition {

    public FishingAreaCondition(Location location) {
        super(location);
    }

    @Override
    public boolean find(Material... findToMaterials) {
        super.propertyLocationPos(true);

        Location location = super.getLocation(Arrays.asList(findToMaterials));
        super.setResult(location);

        return location != null;
    }
}
