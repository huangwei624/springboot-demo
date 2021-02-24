package com.middleyun.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @title sys_user 表操作接口
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

    /**
     * 批量插入用户信息
     * @param users 用户信息
     * @return 插入成功的个数
     */
    Long batchInsertUser(List<SysUser> users);

}
