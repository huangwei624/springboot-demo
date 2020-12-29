package com.middleyun.common.card;


import lombok.Data;

import java.util.List;

/**
 * 控制卡信息
 *  ## 屏幕编号
 *  screenNo = ABC
 *  ## 屏幕宽度
 *  screenWidth = 64
 *  ## 屏幕高度
 *  screenHeight = 64
 *  ## 屏幕所处位置
 *  screenAddress = 上海市浦东新区
 *  ## 屏幕的分块，默认分成四个块, 由四块小屏幕拼接成一块较大的屏幕，下面是对这几块小屏幕的相关配置
 *  screen.block = [{}]
 */
@Data
public class CardController {
    // 屏幕编号
    private String screenNo;
    // 屏幕宽度
    private Integer screenWidth;
    // 屏幕高度
    private Integer screenHeight;
    // 屏幕位置
    private String screenAddress;
    // 屏幕的四块区域
    private List<ScreenBlock> screenBlocks;
}
