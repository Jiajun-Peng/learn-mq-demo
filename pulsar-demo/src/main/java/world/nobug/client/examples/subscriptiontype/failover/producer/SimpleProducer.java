package world.nobug.client.examples.subscriptiontype.failover.producer;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class SimpleProducer {
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
            producer.send(("My message" + i++).getBytes());
            Thread.sleep(1000);
        }
    }
}
