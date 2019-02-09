package com.ndy.packet.impl;

import com.ndy.packet.AbstractPacketWrapper;
import com.ndy.packet.type.PacketAnimationType;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;

public class NpcAnimationPacketWrapper extends AbstractPacketWrapper {

    private NpcPacketWrapper npc;
    private PacketAnimationType type;

    public NpcAnimationPacketWrapper(NpcPacketWrapper npc, PacketAnimationType type) {
        super(new PacketPlayOutAnimation());

        this.npc = npc;
        this.type = type;
    }

    @Override
    public void initializePacket() {
        PacketPlayOutAnimation packet = (PacketPlayOutAnimation) getPacketList().get(0);
        super.setValue(packet, "a", npc.getId());
        super.setValue(packet, "b", type.getId());
    }
}
