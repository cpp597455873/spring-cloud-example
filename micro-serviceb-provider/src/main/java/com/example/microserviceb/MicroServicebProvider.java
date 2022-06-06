package com.example.microserviceb;

import com.example.microserviceb.model.CountVo;
import com.example.microserviceb.model.ServerInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "MicroServicebProvider", name = "MICROSERVICE-B")
public interface MicroServicebProvider {
    @GetMapping("/api/micro/service/b/count")
    CountVo count();

    @GetMapping("/api/micro/service/b/serverInfoVo")
    ServerInfoVo serverInfoVo();
}
