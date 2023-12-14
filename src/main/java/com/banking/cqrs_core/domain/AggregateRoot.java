package com.banking.cqrs_core.domain;

import com.banking.cqrs_core.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
@Getter
@Setter
public abstract class AggregateRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNew) {
        try {
            var method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch (NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("No apply method found for {0}", event.getClass().getName()));
        }catch(Exception e){
            logger.log(Level.SEVERE, MessageFormat.format("Error applying {0} on {1}", event.getClass().getName(), this.getClass().getName()));
        }finally {
            if(isNew){
                this.changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event){
        this.applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> history){
        history.forEach(event -> this.applyChange(event, false));
    }
}
