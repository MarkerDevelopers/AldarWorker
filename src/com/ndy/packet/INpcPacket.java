package com.ndy.packet;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface INpcPacket{
    public void createNpc(String name, Location spawnLocation, ItemStack handItem);
}
