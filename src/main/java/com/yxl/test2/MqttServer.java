package com.yxl.test2;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MqttServer {
    /**
     * 代理服务器ip地址
     */
    public static final String MQTT_BROKER_HOST = "tcp://127.0.0.1:61613";

    /**
     * 订阅标识
     */
    public static final String MQTT_TOPIC = "test2";

    private static String userName = "admin";
    private static String password = "password";

    /**
     * 客户端唯一标识
     */
    public static final String MQTT_CLIENT_ID = "android_server_xiasuhuei32";
    private static MqttTopic topic;
    private static MqttClient client;

    public static void main(String... args) {
        // 推送消息
        MqttMessage message = new MqttMessage();
        try {
            client = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);

            topic = client.getTopic(MQTT_TOPIC);

            message.setQos(1);
            message.setRetained(false);
            message.setPayload("message from server222222".getBytes());
            client.connect(options);

            while (true) {
                MqttDeliveryToken token = topic.publish(message);
                token.waitForCompletion();
                System.out.println("已经发送222");
                Thread.sleep(10000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
