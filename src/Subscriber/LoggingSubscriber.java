package Subscriber;

import Entities.Message;

public class LoggingSubscriber implements Subscriber{
    private final String id;

    public LoggingSubscriber(String id){
        this.id = id;
    }

    @Override
    public String getId(){
        return this.id;
    }

    @Override
    public void onMessage(Message message){
        System.out.printf("[Subscriber %s] received message '%s'%n", id, message.getPayload());
    }
}
