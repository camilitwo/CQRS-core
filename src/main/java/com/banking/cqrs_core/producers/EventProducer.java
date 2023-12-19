package com.banking.cqrs_core.producers;

import com.banking.cqrs_core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
