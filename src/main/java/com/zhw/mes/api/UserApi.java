package com.zhw.mes.api;

import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface UserApi {
    @RequestLine("GET http://www.baidu.com")
    String getUser();
}
