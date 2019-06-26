package com.zephyr.item.service;

import com.zephyr.item.domain.TItem;

public interface ItemService {

	/**
	 * @description 根据商品id获取商品
	 * @param itemId 商品ID
	 */
	public TItem getItem(Integer itemId);
	
	/**
	 * @description 查询商品库存
	 * @param itemId 商品ID
	 * @return
	 */
	public int getItemCounts(Integer itemId);
	
	/**
	 * @description 购买商品成功后减少库存
	 * @param itemId 商品ID
	 * @param buyCounts 购买数量
	 */
	public void displayReduceCounts(Integer itemId, Integer buyCounts);
}
