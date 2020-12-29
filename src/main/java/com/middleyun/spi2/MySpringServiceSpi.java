package com.middleyun.spi2;

import javax.servlet.annotation.HandlesTypes;
import java.util.ArrayList;
import java.util.Set;

@HandlesTypes(MySpringSpi.class)
public class MySpringServiceSpi implements ServiceSpi{

    @Override
    public void execute(Set<Class<?>> classes) {
        ArrayList<MySpringSpi> mySpringSpis = new ArrayList<>();
        if (classes != null) {
            classes.forEach(item -> {
                try {
                    mySpringSpis.add((MySpringSpi) item.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }

        mySpringSpis.forEach(MySpringSpi::execute);
    }
}
