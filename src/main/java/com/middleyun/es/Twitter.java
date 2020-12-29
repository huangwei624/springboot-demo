package com.middleyun.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Twitter {

    private Integer id;
    private String title;
    private String content;
    // 阅读量
    private Integer pageViews;
    // 点赞量
    private Integer startNums;
    private String createUser;
    private Date createTime;
}
