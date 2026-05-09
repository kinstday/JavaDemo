package org.example.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Test {
    public static void main(String[] args) throws Exception {
        ServiceLoader<IRegistry> serviceLoader = ServiceLoader.load(IRegistry.class);
        Iterator<IRegistry> iterator = serviceLoader.iterator();
        while (iterator != null && iterator.hasNext()){
            IRegistry registry = iterator.next();
            System.out.println("class: " + registry.getClass().getName());
            registry.register("SPI");
        }
    }
}
