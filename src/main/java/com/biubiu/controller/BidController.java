package com.biubiu.controller;

import com.biubiu.mapper.BidMapper;
import com.biubiu.po.Bid;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author 张海彪
 * @create 2019-02-21 10:52
 */
@RestController
@RequestMapping("/rest")
@Slf4j
public class BidController {

    private static final int COUNT = 100;

    private static final ExecutorService service = Executors.newFixedThreadPool(COUNT);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BidMapper bidMapper;

    @GetMapping("/bid/{screenId}")
    public void bid(@PathVariable("screenId") String screenId) {
        //CyclicBarrier模拟高并发
        CyclicBarrier barrier = new CyclicBarrier(COUNT);
        RBucket<Long> rBucket = redissonClient.getBucket("price_" + screenId);
        if (!rBucket.isExists()) {
            rBucket.set(0L);
        }
        for (int i = 0; i < COUNT; i++) {
            service.execute(() -> {
                try {
                    long price = (long) (Math.random() * 100000000);
                    System.out.println(Thread.currentThread().getName() + "准备叫价: " + price);
                    Thread.sleep(100);
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "开始叫价--->");
                    addBid(screenId, price);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void addBid(String screenId, long price) {
        RLock lock = redissonClient.getLock(screenId);
        try {
            boolean res = lock.tryLock(1, 1, TimeUnit.SECONDS);
            if (res) {
                try {
                    System.out.println(Thread.currentThread().getName() + "获得锁--->");
                    RBucket<Long> rBucket = redissonClient.getBucket("price_" + screenId);
                    long highPrice = rBucket.get();
                    if (highPrice < price) {
                        rBucket.set(price);
                        doAddBid(screenId, price);
                        System.out.println(Thread.currentThread().getName() + "叫价成功！！！");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "叫价失败！！！");
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    private void doAddBid(String screenId, long price) {
        Bid bid = Bid.builder().screenId(screenId).price(price).creator("zhb").editor("zhb").build();
        bidMapper.insertSelective(bid);
    }

}
