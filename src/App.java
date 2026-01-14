import Subscriber.Subscriber;
import Subscriber.PrintSubscriber;
import Entities.Message;
import Subscriber.LoggingSubscriber;

public class App {
    public static void main(String[] args) throws Exception {
        PubSubService pubsubService = PubSubService.getInstance();

        Subscriber temperatureSubscriber = new PrintSubscriber("temperatureSubscriber");
        Subscriber humiditySubscriber = new PrintSubscriber("humiditySubscriber");
        Subscriber waterSubscriber = new LoggingSubscriber("waterSubscriber");

        final String WATER_TOPIC = "WATER";
        final String HUMIDITY_TOPIC = "HUMIDITY";
        final String TEMPERATURE_TOPIC = "WEATHER";
        final String ENERGY_TOPIC = "ENERGY";

        pubsubService.createTopic(TEMPERATURE_TOPIC);
        pubsubService.createTopic(HUMIDITY_TOPIC);
        pubsubService.createTopic(WATER_TOPIC);
        pubsubService.createTopic(ENERGY_TOPIC);

        pubsubService.subscribe(TEMPERATURE_TOPIC, temperatureSubscriber);
        pubsubService.subscribe(WATER_TOPIC, waterSubscriber);
        pubsubService.subscribe(HUMIDITY_TOPIC, humiditySubscriber);

        pubsubService.publish(WATER_TOPIC, new Message("Water level is 20ft"));

        pubsubService.publish(HUMIDITY_TOPIC, new Message("Humidity is 70%"));

        pubsubService.publish(TEMPERATURE_TOPIC, new Message("Temperature is 39C"));

        pubsubService.publish(ENERGY_TOPIC, new Message("Energy is 31kWh"));

        Thread.sleep(500);

        System.out.println("\n--- Unsubscribing a user and re-publishing ---");

        pubsubService.unSubscribe(TEMPERATURE_TOPIC, temperatureSubscriber);

        pubsubService.publish(TEMPERATURE_TOPIC, new Message("Temperature is 39C"));

        Thread.sleep(500);

        pubsubService.shutdown();
    }
}
