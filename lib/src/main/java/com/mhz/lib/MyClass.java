package com.mhz.lib;

import com.mhz.lib.agent.Agent;
import com.mhz.lib.statis.Al;

public class MyClass {

    public static void main(String[] agrs) {
        Message message = new Al();
        Agent agent = new Agent(message);
        agent.message();
    }
}
