package com.ndy.packet.impl;

import com.mojang.authlib.GameProfile;
import com.ndy.packet.AbstractPacketWrapper;
import com.ndy.packet.INpcPacket;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NpcPacketWrapper extends AbstractPacketWrapper implements INpcPacket {

    private EntityPlayer npc;
    private net.minecraft.server.v1_12_R1.ItemStack nmsItemStack;

    public NpcPacketWrapper(String name, Location spawnLocation, ItemStack itemStack) {
        createNpc(name, spawnLocation, itemStack);
    }

    @Override
    public void createNpc(String name, Location spawnLocation, ItemStack itemStack) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) spawnLocation.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);

        this.npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        this.npc.setLocation(spawnLocation.getX(), spawnLocation.getY(),
                spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());

        this.nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    }

    public void update(double x, double y, double z) {
        npc.move(EnumMoveType.SELF, x, y, z);
        npc.checkMovement(x, y, z);
    }

    public Player getBukkitPlayer() { return npc.getBukkitEntity().getPlayer(); }

    public CraftPlayer getNMSPlayer() { return npc.getBukkitEntity(); }

    public EntityPlayer getNpc() { return npc; }

    public int getId() { return npc.getId(); }

    @Override
    public void initializePacket() {
        super.addPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        super.addPacket(new PacketPlayOutNamedEntitySpawn(npc));
        super.addPacket(new PacketPlayOutEntityEquipment(npc.getId(), EnumItemSlot.MAINHAND, nmsItemStack));
    }

}
