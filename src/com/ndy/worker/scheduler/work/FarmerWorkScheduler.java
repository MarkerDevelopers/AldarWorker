package com.ndy.worker.scheduler.work;

import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;

public class FarmerWorkScheduler extends AbstractWorkScheduler {

    public FarmerWorkScheduler(Worker worker) {
        super(worker, 0L, 100L, true);
        worker.setFind(true);
    }

    @Override
    public void initialize() { }

    @Override
    public void run() {
        Worker worker = getWorker();

        boolean success = worker.doWorking();

        if(!success) next();
        else nextBehavior();

    }

    private void nextBehavior() {
        cancelTask();

        Worker worker = getWorker();
        SchedulerContext context = worker.getSchedulerContext();

        context.setScheduler(new WorkerMoveScheduler(worker, worker.getGoal()));
        context.startScheduler();
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
