package com.middleyun.juc;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
class Order implements Delayed {

    public Order(String orderId, BigDecimal amount, Date createTime) {
        this.orderId = orderId;
        this.amount = amount;
        this.createTime = createTime;
    }

    // 订单编号
    private String orderId;

    // 订单金额
    private BigDecimal amount;

    // 订单创建时间
    private Date createTime;

    // 订单未支付过期时间
    private long orderExpire = 1000 * 20;

    @Override
    public long getDelay(TimeUnit unit) {
        return orderExpire - (System.currentTimeMillis() - createTime.getTime());
    }

    /**
     * 配合延迟队列, 将Order 放入延迟队列后，Order 会按照剩余延迟时间从小到大排序，
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS));
    }
}

public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Order> orders = new DelayQueue<>();
        orders.put(new Order("10001", new BigDecimal("100.25"), new Date()));
        TimeUnit.SECONDS.sleep(4);
        orders.put(new Order("10002", new BigDecimal("1025.6"), new Date(System.currentTimeMillis()-8*1000)));
        TimeUnit.SECONDS.sleep(4);
        orders.put(new Order("10003", new BigDecimal("1022.21"), new Date()));
        System.out.println(orders);

        while (orders.size() != 0) {
            Order take = orders.take();
            System.out.println(take);
        }
    }
}

