package no.hvl.dat110.messages;

public class SubscribeMsg extends Message {

    private String topic;

    // Message sent from client to subscribe to a topic
    public SubscribeMsg(String user, String topic) {
        super(MessageType.SUBSCRIBE, user);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "SubscribeMsg[user=" + getUser() + ", topic=" + topic + "]";
    }
}