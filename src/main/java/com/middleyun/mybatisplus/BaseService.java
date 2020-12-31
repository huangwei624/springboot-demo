package com.middleyun.mybatisplus;

import java.util.Collection;
import java.util.List;

/**
 * 业务抽象接口
 * @param <T>
 * @param <TD>
 */
public interface BaseService<T, TD> {

    /**
     * 添加一个实体
     * @param t
     * @return
     */
    Integer save(TD t);

    /**
     * 通过id删除一个实体
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 通过id集合删除多个实体
     * @param ids
     * @return
     */
    Integer deleteByIds(Collection<Long> ids);

    /**
     * 更新实体信息
     * @param td
     * @return
     */
    Integer update(TD td);

    /**
     * 根据id 查询用户信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 获取所有的实体
     * @return
     */
    List<T> listAll();

}
