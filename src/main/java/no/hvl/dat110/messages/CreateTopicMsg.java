package no.hvl.dat110.messages;

public class CreateTopicMsg extends Message {

    private String topic;

    // Message sent from client to create a topic on the broker
    public CreateTopicMsg(String user, String topic) {
        super(MessageType.CREATETOPIC, user);
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
        return "CreateTopicMsg[user=" + getUser() + ", topic=" + topic + "]";
    }
}