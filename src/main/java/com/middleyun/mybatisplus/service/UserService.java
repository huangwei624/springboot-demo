package com.middleyun.mybatisplus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.middleyun.build.User;
import com.middleyun.mybatisplus.BaseService;
import com.middleyun.mybatisplus.domin.dto.SysUserDTO;
import com.middleyun.mybatisplus.domin.dto.SysUserQueryDTO;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @title 用户管理业务处理
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
public interface UserService extends BaseService<SysUser, SysUserDTO> {

    List<SysUser> getUserByUserQueryDTO(SysUserQueryDTO sysUserQueryDTO);

    Page<SysUser> pageList(Integer pageNum, Integer pageSize, SysUserQueryDTO sysUserQueryDTO);

    Boolean deleteAndCreate(Long id);

}
