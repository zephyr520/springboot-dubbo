package com.zephyr.order.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zephyr.order.core.dao.TOrderMapper;
import com.zephyr.order.domain.TOrder;
import com.zephyr.order.service.OrderService;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private TOrderMapper orderMapper;

	@Override
	public TOrder getOrder(String orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean createOrder(Integer itemId) {
		// 创建订单
		String oid = UUID.randomUUID().toString().replaceAll("-", "");
		TOrder order = new TOrder();
		order.setId(oid);
		order.setOrderNo(oid);
		order.setItemId(itemId);
		orderMapper.insertSelective(order);
		LOGGER.info("订单创建成功");

		return true;
	}

}
