package com.ndy.worker.builder;

import com.ndy.packet.AbstractPacketWrapper;

import java.util.HashMap;
import java.util.Map;

public class WorkerPacketBuilder {

    private Map<String, AbstractPacketWrapper> packetMap = new HashMap<>();

    public WorkerPacketBuilder() {}

    public static WorkerPacketBuilder builder() { return new WorkerPacketBuilder(); }

    public WorkerPacketBuilder setPacket(String packetName, AbstractPacketWrapper wrapper) {
        packetMap.put(packetName, wrapper);

        return this;
    }

    public Map<String, AbstractPacketWrapper> build() { return packetMap; }

}
