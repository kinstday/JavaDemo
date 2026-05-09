package pattern.factory;

/**
 * 工厂模式
 *
 * 创建对象和使用对象分离
 * 易于扩展新类型
 * 遵循开闭原则
 *
 * 类的数量增加，复杂度提升
 * 违反里氏替换原则
 * 工厂类职责过重
 *
 * @author 12
 * Create By 下午2:08
 */
public class FactoryPatternDemo {
    public static void main(String[] args) {
        Shape circle = ShapeFactory.createShape("circle");
        Shape rectangle = ShapeFactory.createShape("rectangle");

        circle.draw();      // 输出: Drawing Circle
        rectangle.draw();   // 输出: Drawing Rectangle
    }
}
