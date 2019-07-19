package com.demo.topic;

import cn.hutool.core.util.RandomUtil;
import com.demo.util.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TestConsumer {

    private static final String url = "tcp://127.0.0.1:61616";

    private static final String topName = "topic_style";

    private static final String consumerName = "consumer-" + RandomUtil.randomString(5);
    public static void main(String [] args) throws JMSException {

        ActiveMQUtil.checkServer();

        System.out.printf("%s consumer started. %n", consumerName);

        ConnectionFactory factory = new ActiveMQConnectionFactory(url);

        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic(topName);

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println(consumerName + "received message: " + textMessage.getText());
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
