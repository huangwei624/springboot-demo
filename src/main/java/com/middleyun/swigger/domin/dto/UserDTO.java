package com.middleyun.swigger.domin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @ApiModelProperty("用户昵称")
    private String nickName;

    private String phone;

    @ApiModelProperty("登录密码")
    private String password;
}
