import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Subscriber.Subscriber;
import Entities.Message;

import Entities.Topic;

public class PubSubService {
    private static PubSubService instance;
    private final ExecutorService deliveryExecutor;
    private final Map<String, Topic> topicRegistry;

    private PubSubService(){
        this.topicRegistry = new ConcurrentHashMap<>();
        deliveryExecutor = Executors.newCachedThreadPool();
    }

    public static PubSubService getInstance(){
        if(instance==null){
            instance = new PubSubService();
        }
        return instance;
    }

    public void createTopic(String name){
        topicRegistry.putIfAbsent(name, new Topic(name, deliveryExecutor));
        System.out.println("Topic " + name + " created");
    }

    public void subscribe(String topicName, Subscriber subscriber){
        Topic topic = topicRegistry.get(topicName);
        if(topic==null){
            throw new IllegalArgumentException("Topic not found"+topicName);
        }
        topic.addSubscriber(subscriber);
        System.out.println("Subscriber '" + subscriber.getId() + "' subscribed to topic: " + topicName);
    }

    public void unSubscribe(String topicName, Subscriber subscriber){
        Topic topic = topicRegistry.get(topicName);
        if(topic==null){
            throw new IllegalArgumentException("Topic not found"+topicName);
        }
        topic.removeSubscriber(subscriber);
        System.out.println("Subscriber '" + subscriber.getId() + "' unsubscribed from topic: " + topicName);
    }

    public void publish(String topicName, Message message){
        System.out.println("Publishing message to topic "+topicName);
        Topic topic = topicRegistry.get(topicName);
        if(topic==null){
            throw new IllegalArgumentException("Topic not found"+topicName);
        }
        topic.publish(message);
    }

    public void shutdown(){
        System.out.println("PubSubService shutting down...");
        deliveryExecutor.shutdown();
        try{
            if(!deliveryExecutor.awaitTermination(60, TimeUnit.SECONDS)){
                deliveryExecutor.shutdownNow();
            }
        }
        catch(InterruptedException e){
            deliveryExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("PubSubService shutdown complete.");
    }
}
