package com.ndy.packet;

import net.minecraft.server.v1_12_R1.Packet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractPacketWrapper{

    private List<Packet> packetList = new ArrayList<>();

    public AbstractPacketWrapper(Packet... packets) {
        this.packetList = Arrays.asList(packets);
    }

    public AbstractPacketWrapper() {}

    protected void setValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    protected void addPacket(Packet packet) { packetList.add(packet); }

    public abstract void initializePacket();

    public List<Packet> getPacketList() { return packetList; }
}
