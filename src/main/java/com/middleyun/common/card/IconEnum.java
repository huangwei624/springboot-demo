package com.middleyun.common.card;

/**
 * 屏幕上的三种图标枚举
 * 前方、左拐、右拐
 * icon-direction-right: 右方向箭头，icon-direction-left: 左方向箭头，icon-direction-straight: 直行方向箭头
 */
public enum  IconEnum {
    RIGHT("icon-direction-right"),
    LEFT("icon-direction-left"),
    STRAIGHT("icon-direction-straight");

    private String direction;

    IconEnum(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
