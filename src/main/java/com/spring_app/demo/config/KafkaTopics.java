package com.spring_app.demo.config;

public enum KafkaTopics {
    EMAIL_TOPIC("email_topic");

    private final String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
