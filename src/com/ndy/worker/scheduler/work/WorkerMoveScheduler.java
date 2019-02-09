package com.ndy.worker.scheduler.work;

import com.ndy.astar.node.Node;
import com.ndy.packet.impl.NpcMovePacketWrapper;
import com.ndy.packet.impl.NpcPacketJumpWrapper;
import com.ndy.packet.manager.PacketContext;
import com.ndy.util.Particle;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.builder.WorkerPacketBuilder;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;

import java.util.List;

public class WorkerMoveScheduler extends AbstractWorkScheduler {

    private int idx = -1;
    private List<Node> path;

    private boolean initialize = true;

    public WorkerMoveScheduler(Worker worker) {
        super(worker,10L, 3L, false);
    }

    public WorkerMoveScheduler(Worker worker, List<Node> nodes) {
        super(worker, 10L, 3L, false);

        this.path = nodes;
        this.initialize = false;
    }

    @Override
    public void initialize() {
        if(super.getWorker().isFind() && initialize) {
            path = super.getWork().getFinder().getPath();
            System.out.println("path:" + path);

            super.getWorker().getPacketContext().send("npc");
        }
    }

    public boolean isCancelable() {
        if(idx >= path.size() || !super.getWorker().isFind()) {
            super.cancelTask();
            next();
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        idx++;

        if(isCancelable()) return;

        Node node = path.get(idx);

        PacketContext context = getWorker().getPacketContext();

        update(node.getLocation());

        context.send("move", "jump");

        try{
            Particle.send(node.getLocation().clone().add(0, 0.5, 0), 0.1f, 0.1f, 0.1f, 0, 1, EnumParticle.FIREWORKS_SPARK);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void update(Location loc) {
        Worker worker = super.getWorker();

        WorkerPacketBuilder builder = WorkerPacketBuilder.builder()
                .setPacket("move", new NpcMovePacketWrapper(worker.getNpc(), loc))
                .setPacket("jump", new NpcPacketJumpWrapper(worker.getNpc().getNpc()));

        worker.getPacketContext().setPackets(builder);

        worker.getNpc().getNpc().setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    @Override
    public void next() {
        Worker worker = getWorker();
        SchedulerContext context = worker.getSchedulerContext();

        context.setScheduler(worker.getSchedulerBranch());
        context.startScheduler();
    }
}
