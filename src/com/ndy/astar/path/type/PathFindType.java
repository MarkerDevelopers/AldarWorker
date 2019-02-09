package com.ndy.astar.path.type;

public enum PathFindType {

    Success(1), Failed(-1);

    private int type;

    PathFindType(int type) { this.type = type; }

}
