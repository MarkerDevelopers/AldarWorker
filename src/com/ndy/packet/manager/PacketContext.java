package com.ndy.packet.manager;

import com.ndy.packet.AbstractPacketWrapper;
import com.ndy.packet.executor.PacketSender;
import com.ndy.worker.builder.WorkerPacketBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PacketContext {

    private Map<String, AbstractPacketWrapper> transmitablePacket = new HashMap<>(); /** 송신될 패킷 Map */

    public void setPackets(WorkerPacketBuilder builder) {
        for(Map.Entry<String, AbstractPacketWrapper> entry : builder.build().entrySet())
            this.transmitablePacket.put(entry.getKey(), entry.getValue());
    }

    public void disposePackets(String... packetNames) {
        for(String packet : packetNames)
            transmitablePacket.remove(packet);
    }

    public AbstractPacketWrapper getPacket(String packetName) { return transmitablePacket.get(packetName); }

    public AbstractPacketWrapper[] getPackets(String... packetNames) {
        AbstractPacketWrapper[] wrappers = new AbstractPacketWrapper[packetNames.length];

        for(int i = 0; i < packetNames.length; i++)
            wrappers[i] = getPacket(packetNames[i]);


        return wrappers;
    }

    public void setPacket(String packet, AbstractPacketWrapper packetWrapper) { transmitablePacket.put(packet, packetWrapper); }

    public void removeAll() { transmitablePacket.clear(); }

    /**
     * 송신가능한 전체 패킷을 보냅니다.
     * */
    public void send() {
        Collection<AbstractPacketWrapper> wrappers = transmitablePacket.values();
        PacketSender.sendPacket(wrappers.toArray(new AbstractPacketWrapper[wrappers.size()]));
    }

    public void send(String... packetNames) {
        AbstractPacketWrapper[] wrappers = getPackets(packetNames);
        PacketSender.sendPacket(wrappers);
    }

}
