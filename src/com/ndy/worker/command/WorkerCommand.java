package com.ndy.worker.command;

import com.ndy.packet.impl.NpcPacketWrapper;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.abstraction.impl.Farmer;
import com.ndy.worker.abstraction.impl.Miner;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.abstraction.impl.FisherMan;
import com.ndy.worker.scheduler.work.WorkerMoveScheduler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorkerCommand implements CommandExecutor {

    Worker worker;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(label.equals("worker")) {
            if(args.length == 2 && args[0].equals("create")) {
                WorkerMoveScheduler scheduler;

                switch (args[1]) {
                    case "fisherman":
                        worker = new FisherMan(WorkerPacketBuilder.builder()
                                .setPacket("npc", new NpcPacketWrapper("FisherMan", player.getLocation(), new ItemStack(Material.FISHING_ROD))));

                        scheduler = new WorkerMoveScheduler(worker);
                        scheduler.start();
                        break;
                    case "farming":
                        worker = new Farmer(WorkerPacketBuilder.builder()
                                        .setPacket("npc", new NpcPacketWrapper("Farmer", player.getLocation(), new ItemStack(Material.DIAMOND_HOE))));

                        scheduler = new WorkerMoveScheduler(worker);
                        scheduler.start();
                        break;
                    case "miner":
                        worker = new Miner(WorkerPacketBuilder.builder()
                                .setPacket("npc", new NpcPacketWrapper("Miner", player.getLocation(), new ItemStack(Material.DIAMOND_PICKAXE))));

                        scheduler = new WorkerMoveScheduler(worker);
                        scheduler.start();
                        break;
                }
            }
            if(args.length == 1 && args[0].equals("open")) {
                player.openInventory(worker.getWorkerInventory());
            }

            if(args.length == 1 && args[0].equals("state")) {
                worker.setSuccessWorking(!worker.isSuccessWorking());
            }
        }
        return false;
    }
}
