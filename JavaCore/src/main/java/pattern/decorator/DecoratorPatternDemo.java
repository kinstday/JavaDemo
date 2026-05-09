package pattern.decorator;

/**
 * 装配器模式
 *
 * 比继承更灵活
 * 可以动态添加功能
 * 遵循开闭原则
 *
 * 会产生很多小对象
 * 排错困难
 * 设计较复杂
 *
 * @author 12
 * Create By 下午2:41
 */
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + ": $" + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + ": $" + coffee.getCost());

        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + ": $" + coffee.getCost());

        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + ": $" + coffee.getCost());
    }
}