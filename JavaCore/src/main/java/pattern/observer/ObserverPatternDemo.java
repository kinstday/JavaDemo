package pattern.observer;

/**
 * 观察者模式
 *
 * 支持广播通信
 * 实现松耦合
 * 符合开闭原则
 *
 * 如果观察者过多，通知效率低
 * 可能引起循环依赖
 * 难以跟踪数据变化
 *
 * @author 12
 * Create By 下午2:13
 */
public class ObserverPatternDemo {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();
        NewsChannel channel1 = new NewsChannel("CNN");
        NewsChannel channel2 = new NewsChannel("BBC");

        agency.attach(channel1);
        agency.attach(channel2);

        agency.setNews("Breaking News: Design Patterns are awesome!");
    }
}
