package com.middleyun.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title sys_user 表操作接口
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

}
