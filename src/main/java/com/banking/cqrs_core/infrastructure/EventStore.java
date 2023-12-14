package com.banking.cqrs_core.infrastructure;

import com.banking.cqrs_core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void save(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);

}
