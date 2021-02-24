package com.middleyun.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.middleyun.build.User;
import com.middleyun.mybatisplus.domin.dto.SysUserDTO;
import com.middleyun.mybatisplus.domin.dto.SysUserQueryDTO;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import com.middleyun.mybatisplus.mapper.UserMapper;
import com.middleyun.mybatisplus.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加一个实体
     * @param sysUserDTO
     * @return
     */
    @Override
    public Integer save(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        return userMapper.insert(sysUser);
    }

    /**
     * 通过id删除一个实体
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    /**
     * 通过id集合删除多个实体
     * @param ids
     * @return
     */
    @Override
    public Integer deleteByIds(Collection<Long> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    /**
     * 更新实体信息
     * @param user
     * @return
     */
    @Override
    public Integer update(SysUserDTO user) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        return userMapper.updateById(sysUser);
    }

    /**
     * 根据id 查询用户信息
     * @param id
     * @return
     */
    @Override
    public SysUser getById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 获取所有的实体
     * @return
     */
    @Override
    public List<SysUser> listAll() {
        return userMapper.selectList(null);
    }

    /**
     * 通过查询条件查询实体
     * @param sysUserQueryDTO
     * @return
     */
    @Override
    public List<SysUser> getUserByUserQueryDTO(SysUserQueryDTO sysUserQueryDTO) {
        return userMapper.selectList(buildQueryWrapper(sysUserQueryDTO));
    }

    @Override
    public Page<SysUser> pageList(Integer pageNum, Integer pageSize, SysUserQueryDTO sysUserQueryDTO) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = new OrderItem("age", false);
        page.setOrders(Arrays.asList(orderItem));
        return userMapper.selectPage(page, buildQueryWrapper(sysUserQueryDTO));
    }

    /**
     * 批量添加用户信息
     * @param users 用户信息
     * @return 添加成功的数量
     */
    @Override
    public Long batchSaveUser(List<SysUser> users) {
        return userMapper.batchInsertUser(users);
    }

    /**
     * 模拟事务，删除并创建用户
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteAndCreate(Long id) {
        SysUser sysUser = userMapper.selectById(id);
        if (sysUser == null) {
            return true;
        }
        userMapper.deleteById(id);
        if (System.currentTimeMillis() > 0) {
            throw new RuntimeException();
        }
        SysUser user = SysUser.builder().name("李四").nickName("孙悟空").age(100).build();
        userMapper.insert(user);
        return true;
    }

    /**
     * 批量保存用户信息
     * @param users
     * @return
     */


    /**
     * 构建动态查询条件
     * @param sysUserQueryDTO
     * @return
     */
    private QueryWrapper<SysUser> buildQueryWrapper(SysUserQueryDTO sysUserQueryDTO) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sysUserQueryDTO.getName())) {
            queryWrapper.likeLeft("name", sysUserQueryDTO.getName());
        }
        if (!StringUtils.isEmpty(sysUserQueryDTO.getNickName())) {
            queryWrapper.likeRight("nick_name", sysUserQueryDTO.getNickName());
        }
        if (sysUserQueryDTO.getAge() != null) {
            queryWrapper.le("age", sysUserQueryDTO.getAge());
        }
        if (sysUserQueryDTO.getAddress() != null) {
            queryWrapper.likeLeft("address", sysUserQueryDTO.getAddress());
        }
        return queryWrapper;
    }
}
