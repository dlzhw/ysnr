package com.zhw.mes.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.zhw.mes.mapper")
@EnableTransactionManagement
public class DataBaseConfiguration {
}
