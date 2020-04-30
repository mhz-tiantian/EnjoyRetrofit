package com.mhz.lib.statis;

import com.mhz.lib.Message;

//真实的实现类
public class Lucy implements Message {
    @Override
    public void message() {
        System.out.println("36D, 服务一流");
    }
}
