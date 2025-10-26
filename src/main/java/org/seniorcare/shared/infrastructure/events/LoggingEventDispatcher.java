package org.seniorcare.shared.infrastructure.events;

import org.seniorcare.shared.application.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggingEventDispatcher implements EventDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(LoggingEventDispatcher.class);

    @Override
    public void dispatch(List<Object> events) {
        if (events != null) {
            events.forEach(this::dispatch);
        }
    }

    @Override
    public void dispatch(Object event) {
        if (event != null) {
            logger.info("--> Dispatching Domain Event: {}", event);
        }
    }
}