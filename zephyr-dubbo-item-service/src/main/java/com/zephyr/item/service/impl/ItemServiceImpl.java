package com.zephyr.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zephyr.item.core.dao.TItemMapper;
import com.zephyr.item.domain.TItem;
import com.zephyr.item.service.ItemService;

@com.alibaba.dubbo.config.annotation.Service
@org.springframework.stereotype.Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TItemMapper itemMapper;

	@Override
	public TItem getItem(Integer itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public int getItemCounts(Integer itemId) {
		TItem item = itemMapper.selectByPrimaryKey(itemId);
		return item.getCounts();
	}

	@Override
	public void displayReduceCounts(Integer itemId, Integer buyCounts) {
		itemMapper.reduceCounts(itemId, buyCounts);
	}

}
