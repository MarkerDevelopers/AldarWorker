package com.ndy.worker.scheduler.task;

public interface INextTaskRunnable {
    /** Task가 끝난 후 다음 할 일이 있을경우 구현합니다. */
    public void next();
}
