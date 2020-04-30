package com.mhz.lib.statis;


import com.mhz.lib.Message;

// 真实的实现类
public class Al implements Message {

    @Override
    public void message() {
        System.out.println("真是的去提供服务");
    }
}
