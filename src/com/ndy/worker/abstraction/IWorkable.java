package com.ndy.worker.abstraction;

import com.ndy.astar.path.PathFinder;
import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;

public interface IWorkable {

    PathFinder findWorkArea(Worker worker, AbstractWorkAreaCondition findToWorkArea); /** 작업 영역 탐색 후 Navigation 반환 */

}
