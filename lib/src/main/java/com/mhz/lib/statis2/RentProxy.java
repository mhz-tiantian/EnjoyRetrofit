package com.mhz.lib.statis2;

import com.mhz.lib.Message;

/**
 * 租房的中介
 */
public class RentProxy implements Rent {
    private final Rent rent;

    public RentProxy(Rent rent) {
        this.rent = rent;
    }

    private  void seeHouse() {
        System.out.println("带客户来看房子");
    }
    private void fare() {
        System.out.println("收中介费");
    }
    @Override
    public void rent() {
        seeHouse();
        rent.rent();
        fare();

    }
}
