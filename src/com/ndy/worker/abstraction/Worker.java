package com.ndy.worker.abstraction;

import com.ndy.astar.node.Node;
import com.ndy.astar.path.PathFinder;
import com.ndy.packet.impl.NpcPacketWrapper;
import com.ndy.packet.manager.PacketContext;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;
import com.ndy.worker.scheduler.work.WorkerMakeOutItemScheduler;
import com.ndy.worker.util.InventoryUtility;
import com.ndy.worker.work.WorkFindChestArea;
import com.ndy.worker.work.condition.ChestAreaCondition;
import com.ndy.worker.work.condition.abstraction.AbstractWorkAreaCondition;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class Worker {

    private PacketContext packetContext;
    private SchedulerContext schedulerContext;
    private List<Node> path;

    private boolean isFind = false;
    protected boolean successWorking = true;
    private Inventory workerInventory;

    public Worker(WorkerPacketBuilder builder) {
        packetContext = new PacketContext();
        packetContext.setPackets(builder);

        schedulerContext = new SchedulerContext();

        this.workerInventory = Bukkit.createInventory(null, 54, "Inventory");
    }

    public PacketContext getPacketContext() { return packetContext; }

    public SchedulerContext getSchedulerContext() { return schedulerContext; }

    public NpcPacketWrapper getNpc() { return (NpcPacketWrapper) getPacketContext().getPacket("npc"); }

    public Inventory getWorkerInventory() { return workerInventory; }

    public void setFind(boolean find) { isFind = find; }

    public boolean isFind() { return isFind; }

    public List<Node> getGoal() { return path; }

    public void setGoal(List<Node> path) { this.path = path; }

    public boolean isSuccessWorking() { return successWorking; }

    public void setSuccessWorking(boolean successWorking) { this.successWorking = successWorking; }

    public boolean isFulInventory() { return InventoryUtility.isFull(workerInventory); }

    protected AbstractWork findWork(AbstractWork work, AbstractWorkAreaCondition condition) {
        if(!isSuccessWorking()) {
            work = new WorkFindChestArea();
            condition = new ChestAreaCondition(getNpc().getBukkitPlayer().getLocation());
            condition.setRangeProperty(50);
        }

        work.findWorkArea(this, condition);
        setFind(work.getFinder() != null);

        return work;
    }

    /**
     * @return 만약 작업에 계속 실패할 경우 아이템 정리를 해주는 스케줄러를 반환합니다.
     * */
    public AbstractWorkScheduler getSchedulerBranch() { return isSuccessWorking() ? getWorkScheduler() : new WorkerMakeOutItemScheduler(this); }

    public abstract AbstractWork findWork();
    public abstract boolean doWorking(Object... objects);
    public abstract AbstractWorkScheduler getWorkScheduler();

}
