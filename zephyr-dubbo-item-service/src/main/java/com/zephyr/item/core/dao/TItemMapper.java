package com.zephyr.item.core.dao;

import org.apache.ibatis.annotations.Param;

import com.zephyr.item.domain.TItem;

public interface TItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TItem record);

    int insertSelective(TItem record);

    TItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TItem record);

    int updateByPrimaryKey(TItem record);
    
    int reduceCounts(@Param("id") Integer id, @Param("buyCounts") Integer buyCounts);
}