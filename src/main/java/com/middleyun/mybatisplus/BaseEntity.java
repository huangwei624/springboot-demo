package com.middleyun.mybatisplus;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @title  实体类公共父类
 * @description
 * @author huangwei
 * @createDate 2020/12/30
 * @version 1.0
 */
@Data
public class BaseEntity {

    @TableId(type = IdType.AUTO)
    protected Long id;

    /**
     * 乐观锁字段
     */
    @Version
    protected Long version;

    /**
     * 逻辑删除字段
     */
    @TableLogic(value = "0", delval = "1")
    protected String deleteFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
}
