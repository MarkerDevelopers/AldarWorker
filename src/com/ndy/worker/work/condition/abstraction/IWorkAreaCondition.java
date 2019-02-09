package com.ndy.worker.work.condition.abstraction;

import org.bukkit.Material;

public interface IWorkAreaCondition {
    boolean find(Material... findToMaterials); /** 찾으려는 Work Area 구조 */
}
