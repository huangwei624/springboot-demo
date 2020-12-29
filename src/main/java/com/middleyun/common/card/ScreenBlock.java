package com.middleyun.common.card;

import lombok.Data;
import lombok.ToString;

/**
 * ## 左上角的x坐标
 * leftTopX: 0,
 * ## 左上角的y坐标
 * leftTopY: 0,
 * ## 右下角的x坐标
 * rightBottomX: 64,
 * ## 右下角的y坐标
 * rightBottomY: 16,
 * ## 是否为动态区， 0 ：节目， 1： 动态区
 * isDynamicArea: 0,
 * ## 该块屏幕的内容， 如果该屏幕为节目，该属性生效，否则不生效
 * contentTemplate: 车位引导
 * ## 该块屏幕的字体
 * contentFont: 宋体,
 * ## 该快屏幕的字体颜色
 * contentFontColor: 255,
 * ## 该快屏幕的字体大小
 * contentFontSize: 12,
 * ## 该块屏幕是否支持图标 0: 不支持， 1： 支持
 * enableIncludeIcon: 1,
 * ## 该块屏幕的图标名称, icon-direction-right: 右方向箭头，icon-direction-left: 左方向箭头，icon-direction-straight: 直行方向箭头
 * iconName: icon-direction-right
 */
@Data
public class ScreenBlock {
     private Integer leftTopX;
     private Integer leftTopY;
     private Integer rightBottomX;
     private Integer rightBottomY;
     private Boolean isDynamicArea;
     private String contentTemplate;
     private String contentFont;
     private Integer contentFontColor;
     private Integer contentFontSize;
     private Boolean enableIncludeIcon;
     private String iconName;
}
