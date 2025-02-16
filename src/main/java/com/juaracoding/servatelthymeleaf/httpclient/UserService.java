package com.juaracoding.servatelthymeleaf.httpclient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "localhost:8080/")
public interface UserService {
}
