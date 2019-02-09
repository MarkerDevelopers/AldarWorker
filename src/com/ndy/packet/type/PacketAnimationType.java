package com.ndy.packet.type;

public enum PacketAnimationType {

    SwingMainArm(0x0),
    TakeDamage(0x1),
    LeaveBed(0x2),
    SwingOffHand(0x3),
    CriticalEffect(0x4),
    MagicCriticalEffect(0x5);

    private int packetId;

    PacketAnimationType(int packetId) {
        this.packetId = packetId;
    }

    public int getId() { return packetId; }
}
