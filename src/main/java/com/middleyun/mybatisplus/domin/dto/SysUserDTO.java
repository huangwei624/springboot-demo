package com.middleyun.mybatisplus.domin.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO {

    private Long id;

    private String name;

    private String nickName;

    private Integer age;

    private String address;

    private String password;

    private Long version;

    private String deleteFlag;

    private Date createTime;

    private Date updateTime;

}

