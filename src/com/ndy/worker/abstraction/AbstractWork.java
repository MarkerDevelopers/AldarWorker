package com.ndy.worker.abstraction;

import com.ndy.astar.path.PathFinder;

public abstract class AbstractWork implements IWorkable {

    private PathFinder finder;

    protected void setPathFinder(PathFinder finder) {
        this.finder = finder;
    }

    public PathFinder getFinder() { return finder; }
}
