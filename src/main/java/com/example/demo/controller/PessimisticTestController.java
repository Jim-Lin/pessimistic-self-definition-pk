package com.example.demo.controller;

import com.example.demo.service.PessimisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequestMapping("/pessimistic")
@RestController
public class PessimisticTestController {
    private final PessimisticService pessimisticService;

    public PessimisticTestController(PessimisticService pessimisticService) {
        this.pessimisticService = pessimisticService;
    }

    @GetMapping("/test")
    public void test() {
        lockAndAdd();
    }

    @GetMapping("/testDeferredResult")
    public DeferredResult<?> testDeferredResult() {
        DeferredResult<?> result = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            lockAndAdd();
            result.setResult(null);
        });

        return result;
    }

    private void lockAndAdd() {
        boolean tryLock = pessimisticService.tryLock();
        try {
            int retry = 1;
            while (!tryLock && retry < 6) {
                Thread.sleep((long) Math.pow(2, retry) * 200);
                retry += 1;
                tryLock = pessimisticService.tryLock();
            }

            if (!tryLock) {
                log.info("cannot get lock!!!");

                return;
            }

            pessimisticService.add();
        } catch (InterruptedException e) {
            throw new RuntimeException("sleep");
        } finally {
            if (tryLock) {
                pessimisticService.releaseLock();
            }
        }
    }
}
