package Subscriber;

import Entities.Message;

public interface Subscriber {
    String getId();
    void onMessage(Message message);
}
