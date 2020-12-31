package com.middleyun.mybatisplus.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserQueryDTO {

    private String name;

    private String nickName;

    private Integer age;

    private String address;

}