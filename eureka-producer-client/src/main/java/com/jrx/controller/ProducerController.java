package com.jrx.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: sunchuanyin
 * @Date: 2019/4/11 11:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {

    /**
     * 打印日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ProducerController.class);
    @Value("${server.port}")
    private String serverport;

    @RequestMapping("/print")
    public String print() {

        logger.info("执行的端口是：{}", serverport);
        return "执行的端口是：" + serverport;
    }
}
