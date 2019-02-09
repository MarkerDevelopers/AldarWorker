package com.ndy.worker.abstraction.impl;

import com.ndy.packet.impl.NpcAnimationPacketWrapper;
import com.ndy.packet.manager.PacketContext;
import com.ndy.packet.type.PacketAnimationType;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.work.MiningWorkScheduler;
import com.ndy.worker.work.WorkMiningArea;
import com.ndy.worker.work.condition.MiningAreaCondition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

public class Miner extends Worker {

    public Miner(WorkerPacketBuilder builder) { super(builder); }

    @Override
    public AbstractWork findWork() {
        MiningAreaCondition condition = new MiningAreaCondition(getNpc().getBukkitPlayer().getLocation());
        AbstractWork workFishing = new WorkMiningArea();

        condition.setRangeProperty(100);

        return super.findWork(workFishing, condition);
    }

    @Override
    public boolean doWorking(Object... objects) {
        Inventory inventory = getWorkerInventory();
        PacketContext context = getPacketContext();

        MiningAreaCondition condition = new MiningAreaCondition(getNpc().getBukkitPlayer().getLocation());
        condition.setRangeProperty(3);

        boolean success = condition.find(Material.FENCE);

        if(success) {
            Location fence = condition.getResult();
            Block mineBlock = fence.add(0, 1, 0).getBlock();
            mineBlock.getDrops().stream().forEach(i -> inventory.addItem(i));
            mineBlock.setType(Material.AIR);

            context.setPacket("animation", new NpcAnimationPacketWrapper(getNpc(), PacketAnimationType.SwingMainArm));
            context.send("animation");
        }

        setSuccessWorking(!isFulInventory());

        return isSuccessWorking();
    }

    @Override
    public AbstractWorkScheduler getWorkScheduler() {
        return new MiningWorkScheduler(this);
    }
}
