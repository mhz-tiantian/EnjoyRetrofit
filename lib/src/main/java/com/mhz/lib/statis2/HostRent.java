package com.mhz.lib.statis2;

// 真实的房东
public class HostRent implements Rent {
    @Override
    public void rent() {
        System.out.println("房租出租出去了.......");
    }
}
