package com.ndy.worker.abstraction.impl;

import com.ndy.astar.path.PathFinder;
import com.ndy.packet.impl.NpcAnimationPacketWrapper;
import com.ndy.packet.manager.PacketContext;
import com.ndy.packet.type.PacketAnimationType;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.work.FarmerWorkScheduler;
import com.ndy.worker.work.WorkFarmingArea;
import com.ndy.worker.work.condition.FarmingAreaCondition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Farmer extends Worker {

    private int idx = 0;
    private List<Location> soilLocations = new ArrayList<>();

    public Farmer(WorkerPacketBuilder builder) {
        super(builder);
    }

    @Override
    public AbstractWork findWork() {
        FarmingAreaCondition condition = new FarmingAreaCondition(getNpc().getBukkitPlayer().getLocation());
        AbstractWork workFishing = new WorkFarmingArea();

        condition.setRangeProperty(100);

        return super.findWork(workFishing, condition);
    }

    @Override
    public boolean doWorking(Object... objects) {
        if(isFulInventory()) {
            idx = 0;
            soilLocations.clear();

            return false;
        }

        if(soilLocations.size() == 0) {
            FarmingAreaCondition condition = new FarmingAreaCondition(getNpc().getBukkitPlayer().getLocation());
            condition.setRangeProperty(50);

            this.soilLocations = condition.getSoilLocations(Material.SOIL);
            Collections.shuffle(soilLocations);
        }

        if(soilLocations.size() > 0) {
            setFind(true);
            Location soilLocation = soilLocations.get(idx).clone().subtract(0,1 , 0);
            Block block = soilLocation.getBlock();
            byte blockData = block.getData();
            List<ItemStack> dropItems = new ArrayList<>(block.getDrops());

            if(blockData == 0x7) {
                block.setData((byte) 0x0);

                dropItems.stream().forEach(i -> getWorkerInventory().addItem(i));

                PacketContext context = super.getPacketContext();
                context.setPacket("animation", new NpcAnimationPacketWrapper(getNpc(), PacketAnimationType.SwingMainArm));
                context.send("animation");
            }

            idx++;

            if(idx == soilLocations.size()) idx = 0;

            PathFinder finder = new PathFinder(soilLocation, soilLocations.get(idx));
            finder.find();

            setGoal(finder.getPath());
        }

        return true;
    }

    @Override
    public AbstractWorkScheduler getWorkScheduler() {
        return new FarmerWorkScheduler(this);
    }
}
