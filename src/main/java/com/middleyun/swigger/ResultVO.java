package com.middleyun.swigger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultVO<T> {

    private Integer code;
    private String msg;
    private T data;

    private ResultVO(Integer code) {
        this.code = code;
    }

    private ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(200, "成功", data);
    }

    public static <T> ResultVO<T> success(String msg) {
        return new ResultVO<>(200, msg);
    }

    public static <T> ResultVO<T> fail(String msg) {
        return new ResultVO<>(500, msg);
    }

}
