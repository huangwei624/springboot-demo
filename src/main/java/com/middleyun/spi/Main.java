package com.middleyun.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<ServiceSpi> spiServiceLoader = ServiceLoader.load(ServiceSpi.class);
        Iterator<ServiceSpi> iterator = spiServiceLoader.iterator();

        while (iterator.hasNext()) {
            iterator.next().execute();
        }
    }
}
