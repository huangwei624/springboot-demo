package com.middleyun.mybatisplus.domin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.middleyun.mybatisplus.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @title 用户实体
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser extends BaseEntity{

    private String name;

    private String nickName;

    private Integer age;

    private String address;

    private String password;

}
