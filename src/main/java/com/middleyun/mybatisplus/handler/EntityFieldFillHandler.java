package com.middleyun.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.middleyun.mybatisplus.domin.entity.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @title 实体属性填充处理器
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Component
public class EntityFieldFillHandler implements MetaObjectHandler {

    /**
     * 当插入时填充属性
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentDate = new Date(System.currentTimeMillis());
        this.strictInsertFill(metaObject, "createTime", Date.class, currentDate);
        this.strictInsertFill(metaObject, "updateTime", Date.class, currentDate);
    }

    /**
     * 当更新时填充属性
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentDate = new Date(System.currentTimeMillis());
        this.strictInsertFill(metaObject, "updateTime", Date.class, currentDate);
    }
}
