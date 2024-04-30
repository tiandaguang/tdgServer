package com.boot.demo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @DATE 2019/11/4 20:33
 * @Description 测试请求类
 * @Author Tian Daguang
 **/
@RestController
@RequestMapping("pay")
public class PayController {
    @PostMapping("send")
    public String send() throws Exception {

        Map<String, String> mps = new HashMap<>();
        mps.put("name", "tianda");
        return JSON.toJSONString(mps);
    }

}
