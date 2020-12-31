package com.middleyun.mybatisplus.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2020/12/31
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInsertDTO {

    @NotEmpty(message = "用户名不能为空")
    private String name;

    @NotEmpty(message = "用户昵称不能为空")
    private String nickName;

    @NotNull(message = "用户年龄不能为空")
    private Integer age;

    @NotEmpty(message = "用户家庭地址不能为空")
    private String address;

    @NotEmpty(message = "用户密码不能为空")
    private String password;

}
