package com.middleyun.spi2;

import java.util.Set;

public interface ServiceSpi {

    void execute(Set<Class<?>> classes);

}
