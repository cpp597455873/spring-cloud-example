package com.example.microservicea.service;

import com.example.microservicea.service.model.CountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "MICROSERVICE-A", name = "MicroServiceaProvider")
public interface MicroServiceaProvider {
    @GetMapping("/api/micro/service/a/count")
    CountVo count();
}
