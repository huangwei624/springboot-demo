package com.middleyun.swigger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
public class UserController {

    @ApiOperation("添加用户")
    @PostMapping("/user")
    public ResultVO<UserVO> insertUser(@RequestBody UserDTO userDTO) {
        UserVO userVO = UserVO.builder().nickName("会飞的乌龟").phone("13625635245").build();
        return ResultVO.success(userVO);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/user/{id}")
    public ResultVO<UserVO> deleteUser(@ApiParam("用户id") @PathVariable Integer id) {
        return ResultVO.fail("删除失败");
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user/{id}")
    public ResultVO<UserVO> getUser(@ApiParam("用户id") @PathVariable Integer id) {
        UserVO userVO = UserVO.builder().nickName("会飞的乌龟").phone("13625635245").build();
        return ResultVO.success(userVO);
    }

}
