package com.ndy.packet.impl;

import com.ndy.packet.AbstractPacketWrapper;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class NpcMovePacketWrapper extends AbstractPacketWrapper {

    private Location location;
    private int npcId;
    private long x, y, z;
    private float yaw, pitch;
    private NpcPacketWrapper npc;

    public NpcMovePacketWrapper(Location location, int npcId) {
        this.location = location;
        this.npcId = npcId;
    }

    public NpcMovePacketWrapper(NpcPacketWrapper npc, Location destination) {
        this.npc = npc;
        this.npcId = npc.getId();
        this.location = destination;

        updateLocation(npc.getBukkitPlayer().getLocation(), destination);
    }

    public NpcMovePacketWrapper(long x, long y, long z, float yaw, float pitch, int npcId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.npcId = npcId;
    }

    private void updateLocation(Location npcLoc, Location destination) {
        double dX = npcLoc.getX() - destination.getX();
        double dY = npcLoc.getY() - destination.getY();
        double dZ = npcLoc.getZ() - destination.getZ();

        this.yaw = (float) Math.atan2(dZ, dX);
        this.pitch = (float) (Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI);

        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        Vector vector = new Vector(x, z, y);

        this.x = encodePosition(vector.getX());
        this.y = (long) vector.getY();//encodePosition(vector.getY());
        this.z = encodePosition(vector.getZ());

        npc.update(x, z, y);
    }

    private long encodePosition(double d) { return (long) (d * 4096D); }
    public void updateDestination(Location location) {
        this.location = location;
    }

    @Override
    public void initializePacket() {
        PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                npcId, x, y, z, (byte) yaw, (byte) pitch, false
        );

        super.addPacket(packet);
    }
}
