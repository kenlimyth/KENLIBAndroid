package com.kenlib.mqtt;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.kenlib.util.Util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQTT
 *
 * @author KEN 2016
 */
public class MqttUtil {

    static String host = "tcp://114.224.199.21:1883";
    static String userName = "admin";
    static String passWord = "public";

    static MqttClient client;
    static MqttConnectOptions options;

    static ScheduledExecutorService scheduler;
    static int chongliancishu = 5;
    static boolean isShowLog = true;
    public static Ido todo;

    /**
     * 连接
     *
     * @param clientid
     */
    public static void connect(String clientid) {
        try {

            if (client != null && client.isConnected()) {
                return;
            }

            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);

            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, clientid, new MemoryPersistence());
            // 设置回调
            client.setCallback(new MqttCallback() {

                public void deliveryComplete(IMqttDeliveryToken token) {
                    // publish后会执行到这里
                    //log("deliveryComplete---------" + token.isComplete());
                }

                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    // subscribe后得到的消息会执行到这里面

                    log("ClientId=" + client.getClientId() + " Message="
                            + new String(message.getPayload(), "UTF-8")
                            + "  QoS=" + message.getQos());

                    if (todo != null) {
                        todo.todo(message);
                    }

                    message.clearPayload();
                }

                public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                    // 连接丢失后，一般在这里面进行重连
                    log("ClientId=" + client.getClientId() + "连接丢失");
                    startReconnect();
                }

            });

            client.connect(options);
            log("ClientId=" + client.getClientId() + "连接成功 !");
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * 发布
     *
     * @param topicName
     * @param msg
     * @param qos
     */
    public static void publish(String topicName, String msg, int qos) {

        try {

            MqttMessage message = new MqttMessage(msg.getBytes("utf-8"));
            message.setQos(qos);
            client.publish(topicName, message);

        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * 订阅
     *
     * @param topicName
     * @param qos
     */
    public static void subscribe(String topicName, int qos) {

        try {

            log("ClientId=" + client.getClientId() + " Subscribing=\"" + topicName + "\" qos=" + qos);
            client.subscribe(topicName, qos);

        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * 取消订阅
     *
     * @param topicName
     */
    public static void unsubscribe(String topicName) {

        try {

            client.unsubscribe(topicName);

        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * 关闭连接
     */
    private static void disconnect() {
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 间隔重连
     */
    private static void startReconnect() {
        try {

            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {

                public void run() {
                    if (!client.isConnected()) {

                        if (chongliancishu > 0) {
                            connect(Util.getGUID());
                        }
                        chongliancishu--;
                    }
                }
            }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // TODO: handle exception
            disconnect();
        }
    }

    public static void setDo(Ido to) {
        todo = to;
    }

    /**
     * 日志打印
     *
     * @param message
     */
    private static void log(String message) {
        if (isShowLog) {
            Date dNow = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("yyyy.MM.dd hh:mm:ss ");

            System.out.println(ft.format(dNow) + message);
        }
    }

    /**
     * 外部操作接口
     *
     * @author Administrator
     */
    public static interface Ido {

        void todo(MqttMessage message);
    }

    //调用
    public static void main(String[] args) throws Exception {

//        MqttUtil.connect(Util.getGUID());
        MqttUtil.connect("android_test");
        MqttUtil.subscribe("foo", 2);
        MqttUtil.publish("foo", "aa", 2);
        MqttUtil.setDo(new Ido() {

            public void todo(MqttMessage message) {
                // TODO Auto-generated method stub
                System.out.print("*");

            }
        });
    }

}
