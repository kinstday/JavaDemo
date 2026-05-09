package org.example;

/**
 * @author 12
 * Create By 上午10:10
 */
public class CandyCat extends Cat implements Animals {

    @Override
    public void eat() {
        System.out.println("Need to eat!");
    }

    @Override
    public void food() {
        System.out.println("CandyCat food is Candy!");
    }
}
