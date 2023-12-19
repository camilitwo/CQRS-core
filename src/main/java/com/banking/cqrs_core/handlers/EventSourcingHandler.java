package com.banking.cqrs_core.handlers;

import com.banking.cqrs_core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
}
