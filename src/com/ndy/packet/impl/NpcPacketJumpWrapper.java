package com.ndy.packet.impl;

import com.ndy.packet.AbstractPacketWrapper;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityVelocity;
import org.bukkit.util.Vector;

public class NpcPacketJumpWrapper extends AbstractPacketWrapper {

    private EntityPlayer player;

    public NpcPacketJumpWrapper(EntityPlayer entityPlayer) {
        this.player = entityPlayer;
    }

    @Override
    public void initializePacket() {
        Vector velocity = player.getBukkitEntity().getVelocity();
        PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(player.getId(),
                velocity.getX() * 8000.0D, velocity.getY() * 8005.0D, velocity.getZ() * 8000.0D);

        addPacket(packet);
    }
}
