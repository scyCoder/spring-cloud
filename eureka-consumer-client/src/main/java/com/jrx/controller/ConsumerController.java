package com.jrx.controller;

import com.jrx.producer.ProducerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: sunchuanyin
 * @Date: 2019/4/11 11:55
 * @Version 1.0
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    /**
     * 打印日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private ProducerClient producerClient;

    @RequestMapping("producer/invokeProducerPrint")
    public String invokeProducerPrint() {

        logger.info("执行了：{}", "hello");
        return producerClient.print();
    }
}
