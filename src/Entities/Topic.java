package Entities;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import Subscriber.Subscriber;

public class Topic {
    private final String name;
    private final Set<Subscriber> subscribers;
    private final ExecutorService deliveryExecutor;

    public Topic(String name, ExecutorService deliveryExecutor){
        this.name = name;
        this.deliveryExecutor = deliveryExecutor;
        this.subscribers = new CopyOnWriteArraySet<>();
    }

    public String getName(){
        return this.name;
    }

    public void addSubscriber(Subscriber sub){
        this.subscribers.add(sub);
    }

    public void removeSubscriber(Subscriber sub){
        this.subscribers.remove(sub);
    }

    public void publish(Message message){
        for(Subscriber subscriber:this.subscribers){
            deliveryExecutor.submit(() -> {
                try{
                    subscriber.onMessage(message);
                }
                catch(Exception e){
                    System.err.println("Error delivering message to subscriber " + subscriber.getId() + ": " + e.getMessage());
                }
            });
        }
    }
}
