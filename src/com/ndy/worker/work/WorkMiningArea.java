package com.ndy.worker.work;

import com.ndy.astar.path.PathFinder;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;
import org.bukkit.Material;

public class WorkMiningArea extends AbstractWork {

    @Override
    public PathFinder findWorkArea(Worker worker, AbstractWorkAreaCondition findToWorkArea) {
        boolean success = findToWorkArea.find(Material.FENCE);

        if(success) {
            PathFinder finder = new PathFinder(worker.getNpc().getBukkitPlayer().getLocation(), findToWorkArea.getResult().clone().subtract(1, 0 ,1));
            finder.find();

            super.setPathFinder(finder);
            return finder;
        }

        return null;
    }
}
