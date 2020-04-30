package com.mhz.lib.agent;


import com.mhz.lib.Message;

/**
 *
 *代理類
 */
public class Agent implements Message {

    private final Message message;

    public Agent(Message message) {
        this.message = message;
    }

    @Override
    public void message() {
        before();
        message.message();
        after();

    }

    private void after() {
        System.out.println("满意度调查");
    }

    private void before() {
        System.out.println("提供各种服务");
    }
}
