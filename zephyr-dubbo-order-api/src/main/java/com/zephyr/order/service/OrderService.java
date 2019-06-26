package com.zephyr.order.service;

import com.zephyr.order.domain.TOrder;

public interface OrderService {

	/**
	 * @description 根据订单id查询订单
	 * @param orderId 订单ID
	 * @return
	 */
	public TOrder getOrder(String orderId);
	
	/**
	 * @description  下订单
	 * @param itemId 商品ID
	 * @return
	 */
	public boolean createOrder(Integer itemId);
}
