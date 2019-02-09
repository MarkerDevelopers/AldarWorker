package com.ndy.worker.scheduler.abstraction;

import com.ndy.WorkerPlugin;
import com.ndy.worker.abstraction.AbstractWork;
import com.ndy.worker.abstraction.Worker;
import com.ndy.worker.scheduler.task.INextTaskRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class AbstractWorkScheduler implements Runnable, INextTaskRunnable {

    private int taskId = -1;
    private Worker worker;
    private AbstractWork work;
    private long tickTime, delayTickTime;
    private boolean isDelayedTask;

    public AbstractWorkScheduler(Worker worker, long delayTickTime, long tickTime, boolean isDelayedTask) {
        this.worker = worker;
        this.work = worker.findWork();
        this.tickTime = tickTime;
        this.isDelayedTask = isDelayedTask;
        this.delayTickTime = delayTickTime;
    }

    public void start() {
        initialize();
        Plugin plugin = WorkerPlugin.getPlugin(WorkerPlugin.class);

        if(isDelayedTask) taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, tickTime);
        else taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, delayTickTime, tickTime);
    }

    public void cancelTask() {
        if(isRunning()) Bukkit.getScheduler().cancelTask(taskId);
    }

    public boolean isRunning() { return taskId != -1; }

    protected AbstractWork getWork() { return work; }
    protected Worker getWorker() { return worker; }

    public abstract void initialize();

}
