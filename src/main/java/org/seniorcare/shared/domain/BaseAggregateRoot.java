package org.seniorcare.shared.domain; // Ou o pacote compartilhado que você preferir

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseAggregateRoot {
    
    private final transient List<Object> domainEvents = new ArrayList<>();


    protected void registerEvent(Object event) {
        if (event != null) {
            this.domainEvents.add(event);
        }
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}