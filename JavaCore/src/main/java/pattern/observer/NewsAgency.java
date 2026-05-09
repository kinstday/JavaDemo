package pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 12
 * Create By 下午2:11
 */
public class NewsAgency implements Subject  {
    private List<Observer> observers = new ArrayList<>();
    private String news;


    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}
