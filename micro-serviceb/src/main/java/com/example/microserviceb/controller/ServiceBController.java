package com.example.microserviceb.controller;

import com.example.microserviceb.MicroServicebProvider;
import com.example.microserviceb.model.CountVo;
import com.example.microserviceb.model.ServerInfoVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController implements MicroServicebProvider {
    @Value("${server.port}")
    Integer port;

    @GetMapping("/api/micro/service/b/count")
    public CountVo count() {
        CountVo countVo = new CountVo();
        countVo.setCount(port);
        return countVo;
    }

    @GetMapping("/api/micro/service/b/serverInfoVo")
    public ServerInfoVo serverInfoVo() {
        ServerInfoVo info = new ServerInfoVo();
        info.setPort(port);
        info.setCode(200);
        return info;
    }
}
