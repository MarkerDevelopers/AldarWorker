package com.ndy.worker.abstraction.impl;

import com.ndy.packet.impl.NpcAnimationPacketWrapper;
import com.ndy.packet.manager.PacketContext;
import com.ndy.packet.type.PacketAnimationType;
import com.ndy.util.ItemBuilder;
import com.ndy.util.ProbabilityUtil;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.work.FisherManWorkScheduler;
import com.ndy.worker.work.WorkFishingArea;
import com.ndy.worker.work.condition.FishingAreaCondition;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FisherMan extends Worker {

    public FisherMan(WorkerPacketBuilder builder) {
        super(builder);
    }

    @Override
    public AbstractWork findWork() {
        FishingAreaCondition condition = new FishingAreaCondition(getNpc().getBukkitPlayer().getLocation());
        AbstractWork workFishing = new WorkFishingArea();

        condition.setRangeProperty(100);

        return super.findWork(workFishing, condition);
    }

    @Override
    public boolean doWorking(Object... objects) {
        if(ProbabilityUtil.isProbability(100, (long) 100)) {
            ItemStack itemStack = new ItemBuilder(Material.RAW_FISH).setDisplayName("§e물고기").addLore("§f낚시꾼이 잡은 최고의 물고기!").build();
            PacketContext context = super.getPacketContext();

            context.setPacket("animation", new NpcAnimationPacketWrapper(getNpc(), PacketAnimationType.SwingMainArm));
            context.send("animation");

            getWorkerInventory().addItem(itemStack);

            setSuccessWorking(!isFulInventory());
        }


        return isSuccessWorking();
    }

    @Override
    public AbstractWorkScheduler getWorkScheduler() {
        return new FisherManWorkScheduler(this);
    }
}
