package com.ndy.worker.scheduler.work;

import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;

public class MiningWorkScheduler extends AbstractWorkScheduler {

    public MiningWorkScheduler(Worker worker) {
        super(worker, 0L, 100L, false);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void run() {
        Worker worker = getWorker();

        boolean success = worker.doWorking();

        if(!success) next();
    }

    @Override
    public void next() {
        cancelTask();

        Worker worker = super.getWorker();
        SchedulerContext context = worker.getSchedulerContext();

        context.setScheduler(worker.getSchedulerBranch());
        context.startScheduler();
    }
}
