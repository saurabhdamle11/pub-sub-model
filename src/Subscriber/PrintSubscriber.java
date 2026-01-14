package Subscriber;

import Entities.Message;

public class PrintSubscriber implements Subscriber{
    private final String id;

    public PrintSubscriber(String id){
        this.id = id;
    }

    @Override
    public String getId(){
        return this.id;
    }

    @Override
    public void onMessage(Message message){
        System.out.printf("!!! [ALERT - %s] : '%s' !!!%n", id, message.getPayload());
    }
}
