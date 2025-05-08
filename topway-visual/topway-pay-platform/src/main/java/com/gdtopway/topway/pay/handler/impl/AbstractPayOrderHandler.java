/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.gdtopway.topway.pay.handler.impl;

import com.gdtopway.topway.common.sequence.sequence.Sequence;
import com.gdtopway.topway.pay.entity.PayChannel;
import com.gdtopway.topway.pay.entity.PayGoodsOrder;
import com.gdtopway.topway.pay.entity.PayTradeOrder;
import com.gdtopway.topway.pay.handler.PayOrderHandler;
import com.gdtopway.topway.pay.mapper.PayGoodsOrderMapper;
import com.gdtopway.topway.pay.utils.ChannelPayApiConfigKit;
import com.gdtopway.topway.pay.utils.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lengleng
 * @date 2019-05-31
 */
public abstract class AbstractPayOrderHandler implements PayOrderHandler {

	@Autowired
	private PayGoodsOrderMapper goodsOrderMapper;

	@Autowired
	private Sequence paySequence;

	/**
	 * 创建商品订单
	 * @param goodsOrder 商品订单
	 * @return
	 */
	@Override
	public void createGoodsOrder(PayGoodsOrder goodsOrder) {
		goodsOrder.setPayOrderId(Long.parseLong(paySequence.nextNo()));
		goodsOrder.setStatus(OrderStatusEnum.INIT.getStatus());
		goodsOrderMapper.insert(goodsOrder);
	}

	/**
	 * 调用入口
	 * @return
	 */
	@Override
	public Object handle(PayGoodsOrder payGoodsOrder) {
		PayChannel payChannel = preparePayParams();
		ChannelPayApiConfigKit.put(payChannel);

		createGoodsOrder(payGoodsOrder);
		PayTradeOrder tradeOrder = createTradeOrder(payGoodsOrder);
		Object result = pay(payGoodsOrder, tradeOrder);
		updateOrder(payGoodsOrder, tradeOrder);
		// 情况ttl
		ChannelPayApiConfigKit.remove();
		return result;
	}

}
