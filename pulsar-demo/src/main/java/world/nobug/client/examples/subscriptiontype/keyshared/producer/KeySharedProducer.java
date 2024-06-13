package world.nobug.client.examples.subscriptiontype.keyshared.producer;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class KeySharedProducer {
    public static void main(String[] args) throws PulsarClientException, InterruptedException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();
        int i = 0;
        while (true) {
            // Send message
            String key = "key" + i % 3;
            byte[] bytes = ("My message " + i++).getBytes();
            producer.newMessage()
                    .key(key)
                    .value(bytes)
                    .send();
            System.out.println("Message sent: " + new String(bytes));
            Thread.sleep(1000);
        }
    }
}
