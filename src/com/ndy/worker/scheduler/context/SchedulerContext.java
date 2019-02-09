package com.ndy.worker.scheduler.context;

import com.ndy.worker.scheduler.abstraction.AbstractWorkScheduler;

public class SchedulerContext {

    private AbstractWorkScheduler scheduler;

    public void setScheduler(AbstractWorkScheduler scheduler) {
        if(this.scheduler != null) stopScheduler();

        this.scheduler = scheduler;
    }

    public void startScheduler() {
        scheduler.start();
    }

    public void stopScheduler() {
        if(scheduler.isRunning()) scheduler.cancelTask();
    }

    public AbstractWorkScheduler getScheduler() { return scheduler; }
    public boolean isRunning() { return scheduler.isRunning(); }

}
