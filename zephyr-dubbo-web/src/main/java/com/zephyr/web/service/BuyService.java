package com.zephyr.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zephyr.item.service.ItemService;
import com.zephyr.order.service.OrderService;
import com.zephyr.web.lock.DistributedLock;

@Service
public class BuyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyService.class);
	
	@Reference
	private ItemService itemService;
	@Reference
	private OrderService orderService;
	@Autowired
	private DistributedLock distributedLock;
	public static final String ZK_PATH = "order";
	
	public boolean orderBuyItem(Integer itemId) {
		int buyCounts = 6;
		// 获取分布式锁
		distributedLock.acquireLock(ZK_PATH);
		// 1、判断库存
		int stockCounts = itemService.getItemCounts(itemId);
		if (stockCounts < buyCounts) {
			LOGGER.info("库存剩余{}件，用户需求量{}件，库存不足，订单创建失败", stockCounts, buyCounts);
			// 释放分布式锁
			distributedLock.releaseLock(ZK_PATH);
			return false;
		}
		// 2、创建订单
		boolean orderCreateSucced = orderService.createOrder(itemId);
		
		// 3、订单创建成功后，扣减库存
		if (orderCreateSucced) {
			LOGGER.info("订单创建成功......");
			itemService.displayReduceCounts(itemId, buyCounts);
			// 释放分布式锁
			distributedLock.releaseLock(ZK_PATH);
			return true;
		} else {
			LOGGER.info("订单创建失败......");
			// 释放分布式锁
			distributedLock.releaseLock(ZK_PATH);
			return false;
		}
	}
}
