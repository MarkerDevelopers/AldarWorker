package com.ndy.worker.scheduler.work;

import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;
import com.ndy.worker.util.InventoryUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WorkerMakeOutItemScheduler extends AbstractWorkScheduler {

    public WorkerMakeOutItemScheduler(Worker worker) {
        super(worker, 0L, 1L, true);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void run() {
        Worker worker = getWorker();
        Chest chest = getChest();

        Inventory workerInventory = worker.getWorkerInventory();
        Inventory chestInventory = chest.getInventory();

        boolean isWorkerInventoryFull = worker.isFulInventory();
        boolean isChestInventoryFull = InventoryUtility.isFull(chestInventory);

        if(isWorkerInventoryFull && !isChestInventoryFull) {
            int emptySpace = InventoryUtility.getEmptySpace(chestInventory);
            ItemStack[] workerInventoryContent = InventoryUtility.getItemAndDelete(workerInventory, emptySpace);

            InventoryUtility.addItems(chestInventory, workerInventoryContent);
        }

        next();
    }

    private Chest getChest() {
        Worker worker = getWorker();
        Location location = worker.getNpc().getBukkitPlayer().getLocation();

        Location subLoc = location.clone().subtract(3, 0, 3);
        Location addLoc = location.clone().add(3, 0 ,3);

        Location chestLocation = getChestLocation(subLoc, addLoc);

        if(chestLocation != null) return (Chest) chestLocation.getBlock().getState();

        return null;
    }

    private Location getChestLocation(Location pos1, Location pos2) {
        for(int y = 0; y < 3; y++) {
            for (int x = 0; x < (pos2.getBlockX() - pos1.getBlockX()); x++) {
                for (int z = 0; z < (pos2.getBlockZ() - pos1.getBlockZ()); z++) {
                    Location find = new Location(pos1.getWorld(), pos1.getBlockX() + x,  pos1.getBlockY() + y, pos1.getBlockZ() + z);

                    if(find.getBlock().getType() == Material.CHEST) return find;
                }
            }
        }

        return null;
    }

    @Override
    public void next() {
        Worker worker = getWorker();
        SchedulerContext context = worker.getSchedulerContext();

        worker.setSuccessWorking(true);

        context.setScheduler(new WorkerMoveScheduler(worker));
        context.startScheduler();
    }
}
