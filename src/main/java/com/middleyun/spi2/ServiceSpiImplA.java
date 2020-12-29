package com.middleyun.spi2;

public class ServiceSpiImplA implements MySpringSpi {

    public ServiceSpiImplA() {
    }

    @Override
    public void execute() {
        System.out.println("spring ServiceSpiImplA");
    }
}
