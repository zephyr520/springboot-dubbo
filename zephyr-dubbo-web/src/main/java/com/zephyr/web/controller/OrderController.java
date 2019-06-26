package com.zephyr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zephyr.common.web.ApiResult;
import com.zephyr.web.service.BuyService;

@RestController
public class OrderController {

	@Autowired
	private BuyService buyService;
	
	@GetMapping("/buy/{itemId}")
	public ApiResult<Boolean> buy(@PathVariable Integer itemId) {
		return new ApiResult<>(buyService.orderBuyItem(itemId));
	}
}
