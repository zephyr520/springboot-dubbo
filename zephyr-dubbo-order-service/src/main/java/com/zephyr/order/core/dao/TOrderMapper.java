package com.zephyr.order.core.dao;

import com.zephyr.order.domain.TOrder;

public interface TOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);
}