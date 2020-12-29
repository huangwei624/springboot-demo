package com.middleyun.spi;

public class ServiceSpiImplA implements ServiceSpi {
    @Override
    public void execute() {
        System.out.println("ServiceSpiImplA");
    }
}
