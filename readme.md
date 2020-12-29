## ElasticSearch
依赖高级API
```xml
<!--es-->
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
    <version>7.6.2</version>
</dependency>
```
高级API客服端工具
```text
@Bean
public RestHighLevelClient restHighLevelClient(ElasticSearchProperties elasticSearchProperties) {
    return new RestHighLevelClient(RestClient.builder(
            new HttpHost(elasticSearchProperties.getHost(),
                    elasticSearchProperties.getPort(), elasticSearchProperties.getScheme())));
}
```


## juc 并发编程
- Condition 线程的准确唤醒
```text
private final Lock lock = new ReentrantLock();
private final Condition producer = lock.newCondition();
private final Condition consumer = lock.newCondition();
...
producer.await();
...
producer.signal();
```
- CAS、原子类（java.util.concurrent.atomic.*）、ReenTrantLock、AQS、ReadWriteLock

- 队列（同步队列，有界队列，无界队列），拓展：双端队列，延迟队列等
```text
// 同步队列, 该队列不会存储数据，最好使用 put 和 take 这组api
SynchronousQueue<Integer> queue = new SynchronousQueue<>();

// 无界队列
LinkedBlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();

// 有界队列
ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(4);

// 3组方法
// add 和 remove
// put 和 take
// offer 和 poll

// 延迟队列
DelayQueue<Order> orders = new DelayQueue<>();

```
- CountDownLatch、 CyclicBarrier、Semaphore 

- ConcurrentHashMap、 CopyOnWriteArrayList、CopyOnWriteArraySet 并发安全集合

- 伪异步Future 和 真异步 CompletableFuture

- Stream并行流、ForkJoin等

- 四大函数接口 Function(函数性)、Consumer(消费型)、Supplier(供应型)、Predicate（断言型）


## minio oss 存储服务
https://docs.min.io/cn/

## SPI 机制

## swigger2 接口开发管理工具



