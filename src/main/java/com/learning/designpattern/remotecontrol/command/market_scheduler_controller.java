package com.example.market.scheduler;

import com.example.market.client.MarketDataClient;
import com.example.market.client.MarketDataResponse;
import com.example.market.client.SymbolPrice;
import com.example.market.repo.CompanyRepository;
import com.example.market.service.PriceDataService;
import com.example.market.util.BatchUtils;
import com.example.market.util.MarketTimeUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class JobWorkerScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> scheduledFuture;

    private final CompanyRepository companyRepo;
    private final PriceDataService priceService;
    private final MarketDataClient marketDataClient;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final Set<String> invalidSymbols = ConcurrentHashMap.newKeySet();
    private List<String> cachedSymbols = new ArrayList<>();
    private Instant lastFetchTime = Instant.MIN;

    public JobWorkerScheduler(CompanyRepository companyRepo,
                              PriceDataService priceService,
                              MarketDataClient marketDataClient) {
        this.companyRepo = companyRepo;
        this.priceService = priceService;
        this.marketDataClient = marketDataClient;
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
        if (!MarketTimeUtils.isMarketOpenNow()) return;

        Instant now = Instant.now();
        if (Duration.between(lastFetchTime, now).getSeconds() >= 60) {
            cachedSymbols = companyRepo.getAllSymbols();
            priceService.insertMissingSymbols(cachedSymbols);
            lastFetchTime = now;
        }

        List<String> toQuery = cachedSymbols.stream()
            .filter(s -> !invalidSymbols.contains(s))
            .map(s -> s + "-USA")
            .toList();

        List<List<String>> batches = BatchUtils.partition(toQuery, 100);
        for (List<String> batch : batches) {
            executor.submit(() -> processBatch(batch));
        }
    }

    private void processBatch(List<String> batch) {
        MarketDataResponse response = marketDataClient.fetchPrices(batch);
        Set<String> returned = response.getValidPrices().stream()
            .map(p -> p.symbol)
            .collect(Collectors.toSet());

        List<String> original = batch.stream()
            .map(s -> s.replace("-USA", ""))
            .toList();

        List<String> missing = original.stream()
            .filter(s -> !returned.contains(s))
            .toList();

        priceService.updatePrices(response.getValidPrices());
        invalidSymbols.addAll(missing);
    }
}


// -- JobControllerScheduler.java --
package com.example.market.scheduler;

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
