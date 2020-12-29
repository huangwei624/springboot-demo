package com.middleyun.spi;

public class ServiceSpiImplB implements ServiceSpi {

    public ServiceSpiImplB() {
    }

    @Override
    public void execute() {
        System.out.println("ServiceSpiImplB");
    }
}
