package com.middleyun.spi2;

public class ServiceSpiImplB implements MySpringSpi {
    @Override
    public void execute() {
        System.out.println("spring ServiceSpiImplB");
    }
}
