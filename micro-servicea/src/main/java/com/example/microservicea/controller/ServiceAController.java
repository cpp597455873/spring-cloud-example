package com.example.microservicea.controller;

import com.example.microservicea.service.MicroServiceaProvider;
import com.example.microservicea.service.model.CountVo;
import com.example.microserviceb.MicroServicebProvider;
import com.example.microserviceb.model.ServerInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ServiceAController implements MicroServiceaProvider {

    @Autowired
    MicroServicebProvider microServicebProvider;

    @GetMapping("/api/micro/service/a/count")
    public CountVo count() {
        CountVo countVo = new CountVo();
        countVo.setCount(new Random().nextInt());
        return countVo;
    }

    @GetMapping("/api/micro/service/a/remoteCount")
    public com.example.microserviceb.model.CountVo remoteCount() {
        return microServicebProvider.count();
    }

    @GetMapping("/api/micro/service/a/serverInfoVo")
    public ServerInfoVo serverInfoVo() {
        return microServicebProvider.serverInfoVo();
    }
}
