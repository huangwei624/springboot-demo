package com.middleyun.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.middleyun.mybatisplus.domin.dto.SysUserDTO;
import com.middleyun.mybatisplus.domin.dto.SysUserQueryDTO;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import com.middleyun.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    @Test
    void deleteAndCreate() {
        userService.deleteAndCreate(18L);
    }
}