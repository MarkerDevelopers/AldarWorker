package com.ndy.worker.work;

import com.ndy.astar.path.PathFinder;
import com.ndy.astar.path.type.PathFindType;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;
import org.bukkit.Material;

public class WorkFarmingArea extends AbstractWork {

    @Override
    public PathFinder findWorkArea(Worker worker, AbstractWorkAreaCondition findToWorkArea) {
        boolean success = findToWorkArea.find(Material.SOIL);

        if(success) {
            PathFinder finder = new PathFinder(worker.getNpc().getBukkitPlayer().getLocation(), findToWorkArea.getResult());
            PathFindType type = finder.find();

            if(type == PathFindType.Success) {
                super.setPathFinder(finder);
                return finder;
            }
        }

        return null;
    }

}
