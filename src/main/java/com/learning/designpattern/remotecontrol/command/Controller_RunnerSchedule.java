package com.learning.designpattern.remotecontrol.command;


import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class JobWorkerScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> scheduledFuture;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public JobWorkerScheduler() {
       
    }

    public void start() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = scheduler.scheduleWithFixedDelay(this::runJob, 0, 5, TimeUnit.SECONDS);
        }
    }

    public void stop() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
    }

    public void runJob() {
       
        Instant now = Instant.now();
        /**if (dy hndlng) {
            //insrt
        }**/

        //do process
    }

    private void processBatch(List<String> batch) {
        //update db
        //invalid list
    }
}


// -- JobControllerScheduler.java --
package com.learning.designpattern.remotecontrol.command;

import java.util.concurrent.*;

public class JobControllerScheduler {
    private final ScheduledExecutorService controllerScheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> controllerFuture;
    private final DbSchedulerLockService lockService;
    private final JobWorkerScheduler jobWorkerScheduler;

    public JobControllerScheduler(DbSchedulerLockService lockService,
                                  JobWorkerScheduler jobWorkerScheduler) {
        this.lockService = lockService;
        this.jobWorkerScheduler = jobWorkerScheduler;
    }

    public void start() {
        //is open
        if (controllerFuture == null || controllerFuture.isCancelled()) {
            controllerFuture = controllerScheduler.scheduleWithFixedDelay(() -> {
                if (lockService.tryAcquireLock("market-data-job")) {
                    jobWorkerScheduler.start();
                } else {
                    jobWorkerScheduler.stop();
                }
            }, 0, 30, TimeUnit.SECONDS);
        }
    }

    public void stop() {
        if (controllerFuture != null && !controllerFuture.isCancelled()) {
            controllerFuture.cancel(false);
        }
        jobWorkerScheduler.stop();
    }
}
