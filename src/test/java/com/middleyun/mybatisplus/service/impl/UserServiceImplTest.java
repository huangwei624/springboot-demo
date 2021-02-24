package com.middleyun.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.middleyun.mybatisplus.domin.dto.SysUserDTO;
import com.middleyun.mybatisplus.domin.dto.SysUserQueryDTO;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import com.middleyun.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        for (int i = 0; i < 10; i++) {
            SysUserDTO sysUserDTO = SysUserDTO.builder()
                    .name("张三" + i).age(new Random().nextInt(50) + 20)
                    .address(i % 2 == 0 ? "上海市" : "北京市")
                    .nickName(i % 2 == 0 ? "会飞的鱼" : "会飞的猪")
                    .password("123456").build();
            userService.save(sysUserDTO);
        }
    }

    @Test
    void deleteById() {
        userService.deleteById(11L);
    }

    @Test
    void deleteByIds() {
        List<Long> ids = Arrays.asList(10L, 11L, 12L);
        Integer count = userService.deleteByIds(ids);
        System.out.println(count);
    }

    @Test
    void update() {
        // 更新一个被删除的数据
        SysUserDTO sysUserDTO = SysUserDTO.builder().id(10L)
                .name("张三").age(120).address("")
                .nickName("").password("000000").build();
        Integer count = userService.update(sysUserDTO);
        System.out.println(count);
        // 更新一个正常的数据
        SysUser sysUser = userService.getById(17L);
        SysUserDTO sysUserDTO2 = SysUserDTO.builder().id(sysUser.getId())
                .name("张三").age(120).address("河南省").version(sysUser.getVersion())
                .nickName("蚂蚁上树").password("000000").build();
        count = userService.update(sysUserDTO2);
        System.out.println(count);
    }

    @Test
    void getById() {
        SysUser user = userService.getById(11L);
        System.out.println(user);
    }

    @Test
    void listAll() {
        List<SysUser> sysUsers = userService.listAll();
        System.out.println(sysUsers);
    }

    @Test
    void getUserByUserQueryDTO() {
        SysUserQueryDTO userQueryDTO = SysUserQueryDTO.builder().age(50).nickName("会飞").build();
        List<SysUser> users = userService.getUserByUserQueryDTO(userQueryDTO);
        System.out.println(users);
    }

    @Test
    void pageList() {
        SysUserQueryDTO userQueryDTO = SysUserQueryDTO.builder().age(80).build();
        Page<SysUser> page = userService.pageList(2, 3, userQueryDTO);
        System.out.println("total: " + page.getTotal());
        System.out.println("elements: " + page.getRecords());
        System.out.println("pages: " + page.getPages());
    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    void testTransactionManger() {
        System.out.println(transactionManager.getClass().getName());
        TransactionStatus transaction = transactionManager.getTransaction(
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        try {
            // todo 数据库 crud
            SysUser user = SysUser.builder().name("张三").age(0).nickName("会飞的鱼").address("河南")
                    .password("123").build();
            user.setDeleteFlag("0");
            user.setVersion(0L);
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(user, sysUserDTO);
            userService.save(sysUserDTO);

            // 模拟异常
            int a = 2 / 0;

            // 提交事务
            transactionManager.commit(transaction);
        } catch (TransactionException e) {
            // 业务出现异常，回滚
            transactionManager.rollback(transaction);
        }
    }

    @Test
    void deleteAndCreate() {
        userService.deleteAndCreate(18L);
    }

    /**
     * 批量插入50w 条记录
     */
    @Test
    void batchInsertUser() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("最大堆内存：" + (heapMemoryUsage.getMax() / 1024 / 1024));
        System.out.println("使用堆内存：" + (heapMemoryUsage.getUsed() / 1024 / 1024));
        System.out.println("初始堆内存：" + (heapMemoryUsage.getInit() / 1024 / 1024));

        ArrayList<SysUser> users = new ArrayList<>();
        IntStream.rangeClosed(700002, 900000).forEach(i -> {
            SysUser user = SysUser.builder().name(UUID.randomUUID().toString()).age(i % 100)
                    .nickName(UUID.randomUUID().toString()).address("河南" + i)
                    .password("123").build();
            user.setDeleteFlag("0");
            user.setVersion(0L);
            users.add(user);
        });
        System.out.println("数据装载完成，准备添加数据库..");
        Long start = System.currentTimeMillis();
        Long count = userService.batchSaveUser(users);
        Long end = System.currentTimeMillis();
        System.out.println("添加记录数量：" + count + ", 耗时：" + (end - start));
    }

    /**
     * 批量插入50w 条记录
     */
    @Test
    void batchInsertUserUseThread() throws InterruptedException {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("最大堆内存：" + (heapMemoryUsage.getMax() / 1024 / 1024));
        System.out.println("使用堆内存：" + (heapMemoryUsage.getUsed() / 1024 / 1024));
        System.out.println("初始堆内存：" + (heapMemoryUsage.getInit() / 1024 / 1024));

        // 保存每个事务执行的状态
        List<Boolean> flags = new CopyOnWriteArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Long start = System.currentTimeMillis();

        CountDownLatch main = new CountDownLatch(1);
        CountDownLatch child = new CountDownLatch(3);
        // 是否需要提交
        AtomicReference<Boolean> ok = new AtomicReference<>(true);

        executorService.execute(() -> {
            // 开启事务
            TransactionStatus transaction = transactionManager.getTransaction(
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
            try {
                ArrayList<SysUser> users = new ArrayList<>();
                IntStream.rangeClosed(1, 100000).forEach(i -> {
                    SysUser user = SysUser.builder().name(UUID.randomUUID().toString()).age(i % 100)
                            .nickName(UUID.randomUUID().toString()).address("河南" + i)
                            .password("123").build();
                    user.setDeleteFlag("0");
                    user.setVersion(0L);
                    users.add(user);
                });
                userService.batchSaveUser(users);
                child.countDown();
                main.await();
                if (ok.get()) {
                    transactionManager.commit(transaction);
                } else  {
                    transactionManager.rollback(transaction);
                }
            } catch (Exception e) {
                flags.add(false);
                child.countDown();
                transactionManager.rollback(transaction);
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            // 开启事务
            TransactionStatus transaction = transactionManager.getTransaction(
                    new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
            try {
                ArrayList<SysUser> users = new ArrayList<>();
                IntStream.rangeClosed(0, 100000).forEach(i -> {
                    SysUser user = SysUser.builder().name(UUID.randomUUID().toString()).age(i % 100)
                            .nickName(UUID.randomUUID().toString()).address("河南" + i)
                            .password("123").build();
                    user.setDeleteFlag("0");
                    user.setVersion(0L);
                    users.add(user);
                });
                Long count = userService.batchSaveUser(users);
                child.countDown();
                main.await();
                if (ok.get()) {
                    transactionManager.commit(transaction);
                } else  {
                    transactionManager.rollback(transaction);
                }
            } catch (Exception e) {
                flags.add(false);
                child.countDown();
                transactionManager.rollback(transaction);
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            // 开启事务
            TransactionStatus transaction = transactionManager.getTransaction(
                    new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
            try {
                ArrayList<SysUser> users = new ArrayList<>();
                IntStream.range(1, 100000).forEach(i -> {
                    SysUser user = SysUser.builder().name(UUID.randomUUID().toString()).age(i % 100)
                            .nickName(UUID.randomUUID().toString()).address("河南" + i)
                            .password("123").build();
                    user.setDeleteFlag("0");
                    user.setVersion(0L);
                    users.add(user);
                });
                Long count = userService.batchSaveUser(users);
                child.countDown();
                main.await();
                if (ok.get()) {
                    transactionManager.commit(transaction);
                } else  {
                    transactionManager.rollback(transaction);
                }
            } catch (Exception e) {
                flags.add(false);
                child.countDown();
                transactionManager.rollback(transaction);
                e.printStackTrace();
            }
        });

        child.await();

        flags.forEach(item -> {
            if(!item) {
                ok.set(false);
            }
        });

        main.countDown();
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }
}