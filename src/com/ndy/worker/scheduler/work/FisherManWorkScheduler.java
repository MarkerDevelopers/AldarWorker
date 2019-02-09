package com.ndy.worker.scheduler.work;

import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;
import com.ndy.worker.scheduler.context.SchedulerContext;

public class FisherManWorkScheduler extends AbstractWorkScheduler {

    /**
     * 낚시꾼 스케줄러
     * */
    public FisherManWorkScheduler(Worker worker) {
        super(worker, 0L, 500L, false);
    }

    @Override
    public void initialize() { }

    @Override
    public void run() {
        Worker worker = super.getWorker();

        boolean success = worker.doWorking();

        if(!success) next();
    }

    @Override
    public void next() {
        cancelTask();

        Worker worker = super.getWorker();
        SchedulerContext context = worker.getSchedulerContext();

        context.setScheduler(new WorkerMoveScheduler(worker));
        context.startScheduler();
    }
}
