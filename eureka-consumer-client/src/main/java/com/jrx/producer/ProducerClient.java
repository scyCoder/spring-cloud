package com.jrx.producer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 调用提供者中的方法
 *
 * @Author: sunchuanyin
 * @Date: 2019/4/11 11:53
 * @Version 1.0
 */
@FeignClient(value = "producer-client")
public interface ProducerClient {

    @RequestMapping("producer/print")
    String print();
}
