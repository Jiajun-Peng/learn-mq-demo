package world.nobug.orderedmessages;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerExample {
    private static final Logger logger = LoggerFactory.getLogger(ProducerExample.class);

    public static void main(String[] args) throws ClientException {
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        // 此处为示例，实际使用时请替换为真实的 Proxy 地址和端口
        String endpoint = "localhost:8081";
        // 消息发送的目标Topic名称，需要提前创建。
        String topic = "TestOrderedTopic";
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();
        for (int i = 0; i < 10; i++) {
            sendMessage(provider, topic, producer, i);
        }
    }

    private static void sendMessage(ClientServiceProvider provider, String topic, Producer producer, int i)
            throws ClientException {
        // 普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys("messageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag("messageTag")
                // 消息体。
                .setBody(("messageBody" + i).getBytes())
                .build();
        // 发送消息，需要关注发送结果，并捕获失败等异常。
        SendReceipt sendReceipt = producer.send(message);
        logger.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
    }
}
