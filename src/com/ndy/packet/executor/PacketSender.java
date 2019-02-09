package com.ndy.packet.executor;

import com.ndy.packet.AbstractPacketWrapper;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketSender {

    /**
     * @param receiver 패킷을 받는 유저
     * */
    public static void sendPacket(Player receiver, AbstractPacketWrapper... packetWrappers) {
        PlayerConnection connection = ((CraftPlayer) receiver).getHandle().playerConnection;

        for(AbstractPacketWrapper wrapper : packetWrappers) {
            if(wrapper == null) continue;

            wrapper.initializePacket();

            wrapper.getPacketList().stream().forEach(i -> {
                connection.sendPacket(i);
            });
        }
    }

    /**
     * 전체 유저에게 패킷을 보냅니다.
     * */
    public static void sendPacket(AbstractPacketWrapper... packetWrappers) {
        for (Player player : Bukkit.getOnlinePlayers())
            sendPacket(player, packetWrappers);
    }

}
