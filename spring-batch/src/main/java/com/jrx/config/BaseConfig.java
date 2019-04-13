package com.jrx.config;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * 抽取公共使用的对象引用
 *
 * @Author: sunchuanyin
 * @Date: 2019/4/10 18:02
 * @Version 1.0
 */
public class BaseConfig {

    /**
     * 创建job对象工厂
     */
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    /**
     * 创建Step对象工厂
     */
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /**
     * 数据源
     */
    @Autowired
    public DataSource dataSource;
}
