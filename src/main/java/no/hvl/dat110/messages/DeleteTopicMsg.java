package no.hvl.dat110.messages;

public class DeleteTopicMsg extends Message {

    private String topic;

    // Message sent from client to delete a topic on the broker
    public DeleteTopicMsg(String user, String topic) {
        super(MessageType.DELETETOPIC, user);
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
        return "DeleteTopicMsg[user=" + getUser() + ", topic=" + topic + "]";
    }
}