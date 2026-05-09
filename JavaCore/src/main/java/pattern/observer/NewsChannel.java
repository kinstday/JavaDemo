package pattern.observer;

/**
 * @author 12
 * Create By 下午2:13
 */
public class NewsChannel implements Observer {
    private String name;

    public NewsChannel(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received news: " + message);
    }
}